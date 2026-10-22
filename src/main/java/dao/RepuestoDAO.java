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

public class RepuestoDAO {
    public boolean registrarRepuesto(String codigo, String nombre, String descripcion, double precio, int cantidad) {
        if (precio < 0 || cantidad < 0) {
            JOptionPane.showMessageDialog(null, "El precio y la cantidad no pueden ser negativos.", "Error de Validación", JOptionPane.WARNING_MESSAGE);
            return false;
        }
        String sql = "INSERT INTO repuesto (codigo, nombre, descripcion, precio, cantidad_disponible) VALUES (?, ?, ?, ?, ?)";
        try (Connection conn = ConexionOracle.obtenerConexio();
             PreparedStatement pst = conn.prepareStatement(sql)) {
            if (conn == null) return false;
            pst.setString(1, codigo);
            pst.setString(2, nombre);
            pst.setString(3, descripcion);
            pst.setDouble(4, precio);
            pst.setInt(5, cantidad);
            pst.executeUpdate();
            conn.commit();
            return true;
        } 
        catch (SQLException e) {
            JOptionPane.showMessageDialog(null, "Error al registrar repuesto: " + e.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
            return false;
        }
    }

    public boolean abastecerInventario(int idRepuesto, int cantidadIngresada) {
        if (cantidadIngresada <= 0) {
            JOptionPane.showMessageDialog(null, "La cantidad a abastecer debe ser mayor a cero.", "Error", JOptionPane.WARNING_MESSAGE);
            return false;
        }
        String sql = "UPDATE repuesto SET cantidad_disponible = cantidad_disponible + ? WHERE id_repuesto = ?";
        try (Connection conn = ConexionOracle.obtenerConexio();
             PreparedStatement pst = conn.prepareStatement(sql)) {
            if (conn == null) return false;

            pst.setInt(1, cantidadIngresada);
            pst.setInt(2, idRepuesto);
            
            int filas = pst.executeUpdate();
            conn.commit();
            return filas > 0;
        } 
        catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }
    
    public void consultarAgotados() {
        String sql = "SELECT codigo, nombre, descripcion FROM repuesto WHERE cantidad_disponible = 0";
    }
}
