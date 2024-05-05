package Controlador;

import Modelo.Alertas;
import Modelo.ConexionBBDD;
import javafx.collections.FXCollections;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;

import javafx.fxml.Initializable;
import javafx.scene.Node;

import javafx.scene.control.Alert;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.stage.Stage;


import java.net.URL;
import java.util.ArrayList;
import java.util.Objects;
import java.util.ResourceBundle;

/**
 * El constructor de la pantalla de editar. Muestra los mismos campos que en la
 * pantalla de insertar, pero esta vez edita los datos de un empleado ya creado.
 */
public class PantallaEditar implements Initializable {

    @FXML
    private Label labelTop;
    @FXML
    private TextField nombre;
    @FXML
    private ComboBox<String> combobox;
    @FXML
    private TextField salario;
    @FXML
    private Label resultado = new Label();

    private String nombreInicial;
    private String puestoInicial;
    private String salarioInicial;

    private final ConexionBBDD conexion = new ConexionBBDD(); //Conexion a la BBDD usada en la pantalla de editar

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        combobox.setItems(FXCollections.observableArrayList(
                        "Scada Manager", "Sales Manager", "Product Owner", "Product Manager", "Analyst Programmer", "Junior Programmer"
                )
        );
    }

    /**
     * Obtiene los datos del empleado desde el otro controlador y
     * los guarda como los valores originales.
     */
    public void cargarDatos(ArrayList<String> valores){
        this.nombreInicial = valores.get(1);
        this.puestoInicial = valores.get(2);
        this.salarioInicial = valores.get(3);
        actualizarDatos();
    }

    /**
     * Setea los datos originales en los Textfiel y combobox.
     */
    public void actualizarDatos(){
        this.nombre.setText(nombreInicial);
        this.combobox.setValue(puestoInicial);
        this.salario.setText(salarioInicial);
        this.labelTop.setText("Editar empleado: " + nombreInicial); //El titulo de la pantalla
    }

    /**
     * Obtiene los datos de los campos y llama a la BBDD para editar el empleado.
     */
    @FXML
    public void editar(ActionEvent event){
        if(!(nombre.getText().trim().isEmpty()) && !(salario.getText().trim().isEmpty()) && (combobox.getValue() != null)){ //Comrueba si algun valor esta vacio
            if(!(Objects.equals(nombre.getText(), nombreInicial)) || !(Objects.equals(salario.getText(), salarioInicial)) || !(Objects.equals(combobox.getValue(), puestoInicial))){ //Comprueba si los datos son los mismos

                ArrayList<String> columnas = new ArrayList<>(); //Arraylist donde seran insertado los valores del empleado

                columnas.add(nombre.getText());
                columnas.add(combobox.getValue());
                columnas.add(salario.getText());

                String mensaje = conexion.editar(columnas, nombreInicial); //Se envia el Arraylist para que sea insertado
                Alertas alerta = new Alertas(mensaje, mensaje, Alert.AlertType.WARNING); //crea una alerta como confirmacion
                alerta.mostrarAlerta();
                this.cerrar(event); //Llama al metodo que cierra la ventana
            }
            else {
                resultado.setText("Los valores son los mismos. Cambia algun dato.");
            }
        }
        else{
            resultado.setText("Rellena todos los campos");
        }
    }

    /**
     * cierra la conexion con la BBDD y cierra la ventana.
     */
    private void cerrar(ActionEvent event){
        conexion.desconectar();
        Node node = (Node) event.getSource();
        Stage stage = (Stage) node.getScene().getWindow();
        stage.close(); //Cerrar
    }


}
