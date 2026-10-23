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

public class RepuestoDAO {
    public boolean registrarRepuesto(int idRepuesto, String nombre, String descripcion, double precio, int cantidad) {
        if (precio < 0 || cantidad < 0) {
            JOptionPane.showMessageDialog(null, "El precio y la cantidad no pueden ser valores negativos.", "Validación", JOptionPane.WARNING_MESSAGE);
            return false;
        }
        String sql = "INSERT INTO repuesto (id_repuesto, nombre, descripcion, precio, cantidad) VALUES (?, ?, ?, ?, ?)";
        try (Connection conn = ConexionOracle.obtenerConexio();
             PreparedStatement pst = conn.prepareStatement(sql)) {
            if (conn == null) return false;
            pst.setInt(1, idRepuesto);
            pst.setString(2, nombre);
            pst.setString(3, descripcion);
            pst.setDouble(4, precio);
            pst.setInt(5, cantidad);
            pst.executeUpdate();
            conn.commit();
            return true;
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(null, "Error al registrar repuesto: " + e.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
            return false;
        }
    }

    public boolean modificarRepuesto(int idRepuesto, String nombre, String descripcion, double precio) {
        if (precio < 0) {
            JOptionPane.showMessageDialog(null, "El precio no puede ser negativo.", "Validación", JOptionPane.WARNING_MESSAGE);
            return false;
        }
        String sql = "UPDATE repuesto SET nombre = ?, descripcion = ?, precio = ? WHERE id_repuesto = ?";
        try (Connection conn = ConexionOracle.obtenerConexio();
             PreparedStatement pst = conn.prepareStatement(sql)) {
            if (conn == null) return false;
            pst.setString(1, nombre);
            pst.setString(2, descripcion);
            pst.setDouble(3, precio);
            pst.setInt(4, idRepuesto);
            int filas = pst.executeUpdate();
            conn.commit();
            return filas > 0;
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(null, "Error al modificar repuesto: " + e.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
            return false;
        }
    }
    
    public boolean abastecerInventario(int idRepuesto, int cantidadASumar) {
        if (cantidadASumar <= 0) {
            JOptionPane.showMessageDialog(null, "La cantidad a abastecer debe ser mayor a cero.", "Validación", JOptionPane.WARNING_MESSAGE);
            return false;
        }
        String sql = "UPDATE repuesto SET cantidad = cantidad + ? WHERE id_repuesto = ?";
        try (Connection conn = ConexionOracle.obtenerConexio();
             PreparedStatement pst = conn.prepareStatement(sql)) {
            if (conn == null) return false;
            pst.setInt(1, cantidadASumar);
            pst.setInt(2, idRepuesto);
            int filas = pst.executeUpdate();
            conn.commit();
            return filas > 0;
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(null, "Error al abastecer inventario: " + e.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
            return false;
        }
    }
    
    public Modelo.Repuesto consultarRepuesto(int idRepuesto) {
        String sql = "SELECT id_repuesto, nombre, descripcion, precio, cantidad FROM repuesto WHERE id_repuesto = ?";
        try (Connection conn = ConexionOracle.obtenerConexio();
             PreparedStatement pst = conn.prepareStatement(sql)) {
            if (conn == null) return null;
            pst.setInt(1, idRepuesto);
            try (ResultSet rs = pst.executeQuery()) {
                if (rs.next()) {
                    return new Modelo.Repuesto(
                        rs.getInt("id_repuesto"),
                        rs.getString("nombre"),
                        rs.getString("descripcion"),
                        rs.getDouble("precio"),
                        rs.getInt("cantidad")
                    );
                }
            }
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(null, "Error al consultar repuesto: " + e.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
        }
        return null;
    }
    
    public boolean eliminarRepuesto(int idRepuesto) {
        String sqlVerificar = "SELECT COUNT(*) FROM detalle_repuesto WHERE id_repuesto = ?";
        String sqlEliminar = "DELETE FROM repuesto WHERE id_repuesto = ?";
        try (Connection conn = ConexionOracle.obtenerConexio()) {
            if (conn == null) return false;
            try (PreparedStatement pstVerificar = conn.prepareStatement(sqlVerificar)) {
                pstVerificar.setInt(1, idRepuesto);
                try (ResultSet rs = pstVerificar.executeQuery()) {
                    if (rs.next() && rs.getInt(1) > 0) {
                        JOptionPane.showMessageDialog(null, 
                            "No se puede eliminar el repuesto porque está asociado a órdenes de trabajo.", 
                            "Operación Denegada", JOptionPane.WARNING_MESSAGE);
                        return false;
                    }
                }
            }
            try (PreparedStatement pstEliminar = conn.prepareStatement(sqlEliminar)) {
                pstEliminar.setInt(1, idRepuesto);
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
