package com.example.c482attempt;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

/**
 * Product class is created for an Object called Product.
 */
public class Product {

    /**
     * ObservableList is a list from Part: saving to associatedParts.
     */
    private ObservableList<Part> associatedParts = FXCollections.observableArrayList();

    private int productId;
    private String productName;
    private double productPrice;
    private int productStock;
    private int productMin;
    private int productMax;


    /**
     *
     * Product is a Product class for an Object called Product.
     *
     */
    public Product(int productId, String productName, double productPrice, int productStock, int productMin, int productMax) {
        this.productId = productId;
        this.productName = productName;
        this.productPrice = productPrice;
        this.productStock = productStock;
        this.productMin = productMin;
        this.productMax = productMax;
    }

    /**
     *
     * setProductId sets the products id.
     * @param productId
     */
    public void setProductId(int productId) {
        this.productId = productId;
    }

    /**
     *
     * setProductName sets the products name.
     * @param productName
     */
    public void setProductName(String productName) {
        this.productName = productName;
    }

    /**
     *
     * setProductPrice sets the products price.
     * @param productPrice
     */
    public void setProductPrice(double productPrice) {
        this.productPrice = productPrice;
    }

    /**
     *
     * setProductStock sets the products stock[Inventory value].
     * @param productStock
     */
    public void setProductStock(int productStock) {
        this.productStock = productStock;
    }


    /**
     *
     * setProductMin sets the products minimum value.
     * @param productMin
     */
    public void setProductMin(int productMin) {
        this.productMin = productMin;
    }

    /**
     *
     * setProductMax sets the products maximum value.
     * @param productMax
     */
    public void setProductMax(int productMax) {
        this.productMax = productMax;
    }


    /**
     *
     * getProductId gets the products id.
     * @return productId
     */
    public int getProductId() {
        return productId;
    }


    /**
     *
     * getProductName gets the products name.
     * @return productName
     */
    public String getProductName() {
        return productName;
    }


    /**
     *
     * getProductPrice gets the products price.
     * @return productPrice
     */
    public double getProductPrice() {
        return productPrice;
    }


    /**
     *
     * getProductStock gets the products stock[Inventory Value].
     * @return productStock
     */
    public int getProductStock() {
        return productStock;
    }


    /**
     *
     * getProductMin gets the products minimum value.
     * @return productMin
     */
    public int getProductMin() {
        return productMin;
    }

    /**
     *
     * getProductsMax gets the products maximum value.
     * @return productMax
     */
    public int getProductMax() {
        return productMax;
    }


    /**
     *
     * addAssociatedParts adds an associatedPart to a product.
     * @param part
     */
    public void addAssociatedPart(Part part) {
        associatedParts.add(part);
    }


    /**
     *
     * deleteAssociatedPart deletes an associatedPart from a product.
     * @param selectedAssociatedPart
     * @return true
     */
    //No clue if return false is the correct thing to use
    //DONE: FIND A WAY TO IMPLEMENT THIS IN modifyProductFormController removeAssociatedPartButton section.
    public boolean deleteAssociatedPart(Part selectedAssociatedPart){
        if(associatedParts.contains(selectedAssociatedPart)){
            associatedParts.remove(selectedAssociatedPart);
            return true;
        } else {
            return false;
        }
    }


    /**
     *
     * getAllAssociatedParts retrieves the list of associatedParts.
     * @return associatedParts
     */
    public ObservableList<Part> getAllAssociatedParts() {
        return associatedParts;
    }

}