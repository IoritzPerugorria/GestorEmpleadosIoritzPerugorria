package Modelo;

import java.sql.*;
import java.util.ArrayList;

public class ConexionBBDD {

    private final String localUrl = "jdbc:mysql://localhost:3306/BD_GESTOR_EMPLEADOS";
    private final String username = "root";
    private final String localPassword = "root";

    private Connection conexion; //La misma conexion usada en tod0 el proyecto

    public ConexionBBDD(){
        this.conectar();
    }

    /**
     * Establece la conexion con la BBDD
     */
    public void conectar(){
        try {
            conexion = DriverManager.getConnection(localUrl, username, localPassword);
            System.out.println("Conexion establecida");

        } catch (SQLException e) {
            System.out.println("No se ha podido establecer conexion");
        }

    }

    /**
     * Cierra la conexion con la BBDD
     */
    public void desconectar(){
        try{
            if(conexion != null){
                conexion.close();
            }
        } catch (SQLException e) {
            System.out.println("No se ha podido desconectar. Intentalo de nuevo.");
        }
    }

    /**
     * Recibe un Arraylist 2D como parametro, e inserta los datos, mediante un
     * for, de dentro de la BBDD conectada. Retorna un mensaje de confirmacion si
     * se insertan los datos, y otro de error si algo ha salido mal.
     */
    public String insertarDesdeArchivo(ArrayList<ArrayList<String>> filas){
        String resultado = ""; //El mensaje de retorno, se actualiza dependiendo si salta excepcion o no.
        try{
            if (conexion != null){
                for (ArrayList<String> columnas : filas) {
                    PreparedStatement ps = conexion.prepareStatement("INSERT INTO EMPLEADOS (NOMBRE, PUESTO, SALARIO, FECHA) VALUES(?,?,?,NOW())");
                    ps.setString(1, columnas.get(0));
                    ps.setString(2, columnas.get(1));
                    ps.setString(3, columnas.get(2));


                    ps.executeUpdate();

                    resultado = "Se han añadido los empleados correctamente";
                }
            }
            else{
                resultado = "No se ha podido establecer conexion";
            }
        } catch (SQLException e) {
            resultado = "No se han podido insertar los datos. Intentalo de nuevo.";
        }
        finally {
            this.desconectar();
        }
        return resultado;
    }



}
