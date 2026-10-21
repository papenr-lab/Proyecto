/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package dao;

import conexion.ConexionOracle;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

public class OrdenDAO 
{
    public boolean iniciarReparacion(int idOrden) {
        String sql = "UPDATE orden_trabajo SET estado = 'EN PROCESO' WHERE id_orden = ? AND estado = 'PENDIENTE'";
        try (Connection conn = ConexionOracle.obtenerConexio();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            
            pstmt.setInt(1, idOrden);
            int filas = pstmt.executeUpdate();
            conn.commit();
            return filas > 0;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }
    
    public boolean agregarServicioAOrden(int idOrden, int idServicio, double costoManoObra) {
        String sqlDetalle = "INSERT INTO detalle_servicio (id_orden, id_servicio, costo) VALUES (?, ?, ?)";
        String sqlActualizarTotal = "UPDATE orden_trabajo SET total_reparacion = total_reparacion + ? WHERE id_orden = ?";

        Connection conn = null;
        try {
            conn = ConexionOracle.obtenerConexio();
            if (conn == null) return false;
            try (PreparedStatement pstmtDet = conn.prepareStatement(sqlDetalle)) {
                pstmtDet.setInt(1, idOrden);
                pstmtDet.setInt(2, idServicio);
                pstmtDet.setDouble(3, costoManoObra);
                pstmtDet.executeUpdate();
            }
            try (PreparedStatement pstmtTot = conn.prepareStatement(sqlActualizarTotal)) {
                pstmtTot.setDouble(1, costoManoObra);
                pstmtTot.setInt(2, idOrden);
                pstmtTot.executeUpdate();
            }
            conn.commit();
            return true;
        } 
        catch (SQLException e) {
            try {
                if (conn != null) conn.rollback();
            } catch (SQLException ex) {
                ex.printStackTrace();
            }
            e.printStackTrace();
            return false;
        } finally {
            try {
                if (conn != null) conn.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
    }
    
    public boolean agregarRepuestoAOrden(int idOrden, int idRepuesto, int cantidadRequerida) {
        String sqlStock = "SELECT cantidad_disponible, precio FROM repuesto WHERE id_repuesto = ?";
        String sqlDescontar = "UPDATE repuesto SET cantidad_disponible = cantidad_disponible - ? WHERE id_repuesto = ? AND cantidad_disponible >= ?";
        String sqlInsertDetalle = "INSERT INTO detalle_repuesto (id_orden, id_repuesto, cantidad, precio_unitario, subtotal) VALUES (?, ?, ?, ?, ?)";
        String sqlSumaTotal = "UPDATE orden_trabajo SET total_reparacion = total_reparacion + ? WHERE id_orden = ?";

        Connection conn = null;
        try {
            conn = ConexionOracle.obtenerConexio();
            if (conn == null) return false;
            double precioUnitario = 0;
            try (PreparedStatement pstStock = conn.prepareStatement(sqlStock)) {
                pstStock.setInt(1, idRepuesto);
                var rs = pstStock.executeQuery();
                if (rs.next()) {
                    int stock = rs.getInt("cantidad_disponible");
                    if (stock < cantidadRequerida || cantidadRequerida <= 0) {
                        return false;
                    }
                    precioUnitario = rs.getDouble("precio");
                } else {
                    return false;
                }
            }
            try (PreparedStatement pstDesc = conn.prepareStatement(sqlDescontar)) {
                pstDesc.setInt(1, cantidadRequerida);
                pstDesc.setInt(2, idRepuesto);
                pstDesc.setInt(3, cantidadRequerida);
                if (pstDesc.executeUpdate() == 0) {
                    conn.rollback();
                    return false;
                }
            }
            double subtotal = cantidadRequerida * precioUnitario;
            try (PreparedStatement pstIns = conn.prepareStatement(sqlInsertDetalle)) {
                pstIns.setInt(1, idOrden);
                pstIns.setInt(2, idRepuesto);
                pstIns.setInt(3, cantidadRequerida);
                pstIns.setDouble(4, precioUnitario);
                pstIns.setDouble(5, subtotal);
                pstIns.executeUpdate();
            }
            try (PreparedStatement pstTot = conn.prepareStatement(sqlSumaTotal)) {
                pstTot.setDouble(1, subtotal);
                pstTot.setInt(2, idOrden);
                pstTot.executeUpdate();
            }
            conn.commit();
            return true;
        } 
        catch (SQLException e) {
            try {
                if (conn != null) conn.rollback();
            } catch (SQLException ex) {
                ex.printStackTrace();
            }
            e.printStackTrace();
            return false;
        } finally {
            try {
                if (conn != null) conn.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
    }
}
