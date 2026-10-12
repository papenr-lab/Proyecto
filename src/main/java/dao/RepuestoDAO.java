/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package dao;
import conexion.ConexionOracle;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class RepuestoDAO {
    public boolean asignarRepuestoAOrden(int idOrden, int idRepuesto, int cantidadRequerida) {
        String sqlVerificarStock = "SELECT cantidad_disponible, precio FROM repuesto WHERE id_repuesto = ?";
        String sqlDescontarStock = "UPDATE repuesto SET cantidad_disponible = cantidad_disponible - ? WHERE id_repuesto = ? AND cantidad_disponible >= ?";
        String sqlInsertarDetalle = "INSERT INTO detalle_repuesto (id_orden, id_repuesto, cantidad, precio_unitario, subtotal) VALUES (?, ?, ?, ?, ?)";

        Connection conn = null;
        try {
            conn = ConexionOracle.obtenerConexio();
            if (conn == null) return false;
            PreparedStatement pstmtStock = conn.prepareStatement(sqlVerificarStock);
            pstmtStock.setInt(1, idRepuesto);
            ResultSet rs = pstmtStock.executeQuery();

            if (rs.next()) {
                int stockDisponible = rs.getInt("cantidad_disponible");
                double precioUnitario = rs.getDouble("precio");

                if (stockDisponible < cantidadRequerida || cantidadRequerida <= 0) {
                    System.out.println("Inventario insuficiente o cantidad inválida.");
                    return false; 
                }

                PreparedStatement pstmtUpdate = conn.prepareStatement(sqlDescontarStock);
                pstmtUpdate.setInt(1, cantidadRequerida);
                pstmtUpdate.setInt(2, idRepuesto);
                pstmtUpdate.setInt(3, cantidadRequerida);
                int filasActualizadas = pstmtUpdate.executeUpdate();

                if (filasActualizadas == 0) {
                    conn.rollback();
                    return false;
                }

                double subtotal = cantidadRequerida * precioUnitario;
                PreparedStatement pstmtInsert = conn.prepareStatement(sqlInsertarDetalle);
                pstmtInsert.setInt(1, idOrden);
                pstmtInsert.setInt(2, idRepuesto);
                pstmtInsert.setInt(3, cantidadRequerida);
                pstmtInsert.setDouble(4, precioUnitario);
                pstmtInsert.setDouble(5, subtotal);
                pstmtInsert.executeUpdate();
                conn.commit();
                return true;
            }
        } catch (SQLException e) {
            if (conn != null) {
                try {
                    conn.rollback(); 
                } catch (SQLException ex) {
                    System.err.println("Error en el rollback: " + ex.getMessage());
                }
            }
        } finally {
            if (conn != null) {
                try {
                    conn.close(); 
                } catch (SQLException ex) {
                    System.err.println("Error al cerrar la conexión: " + ex.getMessage());
                }
            }
        }
        return false;
    }
}
