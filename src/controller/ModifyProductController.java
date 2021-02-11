/**
 * @author Tyler Brown
 */
package controller;

import java.io.IOException;
import java.util.Optional;

import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseEvent;
import javafx.stage.Stage;
import model.*;

import static javafx.collections.FXCollections.observableArrayList;

/**
 * This class is used to control all user interactions that occur with the Modify Product screen.
 */
public class ModifyProductController {

    //Used to set the stage and scene
    Stage stage;
    Parent scene;

    //Create an Observable table to add temporary Part objects to for the bottom table
    private ObservableList<Part> associatedTable = observableArrayList();

    @FXML
    private TextField ModProdIDTxtFld;

    @FXML
    private TextField ModProdNameTxtFld;

    @FXML
    private TextField ModProdInvTxtFld;

    @FXML
    private TextField ModProdPriceTxtFld;

    @FXML
    private TextField ModProdSearchTxtFld;

    @FXML
    private TextField ModProdMaxTxtFld;

    @FXML
    private TextField ModProdMinTxtFld;

    @FXML
    private TableView<Part> ModProdAddTblView;

    @FXML
    private TableColumn<Part, Integer> ModProdAddTblPartIDCol;

    @FXML
    private TableColumn<Part, String> ModProdAddTblPartNameCol;

    @FXML
    private TableColumn<Part, Integer> ModProdAddTblInvLevelCol;

    @FXML
    private TableColumn<Part, Double> ModProdAddTblPCCol;

    @FXML
    private TableView<Part> ModProdRemoveTblView;

    @FXML
    private TableColumn<Part, Integer> ModProdRemoveTblPartIDCol;

    @FXML
    private TableColumn<Part, String> ModProdRemoveTblPartNameCol;

    @FXML
    private TableColumn<Part, Integer> ModProdRemoveTblInvLevelCol;

    @FXML
    private TableColumn<Part, Double> ModProdRemoveTblPCCol;

    @FXML
    private Button ModProdAddBtn;

    @FXML
    private Button ModProdSaveBtn;

    @FXML
    private Button ModProdCancelBtn;

    @FXML
    private Button ModProdRemovePartBtn;

    /**
     * This method adds a Part from the top table to the bottom, Associated Parts, table.
     */
    @FXML //Adds selected Part from top table to the Associated Parts table, bottom table
    void modProductAddPart(MouseEvent event) throws IOException {
        //Adds Part from the top table, all Parts, to the bottom table, Associated Parts
        Part associatedPart = ModProdAddTblView.getSelectionModel().getSelectedItem();

        //Add the selected Part from the top table to the bottom table's Observable list
        associatedTable.add(associatedPart);
    }

    /**
     * This method returns the user to the Main Menu.
     */
    @FXML //Returns the user to the Main Menu
    void modProductCancel(MouseEvent event) throws IOException {
        //Clear the Associated Parts table prior to returning to the Main Menu
        associatedTable.clear();

        //Returns the user to the Main Menu
        stage = (Stage)((Button)event.getSource()).getScene().getWindow();
        scene = FXMLLoader.load(getClass().getResource("/view/MainMenu.fxml"));
        stage.setScene(new Scene(scene));
        stage.show();
    }

    /**
     * This method removes a Part from the bottom, Associated Parts, table.
     */
    @FXML //Removes a Part from the Associated Parts table
    void modProductRemovePart(MouseEvent event) throws IOException {
        //Adds Part from the top table, all Parts, to the bottom table, Associated Parts
        Part deletePart = ModProdAddTblView.getSelectionModel().getSelectedItem();

        //Check with user to make sure selected Part should be deleted, if yes then delete
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION, "Are you sure you want to delete " +
                "Part: " + deletePart.getName() + "?");
        Optional<ButtonType> result = alert.showAndWait();
        if (result.isPresent() && result.get() == ButtonType.OK) {
            associatedTable.remove(deletePart);
        }
    }

    /**
     * This method updates the selected Product in the Inventory class and returns the user to the Main Menu.
     */
    @FXML
    void modProductSaveProduct(MouseEvent event) throws IOException {
        for (Product product : Inventory.getAllProducts()) {
            if (product.getId() == Integer.parseInt(ModProdIDTxtFld.getText())) {

                String newName = ModProdNameTxtFld.getText();
                double newPrice = Double.parseDouble(ModProdPriceTxtFld.getText());
                int newStock = Integer.parseInt(ModProdInvTxtFld.getText());
                int newMin = Integer.parseInt(ModProdMinTxtFld.getText());
                int newMax = Integer.parseInt(ModProdMaxTxtFld.getText());

                //Chain of if-else if -else statements that checks for errors
                if (newPrice < 0.0) {
                    Alert incorrectValues = new Alert(Alert.AlertType.ERROR);
                    incorrectValues.setTitle("Error Pop Up");
                    incorrectValues.setContentText("Please make sure Price value is positive or equal to zero");
                    incorrectValues.showAndWait();

                    ModProdPriceTxtFld.clear();
                }
                else if (newMin < 0) {
                    Alert incorrectValues = new Alert(Alert.AlertType.ERROR);
                    incorrectValues.setTitle("Error Pop Up");
                    incorrectValues.setContentText("Please make sure Min value is positive or equal to zero");
                    incorrectValues.showAndWait();

                    ModProdMinTxtFld.clear();
                }
                else if (newMax < 0) {
                    Alert incorrectValues = new Alert(Alert.AlertType.ERROR);
                    incorrectValues.setTitle("Error Pop Up");
                    incorrectValues.setContentText("Please make sure Max value is positive or equal to zero");
                    incorrectValues.showAndWait();

                    ModProdMaxTxtFld.clear();
                }
                else if (newMin > newMax) {
                    Alert incorrectValues = new Alert(Alert.AlertType.ERROR);
                    incorrectValues.setTitle("Error Pop Up");
                    incorrectValues.setContentText("Please make sure Min value is less than Max value");
                    incorrectValues.showAndWait();

                    ModProdMinTxtFld.clear();
                    ModProdMaxTxtFld.clear();
                }
                else if (newStock > newMax || newStock < newMin){
                    Alert incorrectValues = new Alert(Alert.AlertType.ERROR);
                    incorrectValues.setTitle("Error Pop Up");
                    incorrectValues.setContentText("Please make sure Inventory value is between Max and Min");
                    incorrectValues.showAndWait();

                    ModProdInvTxtFld.clear();
                }
                else {
                    product.setName(newName);
                    product.setPrice(newPrice);
                    product.setStock(newStock);
                    product.setMin(newMin);
                    product.setMax(newMax);

                    //If there are Parts in Associated Parts table, add them to the Products Associated Parts list
                    if (associatedTable.size() > 0) {
                        //Add the Associated Part to the Products Associated Parts Table
                        for (Part part : ModProdRemoveTblView.getItems()) {
                            product.addAssociatedPart(part);
                        }

                        //Update the Product
                        Inventory.updateProduct(Inventory.getAllProducts().indexOf(product), product);

                        //Clear the Associated Parts table prior to returning to the Main Menu
                        associatedTable.clear();

                        //Return the user to the Main Menu and update Product table to include new Product
                        stage = (Stage)((Button)event.getSource()).getScene().getWindow();
                        scene = FXMLLoader.load(getClass().getResource("/view/MainMenu.fxml"));
                        stage.setScene(new Scene(scene));
                        stage.show();
                    }
                    else {
                        //Update the Product
                        Inventory.updateProduct(Inventory.getAllProducts().indexOf(product), product);

                        //Clear the Associated Parts table prior to returning to the Main Menu
                        associatedTable.clear();

                        //Return the user to the Main Menu and update Product table to include new Product
                        stage = (Stage)((Button)event.getSource()).getScene().getWindow();
                        scene = FXMLLoader.load(getClass().getResource("/view/MainMenu.fxml"));
                        stage.setScene(new Scene(scene));
                        stage.show();
                    }
                }
            }
        }
    }

    /**
     * This method searches the Parts table for the desired object, first using a name match, then Id.
     */
    @FXML
    void searchParts(KeyEvent event) throws IOException {
        String desiredPart = ModProdSearchTxtFld.getText();

        //Search by name (String data type first)
        ObservableList<Part> searchedPart = Inventory.lookupPart(desiredPart);
        ModProdAddTblView.setItems(searchedPart);

        //If name was not found, list of Parts is checked by ID
        if (searchedPart.size() == 0) {
            int id = Integer.parseInt(desiredPart);
            Inventory.lookupPart(id);
        }
    }

    /**
     * This method initializes the Modify Product screen.
     *
     * The final error that was fixed.
     * This runtime error repeated displayed a NullPointerException that I could not resolve.
     * The error occurred because the bottom table, the Associated Parts table, would not initialize properly.
     * I constantly compared the lines of code to initialize the table to the code called in the AddProductController
     * to see if there was something different about that code but it was the same. However, the code would not work
     * for this controller. After repeated attempts to resolve the issue, including a try-catch addressing the
     * NullPointerException, I decided to start from the beginning and went back to the FXML file. It was here that I
     * noticed that I had typed the name for the bottom table as the name for the AddProduct.fxml and not the
     * ModProduct.fxml. When I adjusted the name, it finally worked and the error went away.
     */
    @FXML // This method is called by the FXMLLoader when initialization is complete
    void initialize(Product selectedProduct) throws IOException {
        //Initialize the text fields to contain information of the selected Product from the Main Menu
        ModProdIDTxtFld.setText(String.valueOf(selectedProduct.getId()));
        ModProdNameTxtFld.setText(String.valueOf(selectedProduct.getName()));
        ModProdInvTxtFld.setText(String.valueOf(selectedProduct.getStock()));
        ModProdPriceTxtFld.setText(String.valueOf(selectedProduct.getPrice()));
        ModProdMinTxtFld.setText(String.valueOf(selectedProduct.getMin()));
        ModProdMaxTxtFld.setText(String.valueOf(selectedProduct.getMax()));

        //Initialize the top table to contain all Parts from the Main Menu Parts table
        ModProdAddTblView.setItems(Inventory.getAllParts());
        ModProdAddTblPartIDCol.setCellValueFactory(new PropertyValueFactory<Part, Integer>("id"));
        ModProdAddTblPartNameCol.setCellValueFactory(new PropertyValueFactory<Part, String>("name"));
        ModProdAddTblInvLevelCol.setCellValueFactory(new PropertyValueFactory<Part, Integer>("stock"));
        ModProdAddTblPCCol.setCellValueFactory(new PropertyValueFactory<Part, Double>("price"));

        //Initialize the bottom menu to contain all associated parts for the specified Product
        associatedTable.addAll(selectedProduct.getAllAssociatedParts());

        ModProdRemoveTblView.setItems(associatedTable);
        ModProdRemoveTblPartIDCol.setCellValueFactory(new PropertyValueFactory<Part, Integer>("id"));
        ModProdRemoveTblPartNameCol.setCellValueFactory(new PropertyValueFactory<Part, String>("name"));
        ModProdRemoveTblInvLevelCol.setCellValueFactory(new PropertyValueFactory<Part, Integer>("stock"));
        ModProdRemoveTblPCCol.setCellValueFactory(new PropertyValueFactory<Part, Double>("price"));
    }
}
