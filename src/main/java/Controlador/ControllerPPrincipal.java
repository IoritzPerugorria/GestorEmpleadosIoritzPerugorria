package Controlador;

import Modelo.ConexionBBDD;
import Modelo.LectorTXT;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Node;

import javafx.scene.control.*;
import javafx.stage.FileChooser;


import java.io.File;
import java.net.URL;
import java.util.ArrayList;
import java.util.ResourceBundle;

public class ControllerPPrincipal implements Initializable {

    @FXML
    private ComboBox<String> combobox;
    @FXML
    private ListView<String> vistaLista = new ListView<>();
    @FXML
    private TextField nombre;
    @FXML
    private TextField salario;
    @FXML
    private Label resultado; // El mensaje de resultado de la primera pantalla

    //Labels de la segunda pantalla
    @FXML
    private Label labelID;
    @FXML
    private Label labelNombre;
    @FXML
    private Label labelPuesto;
    @FXML
    private Label labelSueldo;
    @FXML
    private Label labelFecha;



    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {

        // Setear los valores del combobox
        combobox.setItems(FXCollections.observableArrayList(
                "Scada Manager", "Sales Manager", "Product Owner", "Product Manager", "Analyst Programmer", "Junior Programmer"
            )
        );
        this.cargarListView(); // iniciar el ListView
        /*
        El siguiente codigo sirve para que al seleccionar un item en ListView, se llame al metodo que
        muestra la informacion.
         */
        vistaLista.getSelectionModel().selectedItemProperty().addListener(new ChangeListener<String>() {
            @Override
            public void changed(ObservableValue<? extends String> observableValue, String s, String t1) {
                ConexionBBDD conexion = new ConexionBBDD();
                String nombreLista = vistaLista.getSelectionModel().getSelectedItem();
                ArrayList<String> valores = conexion.mostrarEmpleado(nombreLista);
                mostrarEmpleado(valores);
                conexion.desconectar();
            }
        });
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

    //Para el boton refrescar
    @FXML
    public void refrescar(){
        this.cargarListView();
    }

    /**
     * Carga los nombre de los empleados en el ListView. Llama a conexionBBDD
     * para obtener los nombres de empleados, y despues los añade al ListView
     * mediante un for.
     */
    public void cargarListView(){
        vistaLista.getItems().clear(); //Vaciar el ListView en caso de que se valla a refrescar
        ConexionBBDD conexion = new ConexionBBDD();

        ArrayList<String> nombres = conexion.consultarNombres(); //Obtener los nombres de empleados

        for(String nombre : nombres){
            vistaLista.getItems().add(nombre);
        }

        conexion.desconectar();
    }


    /**
     * Extrae los valores del Arraylist de entrada y los introduce en
     * los label correspondiente al valor. En caso de que el ArrayList
     * no contenga 5 valores, el catch salta y solo imprime 1 valor, que
     * contendria un mensaje de error.
     */
    public void mostrarEmpleado(ArrayList<String> valores){
        try {
            labelID.setText(valores.getFirst());
            labelNombre.setText(valores.get(1));
            labelPuesto.setText(valores.get(2));
            labelSueldo.setText(valores.get(3));
            labelFecha.setText(valores.getLast());
            valores.clear();
        }
        catch (Exception e){
            labelID.setText(valores.getFirst());
        }

    }

    /**
     * Extrae los datos de los cuadros de texto y combobox, y los usa para insertar un
     * nuevo empleado.
     */
    @FXML
    public void insertarEmpleado(ActionEvent event){
        if(!(nombre.getText().trim().isEmpty()) && !(salario.getText().trim().isEmpty())){ //TODO comprobar el combobox
            ConexionBBDD conexion = new ConexionBBDD();
            ArrayList<String> columnas = new ArrayList<>();

            columnas.add(nombre.getText());
            columnas.add(combobox.getValue());
            columnas.add(salario.getText());

            resultado.setText(conexion.insertar(columnas)); //Se envia el Arraylist para que sea insertado
            conexion.desconectar();
        }
        else{
            resultado.setText("Rellene todos los campos"); //Mensaje de error
        }
    }
}