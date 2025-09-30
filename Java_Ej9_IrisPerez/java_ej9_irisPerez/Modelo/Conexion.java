package Modelo;

import java.sql.Connection;			
import java.sql.DriverManager;
import java.sql.SQLException;

/* Utilizo esta clase para obtener la conexión con la base de datos cine_irisperez 
 * poniendo mi nombre de usuario y contraseña. Controlo el error al establecer la 
 * conexión con la base de datos mediante un try catch. */

public class Conexion {
    public static Connection obtenerConexion() {
        String url = "jdbc:mysql://localhost:3306/java_ej9_irisPerez";
        String usuario = "root";
        String contraseña = "1234";

        try {
            Connection conexion = DriverManager.getConnection(url, usuario, contraseña);
            return conexion;
        } catch (SQLException e) {
            System.out.println("Error de conexión: " + e.getMessage());
            return null;
        }
    }
}
