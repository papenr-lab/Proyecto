/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package vista;
import javax.swing.*;
import java.awt.*;

public class MenuPrincipal extends JFrame {
    private final JTabbedPane tabbedPane;

    public MenuPrincipal() {
        setTitle("Sistema de Gestión - Taller El Último Arranque");
        setSize(800, 600);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);

        tabbedPane = new JTabbedPane();
        JPanel panelOrdenes = new JPanel();
        panelOrdenes.add(new JLabel("Módulo de Órdenes de Trabajo y Repuestos"));
        tabbedPane.addTab("Órdenes", panelOrdenes);
        tabbedPane.addTab("Reportes y Consultas", new PanelReportes());

        add(tabbedPane, BorderLayout.CENTER);
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            new MenuPrincipal().setVisible(true);
        });
    }
}
