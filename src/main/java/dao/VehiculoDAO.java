/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package dao;

import Modelo.Vehiculo;
import conexion.ConexionOracle;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

public class VehiculoDAO {
    public boolean insertarVehiculo(Vehiculo vehiculo) {
       
        String sql = "INSERT INTO Vehiculo (placa, marca, modelo, anio, id_cliente) VALUES (?, ?, ?, ?, ?)";
        Connection conn = null;
        PreparedStatement pstmt = null;
        try {
            conn = ConexionOracle.getConexion();
            conn.setAutoCommit(false); 
            pstmt = conn.prepareStatement(sql);
            pstmt.setString(1, vehiculo.getPlaca());
            pstmt.setString(2, vehiculo.getMarca());
            pstmt.setString(3, vehiculo.getModelo());
            pstmt.setInt(4, vehiculo.getAnio());
            pstmt.setInt(5, vehiculo.getCliente().getIdCliente());
            int filasAfectadas = pstmt.executeUpdate();
            if (filasAfectadas > 0) {
                conn.commit();
                return true;
            }
        } 
        catch (SQLException e) {
            if (conn != null) {
                try {
                    conn.rollback();
                } catch (SQLException ex) {
                    System.err.println("Error en el rollback de vehículo: " + ex.getMessage());
                }
            }
            System.err.println("Error al insertar vehículo en la base de datos: " + e.getMessage());
        } finally {
            try {
                if (pstmt != null) pstmt.close();
                if (conn != null) conn.close();
            } 
            catch (SQLException e) {
                System.err.println("Error al cerrar canales de conexión: " + e.getMessage());
            }
        }
        return false;
    }
}
