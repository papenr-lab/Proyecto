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
    
    public boolean eliminarVehiculo(String placa) {
        String sqlVerificarOrdenes = "SELECT COUNT(*) FROM orden_trabajo WHERE placa_vehiculo = ?";
        String sqlEliminar = "DELETE FROM vehiculo WHERE placa = ?";
        try (Connection conn = ConexionOracle.obtenerConexio()) {
            if (conn == null) return false;
            try (PreparedStatement pstVerificar = conn.prepareStatement(sqlVerificarOrdenes)) {
                pstVerificar.setString(1, placa);
                try (java.sql.ResultSet rs = pstVerificar.executeQuery()) {
                    if (rs.next() && rs.getInt(1) > 0) {
                        javax.swing.JOptionPane.showMessageDialog(null, 
                            "No se puede eliminar el vehículo porque tiene órdenes de trabajo asociadas.", 
                            "Operación Denegada", javax.swing.JOptionPane.WARNING_MESSAGE);
                        return false;
                    }
                }
            }
            try (PreparedStatement pstEliminar = conn.prepareStatement(sqlEliminar)) {
                pstEliminar.setString(1, placa);
                pstEliminar.executeUpdate();
                conn.commit();
                return true;
            }
        } catch (java.sql.SQLException e) {
            e.printStackTrace();
            return false;
        }
    }
    
    public boolean modificarVehiculo(String placa, String nuevaMarca, String nuevoModelo, int nuevoAnio) {
    String sql = "UPDATE vehiculo SET marca = ?, modelo = ?, anio = ? WHERE placa = ?";
    try (Connection conn = ConexionOracle.obtenerConexio();
         PreparedStatement pst = conn.prepareStatement(sql)) {
        if (conn == null) return false;
        pst.setString(1, nuevaMarca);
        pst.setString(2, nuevoModelo);
        pst.setInt(3, nuevoAnio);
        pst.setString(4, placa);
        int filasAfectadas = pst.executeUpdate();
        conn.commit();
        return filasAfectadas > 0;
    } catch (SQLException e) {
        JOptionPane.showMessageDialog(null, "Error al modificar vehículo: " + e.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
        return false;
    }
}
    
    public Modelo.Vehiculo consultarVehiculo(String placa) {
    String sql = "SELECT placa, marca, modelo, anio, id_cliente FROM vehiculo WHERE placa = ?";
    try (Connection conn = ConexionOracle.obtenerConexio();
         PreparedStatement pst = conn.prepareStatement(sql)) {
        if (conn == null) return null;
        pst.setString(1, placa);
        try (ResultSet rs = pst.executeQuery()) {
            if (rs.next()) {
                Modelo.Vehiculo v = new Modelo.Vehiculo();
                v.setPlaca(rs.getString("placa"));
                v.setMarca(rs.getString("marca"));
                v.setModelo(rs.getString("modelo"));
                v.setAnio(rs.getInt("anio"));
                
                Modelo.Cliente c = new Modelo.Cliente(String.valueOf(rs.getInt("id_cliente")), "");
                v.setCliente(c);
                
                return v;
            }
        }
    } 
    catch (SQLException e) {
        JOptionPane.showMessageDialog(null, "Error al consultar vehículo: " + e.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
    }
    return null;
}
}