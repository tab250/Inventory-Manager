/**
 * @author Tyler Brown
 */
package controller;

import java.io.IOException;
import java.util.Optional;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.input.MouseEvent;
import javafx.stage.Stage;
import model.InHouse;
import model.Inventory;
import model.OutSourced;

/**
 * This class is used to control all user interactions that occur with the Add Part screen.
 */
public class AddPartController {

    //Used to set the stage and scene
    Stage stage;
    Parent scene;

    //Used to set id for newly added Part
    public static int partIndex = 1;


    @FXML
    private RadioButton InHouseRadioBtn;

    @FXML
    private ToggleGroup InOrOut;

    @FXML
    private RadioButton OutSourcedRadioBtn;

    @FXML
    private TextField AddPartNameTxt;

    @FXML
    private TextField AddPartInvTxt;

    @FXML
    private TextField AddPartPCTxt;

    @FXML
    private TextField AddPartMachCompTxt;

    @FXML
    private TextField AddPartIDTxt;

    @FXML
    private TextField AddPartMaxTxt;

    @FXML
    private TextField AddPartMinTxt;

    @FXML
    private Button AddPartSaveBtn;

    @FXML
    private Button AddPartCancelBtn;

    @FXML
    private Label MachIDCompName;

    /**
     * This method switches the Company Name label to Machine ID when the Outsourced radio button is
     * switched to the In-House radio button.
     */
    @FXML //Switches the MachIDCompName Label from Company Name to Machine ID
    void CompNameToMachID(MouseEvent event) {
        MachIDCompName.setText("Machine ID");
    }

    /**
     * This method switches the Machine ID label to Company Name when the Outsourced radio button is
     * switched to the In-House radio button.
     */
    @FXML //Switches the MachIDCompName Label from Machine ID to Company Name
    void MachIDToCompName(MouseEvent event) {
        MachIDCompName.setText("Company Name");
    }

    /**
     * This method returns the user to the Main Menu.
     */
    @FXML //Returns the user to the Main Menu
    void addPartCancel(MouseEvent event) throws IOException {
        stage = (Stage)((Button)event.getSource()).getScene().getWindow();
        scene = FXMLLoader.load(getClass().getResource("/view/MainMenu.fxml"));
        stage.setScene(new Scene(scene));
        stage.show();
    }

    /**
     * This method creates a new Part, adds it to the Inventory, and returns the user to the Main Menu.
     */
    @FXML //Adds the new Part to the Parts table in the Inventory and returns the user to the Main Menu
    void addPartSave(MouseEvent event) throws IOException {
        try {
            //If In-House Radio button is selected, In-House Part is made
            if (InHouseRadioBtn.isSelected()) {
                int id = partIndex;
                String name = AddPartNameTxt.getText();
                double price = Double.parseDouble(AddPartPCTxt.getText());
                int stock = Integer.parseInt(AddPartInvTxt.getText());
                int min = Integer.parseInt(AddPartMinTxt.getText());
                int max = Integer.parseInt(AddPartMaxTxt.getText());
                int machineId = Integer.parseInt(AddPartMachCompTxt.getText());

                //Chain of if-else if -else statements that checks for errors
                if (price < 0.0) {
                    Alert incorrectValues = new Alert(Alert.AlertType.ERROR);
                    incorrectValues.setTitle("Error Pop Up");
                    incorrectValues.setContentText("Please make sure Price value is positive or equal to zero");
                    incorrectValues.showAndWait();

                    AddPartPCTxt.clear();
                }
                else if (min < 0) {
                    Alert incorrectValues = new Alert(Alert.AlertType.ERROR);
                    incorrectValues.setTitle("Error Pop Up");
                    incorrectValues.setContentText("Please make sure Min value is positive or equal to zero");
                    incorrectValues.showAndWait();

                    AddPartMinTxt.clear();
                }
                else if (max < 0) {
                    Alert incorrectValues = new Alert(Alert.AlertType.ERROR);
                    incorrectValues.setTitle("Error Pop Up");
                    incorrectValues.setContentText("Please make sure Max value is positive or equal to zero");
                    incorrectValues.showAndWait();

                    AddPartMaxTxt.clear();
                }
                else if (min > max) {
                    Alert incorrectValues = new Alert(Alert.AlertType.ERROR);
                    incorrectValues.setTitle("Error Pop Up");
                    incorrectValues.setContentText("Please make sure Min value is less than Max value");
                    incorrectValues.showAndWait();

                    AddPartMinTxt.clear();
                    AddPartMaxTxt.clear();
                }
                else if (stock > max || stock < min){
                    Alert incorrectValues = new Alert(Alert.AlertType.ERROR);
                    incorrectValues.setTitle("Error Pop Up");
                    incorrectValues.setContentText("Please make sure Inventory value is between Max and Min");
                    incorrectValues.showAndWait();

                    AddPartInvTxt.clear();
                }
                else {
                    Inventory.addPart(new InHouse(id, name, price, stock, min, max, machineId));

                    partIndex++;

                    stage = (Stage)((Button)event.getSource()).getScene().getWindow();
                    scene = FXMLLoader.load(getClass().getResource("/view/MainMenu.fxml"));
                    stage.setScene(new Scene(scene));
                    stage.show();
                }
            }
            //If In-House Radio button is not selected, OutSourced Part is made
            else {
                int id = partIndex;
                String name = AddPartNameTxt.getText();
                double price = Double.parseDouble(AddPartPCTxt.getText());
                int stock = Integer.parseInt(AddPartInvTxt.getText());
                int min = Integer.parseInt(AddPartMinTxt.getText());
                int max = Integer.parseInt(AddPartMaxTxt.getText());
                String companyName = AddPartMachCompTxt.getText();

                //Chain of if-else if -else statements that checks for errors
                if (price < 0.0) {
                    Alert incorrectValues = new Alert(Alert.AlertType.ERROR);
                    incorrectValues.setTitle("Error Pop Up");
                    incorrectValues.setContentText("Please make sure Price value is positive or equal to zero");
                    incorrectValues.showAndWait();

                    AddPartPCTxt.clear();
                }
                else if (min < 0) {
                    Alert incorrectValues = new Alert(Alert.AlertType.ERROR);
                    incorrectValues.setTitle("Error Pop Up");
                    incorrectValues.setContentText("Please make sure Min value is positive or equal to zero");
                    incorrectValues.showAndWait();

                    AddPartMinTxt.clear();
                }
                else if (max < 0) {
                    Alert incorrectValues = new Alert(Alert.AlertType.ERROR);
                    incorrectValues.setTitle("Error Pop Up");
                    incorrectValues.setContentText("Please make sure Max value is positive or equal to zero");
                    incorrectValues.showAndWait();

                    AddPartMaxTxt.clear();
                }
                else if (min > max) {
                    Alert incorrectValues = new Alert(Alert.AlertType.ERROR);
                    incorrectValues.setTitle("Error Pop Up");
                    incorrectValues.setContentText("Please make sure Min value is smaller than Max value");
                    incorrectValues.showAndWait();

                    AddPartMinTxt.clear();
                    AddPartMaxTxt.clear();
                }
                else if (stock > max || stock < min){
                    Alert incorrectValues = new Alert(Alert.AlertType.ERROR);
                    incorrectValues.setTitle("Error Pop Up");
                    incorrectValues.setContentText("Please make sure Inventory value is between Max and Min");
                    incorrectValues.showAndWait();

                    AddPartInvTxt.clear();
                }
                else {
                    Inventory.addPart(new OutSourced(id, name, price, stock, min, max, companyName));

                    partIndex++;

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
     * This method initializes the Add Part screen.
     */
    @FXML // This method is called by the FXMLLoader when initialization is complete
    void initialize() {

    }
}
