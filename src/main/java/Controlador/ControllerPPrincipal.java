package Controlador;

import Modelo.Alertas;
import Modelo.ConexionBBDD;
import Modelo.LectorTXT;
import javafx.collections.FXCollections;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;

import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.image.Image;

import javafx.stage.FileChooser;

import javafx.stage.Stage;
import org.example.gestorempleadosioritzperugorria.Main;


import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.Objects;
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
    @FXML
    private Label resultadoConsultas;

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

    private final ConexionBBDD conexion = new ConexionBBDD(); //La conexion que se usa en tod0 el proyecto

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
        vistaLista.getSelectionModel().selectedItemProperty().addListener((observableValue, s, t1) -> {
            String nombreLista = vistaLista.getSelectionModel().getSelectedItem();
            ArrayList<String> valores = conexion.mostrarEmpleado(nombreLista);
            mostrarEmpleado(valores);
        });

    }

    /**
     * El metodo carga el archivo de trabajadores ubicado en el proyecto y
     * lo manda para que se inserten
     */
    public void cargar(){
        File archivo = new File("src/main/resources/ArchivoTrabajadores/trabajadores.txt");
        this.enviarTXT(archivo);
    }

    /**
     * El metodo abre un FileChooser para escoger un archivo de texto, y
     * lo manda para que se inserten los empleados de dentro
     */
    @FXML
    public void cargarDesdeArchivo(ActionEvent event){

            FileChooser cargador = new FileChooser();

            cargador.getExtensionFilters().addAll(
                    new FileChooser.ExtensionFilter("Archivos de texto", "*.txt") //Solo poder elejir archivos .txt
            );

            Node node = (Node) event.getSource();
            File archivo = cargador.showOpenDialog(node.getScene().getWindow()); //Abrir una ventana para escojer archivo
            this.enviarTXT(archivo);
    }

    /**
     * El metodo recive un archivo .txt y lo manda a LectorTXT
     */
    public void enviarTXT(File archivo){
        if (archivo != null) {
            LectorTXT lector = new LectorTXT();
            resultado.setText(lector.cargarDesdeArchivo(archivo)); //El mensaje de retorno, dependiendo si ha salido bien o mal la insercion dice una cosa u otra.
        }
    }

    //Para el boton refrescar. Vacia los mensajes y llama a cargarListView para que se muestren de nuevo los empleados
    @FXML
    public void refrescar(){
        this.cargarListView();
        resultado.setText("");
        resultadoConsultas.setText("");
    }

    /**
     * Carga los nombre de los empleados en el ListView. Llama a conexionBBDD
     * para obtener los nombres de empleados, y despues los añade al ListView
     * mediante un for.
     */
    public void cargarListView(){
        vistaLista.getItems().clear(); //Vaciar el ListView en caso de que se valla a refrescar

        ArrayList<String> nombres = conexion.consultarNombres(); //Obtener los nombres de empleados

        for(String nombre : nombres){ //Itera para insertar los nombres uno a uno
            vistaLista.getItems().add(nombre);
        }
    }

    /**
     * Extrae los valores del Arraylist de entrada y los introduce en
     * los label correspondiente al valor. En caso de que el ArrayList
     * no contenga 5 valores, el catch salta y solo imprime 1 valor, que
     * contendria un mensaje de error.
     */
    public void mostrarEmpleado(ArrayList<String> valores){
        //Primero vacia los valores, en caso de excepcion
        labelID.setText("");
        labelNombre.setText("");
        labelPuesto.setText("");
        labelSueldo.setText("");
        labelFecha.setText("");
        try {
            labelID.setText(valores.getFirst());
            labelNombre.setText(valores.get(1));
            labelPuesto.setText(valores.get(2));
            labelSueldo.setText(valores.get(3));
            labelFecha.setText(valores.getLast());
            valores.clear();
        }
        catch (Exception e){
            try {
                labelID.setText(valores.getFirst());
            }
            catch (Exception e1){
                System.out.println("Refrescar");
            }
        }

    }

    /**
     * Extrae los datos de los cuadros de texto y combobox, y los usa para insertar un
     * nuevo empleado.
     */
    @FXML
    public void insertarEmpleado() {
        try {
            if (!(nombre.getText().trim().isEmpty()) && !(salario.getText().trim().isEmpty()) && (combobox.getValue() != null)) { //Comprueba que los valores no esten vacios
                ArrayList<String> columnas = new ArrayList<>();

                Integer.parseInt(salario.getText()); //Comprueba si el salario es un numero. Si no lo es, salta el catch.

                columnas.add(nombre.getText());
                columnas.add(combobox.getValue());
                columnas.add(salario.getText());

                resultado.setText(conexion.insertar(columnas)); //Se envia el Arraylist para que sea insertado
            } else {
                resultado.setText("Rellene todos los campos"); //Mensaje de error
            }
        }
        catch (NumberFormatException e){
            resultado.setText("El salario debe de ser un numero");
        }
    }

    /**
     * Para el boton editar. Abre una nueva ventana, cargando pantallaEditar.fxml,
     * y envia la informacion del empleado.
     */
    @FXML
    public void editar(){
        try{
            Stage stage = new Stage();
            FXMLLoader fxmlLoader = new FXMLLoader(Main.class.getResource("pantallaEditar.fxml"));

            Parent root = fxmlLoader.load();
            PantallaEditar pantallaEditar = fxmlLoader.getController();
            String nombreLista = vistaLista.getSelectionModel().getSelectedItem();
            ArrayList<String> valores = conexion.mostrarEmpleado(nombreLista);
            pantallaEditar.cargarDatos(valores);

            Scene scene = new Scene(root, 600, 400);

            stage.setTitle("Editar Empleados");
            stage.setMaxWidth(600);
            stage.setMaxHeight(400);
            stage.setMinWidth(600);
            stage.setMinHeight(400);
            Image imagen = new Image(Objects.requireNonNull(getClass().getResourceAsStream("/Imagenes/icon.png")));
            stage.getIcons().add(imagen);
            stage.setScene(scene);
            stage.show();
        }
        catch (IOException e){
            resultadoConsultas.setText("Error al mostrar la vista");
        }
        catch (IndexOutOfBoundsException n){
            resultadoConsultas.setText("Selecciona un empleado");
        }
    }

    /**
     * Elimina un empleado de la BBDD. Tambien, crea una alerta para
     * cuando se vaya a eliminar un empleado.
     */
    @FXML
    public void eliminar(){

            Alertas alert = new Alertas("Confirmacion", "¿Seguro que quieres borrar el empleado?", Alert.AlertType.CONFIRMATION);
            if (alert.alertaEliminar()) {
                String nombre = vistaLista.getSelectionModel().selectedItemProperty().get(); //Obtiene el nombre
                String resultado = conexion.eliminar(nombre); //Envia el nombre el empleado paras ser eliminado
                resultadoConsultas.setText(resultado); //Setea el mensaje de confirmacion
            }
    }
}