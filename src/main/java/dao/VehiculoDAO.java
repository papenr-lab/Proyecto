/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package dao;

import conexion.ConexionOracle;
import javax.swing.JOptionPane;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class VehiculoDAO {
    public boolean registrarVehiculo(String placa, String marca, String modelo, int anio, int idCliente) {
        String sqlVerificarPlaca = "SELECT COUNT(*) FROM vehiculo WHERE placa = ?";
        String sqlInsertar = "INSERT INTO vehiculo (placa, marca, modelo, anio, id_cliente) VALUES (?, ?, ?, ?, ?)";
        try (Connection conn = ConexionOracle.obtenerConexio()) {
            if (conn == null) return false;
            try (PreparedStatement pstVerificar = conn.prepareStatement(sqlVerificarPlaca)) {
                pstVerificar.setString(1, placa);
                try (ResultSet rs = pstVerificar.executeQuery()) {
                    if (rs.next() && rs.getInt(1) > 0) {
                        JOptionPane.showMessageDialog(null, 
                            "Error: Ya existe un vehículo registrado con la placa " + placa, 
                            "Placa Duplicada", JOptionPane.WARNING_MESSAGE);
                        return false;
                    }
                }
            }
            try (PreparedStatement pstInsert = conn.prepareStatement(sqlInsertar)) {
                pstInsert.setString(1, placa.toUpperCase());
                pstInsert.setString(2, marca);
                pstInsert.setString(3, modelo);
                pstInsert.setInt(4, anio);
                pstInsert.setInt(5, idCliente);
                
                pstInsert.executeUpdate();
                conn.commit();
                return true;
            }
        } 
        catch (SQLException e) {
            JOptionPane.showMessageDialog(null, "Error al registrar vehículo: " + e.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
            e.printStackTrace();
            return false;
        }
    }
    public boolean eliminarVehiculo(int idVehiculo) {
        String sqlVerificarOrdenes = "SELECT COUNT(*) FROM orden_trabajo WHERE id_vehiculo = ?";
        String sqlEliminar = "DELETE FROM vehiculo WHERE id_vehiculo = ?";
        try (Connection conn = ConexionOracle.obtenerConexio()) {
            if (conn == null) return false;
            try (PreparedStatement pstVerificar = conn.prepareStatement(sqlVerificarOrdenes)) {
                pstVerificar.setInt(1, idVehiculo);
                try (ResultSet rs = pstVerificar.executeQuery()) {
                    if (rs.next() && rs.getInt(1) > 0) {
                        JOptionPane.showMessageDialog(null, 
                            "No se puede eliminar el vehículo porque tiene órdenes de trabajo registradas.", 
                            "Operación Denegada", JOptionPane.WARNING_MESSAGE);
                        return false;
                    }
                }
            }
            try (PreparedStatement pstEliminar = conn.prepareStatement(sqlEliminar)) {
                pstEliminar.setInt(1, idVehiculo);
                pstEliminar.executeUpdate();
                conn.commit();
                return true;
            }
        } 
        catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }
}