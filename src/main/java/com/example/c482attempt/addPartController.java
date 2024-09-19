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
 * addPartController works with Part Inventory.
 */
public class addPartController implements Initializable {

    //Buttons
    @FXML private RadioButton radioButtonInHouse;
    @FXML private RadioButton radioButtonOutsourced;

    //Text Fields
    @FXML private TextField addPartInv;
    @FXML private TextField addPartMachineID;
    @FXML private TextField addPartMax;
    @FXML private TextField addPartMin;
    @FXML private TextField addPartName;
    @FXML private TextField addPartPrice;

    //Label
    @FXML
    private Label addPartRadioLabelChange;


    /**
     * radioButtonActionInHouse makes it so when the radio button is activated, it will change the radio Label to "Machine ID".
     * @param event
     */
    @FXML
    void radioButtonActionInHouse(ActionEvent event) {
        addPartRadioLabelChange.setText("Machine ID");
    }


    /**
     * radioButtonActionOutsourced makes it so when the radio button is activated, it will change the radio label to "Company Name".
     * @param event
     */
    @FXML
    void radioButtonActionOutsourced(ActionEvent event) {
        addPartRadioLabelChange.setText("Company Name");
    }


    /**
     * cancelButton on button click will bring a menu allowing the user to choose to go back to the main menu.
     * @param event
     * @throws IOException
     */
    @FXML
    void cancelButton(ActionEvent event) throws IOException {
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
        alert.setTitle("Close Add Part Form Confirmation");
        alert.setHeaderText("Heading back to Inventory Management Form.\r" + "Any unsaved data will be LOST!\r" + "Are you sure you want to continue?\r" + "Click 'OK' to confirm exit.\r" + "Click 'Cancel' to go back.");
        Optional<ButtonType> confirmation = alert.showAndWait();

        if (confirmation.isPresent() && confirmation.get() == ButtonType.OK) {
            InventoryManagement.returnToMenu(event);
        }
    }


    /**
     * saveButton creates and saves a part to the part table.
     * @param event
     * @throws IOException
     * FUTURE ENHANCEMENT: Make it so previously deleted ids can be reused to new parts!
     * ERROR: Need to find a way to make it so part id doesn't repeat after a part is deleted!
     *      - Fixed: Made it so partId can never be reused.
     */
    @FXML
    void saveButton(ActionEvent event) throws IOException {
        try {
            String partName = addPartName.getText();
            int partStock = Integer.parseInt(addPartInv.getText());
            Double partPrice = Double.parseDouble(addPartPrice.getText());
            int partMin = Integer.parseInt(addPartMin.getText());
            int partMax = Integer.parseInt(addPartMax.getText());
            String name = addPartName.getText();

            if (InventoryManagement.isMinimumValueAllowed(partMin, partMax) && InventoryManagement.isInventoryAllowed(partMin, partMax, partStock) && InventoryManagement.isThereAName(name)) {
                if (radioButtonOutsourced.isSelected()) {
                    String partCompanyName = addPartMachineID.getText();
                    int uniquePartID = Inventory.getPartIdCounter();
                    Outsourced temporary = new Outsourced(uniquePartID, partName, partPrice, partStock, partMin, partMax, partCompanyName);
                    temporary.setName(this.addPartName.getText());
                    temporary.setPrice(Double.parseDouble(addPartPrice.getText()));
                    temporary.setStock(Integer.parseInt(addPartInv.getText()));
                    temporary.setMin(Integer.parseInt(addPartMin.getText()));
                    temporary.setMax(Integer.parseInt(addPartMax.getText()));
                    temporary.setCompanyName(addPartMachineID.getText());
                    Inventory.addPart(temporary);

                } else if (radioButtonInHouse.isSelected()) {
                    int partMachineID = Integer.parseInt(addPartMachineID.getText());
                    int uniquePartID = Inventory.getPartIdCounter();
                    InHouse temporary = new InHouse(uniquePartID, partName, partPrice, partStock, partMin, partMax, partMachineID);
                    temporary.setName(this.addPartName.getText());
                    temporary.setPrice(Double.parseDouble(addPartPrice .getText()));
                    temporary.setStock(Integer.parseInt(addPartInv.getText()));
                    temporary.setMin(Integer.parseInt(addPartMin.getText()));
                    temporary.setMax(Integer.parseInt(addPartMax.getText()));
                    temporary.setMachineId(Integer.parseInt(addPartMachineID.getText()));
                    Inventory.addPart(temporary);
                }
               InventoryManagement.returnToMenu(event);
            }
        }
        catch (Exception a) {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Adding Part Form ERROR");
            alert.setHeaderText("Error creating Part");
            alert.setContentText("This form has invalid values or inputs.");
            alert.initModality(Modality.WINDOW_MODAL);
            alert.showAndWait();
        }
    }


    /**
     * Initialize [Only here because public class needed it to run properly]
     */
    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {

    }

}
