/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package vista;

import conexion.ConexionOracle;
import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.sql.*;

public class PanelReportes extends JPanel {
    private final JTable tablaReportes;
    private final DefaultTableModel modeloTabla;
    private final JComboBox<String> cmbTipoReporte;
    private final JButton btnCargarReporte;

    public PanelReportes() {
        setLayout(new BorderLayout());

        String[] columnas = {"Código", "Nombre", "Descripcion", "Precio", "Cantidad Disponible"};
        modeloTabla = new DefaultTableModel(columnas, 0);
        tablaReportes = new JTable(modeloTabla);

        JScrollPane scrollPane = new JScrollPane(tablaReportes);
        add(scrollPane, BorderLayout.CENTER);
        JPanel panelSuperior = new JPanel(new FlowLayout(FlowLayout.LEFT));
        cmbTipoReporte = new JComboBox<>(new String[]{
            "1. Repuestos con Bajo Inventario (<= 5)",
            "2. Ordenes por Estado",
            "3. Ordenes por Mecanico",
            "4. Ingresos por Rango de Fechas (Finalizadas)"
        });
        
        btnCargarReporte = new JButton("Generar Reporte");
        panelSuperior.add(new JLabel("Seleccione Reporte:"));
        panelSuperior.add(cmbTipoReporte);
        panelSuperior.add(btnCargarReporte);
        
        add(panelSuperior, BorderLayout.NORTH);

        btnCargarReporte.addActionListener(e -> seleccionarYGenerarReporte());
    }

    private void seleccionarYGenerarReporte() {
        int seleccion = cmbTipoReporte.getSelectedIndex();

        switch (seleccion) {
            case 0:
                cargarRepuestosBajoInventario();
                break;
            case 1:
                String[] estados = {"PENDIENTE", "EN PROCESO", "FINALIZADA", "CANCELADA"};
                String estadoElegido = (String) JOptionPane.showInputDialog(
                    this, "Seleccione el estado:", "Filtrar Ordenes",
                    JOptionPane.QUESTION_MESSAGE, null, estados, estados[0]
                );
                if (estadoElegido != null) {
                    cargarOrdenesPorEstado(estadoElegido);
                }
                break;
            case 2:
                String mecanicoInput = JOptionPane.showInputDialog(this, "Ingrese el ID del Mecanico:");
                if (mecanicoInput != null && !mecanicoInput.trim().isEmpty()) {
                    try {
                        int idMec = Integer.parseInt(mecanicoInput.trim());
                        cargarOrdenesPorMecanico(idMec);
                    } catch (NumberFormatException ex) {
                        JOptionPane.showMessageDialog(this, "El ID debe ser un numero entero.", "Error", JOptionPane.ERROR_MESSAGE);
                    }
                }
                break;
            case 3:
                JTextField txtInicio = new JTextField("2026-01-01");
                JTextField txtFin = new JTextField("2026-12-31");
                Object[] mensaje = {
                    "Fecha Inicio (YYYY-MM-DD):", txtInicio,
                    "Fecha Fin (YYYY-MM-DD):", txtFin
                };
                int option = JOptionPane.showConfirmDialog(this, mensaje, "Ingrese Rango de Fechas", JOptionPane.OK_CANCEL_OPTION);
                if (option == JOptionPane.OK_OPTION) {
                    cargarIngresosPorFechas(txtInicio.getText().trim(), txtFin.getText().trim());
                }
                break;
            default:
                break;
        }
    }

    private void cargarRepuestosBajoInventario() {
        modeloTabla.setColumnIdentifiers(new String[]{"Codigo", "Nombre", "Descripcion", "Precio", "Cantidad Disponible"});
        modeloTabla.setRowCount(0);

        String sql = "SELECT codigo, nombre, descripcion, precio, cantidad_disponible FROM repuesto WHERE cantidad_disponible <= 5";

        try (Connection conn = ConexionOracle.obtenerConexio();
             Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery(sql)) {

            while (rs.next()) {
                modeloTabla.addRow(new Object[]{
                    rs.getString("codigo"),
                    rs.getString("nombre"),
                    rs.getString("descripcion"),
                    rs.getDouble("precio"),
                    rs.getInt("cantidad_disponible")
                });
            }
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(this, "Error al consultar la base de datos: " + e.getMessage(), 
                    "Error", JOptionPane.ERROR_MESSAGE);
        }
    }

    private void cargarOrdenesPorEstado(String estadoFiltro) {
        modeloTabla.setColumnIdentifiers(new String[]{"ID Orden", "Codigo", "Vehiculo (Placa)", "Mecanico", "Estado", "Total (Q)"});
        modeloTabla.setRowCount(0);

        String sql = "SELECT o.id_orden, o.codigo_orden, v.placa, m.nombre AS mecanico, o.estado, o.total_reparacion " +
                     "FROM orden_trabajo o " +
                     "JOIN vehiculo v ON o.id_vehiculo = v.id_vehiculo " +
                     "JOIN mecanico m ON o.id_mecanico = m.id_mecanico " +
                     "WHERE o.estado = ?";

        try (Connection conn = ConexionOracle.obtenerConexio();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setString(1, estadoFiltro);
            ResultSet rs = pstmt.executeQuery();

            while (rs.next()) {
                modeloTabla.addRow(new Object[]{
                    rs.getInt("id_orden"),
                    rs.getString("codigo_orden"),
                    rs.getString("placa"),
                    rs.getString("mecanico"),
                    rs.getString("estado"),
                    rs.getDouble("total_reparacion")
                });
            }
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(this, "Error al consultar ordenes por estado: " + e.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
        }
    }

    private void cargarOrdenesPorMecanico(int idMecanico) {
        modeloTabla.setColumnIdentifiers(new String[]{"ID Orden", "Codigo", "Problema", "Estado", "Total (Q)"});
        modeloTabla.setRowCount(0);

        String sql = "SELECT id_orden, codigo_orden, descripcion_problema, estado, total_reparacion " +
                     "FROM orden_trabajo WHERE id_mecanico = ?";

        try (Connection conn = ConexionOracle.obtenerConexio();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setInt(1, idMecanico);
            ResultSet rs = pstmt.executeQuery();

            while (rs.next()) {
                modeloTabla.addRow(new Object[]{
                    rs.getInt("id_orden"),
                    rs.getString("codigo_orden"),
                    rs.getString("descripcion_problema"),
                    rs.getString("estado"),
                    rs.getDouble("total_reparacion")
                });
            }
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(this, "Error al consultar órdenes por mecánico: " + e.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
        }
    }

    private void cargarIngresosPorFechas(String fechaInicio, String fechaFin) {
        modeloTabla.setColumnIdentifiers(new String[]{"ID Orden", "Código", "Fecha Creación", "Total (Q)"});
        modeloTabla.setRowCount(0);

        String sql = "SELECT id_orden, codigo_orden, fecha_creacion, total_reparacion " +
                     "FROM orden_trabajo " +
                     "WHERE estado = 'FINALIZADA' AND TO_CHAR(fecha_creacion, 'YYYY-MM-DD') BETWEEN ? AND ?";

        try (Connection conn = ConexionOracle.obtenerConexio();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setString(1, fechaInicio);
            pstmt.setString(2, fechaFin);
            ResultSet rs = pstmt.executeQuery();

            double ingresosTotales = 0;
            while (rs.next()) {
                double total = rs.getDouble("total_reparacion");
                ingresosTotales += total;
                modeloTabla.addRow(new Object[]{
                    rs.getInt("id_orden"),
                    rs.getString("codigo_orden"),
                    rs.getTimestamp("fecha_creacion"),
                    total
                });
            }
            JOptionPane.showMessageDialog(this, "Ingresos totales en el rango: Q" + ingresosTotales, "Resultado de Ingresos", JOptionPane.INFORMATION_MESSAGE);
        } 
        catch (SQLException e) {
            JOptionPane.showMessageDialog(this, "Error al calcular ingresos: " + e.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
        }
    }
}