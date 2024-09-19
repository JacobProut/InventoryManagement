package com.example.c482attempt;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Modality;
import javafx.stage.Stage;

import java.io.IOException;
import java.net.URL;
import java.util.Optional;
import java.util.ResourceBundle;


/**
 * mainFormInventoryManagement is the Main Form Controller
 */
public class mainFormInventoryManagement implements Initializable {

    //Parts Pane [Left Table]
    @FXML private TableView<Part> partsPane;
    @FXML private TextField searchPartField;
    @FXML private TableColumn<Part, Integer> partsColID;
    @FXML private TableColumn<Part, Integer> partsColInventoryLevel;
    @FXML private TableColumn<Part, String> partsColName;
    @FXML private TableColumn<Part, Double> partsColPrice;

    //Products Pane [Right Table]
    @FXML private TableView<Product> productPane;
    @FXML private TextField searchProductField;
    @FXML private TableColumn<Product, Integer> productColID;
    @FXML private TableColumn<Product, Integer> productColInventoryLevel;
    @FXML private TableColumn<Product, String> productColName;
    @FXML private TableColumn<Product, Double> productColPrice;

    Stage stage;
    Parent scene;
    private Part partToFind;
    private Product productToFind;


    /**
     * onActionExit is a method that closes the program all together.
     * @param event
     * LOGICAL ERROR: couldn't get button to work properly.
     *     - Fixed: isPresent() & .get() both needed to be in if statement.
     */
    @FXML
    void onActionExit(ActionEvent event) {
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
        alert.setTitle("Close Program");
        alert.setHeaderText("Are you sure you want to exit the program?");
        alert.setContentText("Click 'OK' to confirm deletion.\r" + "Click 'Cancel' to go back.");
        Optional<ButtonType> confirmation = alert.showAndWait();
        if (confirmation.isPresent() && confirmation.get() == ButtonType.OK) {
            System.exit(0);
        }

    }

    /**
     * onActionPartsAdd brings up a new window allowing parts to be added to the parts table.
     * @param event
     * @throws IOException
     * LOGICAL ERROR: Cant figure out how to get the correct stage.
     *      - Fixed: took a while for me to figure out that the webinars cover this topic.
     */
    @FXML
    void onActionPartsAdd(ActionEvent event) throws IOException {
        stage = (Stage) ((Button)event.getSource()).getScene().getWindow();
        scene = FXMLLoader.load(getClass().getResource("Add_Part_Form.fxml"));
        stage.setScene(new Scene(scene));
        stage.show();
        stage.setTitle("Add Part Form");
    }


    /**
     * onActionProductAdd brings up a new window allowing products to be added to the products table.
     * @param event
     * @throws IOException
     */
    //Same problem as "onActionPartAdd".
    //Resolved the issue once I figured out method above^
    @FXML
    void onActionProductAdd(ActionEvent event) throws IOException {
        stage = (Stage) ((Button)event.getSource()).getScene().getWindow();
        scene = FXMLLoader.load(getClass().getResource("Add_Product_Form.fxml"));
        stage.setScene(new Scene(scene));
        stage.show();
        stage.setTitle("Add Product Form");
    }


    /**
     * onActionPartDelete deletes a selected part.
     * @param event
     */
    //EDIT: This is updated delete method. Using "Inventory.deletePart method"
    //EDIT: The old version contained only ".remove" instead of having "Inventory.deletePart"
    @FXML
    void onActionPartsDelete(ActionEvent event) {
        Part partSelection = partsPane.getSelectionModel().getSelectedItem();
        if (partSelection == null) {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Part selection Error");
            alert.setHeaderText("Part has not been selected");
            alert.showAndWait();
        } else {
            Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
            alert.setTitle("Part Deletion Confirmation");
            alert.setHeaderText("Are you sure you want to delete: '" + partSelection.getName() + "'?");
            alert.setContentText("Click 'OK' to confirm deletion.\r" + "Click 'Cancel' to go back.");
            Optional<ButtonType> result = alert.showAndWait();
            if (result.isPresent() && result.get() == ButtonType.OK) {
                Inventory.deletePart(partSelection);
            }
        }
    }


    /**
     * onActionProductDelete deletes a selected product.
     * @param event
     */
    //EDIT: This is updated delete method. Using "Inventory.deleteProduct method".
    //EDIT: Old version contained only ".remove" instead of having "Inventory.deleteProduct"
    @FXML
    void onActionProductDelete(ActionEvent event) {
        Product selectedProduct = productPane.getSelectionModel().getSelectedItem();
        if (selectedProduct == null) {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Product selection Error");
            alert.setHeaderText("Product has not been selected");
            alert.showAndWait();
        } else {
            Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
            alert.setTitle("Product Deletion Confirmation");
            alert.setHeaderText("Are you sure you want to delete: '" + selectedProduct.getProductName() + "'?");
            alert.setContentText("Click 'OK' to confirm deletion.\r" + "Click 'Cancel' to go back.");
            Optional<ButtonType> buttonPressResult = alert.showAndWait();
            if (buttonPressResult.isPresent() && buttonPressResult.get() == ButtonType.OK) {
                if (selectedProduct.getAllAssociatedParts().isEmpty()) {
                    Inventory.deleteProduct(selectedProduct);
                } else {
                    Alert alert2 = new Alert(Alert.AlertType.ERROR);
                    alert2.setTitle("Deletion Error");
                    alert2.setHeaderText("You are trying to delete a product that has one or multiple Associated Parts linked to it");
                    alert2.setContentText("Removing the associated parts first will allow you to delete the product!");
                    alert2.showAndWait();
                }
            }
        }
    }


    /**
     * onActionPartSearch is a method that allows usage to the part search field.
     * @param event
     * Fix the getting the alert after every search
     *  - Fixed: Had to move "isEmpty" out of scope of for loop.
     * Fix #2: Had to add lookupPart method.
     * Fix #3 : NEED TO ADD: FIND A WAY TO ADD LOOKUP PART ID.
     *  - Fixed: LookupPart Method has been added.
     *  FUTURE ENHANCEMENT: Re-Write the search field to allow all lowerCase and upperCase letters.
     */
    @FXML
    void onActionPartsSearch(ActionEvent event) {
        String searchedPart = searchPartField.getText();

        if(searchedPart.isEmpty()) {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Error searching for Parts");
            alert.setHeaderText("The Part name or Id entered could not be found!");
            alert.showAndWait();
            partsPane.setItems(Inventory.getAllParts());
        }
        else {
            boolean findItem = false;
            try {
                partToFind = Inventory.lookupPart(Integer.parseInt(searchedPart));
                if (partToFind != null) {
                    ObservableList<Part> part = FXCollections.observableArrayList();
                    part.add(partToFind);
                    partsPane.setItems(part);
                }
                else {
                    Alert alert = new Alert(Alert.AlertType.ERROR);
                    alert.setTitle("Error searching for Parts");
                    alert.setHeaderText("The searched Id does not match any of the listings!");
                    alert.showAndWait();
                    partsPane.setItems(Inventory.getAllParts());
                }
            } catch (NumberFormatException e) {
                ObservableList<Part> allParts = Inventory.getAllParts();
                if(allParts.isEmpty()){
                    Alert alert = new Alert(Alert.AlertType.ERROR);
                    alert.setTitle("Error searching for Parts");
                    alert.setHeaderText("No Parts found in listings.\r" + "Please create Parts first, then try again.");
                    alert.showAndWait();
                    partsPane.setItems(Inventory.getAllParts());

                } else {
                    for (Part p : allParts) {
                        if (p.getName().contains(searchedPart)) {
                            ObservableList<Part> part;
                            findItem = true;
                            part = Inventory.lookupPart(searchedPart);
                            partsPane.setItems(part);
                        }
                    }
                    if (!findItem) {
                        Alert alert = new Alert(Alert.AlertType.ERROR);
                        alert.setTitle("Error searching for Parts");
                        alert.setHeaderText("Searched item does not match any listings!");
                        alert.showAndWait();
                        partsPane.setItems(Inventory.getAllParts());
                    }
                }
            }
        }
        resetSearchFields();
    }


    /**
     * onActionProductSearch is a method that allows usage to the product search field.
     * @param event
     * FUTURE ENHANCEMENT: Re-Write the search field to allow all lowerCase and upperCase letters.
     */
    //Had issues with this method too, but I figured it out once I finished "onActionPartSearch"
    @FXML
    void onActionProductSearch(ActionEvent event) {
        String searchedProduct = searchProductField.getText();

        if (searchedProduct.isEmpty()) {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Error searching for Products");
            alert.setHeaderText("The Product name or Id entered could not be found!");
            alert.showAndWait();
            productPane.setItems(Inventory.getAllProducts());
        }
        else {
            boolean findItem = false;
            try {
                productToFind = Inventory.lookupProduct(Integer.parseInt(searchedProduct));
                if (productToFind != null) {
                    ObservableList<Product> products = FXCollections.observableArrayList();
                    products.add(productToFind);
                    productPane.setItems(products);
                } else {
                    Alert alert = new Alert(Alert.AlertType.ERROR);
                    alert.setTitle("Error searching for Parts");
                    alert.setHeaderText("Could not match product to any listed ID's.");
                    alert.showAndWait();
                    productPane.setItems(Inventory.getAllProducts());
                }
            } catch (NumberFormatException e) {
                ObservableList<Product> allProducts = Inventory.getAllProducts();
                if (allProducts.isEmpty()) {
                    Alert alert = new Alert(Alert.AlertType.ERROR);
                    alert.setTitle("Error Searching for Products");
                    alert.setHeaderText("No products found in listings.\r"  + "Please create Products first, then try again.");
                    alert.showAndWait();
                    productPane.setItems(Inventory.getAllProducts());
                }
                else {
                    for (Product p : allProducts) {
                        if (p.getProductName().contains(searchedProduct)) {
                            ObservableList<Product> products;
                            findItem = true;
                            products = Inventory.lookupProduct(searchedProduct);
                            productPane.setItems(products);
                        }
                    }
                    if (!findItem) {
                        Alert alert = new Alert(Alert.AlertType.ERROR);
                        alert.setTitle("Error Searching for Parts");
                        alert.setHeaderText("Searched item does not match any listings!");
                        alert.showAndWait();
                        productPane.setItems(Inventory.getAllProducts());
                    }
                }
            }
        }
        resetSearchFields();
    }


    /**
     * resetSearchFields was created to help with redundant code.
     */
    public void resetSearchFields() {
        searchProductField.setText("");
        searchPartField.setText("");
    }


    /**
     * onActionPartsModify uses a stage to load a scene.
     * @param event
     * @throws IOException
     * NOTE: modifyPartFormController.saveButton explains all the issues I had with the onActionPartModify/onActionProductModify methods.
     */
    @FXML
    void onActionPartsModify(ActionEvent event) throws IOException {
        try {
            stage = (Stage) ((Button) event.getSource()).getScene().getWindow();
            FXMLLoader loadup = new FXMLLoader(getClass().getResource("Modify_Part_Form.fxml"));
            scene = loadup.load();
            Part partSelection = partsPane.getSelectionModel().getSelectedItem();
            modifyPartFormController controller = loadup.getController();
            controller.partSelection(partsPane.getSelectionModel().getSelectedIndex(), partSelection);
            stage.setScene(new Scene(scene));
            stage.show();
            stage.setTitle("Modify Part Form");
        }
        catch (Exception c) {
                Alert alert = new Alert(Alert.AlertType.ERROR);
                alert.setTitle("No Part Selected");
                alert.setHeaderText("Please select a part then try again.");
                alert.initModality(Modality.WINDOW_MODAL);
                alert.showAndWait();
        }
    }


    /**
     * onActionProductsModify uses a stage to load a scene.
     * @param event
     * @throws IOException
     * NOTE: modifyPartFormController.saveButton explains all the issues i had with the onActionPartModify/onActionProductModify methods.
     */
    @FXML
    void onActionProductsModify(ActionEvent event) throws IOException {
        try {
            stage = (Stage) ((Button) event.getSource()).getScene().getWindow();
            FXMLLoader loadupper = new FXMLLoader(getClass().getResource("Modify_Product_Form.fxml"));
            scene = loadupper.load();
            Product productSelection = productPane.getSelectionModel().getSelectedItem();
            modifyProductFormController controller = loadupper.getController();
            controller.productSelection(productPane.getSelectionModel().getSelectedIndex(), productSelection);
            stage.setScene(new Scene(scene));
            stage.show();
            stage.setTitle("Modify Product Form");
        } catch (Exception c) {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("No Product Selected");
            alert.setHeaderText("Please select a product then try again.");
            alert.initModality(Modality.WINDOW_MODAL);
            alert.showAndWait();
        }
    }

    /**
     * Initialize sets the cell values to a property.
     * @param url
     * @param resourceBundle
     */
    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {

        partsPane.setItems(Inventory.getAllParts());
        productPane.setItems(Inventory.getAllProducts());

        partsColID.setCellValueFactory(new PropertyValueFactory<>("id"));
        partsColInventoryLevel.setCellValueFactory(new PropertyValueFactory<>("stock"));
        partsColName.setCellValueFactory(new PropertyValueFactory<>("name"));
        partsColPrice.setCellValueFactory(new PropertyValueFactory<>("price"));

        productColID.setCellValueFactory(new PropertyValueFactory<>("productId"));
        productColInventoryLevel.setCellValueFactory(new PropertyValueFactory<>("productStock"));
        productColName.setCellValueFactory(new PropertyValueFactory<>("productName"));
        productColPrice.setCellValueFactory(new PropertyValueFactory<>("productPrice"));

    }
}
