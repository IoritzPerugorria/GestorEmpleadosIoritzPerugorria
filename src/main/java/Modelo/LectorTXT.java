package Modelo;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Scanner;

public class LectorTXT {

    /**
     * Recibe como parametro un archivo (tiene que ser de texto), y
     * despues lee el contenido e introduce los valores de una fila en
     * un Arraylist, y luego introduce ese Arraylist en ontro Arraylist
     * de Arraylist, creando un Arraylist 2D. Retorna un mensaje de
     * confirmacion si tod0 ha salido bien, y uno de error si algo ha
     * ido mal.
     */
    public String cargarDesdeArchivo(File archivo){
        String resultado; //El mensaje de retorno, se actualiza dependiendo si salta excepcion o no.

        try{
            Scanner scanner = new Scanner(archivo); //Scanner para el archivo
            String cadena;
            ConexionBBDD conexion = new ConexionBBDD();

            while (scanner.hasNext()){
                cadena = scanner.nextLine();
                String[] coleccion = cadena.split(";"); //Divide la linea usando ; como regex

                ArrayList<String> columnas = new ArrayList<>(Arrays.asList(coleccion)); //Convierte el Array a Arraylist

                resultado = conexion.insertar(columnas); //Envia el Arraylist para que sea insertado
            }
        }
        catch (IOException f){
            resultado = "Hay un problema en la lectura del archivo";
        }
        catch (IndexOutOfBoundsException i){
            resultado = "El formato del archivo no es correcto";
        }
        return resultado;
    }
}
