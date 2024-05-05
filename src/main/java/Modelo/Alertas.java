package Modelo;

import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;

import java.util.Optional;

/**
 * Este metodo crea Alerts para una situacion u otra.
 */
public class Alertas {

    Alert alert; //La alerta en cuestion

    /**
     * El constructor para la alerta. Recibe como parametro el titulo y el mensaje
     * de la alerta, y tambien el AlertType del Alert.
     */
    public Alertas(String titulo, String mensaje, Alert.AlertType tipo){
        alert = new Alert(tipo);
        alert.setTitle(titulo);
        alert.setHeaderText(mensaje);
    }

    /**
     * Para la alerta de confirmacion, que retorna true o false.
     */
    public boolean alertaEliminar(){
        Optional<ButtonType> result = alert.showAndWait();
        return result.filter(buttonType -> buttonType == ButtonType.OK).isPresent();

    }

    /**
     * Muestra la alerta.
     */
    public void mostrarAlerta(){
        alert.showAndWait();
    }
}
