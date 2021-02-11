/**
 * @author Tyler Brown
 */
package controller;

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
import model.Part;

import java.io.IOException;
import java.util.Optional;

/**
 * This class is used to control all user interactions that occur with the Modify Part screen.
 * Fix the issue that prevents user from modifying Part from one subclass (In-House or Outsourced) to the other.
 */
public class ModifyPartController {

    //Used to set the stage and scene
    Stage stage;
    Parent scene;

    //Used to change the object if the opposite radio button is selected
    Part switchedPart = null;
    int switchedIndex = 0;

    @FXML
    private RadioButton InHouseRadioBtn;

    @FXML
    private ToggleGroup InOrOut;

    @FXML
    private RadioButton OutSourcedRadioBtn;

    @FXML
    private TextField ModPartNameTxt;

    @FXML
    private TextField ModPartInvTxt;

    @FXML
    private TextField ModPartPCTxt;

    @FXML
    private TextField ModPartMachCompTxt;

    @FXML
    private TextField ModPartIDTxt;

    @FXML
    private TextField ModPartMaxTxt;

    @FXML
    private TextField ModPartMinTxt;

    @FXML
    private Button ModPartSaveBtn;

    @FXML
    private Button ModPartCancelBtn;

    @FXML
    private Label MachIDCompName;

    /**
     * This method switches the Company Name label to Machine ID when the Outsourced radio button is
     * switched to the In-House radio button.
     */
    @FXML //Switches the MachIDCompName Label from Company Name to Machine ID
    void compNameToMachID(MouseEvent event) {
        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setTitle("Error Pop Up");
        alert.setContentText("Outsourced object cannot be changed to In-House. Delete existing object and add a new one");
        alert.showAndWait();
    }

    /**
     * This method switches the Machine ID label to Company Name when the Outsourced radio button is
     * switched to the In-House radio button.
     */
    @FXML //Switches the MachIDCompName Label from Machine ID to Company Name
    void machIDToCompName(MouseEvent event) {
        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setTitle("Error Pop Up");
        alert.setContentText("In-House object cannot be changed to Outsourced. Delete existing object and add a new one");
        alert.showAndWait();
    }

    /**
     * This method returns the user to the Main Menu.
     */
    @FXML //Returns the user to the Main Menu
    void modPartCancel(MouseEvent event) throws IOException {
        stage = (Stage)((Button)event.getSource()).getScene().getWindow();
        scene = FXMLLoader.load(getClass().getResource("/view/MainMenu.fxml"));
        stage.setScene(new Scene(scene));
        stage.show();
    }

    /**
     * This method updates the selected Part and returns the user to the Main Menu.
     */
    @FXML //Updates the Part in the Inventory and returns the user to the Main Menu
    void modPartSave(MouseEvent event) throws IOException {
        try {
            //If In-House Radio button is selected, update In-House Part
            if (InHouseRadioBtn.isSelected()) {
                for (Part inHouse : Inventory.getAllParts()) {
                    if (inHouse.getId() == Integer.parseInt(ModPartIDTxt.getText()) && inHouse instanceof InHouse) {
                        inHouse.setName(ModPartNameTxt.getText());
                        inHouse.setPrice(Double.parseDouble(ModPartPCTxt.getText()));
                        inHouse.setStock(Integer.parseInt(ModPartInvTxt.getText()));
                        inHouse.setMin(Integer.parseInt(ModPartMinTxt.getText()));
                        inHouse.setMax(Integer.parseInt(ModPartMaxTxt.getText()));
                        ((InHouse) inHouse).setMachineId(Integer.parseInt(ModPartMachCompTxt.getText()));

                        Inventory.updatePart(Inventory.getAllParts().indexOf(inHouse), inHouse);
                    }
                }
            }
            //Else, OutSourced Radio button is selected; update In-House Part
            else {
                for (Part outSourced : Inventory.getAllParts()) {
                    if (outSourced.getId() == Integer.parseInt(ModPartIDTxt.getText()) && outSourced instanceof OutSourced) {
                        outSourced.setName(ModPartNameTxt.getText());
                        outSourced.setPrice(Double.parseDouble(ModPartPCTxt.getText()));
                        outSourced.setStock(Integer.parseInt(ModPartInvTxt.getText()));
                        outSourced.setMin(Integer.parseInt(ModPartMinTxt.getText()));
                        outSourced.setMax(Integer.parseInt(ModPartMaxTxt.getText()));
                        ((OutSourced) outSourced).setCompanyName(ModPartMachCompTxt.getText());

                        Inventory.updatePart(Inventory.getAllParts().indexOf(outSourced), outSourced);
                    }
                }
            }

            //Return the user to the Main Menu
            stage = (Stage)((Button)event.getSource()).getScene().getWindow();
            scene = FXMLLoader.load(getClass().getResource("/view/MainMenu.fxml"));
            stage.setScene(new Scene(scene));
            stage.show();
        }
        catch (NumberFormatException e) {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Error Pop Up");
            alert.setContentText("Please enter valid values for each field");
            alert.showAndWait();
        }
    }

    /**
     * This method initializes the Modify Part screen.
     */
    @FXML // This method is called by the FXMLLoader when initialization is complete
    void initialize(Part selectedPart) {
        ModPartIDTxt.setText(String.valueOf(selectedPart.getId()));
        ModPartNameTxt.setText(String.valueOf(selectedPart.getName()));
        ModPartInvTxt.setText(String.valueOf(selectedPart.getStock()));
        ModPartPCTxt.setText(String.valueOf(selectedPart.getPrice()));
        ModPartMinTxt.setText(String.valueOf(selectedPart.getMin()));
        ModPartMaxTxt.setText(String.valueOf(selectedPart.getMax()));

        //Check to see if the selected Item is an InHouse product so the InHouse button will be selected
        if (selectedPart instanceof InHouse) {
            InHouseRadioBtn.setSelected(true);
            ModPartMachCompTxt.setText(String.valueOf(((InHouse) selectedPart).getMachineId()));
        }
        //Otherwise the OutSourced button will be selected
        else {
            OutSourcedRadioBtn.setSelected(true);
            ModPartMachCompTxt.setText(String.valueOf(((OutSourced) selectedPart).getCompanyName()));
            MachIDCompName.setText("Company Name");
        }
    }
}
