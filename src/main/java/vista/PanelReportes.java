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
    private final JButton btnCargarReporte;

    public PanelReportes() {
        setLayout(new BorderLayout());
        String[] columnas = {"Código", "Nombre", "Descripción", "Precio", "Cantidad Disponible"};
        modeloTabla = new DefaultTableModel(columnas, 0);
        tablaReportes = new JTable(modeloTabla);

        JScrollPane scrollPane = new JScrollPane(tablaReportes);
        add(scrollPane, BorderLayout.CENTER);
        JPanel panelSuperior = new JPanel();
        btnCargarReporte = new JButton("Cargar Reporte: Repuestos con Bajo Inventario (<= 5)");
        panelSuperior.add(btnCargarReporte);
        add(panelSuperior, BorderLayout.NORTH);
        btnCargarReporte.addActionListener(e -> cargarRepuestosBajoInventario());
    }

    private void cargarRepuestosBajoInventario() {
        modeloTabla.setRowCount(0);

        String sql = "SELECT codigo, nombre, descripcion, precio, cantidad_disponible FROM repuesto WHERE cantidad_disponible <= 5";

        try (Connection conn = ConexionOracle.obtenerConexio();
             Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery(sql)) {

            while (rs.next()) {
                String codigo = rs.getString("codigo");
                String nombre = rs.getString("nombre");
                String descripcion = rs.getString("descripcion");
                double precio = rs.getDouble("precio");
                int cantidad = rs.getInt("cantidad_disponible");
                modeloTabla.addRow(new Object[]{codigo, nombre, descripcion, precio, cantidad});
            }

        } catch (SQLException e) {
            JOptionPane.showMessageDialog(this, "Error al consultar la base de datos: " + e.getMessage(), 
                    "Error", JOptionPane.ERROR_MESSAGE);
        }
    }
}
