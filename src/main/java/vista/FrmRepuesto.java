/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/GUIForms/JFrame.java to edit this template
 */
package vista;

public class FrmRepuesto extends javax.swing.JFrame {
    
    private static final java.util.logging.Logger logger = java.util.logging.Logger.getLogger(FrmRepuesto.class.getName());

    public FrmRepuesto() {
        initComponents();
    }

    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jLabel1 = new javax.swing.JLabel();
        jLabel2 = new javax.swing.JLabel();
        jLabel3 = new javax.swing.JLabel();
        jLabel4 = new javax.swing.JLabel();
        jLabel5 = new javax.swing.JLabel();
        txtIdRepuesto = new javax.swing.JTextField();
        txtNombre = new javax.swing.JTextField();
        txtDescripcion = new javax.swing.JTextField();
        txtPrecio = new javax.swing.JTextField();
        txtCantidad = new javax.swing.JTextField();
        jButton1 = new javax.swing.JButton();
        jButton2 = new javax.swing.JButton();
        jButton3 = new javax.swing.JButton();
        jButton4 = new javax.swing.JButton();
        txtAbastecer = new javax.swing.JButton();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);

        jLabel1.setText("ID Repuesto:");

        jLabel2.setText("Nombre:");

        jLabel3.setText("Descripción:");

        jLabel4.setText("Precio (Q):");

        jLabel5.setText("Cantidad (Disponible):");

        jButton1.setText("Registrar");
        jButton1.addActionListener(this::jButton1ActionPerformed);

        jButton2.setText("Modificar");
        jButton2.addActionListener(this::jButton2ActionPerformed);

        jButton3.setText("Consultar");
        jButton3.addActionListener(this::jButton3ActionPerformed);

        jButton4.setText("Eliminar");
        jButton4.addActionListener(this::jButton4ActionPerformed);

        txtAbastecer.setText("Abastecer");
        txtAbastecer.addActionListener(this::txtAbastecerActionPerformed);

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createSequentialGroup()
                        .addGap(17, 17, 17)
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
                                    .addComponent(jLabel3)
                                    .addComponent(jLabel4)
                                    .addComponent(jLabel5))
                                .addGap(30, 30, 30)
                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addComponent(txtIdRepuesto)
                                    .addComponent(txtNombre)
                                    .addComponent(txtDescripcion)
                                    .addComponent(txtPrecio)
                                    .addComponent(txtCantidad)))))
                    .addGroup(layout.createSequentialGroup()
                        .addGap(141, 141, 141)
                        .addComponent(txtAbastecer)))
                .addContainerGap(36, Short.MAX_VALUE))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGap(13, 13, 13)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel1)
                    .addComponent(txtIdRepuesto, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel2)
                    .addComponent(txtNombre, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel3)
                    .addComponent(txtDescripcion, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel4)
                    .addComponent(txtPrecio, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel5)
                    .addComponent(txtCantidad, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(18, 18, 18)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jButton1)
                    .addComponent(jButton2)
                    .addComponent(jButton3)
                    .addComponent(jButton4))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(txtAbastecer)
                .addContainerGap(53, Short.MAX_VALUE))
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void jButton1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton1ActionPerformed
        String idText = txtIdRepuesto.getText().trim();
    String nombre = txtNombre.getText().trim();
    String desc = txtDescripcion.getText().trim();
    String precioText = txtPrecio.getText().trim();
    String cantText = txtCantidad.getText().trim();

    if (idText.isEmpty() || nombre.isEmpty() || desc.isEmpty() || precioText.isEmpty() || cantText.isEmpty()) {
        javax.swing.JOptionPane.showMessageDialog(this, "Todos los campos son obligatorios para el registro.", "Advertencia", javax.swing.JOptionPane.WARNING_MESSAGE);
        return;
    }

    try {
        int id = Integer.parseInt(idText);
        double precio = Double.parseDouble(precioText);
        int cantidad = Integer.parseInt(cantText);

        dao.RepuestoDAO repuestoDao = new dao.RepuestoDAO();
        boolean exito = repuestoDao.registrarRepuesto(id, nombre, desc, precio, cantidad);

        if (exito) {
            javax.swing.JOptionPane.showMessageDialog(this, "¡Repuesto registrado en Oracle con éxito!");
            txtIdRepuesto.setText("");
            txtNombre.setText("");
            txtDescripcion.setText("");
            txtPrecio.setText("");
            txtCantidad.setText("");
        }
    } catch (NumberFormatException e) {
        javax.swing.JOptionPane.showMessageDialog(this, "Asegúrese de ingresar formatos numéricos válidos (Precio decimal, ID y Cantidad enteros).", "Error de Formato", javax.swing.JOptionPane.ERROR_MESSAGE);
    }
    }//GEN-LAST:event_jButton1ActionPerformed

    private void txtAbastecerActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txtAbastecerActionPerformed
        String idText = txtIdRepuesto.getText().trim();
    String cantText = txtCantidad.getText().trim();

    if (idText.isEmpty() || cantText.isEmpty()) {
        javax.swing.JOptionPane.showMessageDialog(this, "Debe ingresar el ID del Repuesto y la Cantidad a sumar.", "Advertencia", javax.swing.JOptionPane.WARNING_MESSAGE);
        return;
    }

    try {
        int id = Integer.parseInt(idText);
        int cantidadASumar = Integer.parseInt(cantText);

        dao.RepuestoDAO repuestoDao = new dao.RepuestoDAO();
        boolean exito = repuestoDao.abastecerInventario(id, cantidadASumar);

        if (exito) {
            javax.swing.JOptionPane.showMessageDialog(this, "¡Inventario abastecido con éxito en la Base de Datos!");
            txtIdRepuesto.setText("");
            txtCantidad.setText("");
        } else {
            javax.swing.JOptionPane.showMessageDialog(this, "No se encontró ningún repuesto registrado con ese ID.", "Error", javax.swing.JOptionPane.ERROR_MESSAGE);
        }
    } catch (NumberFormatException e) {
        javax.swing.JOptionPane.showMessageDialog(this, "El ID y la Cantidad a sumar deben ser números enteros.", "Error de Formato", javax.swing.JOptionPane.ERROR_MESSAGE);
    }
    }//GEN-LAST:event_txtAbastecerActionPerformed

    private void jButton2ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton2ActionPerformed
        String idText = txtIdRepuesto.getText().trim();
    String nombre = txtNombre.getText().trim();
    String desc = txtDescripcion.getText().trim();
    String precioText = txtPrecio.getText().trim();

    if (idText.isEmpty() || nombre.isEmpty() || desc.isEmpty() || precioText.isEmpty()) {
        javax.swing.JOptionPane.showMessageDialog(this, "Todos los campos excepto la cantidad son obligatorios.", "Advertencia", javax.swing.JOptionPane.WARNING_MESSAGE);
        return;
    }

    try {
        int id = Integer.parseInt(idText);
        double precio = Double.parseDouble(precioText);

        dao.RepuestoDAO repuestoDao = new dao.RepuestoDAO();
        boolean exito = repuestoDao.modificarRepuesto(id, nombre, desc, precio);

        if (exito) {
            javax.swing.JOptionPane.showMessageDialog(this, "¡Repuesto actualizado con éxito!");
        } else {
            javax.swing.JOptionPane.showMessageDialog(this, "No se encontró ningún repuesto con ese ID.", "Error", javax.swing.JOptionPane.ERROR_MESSAGE);
        }
    } catch (NumberFormatException e) {
        javax.swing.JOptionPane.showMessageDialog(this, "El ID debe ser entero y el Precio decimal.", "Error de Formato", javax.swing.JOptionPane.ERROR_MESSAGE);
    }
    }//GEN-LAST:event_jButton2ActionPerformed

    private void jButton3ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton3ActionPerformed
        String idText = txtIdRepuesto.getText().trim();

    if (idText.isEmpty()) {
        javax.swing.JOptionPane.showMessageDialog(this, "Ingrese el ID del repuesto a consultar.", "Advertencia", javax.swing.JOptionPane.WARNING_MESSAGE);
        return;
    }

    try {
        int id = Integer.parseInt(idText);
        dao.RepuestoDAO repuestoDao = new dao.RepuestoDAO();
        Modelo.Repuesto repuesto = repuestoDao.consultarRepuesto(id);

        if (repuesto != null) {
            txtNombre.setText(repuesto.getNombre());
            txtDescripcion.setText(repuesto.getDescripcion());
            txtPrecio.setText(String.valueOf(repuesto.getPrecio()));
            txtCantidad.setText(String.valueOf(repuesto.getCantidadDisponible()));
            javax.swing.JOptionPane.showMessageDialog(this, "Repuesto encontrado.");
        } else {
            javax.swing.JOptionPane.showMessageDialog(this, "Repuesto no registrado.", "No Encontrado", javax.swing.JOptionPane.INFORMATION_MESSAGE);
            txtNombre.setText("");
            txtDescripcion.setText("");
            txtPrecio.setText("");
            txtCantidad.setText("");
        }
    } catch (NumberFormatException e) {
        javax.swing.JOptionPane.showMessageDialog(this, "El ID debe ser un número entero.", "Error de Formato", javax.swing.JOptionPane.ERROR_MESSAGE);
    }
    }//GEN-LAST:event_jButton3ActionPerformed

    private void jButton4ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton4ActionPerformed
        String idText = txtIdRepuesto.getText().trim();

    if (idText.isEmpty()) {
        javax.swing.JOptionPane.showMessageDialog(this, "Ingrese el ID del repuesto a eliminar.", "Advertencia", javax.swing.JOptionPane.WARNING_MESSAGE);
        return;
    }

    try {
        int id = Integer.parseInt(idText);
        dao.RepuestoDAO repuestoDao = new dao.RepuestoDAO();
        boolean exito = repuestoDao.eliminarRepuesto(id);

        if (exito) {
            javax.swing.JOptionPane.showMessageDialog(this, "¡Repuesto removido correctamente!");
            txtIdRepuesto.setText("");
            txtNombre.setText("");
            txtDescripcion.setText("");
            txtPrecio.setText("");
            txtCantidad.setText("");
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

        java.awt.EventQueue.invokeLater(() -> new FrmRepuesto().setVisible(true));
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton jButton1;
    private javax.swing.JButton jButton2;
    private javax.swing.JButton jButton3;
    private javax.swing.JButton jButton4;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JButton txtAbastecer;
    private javax.swing.JTextField txtCantidad;
    private javax.swing.JTextField txtDescripcion;
    private javax.swing.JTextField txtIdRepuesto;
    private javax.swing.JTextField txtNombre;
    private javax.swing.JTextField txtPrecio;
    // End of variables declaration//GEN-END:variables
}
