package com.example.c482attempt;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.stage.Modality;

import java.io.IOException;
import java.net.URL;
import java.util.Optional;
import java.util.ResourceBundle;

/**
 * modifyPartFormController works with/modifies Parts in Part Inventory
 */
public class modifyPartFormController implements Initializable {

    //Text Fields
    @FXML private TextField modifyPartId;
    @FXML private TextField modifyPartInv;
    @FXML private TextField modifyPartMachineId;
    @FXML private TextField modifyPartMax;
    @FXML private TextField modifyPartMin;
    @FXML private TextField modifyPartName;
    @FXML private TextField modifyPartPrice;

    //Radio Buttons
    @FXML private RadioButton radioButtonInHouse;
    @FXML private RadioButton radioButtonOutsourced;

    //Label
    @FXML private Label modifyPartRadioLabelChange;

    public int selectedIndex;
    public Part selectedPart;


    /**
     * radioButtonActionInHouse makes it so when the radio button is activated, it will change the radio Label to "Machine ID".
     * @param event
     */
    @FXML
    void radioButtonActionInHouse(ActionEvent event) {
        modifyPartRadioLabelChange.setText("Machine ID");
    }


    /**
     * radioButtonActionOutsourced makes it so when the radio button is activated, it will change the radio label to "Company Name".
     * @param event
     */
    @FXML
    void radioButtonActionOutsourced(ActionEvent event) {
        modifyPartRadioLabelChange.setText("Company Name");
    }


    /**
     * partSelection is the method used for setting the current product.
     * This method is used hand to hand with the "mainFormInventoryManagement.onActionPartModify"
     * @param index
     * @param selectedPart
     */
    public void partSelection(int index, Part selectedPart) {
        this.selectedPart = selectedPart;
        this.selectedIndex = index;
        if (selectedPart instanceof Outsourced outsourced) {
            radioButtonOutsourced.setSelected(true);
            this.modifyPartRadioLabelChange.setText("Company Name");
            modifyPartMachineId.setText(outsourced.getCompanyName());
        }
        else if (selectedPart instanceof InHouse inHouse) {
            radioButtonInHouse.setSelected(true);
            this.modifyPartRadioLabelChange.setText("Machine ID");
            modifyPartMachineId.setText(Integer.toString(inHouse.getMachineId()));
        }
        modifyPartId.setText(String.valueOf(selectedPart.getId()));
        modifyPartName.setText(selectedPart.getName());
        modifyPartInv.setText(Integer.toString(selectedPart.getStock()));
        modifyPartPrice.setText(Double.toString(selectedPart.getPrice()));
        modifyPartMin.setText(Integer.toString(selectedPart.getMin()));
        modifyPartMax.setText(Integer.toString(selectedPart.getMax()));
    }


    /**
     * saveButton updates and saves current part to part table.
     * @param event
     * @throws IOException
     *
     * ISSUE: Everytime a product is modified, it overwrites the next id in line.
     *     - FIX: I had updatePart set to (partId, temporary) instead of (selectedIndex, temporary).
     *      Also had an issue with mainFormInventoryManagement.onActionPartsModify.
     *      controller.partSelection was set to (controller.selectedIndex, partSelection), instead of (partsPane.getSelection().getSelectionIndex(), partSelection).
     *      Problem with that is it was trying to save the original selectedIndex. Basically a simple error that took me hours of frustration.
     *      Thanks to the course instructor Anji for explaining how the problem was happening.
     */
    @FXML
    void saveButton(ActionEvent event) throws IOException {

        int partId = Integer.parseInt(modifyPartId.getText());
        String partName = modifyPartName.getText();
        Double partPrice = Double.parseDouble(modifyPartPrice.getText());
        int partInv = Integer.parseInt(modifyPartInv.getText());
        int partMin = Integer.parseInt(modifyPartMin.getText());
        int partMax = Integer.parseInt(modifyPartMax.getText());
        String name = modifyPartName.getText();

        try {
            if (InventoryManagement.isMinimumValueAllowed(partMin, partMax) && InventoryManagement.isInventoryAllowed(partMin, partMax, partInv) && InventoryManagement.isThereAName(name)) {
                if (radioButtonOutsourced.isSelected()) {
                    String partCompanyName = modifyPartMachineId.getText();
                    Outsourced temporary = new Outsourced(partId, partName, partPrice, partInv, partMin, partMax, partCompanyName);
                    Inventory.updatePart(selectedIndex, temporary);
                    InventoryManagement.returnToMenu(event);
                }
                if (radioButtonInHouse.isSelected()) {
                    int partMachineID = Integer.parseInt(modifyPartMachineId.getText());
                    InHouse temporary = new InHouse(partId, partName, partPrice, partInv, partMin, partMax, partMachineID);
                    Inventory.updatePart(selectedIndex, temporary);
                    InventoryManagement.returnToMenu(event);
                }
            }

            } catch(Exception b){
                Alert alert = new Alert(Alert.AlertType.ERROR);
                alert.setTitle("Modifying Part Form ERROR");
                alert.setHeaderText("Error Modifying Part");
                alert.setContentText("This form has invalid values or inputs.");
                alert.initModality(Modality.WINDOW_MODAL);
                alert.showAndWait();
            }
    }


    /**
     * cancelButton on button click will bring a menu allowing the user to choose to go back to the main menu.
     * @param event
     * @throws IOException
     */
    //Same functionality as modifyProductFormController.cancelButton(ActionEvent event).
    @FXML
    void cancelButton(ActionEvent event) throws IOException {
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
        alert.setTitle("Close Modify Part Form Confirmation");
        alert.setHeaderText("Heading back to Inventory Management Form.\r" + "Any unsaved data will be LOST!\r" + "Are you sure you want to continue?\r" + "Click 'OK' to confirm exit.\r" + "Click 'Cancel' to go back.");
        Optional<ButtonType> confirmation = alert.showAndWait();

        if (confirmation.isPresent() && confirmation.get() == ButtonType.OK) {
            InventoryManagement.returnToMenu(event);
        }
    }


    /**
     * Initialize [Only here because public class needed it to run properly]
     * @param location
     * @param resources
     */
    @Override
    public void initialize(URL location, ResourceBundle resources) {

        }
    }

