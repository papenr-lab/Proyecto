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

public class MenuPrincipal extends JFrame {
    private final JTabbedPane tabbedPane;
    private final DefaultTableModel modeloTabla;

    public MenuPrincipal() {
        setTitle("Sistema de Gestión - Taller El Último Arranque");
        setSize(800, 600);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
        String[] columnas = {"ID", "Nombre", "Tipo", "Precio", "Disponibilidad"};
        modeloTabla = new DefaultTableModel(columnas, 0);

        tabbedPane = new JTabbedPane();
        tabbedPane.addTab("Reportes Obligatorios", crearPanelTabla());
        tabbedPane.addTab("Órdenes", crearPanelOrdenes());
        tabbedPane.addTab("Reportes y Consultas", new PanelReportes());

        add(tabbedPane, BorderLayout.CENTER);

        cargarDatosDePrueba();
    }

    private JPanel crearPanelTabla() {
        JPanel panel = new JPanel(new BorderLayout());
        panel.add(new JScrollPane(new JTable(modeloTabla)), BorderLayout.CENTER);
        JButton botonExportar = new JButton("Exportar Reporte a CSV");
        botonExportar.setFont(new Font("Arial", Font.BOLD, 14));
        botonExportar.setBackground(new Color(34, 139, 34)); // Verde elegante
        botonExportar.setForeground(Color.WHITE); // Letras blancas
        botonExportar.setFocusPainted(false);
        botonExportar.addActionListener(e -> {
            exportarTablaToCSV("reporte_taller_mecanico.csv");
            JOptionPane.showMessageDialog(this, 
                    "¡Archivo CSV fue generado con éxito en la raíz del proyecto!", 
                    "Exportación Exitosa", 
                    JOptionPane.INFORMATION_MESSAGE);
        });

        panel.add(botonExportar, BorderLayout.SOUTH);
        
        return panel;
    }

    private JPanel crearPanelOrdenes() {
        JPanel panel = new JPanel();
        panel.add(new JLabel("Módulo de Órdenes de Trabajo y Repuestos"));
        return panel;
    }

    private void cargarDatosDePrueba() {
        modeloTabla.addRow(new Object[]{"1", "Cambio de aceite", "Mano de obra", "Q100.00", "Disponible"});
        modeloTabla.addRow(new Object[]{"101", "Filtro de Aceite", "Filtro sintético", "Q75.00", "19 unidades"});
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
