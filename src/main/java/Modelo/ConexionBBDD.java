package Modelo;

import java.sql.*;
import java.util.ArrayList;


public class ConexionBBDD {

    private Connection conexion; //La misma conexion usada en tod0 el proyecto

    public ConexionBBDD(){
        this.conectar();
    }

    /**
     * Establece la conexion con la BBDD
     */
    public void conectar(){
        try {
            String localUrl = "jdbc:mysql://localhost:3306/BD_GESTOR_EMPLEADOS";
            String username = "root";
            String localPassword = "root";
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
     * Recibe un Arraylist  como parametro, e inserta los datos dentro de la BBDD conectada. Retorna
     * un mensaje de confirmacion si se insertan los datos, y otro de error si algo ha salido mal.
     */
    public String insertar(ArrayList<String> columnas){
        String resultado; //El mensaje de retorno, se actualiza dependiendo si salta excepcion o no.
        try{
            if (conexion != null){
                    PreparedStatement ps = conexion.prepareStatement("INSERT INTO EMPLEADOS (NOMBRE, PUESTO, SALARIO, FECHA) VALUES(?,?,?,NOW())");
                    ps.setString(1, columnas.get(0));
                    ps.setString(2, columnas.get(1));
                    ps.setString(3, columnas.get(2));

                    ps.executeUpdate();

                    resultado = "Se han añadido los empleados correctamente";
            }
            else{
                resultado = "No se ha podido establecer conexion";
            }
        } catch (SQLException e) {
            resultado = "No se han podido insertar los datos. Intentalo de nuevo.";
        }
        return resultado;
    }


    /**
     * Elimina un empleado de la BBDD
     */
    public String eliminar(String nombre){
        String resultado; //El mensaje de retorno, se actualiza dependiendo si salta excepcion o no.
        try{
            if (conexion != null){
                PreparedStatement ps = conexion.prepareStatement("DELETE FROM EMPLEADOS WHERE NOMBRE = ?");
                ps.setString(1, nombre);

                ps.executeUpdate();

                resultado = "Se ha eliminado el empleado";
            }
            else{
                resultado = "No se ha podido establecer conexion";
            }
        } catch (SQLException e) {
            resultado = "No se han podido eliminar el empleado. Intentalo de nuevo.";
        }
        return resultado;
    }

    /**
     * Obtiene los nombres de todos los empleados
     */
    public ArrayList<String> consultarNombres(){
        ArrayList<String> nombres = new ArrayList<>();
        try {
            Statement statement = conexion.createStatement();
            ResultSet resultSet = statement.executeQuery("SELECT NOMBRE FROM EMPLEADOS");
            while (resultSet.next()){
                nombres.add(resultSet.getString("NOMBRE")); //Añade el nombre al Arraylist
            }
        }
        catch (SQLException s){
            nombres.clear();
            nombres.add("No se han podido cargar los datos");
        }
        return nombres; //Retorna el Arraylist
    }

    /**
     * Realiza una consulta de todos los valores de la tabla empleado con un valor
     * NOMBRE especifico. Retorna un Arraylist de 5 valores con los datos del
     * emplado si tod0 sale bien, y un Arraylist de 1 valor con un mensaje de
     * error si algo sale mal
     */
    public ArrayList<String> mostrarEmpleado(String nombreLista){
        ArrayList<String> valores = new ArrayList<>();
        try {
            PreparedStatement ps = conexion.prepareStatement("SELECT * FROM EMPLEADOS WHERE NOMBRE = ?"); //Prepara el query
            ps.setString(1, nombreLista); //Introduce el nombre en el WHERE
            ResultSet resultSet = ps.executeQuery(); //Ejecuta el query y lo añade a un ResultSet
            while (resultSet.next()){ //Añade los valores al Arraylist
                valores.add(resultSet.getString("ID"));
                valores.add(resultSet.getString("NOMBRE"));
                valores.add(resultSet.getString("PUESTO"));
                valores.add(resultSet.getString("SALARIO"));
                valores.add(resultSet.getString("FECHA"));
            }
        }
        catch (SQLException s){
            valores.clear();
            valores.add("No se han podido cargar los datos"); //Mensaje de error en caso que algo falle
        }
        return valores;
    }

    /**
     * Edita los datos de un empleado ya insertado. Recibe como parametro un
     * Arraylist con los valores nuevos, y el nombre del empleado que se quiere cambiar.
     */
    public String editar(ArrayList<String> valores, String nombreReal){
        String resultado;
        try{
            PreparedStatement ps = conexion.prepareStatement("UPDATE EMPLEADOS SET NOMBRE = ?, PUESTO = ?, SALARIO = ?, FECHA = NOW() WHERE NOMBRE = ?");
            ps.setString(1, valores.get(0));
            ps.setString(2, valores.get(1));
            ps.setString(3, valores.get(2));
            ps.setString(4, nombreReal);
            ps.executeUpdate();

            resultado = "Se han modificado los datos del empleado";
        }
        catch (SQLException s){
            resultado = "No se han podido modificar los datos del empleado";
        }
        return resultado;
    }
}
