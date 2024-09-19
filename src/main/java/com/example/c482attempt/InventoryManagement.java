package com.example.c482attempt;

import javafx.application.Application;
import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.stage.Stage;

import java.io.IOException;

//            !!!  JAVADOCS file is located at "c482attempt\javadocs"  !!!

/**
 * InventoryManagement is the main class that creates the Inventory Management Application.
 */
public class InventoryManagement extends Application {

    /**
     * start method loads the MainForm.fxml file.
     * @param stage the primary stage for this application, onto which
     * the application scene can be set.
     * Applications may create other stages, if needed, but they will not be
     * primary stages.
     * @throws IOException
     */
    @Override
    public void start(Stage stage) throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(InventoryManagement.class.getResource("MainForm.fxml"));
        Scene scene = new Scene(fxmlLoader.load(), 1025, 400);
        stage.setTitle("Inventory Management System");
        stage.setScene(scene);
        stage.show();
    }


    /**
     * returnToMenu is a stage loader that loads the main menu.
     * @param event
     * @throws IOException
     */
    //This method is called multiple times throughout the program.
    public static void returnToMenu(ActionEvent event) throws IOException {
        Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        Parent parent = FXMLLoader.load(InventoryManagement.class.getResource("MainForm.fxml"));
        Scene scene = new Scene(parent);
        stage.setScene(scene);
        stage.show();
    }


    /**
     * isMinimumValueAllowed is a boolean method that checks to make sure minimum value is less-than-or-equal-to zero and/or minimum value is greater-than-or-equal-to max value.
     * @param min
     * @param max
     * @return isValueAllowed
     */
    public static boolean isMinimumValueAllowed(int min, int max) {
        boolean isValueAllowed = true;
        if (min <= 0 || min > max) {
            isValueAllowed = false;
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Minimum value error");
            alert.setHeaderText("Invalid minimum value entered");
            alert.setContentText("The minimum value must be greater than zero and must be smaller than the maximum number");
            alert.showAndWait();
        }
        return isValueAllowed;
    }


    /**
     * isInventoryAllowed is a boolean method that checks to make sure stock is less than minimum and/or stock is greater than max.
     * @param min
     * @param max
     * @param stock
     * @return isInvAllowed
     */
    public static boolean isInventoryAllowed(int min, int max, int stock) {
        boolean isInvAllowed = true;
        if (stock < min || stock > max) {
            isInvAllowed = false;
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Stock value error");
            alert.setHeaderText("Invalid Inventory value entered");
            alert.setContentText("Inventory value must be equal or greater than minimum but less than maximum values");
            alert.showAndWait();
        }
        return isInvAllowed;
    }


    /**
     * isThereAName is a boolean method that checks to see if there is a name in the name field.
     * @param name
     * @return isName
     */
    public static boolean isThereAName(String name) {
        boolean isName = true;
        if (name.isEmpty()) {
            isName = false;
            Alert alert = new Alert(Alert.AlertType.WARNING);
            alert.setTitle("Name warning");
            alert.setHeaderText("Invalid Name Entered");
            alert.setContentText("Please input a Name, then try again!");
            alert.showAndWait();
        }
        return isName;
    }


    //                 !!!  JAVADOCS file is located at "c482attempt\javadocs"  !!!
    /**
     * main method is needed for program to launch.
     * Inside the main method is pre-defined items for parts/product tables.
     * @param args
     */
    public static void main(String[] args) {

        //Outsourced Parts
        Inventory.addPart(new Outsourced(Inventory.getPartIdCounter(), "Motherboard", 324.99, 10, 3, 15, "Asus"));
        Inventory.addPart(new Outsourced(Inventory.getPartIdCounter(), "Power Supply", 149.99, 5, 2, 10, "Corsair"));
        Inventory.addPart(new Outsourced(Inventory.getPartIdCounter(), "DDR5 RAM", 174.99, 5, 2, 10, "Corsair"));

        //InHouse Parts
        Inventory.addPart(new InHouse(Inventory.getPartIdCounter(), "Liquid Cooler", 135.99, 7, 3, 15, 25));
        Inventory.addPart(new InHouse(Inventory.getPartIdCounter(), "CPU", 424.99, 3, 2, 12, 14));

        //Products
        Inventory.addProduct(new Product(Inventory.getProductIdCounter(), "Game ready computer", 1499.99, 8, 1, 10));
        Inventory.addProduct(new Product(Inventory.getProductIdCounter(), "Streaming Deck", 699.99, 2, 1, 8));

        launch(args);
    }

}