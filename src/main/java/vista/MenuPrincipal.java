/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package vista;

import javax.swing.*;
import java.awt.*;
import javax.swing.table.DefaultTableModel;
import java.io.FileWriter;
import java.io.IOException;
import dao.ClienteDAO;
import dao.MecanicoDAO;
import dao.OrdenDAO;
import dao.RepuestoDAO;
import dao.ServicioDAO;
import dao.VehiculoDAO;
import Modelo.Servicio;
import java.util.List;

public class MenuPrincipal extends JFrame {
    private final JTabbedPane tabbedPane;
    private final DefaultTableModel modeloTabla;

    public MenuPrincipal() {
        setTitle("Sistema de Gestion - Taller El Último Arranque");
        setSize(800, 600);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
        String[] columnas = {"ID", "Nombre", "Tipo", "Precio", "Disponibilidad"};
        modeloTabla = new DefaultTableModel(columnas, 0);
        tabbedPane = new JTabbedPane();
        tabbedPane.addTab("Reportes Obligatorios", crearPanelTabla());
        tabbedPane.addTab("Ordenes", crearPanelOrdenes());
        tabbedPane.addTab("Reportes y Consultas", new PanelReportes());

        add(tabbedPane, BorderLayout.CENTER);

        cargarDatosDePrueba();
    }

    private JPanel crearPanelTabla() {
        JPanel panel = new JPanel(new BorderLayout());
        panel.add(new JScrollPane(new JTable(modeloTabla)), BorderLayout.CENTER);
        JButton botonExportar = new JButton("Exportar Reporte a CSV");
        botonExportar.setFont(new Font("Arial", Font.BOLD, 14));
        botonExportar.setBackground(new Color(34, 139, 34)); 
        botonExportar.setForeground(Color.WHITE); 
        botonExportar.setFocusPainted(false);
        botonExportar.addActionListener(e -> {
            exportarTablaToCSV("reporte_taller_mecanico.csv");
            JOptionPane.showMessageDialog(this, 
                    "¡Archivo CSV fue generado con exito en la raiz del proyecto!", 
                    "Exportación Exitosa", 
                    JOptionPane.INFORMATION_MESSAGE);
        });

        panel.add(botonExportar, BorderLayout.SOUTH);
        
        return panel;
    }

    private JPanel crearPanelOrdenes() {
        return new PanelOrdenesTrabajo();
    }

    private void cargarDatosDePrueba() {
         modeloTabla.setRowCount(0);
    
    ServicioDAO servicioDAO = new ServicioDAO();
    java.util.List<Modelo.Servicio> listaServicios = servicioDAO.obtenerTodosLosServicios();
    
    for (Modelo.Servicio s : listaServicios) {
        modeloTabla.addRow(new Object[]{
            s.getIdServicio(), 
            s.getDescripcion(), 
            "Mano de obra", 
            "Q" + s.getCostoManoObra(), 
            "Disponible"
        });
    }
}
    
    private void exportarTablaToCSV(String rutaArchivo) {
        try (FileWriter csv = new FileWriter(rutaArchivo)) 
        {
            for (int i = 0; i < modeloTabla.getColumnCount(); i++) {
                csv.write(modeloTabla.getColumnName(i) + (i == modeloTabla.getColumnCount() - 1 ? "" : ","));
            }
            csv.write("\n");
            
            for (int i = 0; i < modeloTabla.getRowCount(); i++) {
                for (int j = 0; j < modeloTabla.getColumnCount(); j++) {
                    String valor = String.valueOf(modeloTabla.getValueAt(i, j)).replace(",", "");
                    csv.write(valor + (j == modeloTabla.getColumnCount() - 1 ? "" : ","));
                }
                csv.write("\n");
            }
        } 
        catch (IOException e) {
            System.err.println("Error al guardar el archivo CSV: " + e.getMessage());
        }
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> new MenuPrincipal().setVisible(true));
    }
}
