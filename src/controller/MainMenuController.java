/**
 * @author Tyler Brown
 */
package controller;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
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

import java.awt.*;
import java.io.IOException;
import java.util.Optional;

/**
 * This class is used to control all user interactions that occur with the Main Menu screen.
 */
public class MainMenuController {

    Stage stage;
    Parent scene;

    @FXML
    private Button ExitBtn;

    @FXML
    private TableView<Product> MainProductsTableView;

    @FXML
    private TableColumn<Product, Integer> ProdIDCol;

    @FXML
    private TableColumn<Product, String> ProdNameCol;

    @FXML
    private TableColumn<Product, Integer> ProdInvLevelCol;

    @FXML
    private TableColumn<Product, Double> ProdPriceCostCol;

    @FXML
    private TextField MainProdSearchTxt;

    @FXML
    private Button MainProdAddBtn;

    @FXML
    private Button MainProdModBtn;

    @FXML
    private Button MainProdDeleteBtn;

    @FXML
    private TableView<Part> MainPartsTableView;

    @FXML
    private TableColumn<Part, Integer> PartIDCol;

    @FXML
    private TableColumn<Part, String> PartNameCol;

    @FXML
    private TableColumn<Part, Integer> PartInvLevelCol;

    @FXML
    private TableColumn<Part, Double> PartPriceCostCol;

    @FXML
    private TextField MainPartsSearchTxt;

    @FXML
    private Button MainPartsAddBtn;

    @FXML
    private Button MainPartsModBtn;

    @FXML
    private Button MainPartsDeleteBtn;

    /**
     * This method closes the program.
     */
    @FXML //Closes the application
     void onClickExit(MouseEvent event) {

        System.exit(0);
    }

    /**
     * This method takes the user to the Add Part screen.
     */
    @FXML //Takes the user to the Add Part screen
    void onClickPartsAdd(MouseEvent event) throws IOException {
        stage = (Stage)((Button)event.getSource()).getScene().getWindow();
        scene = FXMLLoader.load(getClass().getResource("/view/AddPart.fxml"));
        stage.setScene(new Scene(scene));
        stage.show();
    }

    /**
     * This method removes a Part from the Parts table.
     */
    @FXML //Deletes selected Part from the Parts Table
    void onClickPartsDelete(MouseEvent event) throws IOException {

        //Declare variable to equal selected Product to be deleted
        Part selectedPart = MainPartsTableView.getSelectionModel().getSelectedItem();

        //Confirm with the user that the Part should be deleted
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION, "Are you sure you want to delete " +
                "Part: " + selectedPart.getName() + "?");
        Optional<ButtonType> result = alert.showAndWait();
        if (result.isPresent() && result.get() == ButtonType.OK) {
            Inventory.deletePart(selectedPart);
        }

    }


    /**
     * This method takes the user to the Modify Part screen.
     */
    @FXML //Takes user to the Modify Part screen
    void onClickPartsMod(MouseEvent event) throws IOException {

        FXMLLoader loader = new FXMLLoader();
        loader.setLocation(getClass().getResource("/view/ModifyPart.fxml"));
        loader.load();

        ModifyPartController ModPartController = loader.getController();
        ModPartController.initialize(MainPartsTableView.getSelectionModel().getSelectedItem());

        stage = (Stage)((Button)event.getSource()).getScene().getWindow();
        Parent scene = loader.getRoot();
        stage.setScene(new Scene(scene));
        stage.show();
    }


    /**
     * This method takes the user to the Add Product screen.
     */
    @FXML //Takes user to the Add Product screen
    void onClickProdAdd(MouseEvent event) throws IOException {
        stage = (Stage)((Button)event.getSource()).getScene().getWindow();
        scene = FXMLLoader.load(getClass().getResource("/view/AddProduct.fxml"));
        stage.setScene(new Scene(scene));
        stage.show();
    }

    /**
     * This method removes a Product from the Product table if it does not have any associated parts.
     */
    @FXML //Deletes selected item from the Products table
    void onClickProdDel(MouseEvent event) throws IOException {
        //Declare variable to equal selected Product to be deleted
        Product selectedProduct = MainProductsTableView.getSelectionModel().getSelectedItem();

        //Check to see if the Product has Associated Part. If it does not, delete the Product but check with user first
        if (selectedProduct.getAllAssociatedParts().isEmpty()) {
            Alert alert = new Alert(Alert.AlertType.CONFIRMATION, "Are you sure you want to delete " +
                    "Product: " + selectedProduct.getName() + "?");
            Optional<ButtonType> result = alert.showAndWait();
            if (result.isPresent() && result.get() == ButtonType.OK) {
                Inventory.deleteProduct(selectedProduct);
            }
        }
        else {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Error Pop Up");
            alert.setContentText("This Product has Associated Parts, modify the Product and clear " +
                    "Associated Parts prior to deleting the Product");
            alert.showAndWait();
        }
    }


    /**
     * This method takes the user to the Modify Product screen.
     */
    @FXML //Takes user to Modify Product screen
    void onClickProdMod(MouseEvent event) throws IOException {

        FXMLLoader loader = new FXMLLoader();
        loader.setLocation(getClass().getResource("/view/ModifyProduct.fxml"));
        loader.load();

        ModifyProductController ModProdController = loader.getController();
        ModProdController.initialize(MainProductsTableView.getSelectionModel().getSelectedItem());

        stage = (Stage)((Button)event.getSource()).getScene().getWindow();
        Parent scene = loader.getRoot();
        stage.setScene(new Scene(scene));
        stage.show();
    }

    /**
     * This method searches the Parts table for the desired object, first using a name match, then Id.
     */
    @FXML //Searches Parts list for desired part
    void searchParts(KeyEvent event) throws IOException {
        try {
            String desiredPart = MainPartsSearchTxt.getText();

            //Search by name (String data type first)
            ObservableList<Part> searchedPart = Inventory.lookupPart(desiredPart);
            MainPartsTableView.setItems(searchedPart);

            //If name was not found, list of Parts is checked by ID
            if (searchedPart.isEmpty()) {
                int id = Integer.parseInt(desiredPart);
                if (id > Inventory.getAllParts().size()) {
                    Alert alert = new Alert(Alert.AlertType.ERROR);
                    alert.setTitle("Error Pop Up");
                    alert.setContentText("If searching by Part ID, please enter value no greater than last Part's ID");
                    alert.showAndWait();

                    //Clear the Search Parts text field
                    MainPartsSearchTxt.clear();

                    //Reload the Parts table
                    MainPartsTableView.setItems(Inventory.getAllParts());
                    PartIDCol.setCellValueFactory(new PropertyValueFactory<Part, Integer>("id"));
                    PartNameCol.setCellValueFactory(new PropertyValueFactory<Part, String>("name"));
                    PartInvLevelCol.setCellValueFactory(new PropertyValueFactory<Part, Integer>("stock"));
                    PartPriceCostCol.setCellValueFactory(new PropertyValueFactory<Part, Double>("price"));
                }
                searchedPart.add(Inventory.lookupPart(id));
            }
        }
        catch (NumberFormatException e) {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Error Pop Up");
            alert.setContentText("Please enter a valid search request");
            alert.showAndWait();

            //Clear the Search Parts text field
            MainPartsSearchTxt.clear();

            //Reload the Parts table
            MainPartsTableView.setItems(Inventory.getAllParts());
            PartIDCol.setCellValueFactory(new PropertyValueFactory<Part, Integer>("id"));
            PartNameCol.setCellValueFactory(new PropertyValueFactory<Part, String>("name"));
            PartInvLevelCol.setCellValueFactory(new PropertyValueFactory<Part, Integer>("stock"));
            PartPriceCostCol.setCellValueFactory(new PropertyValueFactory<Part, Double>("price"));
        }
    }

    /**
     * This method searches the Products table for the desired object, first using a name match, then Id.
     */
    @FXML //Searches Products list for desired product
    void searchProducts(KeyEvent event) throws IOException{
        try {
            String desiredProduct = MainProdSearchTxt.getText();

            //Search by name (String data type first)
            ObservableList<Product> searchedProduct = Inventory.lookupProduct(desiredProduct);
            MainProductsTableView.setItems(searchedProduct);

            //If name was not found, list of Parts is checked by ID
            if (searchedProduct.isEmpty()) {
                int id = Integer.parseInt(desiredProduct);
                if (id > Inventory.getAllProducts().size()) {
                    Alert alert = new Alert(Alert.AlertType.ERROR);
                    alert.setTitle("Error Pop Up");
                    alert.setContentText("If searching by Product ID, please enter value no greater than last Product's ID");
                    alert.showAndWait();

                    //Clear the Search Parts text field
                    MainProdSearchTxt.clear();

                    //Reload the Parts table
                    MainProductsTableView.setItems(Inventory.getAllProducts());
                    ProdIDCol.setCellValueFactory(new PropertyValueFactory<Product, Integer>("id"));
                    ProdNameCol.setCellValueFactory(new PropertyValueFactory<Product, String>("name"));
                    ProdInvLevelCol.setCellValueFactory(new PropertyValueFactory<Product, Integer>("stock"));
                    ProdPriceCostCol.setCellValueFactory(new PropertyValueFactory<Product, Double>("price"));
                }
                searchedProduct.add(Inventory.lookupProduct(id));
            }
        }
        catch (NumberFormatException e) {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Error Pop Up");
            alert.setContentText("Please enter a valid search request");
            alert.showAndWait();

            //Clear the Search Products text field
            MainProdSearchTxt.clear();

            //Reload the Products table
            MainProductsTableView.setItems(Inventory.getAllProducts());
            ProdIDCol.setCellValueFactory(new PropertyValueFactory<Product, Integer>("id"));
            ProdNameCol.setCellValueFactory(new PropertyValueFactory<Product, String>("name"));
            ProdInvLevelCol.setCellValueFactory(new PropertyValueFactory<Product, Integer>("stock"));
            ProdPriceCostCol.setCellValueFactory(new PropertyValueFactory<Product, Double>("price"));
        }
    }

    /**
     * This method initializes the Main Menu.
     */
    @FXML // This method is called by the FXMLLoader when initialization is complete
    void initialize() {
        MainPartsTableView.setItems(Inventory.getAllParts());
        PartIDCol.setCellValueFactory(new PropertyValueFactory<Part, Integer>("id"));
        PartNameCol.setCellValueFactory(new PropertyValueFactory<Part, String>("name"));
        PartInvLevelCol.setCellValueFactory(new PropertyValueFactory<Part, Integer>("stock"));
        PartPriceCostCol.setCellValueFactory(new PropertyValueFactory<Part, Double>("price"));

        MainProductsTableView.setItems(Inventory.getAllProducts());
        ProdIDCol.setCellValueFactory(new PropertyValueFactory<Product, Integer>("id"));
        ProdNameCol.setCellValueFactory(new PropertyValueFactory<Product, String>("name"));
        ProdInvLevelCol.setCellValueFactory(new PropertyValueFactory<Product, Integer>("stock"));
        ProdPriceCostCol.setCellValueFactory(new PropertyValueFactory<Product, Double>("price"));
    }
}
