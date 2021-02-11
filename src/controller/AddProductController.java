/**
 * @author Tyler Brown
 */
package controller;

import java.awt.*;
import java.io.IOException;
import java.util.Optional;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseEvent;
import javafx.stage.Stage;
import model.Inventory;
import model.Part;
import model.Product;

import static javafx.collections.FXCollections.*;

/**
 * This class is used to control all user interactions that occur with the Add Product screen.
 */
public class AddProductController {

    //Used to set the stage and scene
    Stage stage;
    Parent scene;

    //Used to set id for newly added Product
    public static int productIndex = 1;

    //Create an Observable table to add temporary Part objects to for the bottom table
    private ObservableList<Part> associatedTable = observableArrayList();

    @FXML
    private TextField AddProdIDTxtFld;

    @FXML
    private TextField AddProdNameTxtFld;

    @FXML
    private TextField AddProdInvTxtFld;

    @FXML
    private TextField AddProdPriceTxtFld;

    @FXML
    private TextField AddProdSearchTxtFld;

    @FXML
    private TextField AddProdMaxTxtFld;

    @FXML
    private TextField AddProdMinTxtFld;

    @FXML
    private TableView<Part> AddProdAddTblView;

    @FXML
    private TableColumn<Part, Integer> AddPartTblPartIDCol;

    @FXML
    private TableColumn<Part, String> AddPartTblPartNameCol;

    @FXML
    private TableColumn<Part, Integer> AddPartTblInvLevelCol;

    @FXML
    private TableColumn<Part, Double> AddPartTblPCCol;

    @FXML
    private TableView<Part> AddProdRemoveTblView;

    @FXML
    private TableColumn<Part, Integer> RemovePartTblPartIDCol;

    @FXML
    private TableColumn<Part, String> RemovePartTblPartNameCol;

    @FXML
    private TableColumn<Part, Integer> RemovePartTblInvLevelCol;

    @FXML
    private TableColumn<Part, Double> RemovePartTblPCCol;

    @FXML
    private Button AddProdAddBtn;

    @FXML
    private Button AddProdSaveBtn;

    @FXML
    private Button AddProdCancelBtn;

    @FXML
    private Button AddProdRemovePartBtn;

    /**
     * This method returns the user to the Main Menu.
     */
    @FXML //Returns the user to the Main Menu
    void addProductCancel(MouseEvent event) throws IOException {
        //Clear the Associated Table incase something was added
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
    @FXML //Deletes Associated Parts from the Associated Parts table
    void addProductRemovePart(MouseEvent event) throws IOException {
        //Adds Part from the top table, all Parts, to the bottom table, Associated Parts
        Part deletePart = AddProdAddTblView.getSelectionModel().getSelectedItem();

        //Check with user to make sure selected Part should be deleted, if yes then delete
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION, "Are you sure you want to delete " +
                "Part: " + deletePart.getName() + "?");
        Optional<ButtonType> result = alert.showAndWait();
        if (result.isPresent() && result.get() == ButtonType.OK) {
            associatedTable.remove(deletePart);
        }
    }

    /**
     * This method creates a new Product object, adds it to the Inventory and returns the user to the Main Menu.
     */
    @FXML //Adds the new Product to the Product table in the Inventory and returns the user to the Main Menu
    void addProductSaveProduct(MouseEvent event) throws IOException {
        try {
            //Parse information in texts fields and use them to make a new Product
            int id = productIndex;
            String name = AddProdNameTxtFld.getText();
            double price = Double.parseDouble(AddProdPriceTxtFld.getText());
            int stock = Integer.parseInt(AddProdInvTxtFld.getText());
            int min = Integer.parseInt(AddProdMinTxtFld.getText());
            int max = Integer.parseInt(AddProdMaxTxtFld.getText());

            //Chain of if-else if -else statements that checks for errors
            if (price < 0.0) {
                Alert incorrectValues = new Alert(Alert.AlertType.ERROR);
                incorrectValues.setTitle("Error Pop Up");
                incorrectValues.setContentText("Please make sure Price value is positive or equal to zero");
                incorrectValues.showAndWait();

                AddProdPriceTxtFld.clear();
            }
            else if (min < 0) {
                Alert incorrectValues = new Alert(Alert.AlertType.ERROR);
                incorrectValues.setTitle("Error Pop Up");
                incorrectValues.setContentText("Please make sure Min value is positive or equal to zero");
                incorrectValues.showAndWait();

                AddProdMinTxtFld.clear();
            }
            else if (max < 0) {
                Alert incorrectValues = new Alert(Alert.AlertType.ERROR);
                incorrectValues.setTitle("Error Pop Up");
                incorrectValues.setContentText("Please make sure Max value is positive or equal to zero");
                incorrectValues.showAndWait();

                AddProdMaxTxtFld.clear();
            }
            else if (min > max) {
                Alert incorrectValues = new Alert(Alert.AlertType.ERROR);
                incorrectValues.setTitle("Error Pop Up");
                incorrectValues.setContentText("Please make sure Min value is less than Max value");
                incorrectValues.showAndWait();

                AddProdMinTxtFld.clear();
                AddProdMaxTxtFld.clear();
            }
            else if (stock > max || stock < min){
                Alert incorrectValues = new Alert(Alert.AlertType.ERROR);
                incorrectValues.setTitle("Error Pop Up");
                incorrectValues.setContentText("Please make sure Inventory value is between Max and Min");
                incorrectValues.showAndWait();

                AddProdInvTxtFld.clear();
            }
            else {
                //Create a new Product
                Inventory.addProduct(new Product(id, name, price, stock, min, max));

                //Check the Associated Parts table to see if Parts need to be added to the new Products Associated Parts table
                if (associatedTable.size() > 0) {
                    //Add the Associated Part to the Products Associated Parts Table
                    for (Part part : AddProdRemoveTblView.getItems()) {
                        Product.addAssociatedPart(part);
                    }

                    //Increase index value for next added Product
                    productIndex++;

                    //Clear the Associated Parts table prior to returning to the Main Menu
                    associatedTable.clear();

                    //Return the user to the Main Menu and update Product table to include new Product
                    stage = (Stage)((Button)event.getSource()).getScene().getWindow();
                    scene = FXMLLoader.load(getClass().getResource("/view/MainMenu.fxml"));
                    stage.setScene(new Scene(scene));
                    stage.show();
                }

                else {
                    //Increase index value for next added Product
                    productIndex++;

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
        catch (NumberFormatException e) {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Error Pop Up");
            alert.setContentText("Please enter valid values for each field");
            alert.showAndWait();
        }
    }

    /**
     * This method adds a Part from the top table to the bottom, Associated Parts, table.
     */
    @FXML //Adds selected Part from top table to the Associated Parts table, bottom table
    void addProductToTable(MouseEvent event) throws IOException {

        //Adds Part from the top table, all Parts, to the bottom table, Associated Parts
        Part associatedPart = AddProdAddTblView.getSelectionModel().getSelectedItem();

        //Add the selected Part from the top table to the bottom table's Observable list
        associatedTable.add(associatedPart);

        //Populate bottom table, Associated Parts, with the newly added Part
        AddProdRemoveTblView.setItems(associatedTable);
        RemovePartTblPartIDCol.setCellValueFactory(new PropertyValueFactory<Part, Integer>("id"));
        RemovePartTblPartNameCol.setCellValueFactory(new PropertyValueFactory<Part, String>("name"));
        RemovePartTblInvLevelCol.setCellValueFactory(new PropertyValueFactory<Part, Integer>("stock"));
        RemovePartTblPCCol.setCellValueFactory(new PropertyValueFactory<Part, Double>("price"));
    }

    /**
     * This method searches the Parts table for the desired object, first using a name match, then Id.
     */
    @FXML //Searches through the Parts list to find a match for names or the ID
    void searchParts(KeyEvent event) throws IOException {
        try {
            String desiredPart = AddProdSearchTxtFld.getText();

            //Search by name (String data type first)
            ObservableList<Part> searchedPart = Inventory.lookupPart(desiredPart);
            AddProdAddTblView.setItems(searchedPart);

            //If name was not found, list of Parts is checked by ID
            if (searchedPart.isEmpty()) {
                int id = Integer.parseInt(desiredPart);
                searchedPart.add(Inventory.lookupPart(id));
            }
        }
        catch (NumberFormatException e) {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Error Pop Up");
            alert.setContentText("Please enter a valid search request");
            alert.showAndWait();

            //Clear the Search Parts text field
            AddProdSearchTxtFld.clear();

            //Reload the Parts table
            AddProdAddTblView.setItems(Inventory.getAllParts());
            AddPartTblPartIDCol.setCellValueFactory(new PropertyValueFactory<Part, Integer>("id"));
            AddPartTblPartNameCol.setCellValueFactory(new PropertyValueFactory<Part, String>("name"));
            AddPartTblInvLevelCol.setCellValueFactory(new PropertyValueFactory<Part, Integer>("stock"));
            AddPartTblPCCol.setCellValueFactory(new PropertyValueFactory<Part, Double>("price"));
        }
    }

    /**
     * This method initializes the Add Product screen.
     */
    @FXML // This method is called by the FXMLLoader when initialization is complete
    void initialize() {
        //Populate the top table with the Parts list
        AddProdAddTblView.setItems(Inventory.getAllParts());
        AddPartTblPartIDCol.setCellValueFactory(new PropertyValueFactory<Part, Integer>("id"));
        AddPartTblPartNameCol.setCellValueFactory(new PropertyValueFactory<Part, String>("name"));
        AddPartTblInvLevelCol.setCellValueFactory(new PropertyValueFactory<Part, Integer>("stock"));
        AddPartTblPCCol.setCellValueFactory(new PropertyValueFactory<Part, Double>("price"));
    }
}
