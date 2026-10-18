/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package vista;

import javax.swing.*;
import java.awt.*;
import javax.swing.table.DefaultTableModel;

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

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> new MenuPrincipal().setVisible(true));
    }
}