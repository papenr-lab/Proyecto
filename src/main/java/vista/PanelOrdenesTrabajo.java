/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package vista;

import conexion.ConexionOracle;
import dao.OrdenDAO;
import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.sql.*;

public class PanelOrdenesTrabajo extends JPanel {
    private JComboBox<String> cbVehiculos;
    private JComboBox<String> cbMecanicos;
    private JTextArea txtProblema;
    private JButton btnCrearOrden;
    private JButton btnAgregarRepuesto;
    private JTable tablaOrdenes;
    private DefaultTableModel modeloTabla;
    private dao.OrdenDAO ordenDAO = new dao.OrdenDAO();

    public PanelOrdenesTrabajo() {
        setLayout(new BorderLayout(10, 10));
        JPanel panelFormulario = new JPanel(new GridLayout(4, 2, 5, 5));
        panelFormulario.setBorder(BorderFactory.createTitledBorder("Nueva Orden de Trabajo"));
        panelFormulario.add(new JLabel("Vehiculo (Placa):"));
        cbVehiculos = new JComboBox<>();
        panelFormulario.add(cbVehiculos);
        panelFormulario.add(new JLabel("Mecanico Asignado:"));
        cbMecanicos = new JComboBox<>();
        panelFormulario.add(cbMecanicos);
        panelFormulario.add(new JLabel("Descripcion del Problema:"));
        txtProblema = new JTextArea(2, 20);
        panelFormulario.add(new JScrollPane(txtProblema));
        btnCrearOrden = new JButton("Crear Orden (PENDIENTE)");
        btnAgregarRepuesto = new JButton("Gestionar Repuestos / Servicios");
        JPanel panelBotones = new JPanel(new FlowLayout());
        panelBotones.add(btnCrearOrden);
        panelBotones.add(btnAgregarRepuesto);
        panelFormulario.add(panelBotones);
        add(panelFormulario, BorderLayout.NORTH);
        
        String[] columnas = {"ID Orden", "Codigo", "Problema", "Estado", "Total (Q)"};
        modeloTabla = new DefaultTableModel(columnas, 0);
        tablaOrdenes = new JTable(modeloTabla);
        add(new JScrollPane(tablaOrdenes), BorderLayout.CENTER);       
        cargarCombos();
        cargarTablaOrdenes();
        btnCrearOrden.addActionListener(e -> registrarOrdenPendiente());
        btnAgregarRepuesto.addActionListener(e -> pasarOrdenAEnProceso());
    }

    private void cargarCombos() 
    {
        try (Connection conn = ConexionOracle.obtenerConexio();
             Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery("SELECT id_vehiculo, placa FROM vehiculo")) {
            cbVehiculos.removeAllItems();
            while (rs.next()) {
                cbVehiculos.addItem(rs.getInt("id_vehiculo") + " - " + rs.getString("placa"));
            }
        } 
        catch (SQLException e) {
            e.printStackTrace();
        }
        try (Connection conn = ConexionOracle.obtenerConexio();
             Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery("SELECT id_mecanico, nombre FROM mecanico")) {
            cbMecanicos.removeAllItems();
            while (rs.next()) {
                cbMecanicos.addItem(rs.getInt("id_mecanico") + " - " + rs.getString("nombre"));
            }
        } 
        catch (SQLException e) {
            e.printStackTrace();
        }
    }

    private void registrarOrdenPendiente() {
        String vehiculoSeleccionado = (String) cbVehiculos.getSelectedItem();
        String mecanicoSeleccionado = (String) cbMecanicos.getSelectedItem();
        String problema = txtProblema.getText().trim();

        if (vehiculoSeleccionado == null || mecanicoSeleccionado == null || problema.isEmpty()) {
            JOptionPane.showMessageDialog(this, "Por favor complete todos los campos.", "Advertencia", JOptionPane.WARNING_MESSAGE);
            return;
        }
        int idVehiculo = Integer.parseInt(vehiculoSeleccionado.split(" - ")[0]);
        int idMecanico = Integer.parseInt(mecanicoSeleccionado.split(" - ")[0]);
        String codigoOrden = "ORD-" + System.currentTimeMillis(); 
        String sql = "INSERT INTO orden_trabajo (codigo_orden, id_vehiculo, id_mecanico, descripcion_problema, estado, total_reparacion) VALUES (?, ?, ?, ?, 'PENDIENTE', 0)";

        try (Connection conn = ConexionOracle.obtenerConexio();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {

            pstmt.setString(1, codigoOrden);
            pstmt.setInt(2, idVehiculo);
            pstmt.setInt(3, idMecanico);
            pstmt.setString(4, problema);
            
            pstmt.executeUpdate();
            conn.commit(); 

            JOptionPane.showMessageDialog(this, "¡Orden de trabajo creada en estado PENDIENTE con exito!", "Exito", JOptionPane.INFORMATION_MESSAGE);
            txtProblema.setText("");
            cargarTablaOrdenes();
        } 
        catch (SQLException e) {
            JOptionPane.showMessageDialog(this, "Error al crear la orden: " + e.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
            e.printStackTrace();
        }
    }

    public void cargarTablaOrdenes() {
        modeloTabla.setRowCount(0);
        String sql = "SELECT id_orden, codigo_orden, descripcion_problema, estado, total_reparacion FROM orden_trabajo";

        try (Connection conn = ConexionOracle.obtenerConexio();
             Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery(sql)) {

            while (rs.next()) {
                int id = rs.getInt("id_orden");
                String codigo = rs.getString("codigo_orden");
                String problema = rs.getString("descripcion_problema");
                String estado = rs.getString("estado");
                double total = rs.getDouble("total_reparacion");

                modeloTabla.addRow(new Object[]{id, codigo, problema, estado, total});
            }
        } 
        catch (SQLException e) {
            e.printStackTrace();
        }
    }
    
    private void pasarOrdenAEnProceso() {
    int filaSeleccionada = tablaOrdenes.getSelectedRow();
        if (filaSeleccionada == -1) {
            JOptionPane.showMessageDialog(this, "Por favor, seleccione una orden de la tabla primero.", "Advertencia", JOptionPane.WARNING_MESSAGE);
            return;
        }
        int idOrden = (int) tablaOrdenes.getValueAt(filaSeleccionada, 0);
        boolean exito = ordenDAO.iniciarReparacion(idOrden);
        if (exito) {
            JOptionPane.showMessageDialog(this, "La orden #" + idOrden + " ahora está EN PROCESO.", "Exito", JOptionPane.INFORMATION_MESSAGE);
            cargarTablaOrdenes(); 
        } else {
            JOptionPane.showMessageDialog(this, "No se pudo actualizar el estado de la orden.", "Error", JOptionPane.ERROR_MESSAGE);
        }
    }
}