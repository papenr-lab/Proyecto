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

    public boolean permitirModificacion(int idOrden) {
        String sql = "SELECT estado FROM orden_trabajo WHERE id_orden = ?";
        try (Connection conn = ConexionOracle.obtenerConexio();
             PreparedStatement pst = conn.prepareStatement(sql)) {
            pst.setInt(1, idOrden);
            try (ResultSet rs = pst.executeQuery()) {
                if (rs.next()) {
                    String estado = rs.getString("estado");
                    if ("FINALIZADA".equals(estado) || "CANCELADA".equals(estado)) {
                        JOptionPane.showMessageDialog(null, 
                            "Operación denegada: La orden se encuentra " + estado + " y ya no puede ser modificada.", 
                            "Orden Cerrada", JOptionPane.WARNING_MESSAGE);
                        return false;
                    }
                }
            }
        } 
        catch (SQLException e) {
            e.printStackTrace();
        }
        return true;
    }

    public boolean finalizarOrden(int idOrden) {
        if (!permitirModificacion(idOrden)) return false;
        String sqlVerificarServicios = "SELECT COUNT(*) FROM detalle_servicio WHERE id_orden = ?";
        String sqlFinalizar = "UPDATE orden_trabajo SET estado = 'FINALIZADA' WHERE id_orden = ?";
        String sqlObtenerTotal = "SELECT total_reparacion, codigo_orden FROM orden_trabajo WHERE id_orden = ?";
        Connection conn = null;
        try {
            conn = ConexionOracle.obtenerConexio();
            if (conn == null) return false;
            try (PreparedStatement pstServ = conn.prepareStatement(sqlVerificarServicios)) {
                pstServ.setInt(1, idOrden);
                try (ResultSet rs = pstServ.executeQuery()) {
                    if (rs.next() && rs.getInt(1) == 0) {
                        JOptionPane.showMessageDialog(null, 
                            "No se puede finalizar la orden: No tiene servicios registrados.", 
                            "Validación", JOptionPane.WARNING_MESSAGE);
                        return false;
                    }
                }
            }
            try (PreparedStatement pstFin = conn.prepareStatement(sqlFinalizar)) {
                pstFin.setInt(1, idOrden);
                pstFin.executeUpdate();
            }
            double total = 0;
            String codigoOrden = "";
            try (PreparedStatement pstTot = conn.prepareStatement(sqlObtenerTotal)) {
                pstTot.setInt(1, idOrden);
                try (ResultSet rs = pstTot.executeQuery()) {
                    if (rs.next()) {
                        total = rs.getDouble("total_reparacion");
                        codigoOrden = rs.getString("codigo_orden");
                    }
                }
            }
            conn.commit();
            JOptionPane.showMessageDialog(null, 
                "¡Reparación finalizada con éxito!\n\n=== COMPROBANTE DE REPARACIÓN ===\nOrden: " + codigoOrden + "\nMonto Total a Pagar: Q" + total, 
                "Comprobante Generado", JOptionPane.INFORMATION_MESSAGE);
            return true;
        } 
        catch (SQLException e) {
            try { if (conn != null) conn.rollback(); } catch (SQLException ex) { ex.printStackTrace(); }
            e.printStackTrace();
            return false;
        } finally {
            try { if (conn != null) conn.close(); } catch (SQLException e) { e.printStackTrace(); }
        }
    }

    public boolean cancelarOrden(int idOrden) {
        if (!permitirModificacion(idOrden)) return false;
        String sqlObtenerRepuestos = "SELECT id_repuesto, cantidad FROM detalle_repuesto WHERE id_orden = ?";
        String sqlDevolverStock = "UPDATE repuesto SET cantidad_disponible = cantidad_disponible + ? WHERE id_repuesto = ?";
        String sqlCancelarOrden = "UPDATE orden_trabajo SET estado = 'CANCELADA' WHERE id_orden = ?";
        Connection conn = null;
        try {
            conn = ConexionOracle.obtenerConexio();
            if (conn == null) return false;
            try (PreparedStatement pstRep = conn.prepareStatement(sqlObtenerRepuestos)) {
                pstRep.setInt(1, idOrden);
                try (ResultSet rs = pstRep.executeQuery()) {
                    while (rs.next()) {
                        int idRepuesto = rs.getInt("id_repuesto");
                        int cantidadDevolver = rs.getInt("cantidad");
                        try (PreparedStatement pstDev = conn.prepareStatement(sqlDevolverStock)) {
                            pstDev.setInt(1, cantidadDevolver);
                            pstDev.setInt(2, idRepuesto);
                            pstDev.executeUpdate();
                        }
                    }
                }
            }
            try (PreparedStatement pstCan = conn.prepareStatement(sqlCancelarOrden)) {
                pstCan.setInt(1, idOrden);
                pstCan.executeUpdate();
            }
            conn.commit(); 
            JOptionPane.showMessageDialog(null, 
                "La orden ha sido cancelada y los repuestos fueron reintegrados al inventario.", 
                "Orden Cancelada", JOptionPane.INFORMATION_MESSAGE);
            return true;
        } 
        catch (SQLException e) {
            try { if (conn != null) conn.rollback(); } catch (SQLException ex) { ex.printStackTrace(); }
            e.printStackTrace();
            return false;
        } finally {
            try { if (conn != null) conn.close(); } catch (SQLException e) { e.printStackTrace(); }
        }
    }
}
