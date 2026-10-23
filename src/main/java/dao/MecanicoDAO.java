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
        public boolean registrarMecanico(int idMecanico, String nombre, String especialidad) {
        String sql = "INSERT INTO mecanico (id_mecanico, identificador, nombre, especialidad) VALUES (?, ?, ?, ?)";
        try (Connection conn = ConexionOracle.obtenerConexio();
             PreparedStatement pst = conn.prepareStatement(sql)) {
            if (conn == null) return false;
            pst.setInt(1, idMecanico);
            pst.setString(2, String.valueOf(idMecanico));
            pst.setString(3, nombre);
            pst.setString(4, especialidad);
            pst.executeUpdate();
            conn.commit();
            return true;
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(null, "Error al registrar mecánico: " + e.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
            return false;
        }
    }
            
     public boolean modificarMecanico(int idMecanico, String nuevoNombre, String nuevaEspecialidad) {
        String sql = "UPDATE mecanico SET nombre = ?, especialidad = ? WHERE id_mecanico = ?";
        try (Connection conn = ConexionOracle.obtenerConexio();
             PreparedStatement pst = conn.prepareStatement(sql)) {
            if (conn == null) return false;
            pst.setString(1, nuevoNombre);
            pst.setString(2, nuevaEspecialidad);
            pst.setInt(3, idMecanico);
            int filas = pst.executeUpdate();
            conn.commit();
            return filas > 0;
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(null, "Error al modificar mecánico: " + e.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
            return false;
        }
    }
    
    public Modelo.Mecanico consultarMecanico(int idMecanico) {
        String sql = "SELECT id_mecanico, nombre, especialidad FROM mecanico WHERE id_mecanico = ?";
        try (Connection conn = ConexionOracle.obtenerConexio();
             PreparedStatement pst = conn.prepareStatement(sql)) {
            if (conn == null) return null;
            pst.setInt(1, idMecanico);
            try (ResultSet rs = pst.executeQuery()) {
                if (rs.next()) {
                    return new Modelo.Mecanico(
                        String.valueOf(rs.getInt("id_mecanico")),
                        rs.getString("nombre"),
                        rs.getString("specialidad")
                    );
                }
            }
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(null, "Error al consultar mecánico: " + e.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
        }
        return null;
    }
    
    public boolean eliminarMecanico(int idMecanico) {
        String sqlVerificar = "SELECT COUNT(*) FROM orden_trabajo WHERE id_mecanico = ?";
        String sqlEliminar = "DELETE FROM mecanico WHERE id_mecanico = ?";
        try (Connection conn = ConexionOracle.obtenerConexio()) {
            if (conn == null) return false;
            try (PreparedStatement pstVerificar = conn.prepareStatement(sqlVerificar)) {
                pstVerificar.setInt(1, idMecanico);
                try (ResultSet rs = pstVerificar.executeQuery()) {
                    if (rs.next() && rs.getInt(1) > 0) {
                        JOptionPane.showMessageDialog(null, 
                            "No se puede eliminar el mecánico porque tiene órdenes de trabajo asignadas.", 
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
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }
}
