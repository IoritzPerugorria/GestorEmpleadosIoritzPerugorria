package org.example.gestorempleadosioritzperugorria;

import javafx.collections.FXCollections;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.stage.FileChooser;
import javafx.stage.Stage;
import javafx.stage.Window;

import java.io.File;
import java.net.URL;
import java.util.ResourceBundle;

public class ControllerPPrincipal implements Initializable {

    @FXML
    private ComboBox<String> combobox;

    @FXML
    private Button botonCargar;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        combobox.setItems(FXCollections.observableArrayList(
                "Scada Manager", "Sales Manager", "Product Owner", "Product Manager", "Analyst Programmer", "Junior Programmer"
            )
        );
    }

    @FXML
    public void cargarDesdeArchivo(ActionEvent event){
        FileChooser cargador = new FileChooser();

        cargador.getExtensionFilters().addAll(
                new FileChooser.ExtensionFilter("Archivos de texto", "*.txt")
        );

        Node node = (Node) event.getSource();
        File archivo = cargador.showOpenDialog(node.getScene().getWindow());
    }
}