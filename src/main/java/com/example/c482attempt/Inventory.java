package com.example.c482attempt;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

/**
 * Inventory is a class that holds methods/inventory for Part and Product classes
 */
public class Inventory {

    //ObservableLists
    /**
     * ObservableList is a list from Part: saving to allParts.
     */
    private static ObservableList<Part> allParts = FXCollections.observableArrayList();
    /**
     * ObservableList is a list from Product: saving to allProducts.
     */
    private static ObservableList<Product> allProducts = FXCollections.observableArrayList();

    //------------------------------------------------
    // PART SECTION

    /**
     * partIdCounter Initializes a static counter.
     */
    public static int partIdCounter = 1;


    /**
     * getPartIdCounter returns partIdCounter + increments it by 1 each time it is called.
     * @return partIdCounter++
     */
    public static int getPartIdCounter() {
        return partIdCounter++;
    }


    /**
     * addPart adds a part to an ObservableList.
     * @param newPart
     */
    public static void addPart(Part newPart) {
        allParts.add(newPart);
    }


    /**
     * lookupPart(int Id) searches for part that matches the Id given.
     * @param Id
     * @return
     */
    //NO CLUE HOW TO DO. EDIT: Watched Observable list and table view webinar.
    public static Part lookupPart(int Id) {

        for (Part lookupPart : allParts) {
            if (lookupPart.getId() == Id)
                return lookupPart;
        }
        return null;
    }


    /**
     * lookupPart(String searchedPart) searches for a part that matches the parts String.
     * @param searchedPart
     * @return lookupPart
     */
    //Struggled coming up with a simple for loop. Figured it out after reviewing coursework.
    public static ObservableList<Part> lookupPart(String searchedPart) {
        ObservableList<Part> lookupPart = FXCollections.observableArrayList();
            for (Part part : getAllParts()) {
                if (part.getName().contains(searchedPart) || (part.getName().equals(searchedPart)) || (String.valueOf(part.getId()).contains(searchedPart))) {
                    lookupPart.add(part);
                }
            }
            return lookupPart;
    }


    /**
     * updatePart updates a selected part on the modifyPartFormController.saveButton
     * @param index
     * @param selectedPart
     */
    public static void updatePart(int index, Part selectedPart) {
        allParts.set(index, selectedPart);
    }


    /**
     * deletePart deletes a selected part in the "mainFormInventoryManagement.onActionPartsDelete" method
     * @param selectedPart
     */
    public static boolean deletePart(Part selectedPart) {
        if (allParts.contains(selectedPart)) {
            allParts.remove(selectedPart);
            return true;
        }
        else
        {
            return false;
        }
    }


    /**
     * getAllParts returns allParts from an ObservableList.
     * @return allParts
     */
    public static ObservableList<Part> getAllParts() {
        return allParts;
    }


    //------------------------------------------------
    // PRODUCT SECTION

    /**
     * productIdCounter Initializes a static counter.
     */
    public static int productIdCounter = 1;

    /**
     * getProductIdCounter returns productIdCounter + increments it by 1 each time it is called.
     * @return productIdCounter++
     */
    public static int getProductIdCounter() {
        return productIdCounter++;
    }

    /**
     * addProduct adds a product to an ObservableList.
     * @param selectedProduct
     */
    public static void addProduct(Product selectedProduct) {
        allProducts.add(selectedProduct);
    }

    /**
     * lookupProduct(int productId) searches for product that matches the productId given.
     * @param productId
     * @return lookupProduct
     */
    public static Product lookupProduct(int productId) {
        for (Product lookupProduct : allProducts) {
            if (lookupProduct.getProductId() == productId) {
                return lookupProduct;
            }
        }
        return null;
    }


    /**
     * lookupProduct(String searchedName) searches for a product that matches the products String.
     * @param searchedName
     * @return lookupProducts in tables
     */
    public static ObservableList<Product> lookupProduct(String searchedName) {
        ObservableList<Product> lookupProducts = FXCollections.observableArrayList();
        for (Product product : getAllProducts()) {
            if(product.getProductName().contains(searchedName) || (String.valueOf(product.getProductId()).contains(searchedName))) {
                lookupProducts.add(product);
            }
        }
        return lookupProducts;
    }

    /**
     * updateProduct updates a selected product on the modifyProductFormController.saveButton
     * @param index
     * @param selectedProduct
     */
    public static void updateProduct(int index, Product selectedProduct) {
        allProducts.set(index, selectedProduct);
    }


    /**
     * deleteProduct deletes a selected product from "mainFormInventoryManagement.onActionProductDelete" method.
     * @param selectedProduct
     */
    public static boolean deleteProduct(Product selectedProduct) {
        if (allProducts.contains(selectedProduct)) {
            allProducts.remove(selectedProduct);
            return true;
        }
        else
        {
            return false;
        }
    }


    /**
     * getAllProducts returns allProducts from an ObservableList.
     * @return allProducts
     */
    public static ObservableList<Product> getAllProducts() {
        return allProducts;
    }
}
