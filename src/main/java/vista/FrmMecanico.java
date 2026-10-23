/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/GUIForms/JFrame.java to edit this template
 */
package vista;

public class FrmMecanico extends javax.swing.JFrame {
    
    private static final java.util.logging.Logger logger = java.util.logging.Logger.getLogger(FrmMecanico.class.getName());

    public FrmMecanico() {
        initComponents();
    }

    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jLabel1 = new javax.swing.JLabel();
        jLabel2 = new javax.swing.JLabel();
        jLabel3 = new javax.swing.JLabel();
        txtIdMecanico = new javax.swing.JTextField();
        txtNombre = new javax.swing.JTextField();
        txtEspecialidad = new javax.swing.JTextField();
        jButton1 = new javax.swing.JButton();
        jButton2 = new javax.swing.JButton();
        jButton3 = new javax.swing.JButton();
        jButton4 = new javax.swing.JButton();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);

        jLabel1.setText("ID Mecanico:");

        jLabel2.setText("Nombre:");

        jLabel3.setText("Especialidad:");

        jButton1.setText("Registrar");
        jButton1.addActionListener(this::jButton1ActionPerformed);

        jButton2.setText("Modificar");
        jButton2.addActionListener(this::jButton2ActionPerformed);

        jButton3.setText("Consultar");
        jButton3.addActionListener(this::jButton3ActionPerformed);

        jButton4.setText("Eliminar");
        jButton4.addActionListener(this::jButton4ActionPerformed);

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGap(16, 16, 16)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addGroup(layout.createSequentialGroup()
                        .addComponent(jButton1)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(jButton2)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(jButton3)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(jButton4))
                    .addGroup(layout.createSequentialGroup()
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jLabel1)
                            .addComponent(jLabel2)
                            .addComponent(jLabel3))
                        .addGap(33, 33, 33)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(layout.createSequentialGroup()
                                .addComponent(txtIdMecanico, javax.swing.GroupLayout.PREFERRED_SIZE, 91, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGap(0, 0, Short.MAX_VALUE))
                            .addComponent(txtNombre)
                            .addComponent(txtEspecialidad))))
                .addContainerGap(37, Short.MAX_VALUE))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGap(14, 14, 14)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel1)
                    .addComponent(txtIdMecanico, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel2)
                    .addComponent(txtNombre, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(jLabel3)
                    .addComponent(txtEspecialidad, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(18, 18, 18)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jButton1)
                    .addComponent(jButton2)
                    .addComponent(jButton3)
                    .addComponent(jButton4))
                .addContainerGap(161, Short.MAX_VALUE))
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void jButton1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton1ActionPerformed
    String idText = txtIdMecanico.getText().trim();
    String nombre = txtNombre.getText().trim();
    String especialidad = txtEspecialidad.getText().trim();

    if (idText.isEmpty() || nombre.isEmpty() || especialidad.isEmpty()) {
        javax.swing.JOptionPane.showMessageDialog(this, "Todos los campos son obligatorios.", "Advertencia", javax.swing.JOptionPane.WARNING_MESSAGE);
        return;
    }

    try {
        int id = Integer.parseInt(idText);
        dao.MecanicoDAO mecanicoDao = new dao.MecanicoDAO();
        boolean exito = mecanicoDao.registrarMecanico(id, nombre, especialidad);

        if (exito) {
            javax.swing.JOptionPane.showMessageDialog(this, "¡Mecánico registrado exitosamente en Oracle!");
            txtIdMecanico.setText("");
            txtNombre.setText("");
            txtEspecialidad.setText("");
        }
    } catch (NumberFormatException e) {
        javax.swing.JOptionPane.showMessageDialog(this, "El ID del mecánico debe ser un número entero.", "Error de Formato", javax.swing.JOptionPane.ERROR_MESSAGE);
    }
    }//GEN-LAST:event_jButton1ActionPerformed

    private void jButton2ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton2ActionPerformed
    String idText = txtIdMecanico.getText().trim();
    String nombre = txtNombre.getText().trim();
    String especialidad = txtEspecialidad.getText().trim();

    if (idText.isEmpty() || nombre.isEmpty() || especialidad.isEmpty()) {
        javax.swing.JOptionPane.showMessageDialog(this, "Todos los campos son obligatorios para modificar.", "Advertencia", javax.swing.JOptionPane.WARNING_MESSAGE);
        return;
    }

    try {
        int id = Integer.parseInt(idText);
        dao.MecanicoDAO mecanicoDao = new dao.MecanicoDAO();
        boolean exito = mecanicoDao.modificarMecanico(id, nombre, especialidad);

        if (exito) {
            javax.swing.JOptionPane.showMessageDialog(this, "¡Datos del mecánico actualizados!");
        } else {
            javax.swing.JOptionPane.showMessageDialog(this, "No se encontró ningún mecánico con ese ID.", "Error", javax.swing.JOptionPane.ERROR_MESSAGE);
        }
    } catch (NumberFormatException e) {
        javax.swing.JOptionPane.showMessageDialog(this, "El ID debe ser un número entero.", "Error de Formato", javax.swing.JOptionPane.ERROR_MESSAGE);
    }
    }//GEN-LAST:event_jButton2ActionPerformed

    private void jButton3ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton3ActionPerformed
    String idText = txtIdMecanico.getText().trim();

    if (idText.isEmpty()) {
        javax.swing.JOptionPane.showMessageDialog(this, "Ingrese el ID del mecánico a consultar.", "Advertencia", javax.swing.JOptionPane.WARNING_MESSAGE);
        return;
    }

    try {
        int id = Integer.parseInt(idText);
        dao.MecanicoDAO mecanicoDao = new dao.MecanicoDAO();
        Modelo.Mecanico mecanico = mecanicoDao.consultarMecanico(id);

        if (mecanico != null) {
            txtNombre.setText(mecanico.getNombre());
            // Nota: Verifica si tu clase hereda getNombre() o usa otro método para mostrar la especialidad
            txtEspecialidad.setText(mecanico.getEspecialidad());
            javax.swing.JOptionPane.showMessageDialog(this, "Mecánico encontrado.");
        } else {
            javax.swing.JOptionPane.showMessageDialog(this, "Mecánico no registrado.", "No Encontrado", javax.swing.JOptionPane.INFORMATION_MESSAGE);
            txtNombre.setText("");
            txtEspecialidad.setText("");
        }
    } catch (NumberFormatException e) {
        javax.swing.JOptionPane.showMessageDialog(this, "El ID debe ser un número entero.", "Error de Formato", javax.swing.JOptionPane.ERROR_MESSAGE);
    }
    }//GEN-LAST:event_jButton3ActionPerformed

    private void jButton4ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton4ActionPerformed
    String idText = txtIdMecanico.getText().trim();

    if (idText.isEmpty()) {
        javax.swing.JOptionPane.showMessageDialog(this, "Ingrese el ID del mecánico a eliminar.", "Advertencia", javax.swing.JOptionPane.WARNING_MESSAGE);
        return;
    }

    try {
        int id = Integer.parseInt(idText);
        dao.MecanicoDAO mecanicoDao = new dao.MecanicoDAO();
        boolean exito = mecanicoDao.eliminarMecanico(id);

        if (exito) {
            javax.swing.JOptionPane.showMessageDialog(this, "¡Mecánico removido del sistema!");
            txtIdMecanico.setText("");
            txtNombre.setText("");
            txtEspecialidad.setText("");
        }
    } catch (NumberFormatException e) {
        javax.swing.JOptionPane.showMessageDialog(this, "El ID debe ser un número entero.", "Error de Formato", javax.swing.JOptionPane.ERROR_MESSAGE);
    }
    }//GEN-LAST:event_jButton4ActionPerformed

    public static void main(String args[]) {

        //<editor-fold defaultstate="collapsed" desc=" Look and feel setting code (optional) ">
        /* If Nimbus (introduced in Java SE 6) is not available, stay with the default look and feel.
         * For details see http://download.oracle.com/javase/tutorial/uiswing/lookandfeel/plaf.html 
         */
        try {
            for (javax.swing.UIManager.LookAndFeelInfo info : javax.swing.UIManager.getInstalledLookAndFeels()) {
                if ("Nimbus".equals(info.getName())) {
                    javax.swing.UIManager.setLookAndFeel(info.getClassName());
                    break;
                }
            }
        } catch (ReflectiveOperationException | javax.swing.UnsupportedLookAndFeelException ex) {
            logger.log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>

        java.awt.EventQueue.invokeLater(() -> new FrmMecanico().setVisible(true));
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton jButton1;
    private javax.swing.JButton jButton2;
    private javax.swing.JButton jButton3;
    private javax.swing.JButton jButton4;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JTextField txtEspecialidad;
    private javax.swing.JTextField txtIdMecanico;
    private javax.swing.JTextField txtNombre;
    // End of variables declaration//GEN-END:variables
}
