package org.example.gestorempleadosioritzperugorria;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;

public class HelloController {
    @FXML
    private Label welcomeText;

    @FXML
    ObservableList<String> profesiones = FXCollections.observableArrayList(
        "Scada Manager", "Sales Manager", "Product Owner", "Product Manager", "Analyst Programmer", "Junior Programmer"
    );

    @FXML
    private ComboBox<String> combobox = new ComboBox<>(profesiones);


    @FXML
    protected void onHelloButtonClick() {
        welcomeText.setText("Welcome to JavaFX Application!");
    }
}