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

public class ClienteDAO {

    public boolean eliminarCliente(int idCliente) {
        String sqlVerificarVehiculos = "SELECT COUNT(*) FROM vehiculo WHERE id_cliente = ?";
        String sqlEliminar = "DELETE FROM cliente WHERE id_cliente = ?";
        try (Connection conn = ConexionOracle.obtenerConexio()) {
            if (conn == null) return false;
            try (PreparedStatement pstVerificar = conn.prepareStatement(sqlVerificarVehiculos)) {
                pstVerificar.setInt(1, idCliente);
                try (ResultSet rs = pstVerificar.executeQuery()) {
                    if (rs.next() && rs.getInt(1) > 0) {
                        JOptionPane.showMessageDialog(null, 
                            "No se puede eliminar el cliente porque tiene vehículos asociados.", 
                            "Operación Denegada", JOptionPane.WARNING_MESSAGE);
                        return false;
                    }
                }
            }
            try (PreparedStatement pstEliminar = conn.prepareStatement(sqlEliminar)) {
                pstEliminar.setInt(1, idCliente);
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
    
    public boolean registrarCliente(int idCliente, String nombre) {
    String sql = "INSERT INTO cliente (id_cliente, nombre) VALUES (?, ?)";
    try (Connection conn = ConexionOracle.obtenerConexio();
         PreparedStatement pst = conn.prepareStatement(sql)) {
        if (conn == null) return false;
        pst.setInt(1, idCliente);
        pst.setString(2, nombre);
        pst.executeUpdate();
        conn.commit();
        return true;
    } 
    catch (SQLException e) {
        JOptionPane.showMessageDialog(null, "Error al registrar cliente: " + e.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
        return false;
    }
}
    
    public boolean modificarCliente(int idCliente, String nuevoNombre) {
    String sql = "UPDATE cliente SET nombre = ? WHERE id_cliente = ?";
    try (Connection conn = ConexionOracle.obtenerConexio();
         PreparedStatement pst = conn.prepareStatement(sql)) {
        if (conn == null) return false;
        pst.setString(1, nuevoNombre);
        pst.setInt(2, idCliente);
        int filasAfectadas = pst.executeUpdate();
        conn.commit();
        return filasAfectadas > 0;
    } 
    catch (SQLException e) {
        JOptionPane.showMessageDialog(null, "Error al modificar cliente: " + e.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
        return false;
    }
}

public Modelo.Cliente consultarCliente(int idCliente) {
    String sql = "SELECT id_cliente, nombre FROM cliente WHERE id_cliente = ?";
    try (Connection conn = ConexionOracle.obtenerConexio();
         PreparedStatement pst = conn.prepareStatement(sql)) {
        if (conn == null) return null;
        pst.setInt(1, idCliente);
        try (ResultSet rs = pst.executeQuery()) {
            if (rs.next()) {
                return new Modelo.Cliente(
                    String.valueOf(rs.getInt("id_cliente")),
                    rs.getString("nombre")
                );
            }
        }
    } 
    catch (SQLException e) {
        JOptionPane.showMessageDialog(null, "Error al consultar cliente: " + e.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
    }
    return null;
}
}
