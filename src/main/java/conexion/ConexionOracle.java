/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package conexion;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class ConexionOracle {
    private static final String URL = "jdbc:oracle:thin:@localhost:1521:umg"; 
    private static final String USUARIO = "system";
    private static final String PASSWORD = "Umg$2026";

    public static Connection obtenerConexio() {
        Connection conexion = null;
        try {
            Class.forName("oracle.jdbc.OracleDriver");
            conexion = DriverManager.getConnection(URL, USUARIO, PASSWORD);
            conexion.setAutoCommit(false);
        } 
        catch (ClassNotFoundException e) {
            System.err.println("Error: No se encontró el Driver de Oracle JDBC -> " + e.getMessage());
        } 
        catch (SQLException e) {
            System.err.println("Error de conexión a la Base de Datos -> " + e.getMessage());
        }
        return conexion;
    }
}
