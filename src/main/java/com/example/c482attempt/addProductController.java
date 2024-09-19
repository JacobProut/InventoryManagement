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
 * addProductController works with/adds products to Product Inventory.
 */
public class addProductController implements Initializable {

    //Text Fields
    @FXML private TextField searchField;
    @FXML private TextField addProductFormID;
    @FXML private TextField addProductFormInv;
    @FXML private TextField addProductFormMax;
    @FXML private TextField addProductFormMin;
    @FXML private TextField addProductFormName;
    @FXML private TextField addProductFormPrice;

    // Parts Section [Top Table]
    @FXML private TableView<Part> partPane;
    @FXML private TableColumn<Part, Integer> partsColID;
    @FXML private TableColumn<Part, Integer> partsColInventoryLevel;
    @FXML private TableColumn<Part, String> partsColName;
    @FXML private TableColumn<Part, Double> partsColPrice;

    //Associated Parts Section [Bottom Table]
    @FXML private TableView<Part> associatedPartsPane;
    @FXML private TableColumn<Part, Integer> associatedPartsColID;
    @FXML private TableColumn<Part, Integer> associatedPartsColInventoryLevel;
    @FXML private TableColumn<Part, String> associatedPartsColName;
    @FXML private TableColumn<Part, Double> associatedPartsColPrice;

    /**
     * ObservableList is a list from Part: saving to associatedParts.
     */
    private ObservableList<Part> associatedParts = FXCollections.observableArrayList();

    Product newProduct = new Product(1, "n", 1, 1, 1, 1);
    private Part partToFind;


    /**
     * onActionPartSearch allows the user to search for items via text.
     * @param event
     * FUTURE ENHANCEMENT: Re-Write the search field to allow all lowerCase and upperCase letters.
     * LOGICAL ERROR: Change Search to include lookupProduct methods? Or add to mainFormInventoryManagement.
     *     - FIXED: Updated Search method. [CASE SENSITIVE]
     */
    //SAME exact method[Besides for tableview names] as mainFormInventoryManagement.onActionPartSearch.
    @FXML
    void onActionPartsSearch(ActionEvent event) {
        String searchedPart = searchField.getText();

        if(searchedPart.isEmpty()) {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Error searching for Parts");
            alert.setHeaderText("The Part name or Id entered could not be found!");
            alert.showAndWait();
            partPane.setItems(Inventory.getAllParts());
        }
        else {
            boolean findItem = false;
            try {
                partToFind = Inventory.lookupPart(Integer.parseInt(searchedPart));
                if (partToFind != null) {
                    ObservableList<Part> part = FXCollections.observableArrayList();
                    part.add(partToFind);
                    partPane.setItems(part);
                }
                else {
                    Alert alert = new Alert(Alert.AlertType.ERROR);
                    alert.setTitle("Error searching for Parts");
                    alert.setHeaderText("The searched Id does not match any of the listings!");
                    alert.showAndWait();
                    partPane.setItems(Inventory.getAllParts());
                }
            } catch (NumberFormatException e) {
                ObservableList<Part> allParts = Inventory.getAllParts();
                if(allParts.isEmpty()){
                    Alert alert = new Alert(Alert.AlertType.ERROR);
                    alert.setTitle("Error searching for Parts");
                    alert.setHeaderText("No Parts found in listings.\r" + "Please create Parts first, then try again.");
                    alert.showAndWait();
                    partPane.setItems(Inventory.getAllParts());

                } else {
                    for (Part p : allParts) {
                        if (p.getName().contains(searchedPart)) {
                            ObservableList<Part> part;
                            findItem = true;
                            part = Inventory.lookupPart(searchedPart);
                            partPane.setItems(part);
                        }
                    }
                    if (!findItem) {
                        Alert alert = new Alert(Alert.AlertType.ERROR);
                        alert.setTitle("Error searching for Parts");
                        alert.setHeaderText("Searched item does not match any listings!");
                        alert.showAndWait();
                        partPane.setItems(Inventory.getAllParts());
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
     * cancelButton on button click will bring a menu allowing the user to choose to go back to the main menu.
     * @param event
     * @throws IOException
     */
    //Same functionality as modifyProductFormController.cancelButton(ActionEvent event).
    @FXML
    void cancelButton(ActionEvent event) throws IOException {
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
        alert.setTitle("Close Add Product Form Confirmation");
        alert.setHeaderText("Heading back to Inventory Management Form.\r" + "Any unsaved data will be LOST!\r" + "Are you sure you want to continue?\r" + "Click 'OK' to confirm exit.\r" + "Click 'Cancel' to go back.");
        Optional<ButtonType> confirmation = alert.showAndWait();

        if (confirmation.isPresent() && confirmation.get() == ButtonType.OK) {
            InventoryManagement.returnToMenu(event);
        }
    }


    /**
     * addButton adds a associatedPart to the bottom table.
     * @param event
     */
    //Same functionality[Besides tableview names] as modifyProductFormController.addButton(ActionEvent event).
    @FXML
    void addButton(ActionEvent event) {
        Part selectedPart = partPane.getSelectionModel().getSelectedItem();
        if (selectedPart != null) {
            associatedParts.add(selectedPart);
            associatedPartsPane.setItems(associatedParts);
        } else {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Selecting Part Error");
            alert.setHeaderText("Please select a part");
            alert.showAndWait();
        }

    }


    /**
     * removeAssociatedPartButton removes the associatedPart[Bottom table] item from the product.
     * @param event
     * LOGICAL ERROR: FIND A WAY TO ADD DELETEASSOCIATEDPART METHOD!
     *      - deleteAssociatedPart Added!
     * LOGICAL ERROR: Make it so nothing pops up unless there is items selected in the associatedPane.
     *      - Fixed: had to move alert into scope of (part != null).
     */
    @FXML
    void removeAssociatedPartButton(ActionEvent event) {
            Part part = associatedPartsPane.getSelectionModel().getSelectedItem();
            if (part != null) {
                Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
                alert.setTitle("ALERT: Removing Part");
                alert.setHeaderText("You are about to remove Part: '" + part.getName() + "'?");
                alert.setContentText("Are you sure you want to remove this part?\r" + "Click 'OK' to confirm exit.\r" + "Click 'Cancel' to go back.");
                Optional<ButtonType> confirmation = alert.showAndWait();
                if (confirmation.isPresent() && confirmation.get() == ButtonType.OK) {
                    associatedParts.remove(part);
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
     * saveButton allows a product to be created and saved to the products table.
     * @param event
     * FUTURE ENHANCEMENT: Make it so previously deleted ids can be reused to new parts!
     * LOGICAL ERROR: Incrementing issue with id
     *      - FIXED: Found out I needed to make a static productIdCounter w/ a .get method.
     */
    @FXML
    void saveButton(ActionEvent event) {
        try {
            int productMinimum = Integer.parseInt(addProductFormMin.getText());
            int productMaximum = Integer.parseInt(addProductFormMax.getText());
            int productInventory = Integer.parseInt(addProductFormInv.getText());
            String name = addProductFormName.getText();

            if (InventoryManagement.isMinimumValueAllowed(productMinimum, productMaximum) && InventoryManagement.isInventoryAllowed(productMinimum, productMaximum, productInventory) && InventoryManagement.isThereAName(name)) {
                newProduct.setProductId(Integer.parseInt(addProductFormID.getText()));
                newProduct.setProductName(addProductFormName.getText());
                newProduct.setProductStock(Integer.parseInt(addProductFormInv.getText()));
                newProduct.setProductPrice(Double.parseDouble(addProductFormPrice.getText()));
                newProduct.setProductMin(Integer.parseInt(addProductFormMin.getText()));
                newProduct.setProductMax(Integer.parseInt(addProductFormMax.getText()));
                Inventory.addProduct(newProduct);

                for (Part part : associatedParts) {
                    newProduct.addAssociatedPart(part);
                }

                InventoryManagement.returnToMenu(event);
            }
            } catch(Exception d) {
                Alert alert = new Alert(Alert.AlertType.ERROR);
                alert.setTitle("Adding Product Form ERROR");
                alert.setHeaderText("Error creating Product");
                alert.setContentText("This form has invalid values or inputs.");
                alert.initModality(Modality.WINDOW_MODAL);
                alert.showAndWait();
            }
        }


    /**
     * Initialize cell to a set property.
     * @param location
     * @param resources
     */
    @Override
    public void initialize(URL location, ResourceBundle resources) {
        addProductFormID.setText(String.valueOf(Inventory.getAllProducts().size()+1));
        partsColID.setCellValueFactory(new PropertyValueFactory<>("id"));
        partsColInventoryLevel.setCellValueFactory(new PropertyValueFactory<>("stock"));
        partsColName.setCellValueFactory(new PropertyValueFactory<>("name"));
        partsColPrice.setCellValueFactory(new PropertyValueFactory<>("price"));
        partPane.setItems(Inventory.getAllParts());


        //Not sure if this is complete or not. Edit: Looks good
        associatedPartsColID.setCellValueFactory(new PropertyValueFactory<>("id"));
        associatedPartsColInventoryLevel.setCellValueFactory(new PropertyValueFactory<>("stock"));
        associatedPartsColName.setCellValueFactory(new PropertyValueFactory<>("name"));
        associatedPartsColPrice.setCellValueFactory(new PropertyValueFactory<>("price"));
        associatedPartsPane.setItems(associatedParts);
    }
}
