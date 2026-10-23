/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/GUIForms/JFrame.java to edit this template
 */
package vista;

public class FrmCliente extends javax.swing.JFrame {
    
    private static final java.util.logging.Logger logger = java.util.logging.Logger.getLogger(FrmCliente.class.getName());

    public FrmCliente() {
        initComponents();
    }

    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jLabel1 = new javax.swing.JLabel();
        jLabel2 = new javax.swing.JLabel();
        txtId = new javax.swing.JTextField();
        txtNombre = new javax.swing.JTextField();
        jButton2 = new javax.swing.JButton();
        jButton3 = new javax.swing.JButton();
        jButton4 = new javax.swing.JButton();
        jButton5 = new javax.swing.JButton();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);

        jLabel1.setText("ID Cliente ");

        jLabel2.setText("Nombre del Cliente ");

        jButton2.setText("Registrar");
        jButton2.addActionListener(this::jButton2ActionPerformed);

        jButton3.setText("Modificar");
        jButton3.addActionListener(this::jButton3ActionPerformed);

        jButton4.setText("Consultar");
        jButton4.addActionListener(this::jButton4ActionPerformed);

        jButton5.setText("Eliminar");
        jButton5.addActionListener(this::jButton5ActionPerformed);

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGap(24, 24, 24)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addGroup(layout.createSequentialGroup()
                        .addComponent(jButton2)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(jButton3)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(jButton4)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(jButton5))
                    .addGroup(layout.createSequentialGroup()
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jLabel1)
                            .addComponent(jLabel2))
                        .addGap(30, 30, 30)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(txtId, javax.swing.GroupLayout.PREFERRED_SIZE, 104, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(txtNombre))))
                .addContainerGap(29, Short.MAX_VALUE))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGap(12, 12, 12)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel1)
                    .addComponent(txtId, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel2)
                    .addComponent(txtNombre, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(18, 18, 18)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jButton2)
                    .addComponent(jButton3)
                    .addComponent(jButton4)
                    .addComponent(jButton5))
                .addContainerGap(197, Short.MAX_VALUE))
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void jButton2ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton2ActionPerformed
   String id = txtId.getText();
        String nombre = txtNombre.getText();

        if (id.trim().isEmpty() || nombre.trim().isEmpty()) {
            javax.swing.JOptionPane.showMessageDialog(this, "Todos los campos son obligatorios.", "Advertencia", javax.swing.JOptionPane.WARNING_MESSAGE);
            return;
        }
        dao.ClienteDAO clienteDao = new dao.ClienteDAO();
        boolean exito = clienteDao.registrarCliente(Integer.parseInt(id), nombre);
        
        if (exito) {
            javax.swing.JOptionPane.showMessageDialog(this, "¡Cliente registrado con éxito en Oracle!");
            txtId.setText("");
            txtNombre.setText("");
        }
    }//GEN-LAST:event_jButton2ActionPerformed

    private void jButton5ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton5ActionPerformed
        String idText = txtId.getText();

    if (idText.trim().isEmpty()) {
        javax.swing.JOptionPane.showMessageDialog(this, "Ingrese el ID del cliente que desea eliminar.", "Advertencia", javax.swing.JOptionPane.WARNING_MESSAGE);
        return;
    }

    try {
        int id = Integer.parseInt(idText);
        dao.ClienteDAO clienteDao = new dao.ClienteDAO();
        boolean exito = clienteDao.eliminarCliente(id);

        if (exito) {
            javax.swing.JOptionPane.showMessageDialog(this, "¡Cliente eliminado con éxito de la base de datos!");
            txtId.setText("");
            txtNombre.setText("");
        }
    } 
    catch (NumberFormatException e) {
        javax.swing.JOptionPane.showMessageDialog(this, "El ID debe ser un número entero válido.", "Error de Formato", javax.swing.JOptionPane.ERROR_MESSAGE);
    }
    }//GEN-LAST:event_jButton5ActionPerformed

    private void jButton3ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton3ActionPerformed
        String idText = txtId.getText();
    String nombreText = txtNombre.getText();

    if (idText.trim().isEmpty() || nombreText.trim().isEmpty()) {
        javax.swing.JOptionPane.showMessageDialog(this, "Todos los campos son obligatorios para modificar.", "Advertencia", javax.swing.JOptionPane.WARNING_MESSAGE);
        return;
    }

    dao.ClienteDAO clienteDao = new dao.ClienteDAO();
    boolean exito = clienteDao.modificarCliente(Integer.parseInt(idText), nombreText);

    if (exito) {
        javax.swing.JOptionPane.showMessageDialog(this, "¡Cliente actualizado con éxito!");
    } else {
        javax.swing.JOptionPane.showMessageDialog(this, "No se encontró ningún cliente con ese ID.", "Error", javax.swing.JOptionPane.ERROR_MESSAGE);
    }
    }//GEN-LAST:event_jButton3ActionPerformed

    private void jButton4ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton4ActionPerformed
        String idText = txtId.getText();

    if (idText.trim().isEmpty()) {
        javax.swing.JOptionPane.showMessageDialog(this, "Ingrese el ID del cliente a consultar.", "Advertencia", javax.swing.JOptionPane.WARNING_MESSAGE);
        return;
    }

    dao.ClienteDAO clienteDao = new dao.ClienteDAO();
    Modelo.Cliente cliente = clienteDao.consultarCliente(Integer.parseInt(idText));

    if (cliente != null) {
        txtNombre.setText(cliente.getNombre());
        javax.swing.JOptionPane.showMessageDialog(this, "Cliente encontrado.");
    } else {
        javax.swing.JOptionPane.showMessageDialog(this, "Cliente no registrado en el sistema.", "No Encontrado", javax.swing.JOptionPane.INFORMATION_MESSAGE);
        txtNombre.setText("");
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
        java.awt.EventQueue.invokeLater(() -> new FrmCliente().setVisible(true));
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton jButton2;
    private javax.swing.JButton jButton3;
    private javax.swing.JButton jButton4;
    private javax.swing.JButton jButton5;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JTextField txtId;
    private javax.swing.JTextField txtNombre;
    // End of variables declaration//GEN-END:variables
}
