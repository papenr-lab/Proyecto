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
}
