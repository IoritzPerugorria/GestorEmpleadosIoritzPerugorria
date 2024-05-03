package Modelo;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Scanner;

public class LectorTXT {

    public String cargarDesdeArchivo(File archivo){
        String resultado = "";
        ArrayList<ArrayList<String>> filas = new ArrayList<>();

        try{
            Scanner scanner = new Scanner(archivo);
            String cadena;

            while (scanner.hasNext()){
                cadena = scanner.nextLine();
                String[] coleccion = cadena.split(";");

                ArrayList<String> columnas = new ArrayList<>(Arrays.asList(coleccion));

                filas.add(columnas);
            }
            ConexionBBDD conexion = new ConexionBBDD();
            resultado = conexion.insertarDesdeArchivo(filas);

        }
        catch (IOException f){
            resultado = "Hay un problema en la lectura del archivo";
        }
        return resultado;
    }
}
