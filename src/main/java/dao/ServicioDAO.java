/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package dao;

import conexion.ConexionOracle;
import javax.swing.JOptionPane;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

public class ServicioDAO {
    public boolean registrarServicio(String codigo, String descripcion, double costoManoObra) {
        if (costoManoObra < 0) {
            JOptionPane.showMessageDialog(null, "El costo de mano de obra no puede ser negativo.", "Error de Validación", JOptionPane.WARNING_MESSAGE);
            return false;
        }
        String sql = "INSERT INTO servicio (codigo, descripcion, costo_mano_obra) VALUES (?, ?, ?)";
        try (Connection conn = ConexionOracle.obtenerConexio();
             PreparedStatement pst = conn.prepareStatement(sql)) {
            if (conn == null) return false;
            pst.setString(1, codigo);
            pst.setString(2, descripcion);
            pst.setDouble(3, costoManoObra);
            pst.executeUpdate();
            conn.commit();
            return true;
        } 
        catch (SQLException e) {
            JOptionPane.showMessageDialog(null, "Error al registrar servicio: " + e.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
            return false;
        }
    }
}
