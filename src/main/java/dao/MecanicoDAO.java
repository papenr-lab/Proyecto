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

public class MecanicoDAO {
    public boolean registrarMecanico(String identificador, String nombre, String especialidad) {
        String sqlVerificar = "SELECT COUNT(*) FROM mecanico WHERE identificador = ?";
        String sqlInsertar = "INSERT INTO mecanico (identificador, nombre, especialidad) VALUES (?, ?, ?)";

        try (Connection conn = ConexionOracle.obtenerConexio()) {
            if (conn == null) return false;
            try (PreparedStatement pstVerificar = conn.prepareStatement(sqlVerificar)) {
                pstVerificar.setString(1, identificador);
                try (ResultSet rs = pstVerificar.executeQuery()) {
                    if (rs.next() && rs.getInt(1) > 0) {
                        JOptionPane.showMessageDialog(null, 
                            "Error: Ya existe un mecánico registrado con el identificador " + identificador, 
                            "Identificador Duplicado", JOptionPane.WARNING_MESSAGE);
                        return false;
                    }
                }
            }
            try (PreparedStatement pstInsert = conn.prepareStatement(sqlInsertar)) {
                pstInsert.setString(1, identificador);
                pstInsert.setString(2, nombre);
                pstInsert.setString(3, especialidad);
                pstInsert.executeUpdate();
                conn.commit();
                return true;
            }
        }
        catch (SQLException e) {
            JOptionPane.showMessageDialog(null, "Error al registrar mecánico: " + e.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
            e.printStackTrace();
            return false;
        }
    }

    public boolean modificarMecanico(int idMecanico, String nombre, String especialidad) {
        String sql = "UPDATE mecanico SET nombre = ?, especialidad = ? WHERE id_mecanico = ?";

        try (Connection conn = ConexionOracle.obtenerConexio()) {
            if (conn == null) return false;
            try (PreparedStatement pst = conn.prepareStatement(sql)) {
                pst.setString(1, nombre);
                pst.setString(2, especialidad);
                pst.setInt(3, idMecanico);
                pst.executeUpdate();
                conn.commit();
                return true;
            }
        } 
        catch (SQLException e) {
            JOptionPane.showMessageDialog(null, "Error al modificar mecánico: " + e.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
            e.printStackTrace();
            return false;
        }
    }
    
    public boolean eliminarMecanico(int idMecanico) {
        String sqlVerificarOrdenes = "SELECT COUNT(*) FROM orden_trabajo WHERE id_mecanico = ?";
        String sqlEliminar = "DELETE FROM mecanico WHERE id_mecanico = ?";

        try (Connection conn = ConexionOracle.obtenerConexio()) {
            if (conn == null) return false;
            try (PreparedStatement pstVerificar = conn.prepareStatement(sqlVerificarOrdenes)) {
                pstVerificar.setInt(1, idMecanico);
                try (ResultSet rs = pstVerificar.executeQuery()) {
                    if (rs.next() && rs.getInt(1) > 0) {
                        JOptionPane.showMessageDialog(null, 
                            "No se puede eliminar el mecánico porque tiene órdenes de trabajo asociadas.", 
                            "Operación Denegada", JOptionPane.WARNING_MESSAGE);
                        return false;
                    }
                }
            }
            try (PreparedStatement pstEliminar = conn.prepareStatement(sqlEliminar)) {
                pstEliminar.setInt(1, idMecanico);
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
