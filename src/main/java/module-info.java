module com.example.c482attempt {
    requires javafx.controls;
    requires javafx.fxml;


    opens com.example.c482attempt to javafx.fxml;
    exports com.example.c482attempt;
}