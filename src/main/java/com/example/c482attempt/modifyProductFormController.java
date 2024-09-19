package com.example.c482attempt;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Modality;

import java.io.IOException;
import java.net.URL;
import java.util.Optional;
import java.util.ResourceBundle;

/**
 * modifyProductFormController works with/modifies products in Product Inventory.
 */
public class modifyProductFormController implements Initializable {

    //Text Fields
    @FXML private TextField modifyProductFormId;
    @FXML private TextField modifyProductFormInv;
    @FXML private TextField modifyProductFormMax;
    @FXML private TextField modifyProductFormMin;
    @FXML private TextField modifyProductFormName;
    @FXML private TextField modifyProductFormPrice;
    @FXML private TextField searchField;

    //Modify Product Pane [Top Table]
    @FXML private TableView<Part> modifyProductPane;
    @FXML private TableColumn<Part, Integer> modifyProductId;
    @FXML private TableColumn<Part, Integer> modifyProductInventoryLevel;
    @FXML private TableColumn<Part, String> modifyProductName;
    @FXML private TableColumn<Part, Double> modifyProductPrice;

    //Modify Associate Product Pane [Bottom Table]
    @FXML private TableView<Part> modifyAssociatedProductPane;
    @FXML private TableColumn<Part, Integer> modifyAssociatedProductId;
    @FXML private TableColumn<Part, Integer> modifyAssociatedProductInventoryLevel;
    @FXML private TableColumn<Part, String> modifyAssociatedProductName;
    @FXML private TableColumn<Part, Double> modifyAssociatedProductPrice;

    private ObservableList<Part> associatedParts = FXCollections.observableArrayList();
    public int selectedIndex;
    private Part partToFind;
    Product newProduct = new Product(1, "n", 1, 1, 1, 1); //Same thing from addProductController. not sure if it's right. EDIT: It works.


    /**
     * onActionPartSearch allows the user to search for items via text.
     * @param event
     * FUTURE ENHANCEMENT: Re-Write the search field to allow all lowerCase and upperCase letters.
     * ISSUE: Change Search to include lookupProduct methods? Or add to mainFormInventoryManagement.
     *      - Fixed: Updated Search method with lookupPart method. [CASE SENSITIVE]
     */
    //SAME exact method[Besides for tableview names] as mainFormInventoryManagement.onActionPartSearch.
    @FXML
    void onActionPartSearch(ActionEvent event) {
        String searchedPart = searchField.getText();

        if(searchedPart.isEmpty()) {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Error searching for Parts");
            alert.setHeaderText("The Part name or Id entered could not be found!");
            alert.showAndWait();
            modifyProductPane.setItems(Inventory.getAllParts());
        }
        else {
            boolean findItem = false;
            try {
                partToFind = Inventory.lookupPart(Integer.parseInt(searchedPart));
                if (partToFind != null) {
                    ObservableList<Part> part = FXCollections.observableArrayList();
                    part.add(partToFind);
                    modifyProductPane.setItems(part);
                }
                else {
                    Alert alert = new Alert(Alert.AlertType.ERROR);
                    alert.setTitle("Error searching for Parts");
                    alert.setHeaderText("The searched Id does not match any of the listings!");
                    alert.showAndWait();
                    modifyProductPane.setItems(Inventory.getAllParts());
                }
            } catch (NumberFormatException e) {
                ObservableList<Part> allParts = Inventory.getAllParts();
                if(allParts.isEmpty()){
                    Alert alert = new Alert(Alert.AlertType.ERROR);
                    alert.setTitle("Error searching for Parts");
                    alert.setHeaderText("No Parts found in listings.\r" + "Please create Parts first, then try again.");
                    alert.showAndWait();
                    modifyProductPane.setItems(Inventory.getAllParts());

                } else {
                    for (Part p : allParts) {
                        if (p.getName().contains(searchedPart)) {
                            ObservableList<Part> part;
                            findItem = true;
                            part = Inventory.lookupPart(searchedPart);
                            modifyProductPane.setItems(part);
                        }
                    }
                    if (!findItem) {
                        Alert alert = new Alert(Alert.AlertType.ERROR);
                        alert.setTitle("Error searching for Parts");
                        alert.setHeaderText("Searched item does not match any listings!");
                        alert.showAndWait();
                        modifyProductPane.setItems(Inventory.getAllParts());
                    }
                }
            }
        }
        resetSearchFields();
    }


    /**
     * resetSearchField resets the search field to its original state.
     */
    public void resetSearchFields() {
        searchField.setText("");
    }


    /**
     * addButton adds a associatedPart to the bottom table.
     * @param event
     */
    @FXML
    void addButton(ActionEvent event) {
        Part selectedPart = modifyProductPane.getSelectionModel().getSelectedItem();
        if (selectedPart != null) {
            associatedParts.add(selectedPart);
            modifyAssociatedProductPane.setItems(associatedParts);
        } else {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Selecting Part Error");
            alert.setHeaderText("Please select a part");
            alert.showAndWait();
        }

    }


    /**
     * cancelButton on button click will bring a menu allowing the user to choose to go back to the main menu.
     * @param event
     * @throws IOException
     */
    @FXML
    void cancelButton(ActionEvent event) throws IOException {
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
        alert.setTitle("Close Modify Product Form Confirmation");
        alert.setHeaderText("Heading back to Inventory Management Form.\r" + "Any unsaved data will be LOST!\r" + "Are you sure you want to continue?\r" + "Click 'OK' to confirm exit.\r" + "Click 'Cancel' to go back.");
        Optional<ButtonType> confirmation = alert.showAndWait();

        if (confirmation.isPresent() && confirmation.get() == ButtonType.OK) {
            InventoryManagement.returnToMenu(event);
        }
    }


    /**
     * removeAssociatedPartButton removes the associatedPart[Bottom table] item from the product.
     * @param event
     * LOGICAL ERROR: Make it so nothing pops up unless there is items selected in the associatedPane.
     *      - Fixed: added alert inside (part != null), then added a confirmation button.
     */
    @FXML
    void removeAssociatedPartButton(ActionEvent event) {
        Part part = modifyAssociatedProductPane.getSelectionModel().getSelectedItem();

        if (part != null) {
            Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
            alert.setTitle("ALERT: Removing Part");
            alert.setHeaderText("You are about to remove Part: '" + part.getName() + "'?");
            alert.setContentText("Are you sure you want to remove this part?\r" + "Click 'OK' to confirm exit.\r" + "Click 'Cancel' to go back.");
            Optional<ButtonType> confirmation = alert.showAndWait();
            if (confirmation.isPresent() && confirmation.get() == ButtonType.OK) {
                newProduct.deleteAssociatedPart(part);
            }
        }
        else {
            Alert alert = new Alert(Alert.AlertType.WARNING);
            alert.setTitle("Error removing Associated Part");
            alert.setHeaderText("An error has occurred while trying to remove an Associated Part");
            alert.setContentText("Please select a part and try again.");
            alert.showAndWait();
        }
    }


    /**
     * saveButton allows a modified product to be saved to the products table.
     * @param event
     * @throws IOException
     * FUTURE ENHANCEMENT: Make it so previously deleted ids can be reused to new parts!
     * LOGICAL ERROR: ITEMS KEEP ON OVERWRITING NEXT ONE IN LINE
     *     - Fixed: Same exact issue I was having with "modifyPartFormController.saveButton(ActionEvent event)".
     * LOGICAL ERROR: Why does associated parts turn invisible when re-modifying item.
     *     - Fixed: It was the way my productSelection was selecting the parts. Had to fix updateProduct method.
     */
    @FXML void saveButton(ActionEvent event) throws IOException {
        try {
            int productMinimum = Integer.parseInt(modifyProductFormMin.getText());
            int productMaximum = Integer.parseInt(modifyProductFormMax.getText());
            int productInventory = Integer.parseInt(modifyProductFormInv.getText());
            String name = modifyProductFormName.getText();
            Product modifiedProduct = new Product(1, "n", 1, 1, 1, 1);
            if (InventoryManagement.isMinimumValueAllowed(productMinimum, productMaximum) && InventoryManagement.isInventoryAllowed(productMinimum, productMaximum, productInventory) && InventoryManagement.isThereAName(name)) {
                modifiedProduct.setProductId(Integer.parseInt(modifyProductFormId.getText()));
                modifiedProduct.setProductName(modifyProductFormName.getText());
                modifiedProduct.setProductStock(Integer.parseInt(modifyProductFormInv.getText()));
                modifiedProduct.setProductPrice(Double.parseDouble(modifyProductFormPrice.getText()));
                modifiedProduct.setProductMin(Integer.parseInt(modifyProductFormMin.getText()));
                modifiedProduct.setProductMax(Integer.parseInt(modifyProductFormMax.getText()));
                Inventory.updateProduct(selectedIndex, modifiedProduct); //Not sure if this is correct either| EDIT: It works

                for (Part part : associatedParts) {
                    modifiedProduct.addAssociatedPart(part);
                }
                InventoryManagement.returnToMenu(event);
            }
        } catch(Exception f) {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Adding Product Form ERROR");
            alert.setHeaderText("Error creating Product");
            alert.setContentText("This form has invalid values or inputs.");
            alert.initModality(Modality.WINDOW_MODAL);
            alert.showAndWait();
        }
    }


    /**
     * productSelection is the method used for setting the current product.
     * This method is used hand to hand with the "mainFormInventoryManagement.onActionProductModify"
     * @param index
     * @param selectedProduct
     */
    public void productSelection(int index, Product selectedProduct) {
        this.newProduct = selectedProduct;
        this.selectedIndex = index;
        modifyProductFormId.setText(String.valueOf(newProduct.getProductId()));
        modifyProductFormName.setText(newProduct.getProductName());
        modifyProductFormInv.setText(Integer.toString(newProduct.getProductStock()));
        modifyProductFormPrice.setText(Double.toString(newProduct.getProductPrice()));
        modifyProductFormMin.setText(Integer.toString(newProduct.getProductMin()));
        modifyProductFormMax.setText(Integer.toString(newProduct.getProductMax()));
        modifyAssociatedProductPane.setItems(newProduct.getAllAssociatedParts());
        this.associatedParts = newProduct.getAllAssociatedParts();
    }


    /**
     * Initialize cell to a set property.
     * @param location
     * @param resources
     */
    @Override
    public void initialize(URL location, ResourceBundle resources) {
        modifyProductFormId.setText(String.valueOf(Inventory.getAllProducts().size()+1)); //unsure if this is correct.| EDIT: It works.


        modifyProductId.setCellValueFactory(new PropertyValueFactory<>("id"));
        modifyProductInventoryLevel.setCellValueFactory(new PropertyValueFactory<>("stock"));
        modifyProductName.setCellValueFactory(new PropertyValueFactory<>("name"));
        modifyProductPrice.setCellValueFactory(new PropertyValueFactory<>("price"));
        modifyProductPane.setItems(Inventory.getAllParts());

        modifyAssociatedProductId.setCellValueFactory(new PropertyValueFactory<>("id"));
        modifyAssociatedProductInventoryLevel.setCellValueFactory(new PropertyValueFactory<>("stock"));
        modifyAssociatedProductName.setCellValueFactory(new PropertyValueFactory<>("name"));
        modifyAssociatedProductPrice.setCellValueFactory(new PropertyValueFactory<>("price"));
    }
}
