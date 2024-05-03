package org.example.gestorempleadosioritzperugorria;

import Modelo.LectorTXT;
import javafx.collections.FXCollections;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Node;

import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.stage.FileChooser;


import java.io.File;
import java.net.URL;
import java.util.ResourceBundle;

public class ControllerPPrincipal implements Initializable {

    @FXML
    private ComboBox<String> combobox;

    @FXML
    private Button botonCargar;

    @FXML
    private Label resultado;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        combobox.setItems(FXCollections.observableArrayList(
                "Scada Manager", "Sales Manager", "Product Owner", "Product Manager", "Analyst Programmer", "Junior Programmer"
            )
        );
    }

    /**
     * El metodo primero carga un archivo usando el ActionEvent
     * del parametro de entrada, y luego llama a LectorTXT
     */
    @FXML
    public void cargarDesdeArchivo(ActionEvent event){
        FileChooser cargador = new FileChooser();

        cargador.getExtensionFilters().addAll(
                new FileChooser.ExtensionFilter("Archivos de texto", "*.txt") //Solo poder elejir archivos .txt
        );

        Node node = (Node) event.getSource();
        File archivo = cargador.showOpenDialog(node.getScene().getWindow()); //Abrir una ventana para escojer archivo

        if (archivo != null) {
            LectorTXT lector = new LectorTXT();
            resultado.setText(lector.cargarDesdeArchivo(archivo)); //El mensaje de retorno, dependiendo si ha salido bien o mal la insercion dice una cosa u otra.
        }
    }
}