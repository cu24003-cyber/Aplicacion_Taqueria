package Formularios;

import clasesDAO.UsuarioDAO;
import clases.Usuario;
import java.util.List;
import javax.swing.table.DefaultTableModel;
import java.awt.Color;
import java.awt.Font;
import javax.swing.BorderFactory;
import javax.swing.ImageIcon;

public class frmUsuarios extends javax.swing.JFrame {

    UsuarioDAO usuarioDAO = new UsuarioDAO();
    DefaultTableModel modeloMeseros = new DefaultTableModel();
    boolean editando = false;
    private static final java.util.logging.Logger logger = java.util.logging.Logger.getLogger(frmUsuarios.class.getName());

    public frmUsuarios() {
        this.setUndecorated(true);
        initComponents();
        setLocationRelativeTo(null);
        setIconImage(new ImageIcon(getClass().getResource("/Imagenes/logo.png")).getImage());
        setSize(650, 530);
        // ── Estilos ───────────────────────────────────────────────────
        Color fondoOscuro = new Color(20, 28, 48);
        Color fondoCampo = new Color(45, 55, 80);
        Color azul = new Color(100, 149, 237);
        getContentPane().setBackground(fondoOscuro);

// ── Encabezado y etiquetas ─────────────────────────────────────
        lblEncabezado.setForeground(Color.WHITE);
        lblEncabezado.setFont(new Font("Segoe UI", Font.BOLD, 16));
        lblMeseros.setForeground(Color.WHITE);
        lblMeseros.setFont(new Font("Segoe UI", Font.BOLD, 14));

        for (javax.swing.JLabel lbl : new javax.swing.JLabel[]{
            lblId, lblNombre, jLabel1, jLabel2, lblContrasenia}) {
            lbl.setForeground(azul);
            lbl.setFont(new Font("Segoe UI", Font.BOLD, 13));
        }

// ── Campos de texto ──────────────────────────────────────────
        for (javax.swing.JTextField tf : new javax.swing.JTextField[]{
            txtID, txtNombre, txtApellido, txtUsuario, txtContrasenia}) {
            tf.setBackground(fondoCampo);
            tf.setForeground(Color.WHITE);
            tf.setCaretColor(Color.WHITE);
            tf.setBorder(BorderFactory.createCompoundBorder(
                    BorderFactory.createLineBorder(azul, 2),
                    BorderFactory.createEmptyBorder(4, 8, 4, 8)));
            tf.setFont(new Font("Segoe UI", Font.PLAIN, 13));
        }

// ── Botón Cerrar ──────────────────────────────────────────────
        BtnCerrar.setBackground(Color.RED);
        BtnCerrar.setForeground(Color.WHITE);
        BtnCerrar.setFocusPainted(false);
        BtnCerrar.setBorderPainted(false);
        BtnCerrar.setFont(new Font("Segoe UI", Font.BOLD, 13));

// ── Botón Guardar ─────────────────────────────────────────────
        btnGuardar.setBackground(new Color(63, 94, 251));
        btnGuardar.setForeground(Color.WHITE);
        btnGuardar.setFont(new Font("Segoe UI", Font.BOLD, 13));
        btnGuardar.setFocusPainted(false);
        btnGuardar.setOpaque(true);
        btnGuardar.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        btnGuardar.setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createLineBorder(new Color(40, 60, 180), 2),
                BorderFactory.createEmptyBorder(6, 16, 6, 16)));

// ── Botón Actualizar ──────────────────────────────────────────
        btnActualizar.setBackground(new Color(40, 167, 69));
        btnActualizar.setForeground(Color.WHITE);
        btnActualizar.setFont(new Font("Segoe UI", Font.BOLD, 13));
        btnActualizar.setFocusPainted(false);
        btnActualizar.setOpaque(true);
        btnActualizar.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        btnActualizar.setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createLineBorder(new Color(30, 130, 50), 2),
                BorderFactory.createEmptyBorder(6, 16, 6, 16)));

// ── Botón Eliminar ────────────────────────────────────────────
        btnEliminar.setBackground(new Color(220, 53, 69));
        btnEliminar.setForeground(Color.WHITE);
        btnEliminar.setFont(new Font("Segoe UI", Font.BOLD, 13));
        btnEliminar.setFocusPainted(false);
        btnEliminar.setOpaque(true);
        btnEliminar.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        btnEliminar.setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createLineBorder(new Color(170, 30, 45), 2),
                BorderFactory.createEmptyBorder(6, 16, 6, 16)));

// ── Botón Limpiar ─────────────────────────────────────────────
        btnLimpiar.setBackground(new Color(255, 193, 7));
        btnLimpiar.setForeground(new Color(30, 30, 30));
        btnLimpiar.setFont(new Font("Segoe UI", Font.BOLD, 13));
        btnLimpiar.setFocusPainted(false);
        btnLimpiar.setOpaque(true);
        btnLimpiar.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        btnLimpiar.setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createLineBorder(new Color(200, 150, 0), 2),
                BorderFactory.createEmptyBorder(6, 16, 6, 16)));
        txtID.setEditable(false);
        listarMeseros();
        limpiarCampos();
    }

    private void listarMeseros() {
        String[] titulos = {"ID", "Nombre", "Apellido", "Usuario"};
        modeloMeseros = new DefaultTableModel(null, titulos) {
            @Override
            public boolean isCellEditable(int r, int c) {
                return false;
            }
        };
        for (Usuario u : usuarioDAO.listarMeseros()) {
            modeloMeseros.addRow(new Object[]{
                u.getIdUsuario(), u.getNombre(), u.getApellido(), u.getUsuario()
            });
        }
        tblMeseros.setModel(modeloMeseros);
    }

    private void limpiarCampos() {
        txtID.setText(String.valueOf(usuarioDAO.obtenerProximoIdUsuario()));
        txtNombre.setText("");
        txtApellido.setText("");
        txtUsuario.setText("");
        txtContrasenia.setText("");
        editando = false;
    }

    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        lblEncabezado = new javax.swing.JLabel();
        BtnCerrar = new javax.swing.JButton();
        lblId = new javax.swing.JLabel();
        txtID = new javax.swing.JTextField();
        lblNombre = new javax.swing.JLabel();
        txtNombre = new javax.swing.JTextField();
        jLabel1 = new javax.swing.JLabel();
        txtApellido = new javax.swing.JTextField();
        jLabel2 = new javax.swing.JLabel();
        txtUsuario = new javax.swing.JTextField();
        lblContrasenia = new javax.swing.JLabel();
        txtContrasenia = new javax.swing.JTextField();
        btnGuardar = new javax.swing.JButton();
        btnEliminar = new javax.swing.JButton();
        btnActualizar = new javax.swing.JButton();
        btnLimpiar = new javax.swing.JButton();
        lblMeseros = new javax.swing.JLabel();
        scrllMeseros = new javax.swing.JScrollPane();
        tblMeseros = new javax.swing.JTable();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
        getContentPane().setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        lblEncabezado.setText("Gestion de ususarios (Meseros)");
        getContentPane().add(lblEncabezado, new org.netbeans.lib.awtextra.AbsoluteConstraints(20, 20, -1, -1));

        BtnCerrar.setText("Cerrar");
        BtnCerrar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                BtnCerrarActionPerformed(evt);
            }
        });
        getContentPane().add(BtnCerrar, new org.netbeans.lib.awtextra.AbsoluteConstraints(540, 10, -1, -1));

        lblId.setText("ID del usuario");
        getContentPane().add(lblId, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 60, -1, -1));
        getContentPane().add(txtID, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 90, 180, -1));

        lblNombre.setText("Nombre");
        getContentPane().add(lblNombre, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 130, 70, -1));
        getContentPane().add(txtNombre, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 160, 180, -1));

        jLabel1.setText("Apellido");
        getContentPane().add(jLabel1, new org.netbeans.lib.awtextra.AbsoluteConstraints(240, 130, -1, -1));
        getContentPane().add(txtApellido, new org.netbeans.lib.awtextra.AbsoluteConstraints(240, 160, 180, -1));

        jLabel2.setText("Usuario");
        getContentPane().add(jLabel2, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 210, 60, -1));
        getContentPane().add(txtUsuario, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 240, 170, -1));

        lblContrasenia.setText("Contraseña");
        getContentPane().add(lblContrasenia, new org.netbeans.lib.awtextra.AbsoluteConstraints(240, 210, -1, -1));
        getContentPane().add(txtContrasenia, new org.netbeans.lib.awtextra.AbsoluteConstraints(240, 240, 180, -1));

        btnGuardar.setText("Guardar");
        btnGuardar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnGuardarActionPerformed(evt);
            }
        });
        getContentPane().add(btnGuardar, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 290, -1, -1));

        btnEliminar.setText("Eliminar");
        btnEliminar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnEliminarActionPerformed(evt);
            }
        });
        getContentPane().add(btnEliminar, new org.netbeans.lib.awtextra.AbsoluteConstraints(230, 290, -1, -1));

        btnActualizar.setText("Actualizar");
        btnActualizar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnActualizarActionPerformed(evt);
            }
        });
        getContentPane().add(btnActualizar, new org.netbeans.lib.awtextra.AbsoluteConstraints(120, 290, -1, -1));

        btnLimpiar.setText("Limpiar");
        btnLimpiar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnLimpiarActionPerformed(evt);
            }
        });
        getContentPane().add(btnLimpiar, new org.netbeans.lib.awtextra.AbsoluteConstraints(330, 290, -1, -1));

        lblMeseros.setText("Meseros Registrados");
        getContentPane().add(lblMeseros, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 340, 150, -1));

        tblMeseros.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null}
            },
            new String [] {
                "Title 1", "Title 2", "Title 3", "Title 4"
            }
        ));
        tblMeseros.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                tblMeserosMouseClicked(evt);
            }
        });
        scrllMeseros.setViewportView(tblMeseros);

        getContentPane().add(scrllMeseros, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 370, 600, 120));

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void btnGuardarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnGuardarActionPerformed
        if (editando) {
            javax.swing.JOptionPane.showMessageDialog(this, "Está editando un mesero existente. Use Actualizar o Limpiar.");
            return;
        }
        if (txtNombre.getText().trim().isEmpty() || txtApellido.getText().trim().isEmpty()
                || txtUsuario.getText().trim().isEmpty() || txtContrasenia.getText().trim().isEmpty()) {
            javax.swing.JOptionPane.showMessageDialog(this, "Complete todos los campos.");
            return;
        }
        if (usuarioDAO.existeUsuario(txtUsuario.getText().trim())) {
            javax.swing.JOptionPane.showMessageDialog(this, "Ese nombre de usuario ya existe.");
            return;
        }

        Usuario u = new Usuario();
        u.setNombre(txtNombre.getText().trim());
        u.setApellido(txtApellido.getText().trim());
        u.setUsuario(txtUsuario.getText().trim());
        u.setIdRol(usuarioDAO.obtenerIdRolMesero());

        boolean res = usuarioDAO.insertar(u, txtContrasenia.getText().trim());
        if (res) {
            javax.swing.JOptionPane.showMessageDialog(this, "Mesero creado correctamente.");
            listarMeseros();
            limpiarCampos();
        } else {
            javax.swing.JOptionPane.showMessageDialog(this, "Error al crear el mesero.");
        }
    }//GEN-LAST:event_btnGuardarActionPerformed

    private void btnActualizarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnActualizarActionPerformed

        if (!editando) {
            javax.swing.JOptionPane.showMessageDialog(this, "Seleccione un mesero de la tabla.");
            return;
        }
        if (txtNombre.getText().trim().isEmpty() || txtApellido.getText().trim().isEmpty()
                || txtUsuario.getText().trim().isEmpty()) {
            javax.swing.JOptionPane.showMessageDialog(this, "Complete todos los campos.");
            return;
        }

        Usuario u = new Usuario();
        u.setIdUsuario(Integer.parseInt(txtID.getText()));
        u.setNombre(txtNombre.getText().trim());
        u.setApellido(txtApellido.getText().trim());
        u.setUsuario(txtUsuario.getText().trim());
        u.setIdRol(usuarioDAO.obtenerIdRolMesero());

        boolean res;
        if (txtContrasenia.getText().trim().isEmpty()) {
            res = usuarioDAO.actualizar(u);
        } else {
            res = usuarioDAO.actualizarConContrasenia(u, txtContrasenia.getText().trim());
        }

        if (res) {
            javax.swing.JOptionPane.showMessageDialog(this, "Mesero actualizado correctamente.");
            listarMeseros();
            limpiarCampos();
        } else {
            javax.swing.JOptionPane.showMessageDialog(this, "Error al actualizar el mesero.");
        }
    }//GEN-LAST:event_btnActualizarActionPerformed

    private void btnEliminarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnEliminarActionPerformed
        if (txtID.getText().trim().isEmpty()) {
            javax.swing.JOptionPane.showMessageDialog(this, "Seleccione un mesero de la tabla.");
            return;
        }

        int confirm = javax.swing.JOptionPane.showConfirmDialog(this,
                "¿Está seguro de eliminar este mesero?", "Confirmar", javax.swing.JOptionPane.YES_NO_OPTION);
        if (confirm != javax.swing.JOptionPane.YES_OPTION) {
            return;
        }

        boolean res = usuarioDAO.eliminar(Integer.parseInt(txtID.getText()));
        if (res) {
            javax.swing.JOptionPane.showMessageDialog(this, "Mesero eliminado correctamente.");
            listarMeseros();
            limpiarCampos();
        } else {
            javax.swing.JOptionPane.showMessageDialog(this,
                    "No se pudo eliminar. Puede que tenga órdenes registradas.");
        }
    }//GEN-LAST:event_btnEliminarActionPerformed

    private void btnLimpiarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnLimpiarActionPerformed
        limpiarCampos();
    }//GEN-LAST:event_btnLimpiarActionPerformed

    private void BtnCerrarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_BtnCerrarActionPerformed
        this.dispose();
    }//GEN-LAST:event_BtnCerrarActionPerformed

    private void tblMeserosMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_tblMeserosMouseClicked
        int fila = tblMeseros.getSelectedRow();
        if (fila == -1) {
            return;
        }
        txtID.setText(modeloMeseros.getValueAt(fila, 0).toString());
        txtNombre.setText(modeloMeseros.getValueAt(fila, 1).toString());
        txtApellido.setText(modeloMeseros.getValueAt(fila, 2).toString());
        txtUsuario.setText(modeloMeseros.getValueAt(fila, 3).toString());
        txtContrasenia.setText("");
        editando = true;

    }//GEN-LAST:event_tblMeserosMouseClicked

    public static void main(String args[]) {
        /* Set the Nimbus look and feel */
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

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(() -> new frmUsuarios().setVisible(true));
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton BtnCerrar;
    private javax.swing.JButton btnActualizar;
    private javax.swing.JButton btnEliminar;
    private javax.swing.JButton btnGuardar;
    private javax.swing.JButton btnLimpiar;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel lblContrasenia;
    private javax.swing.JLabel lblEncabezado;
    private javax.swing.JLabel lblId;
    private javax.swing.JLabel lblMeseros;
    private javax.swing.JLabel lblNombre;
    private javax.swing.JScrollPane scrllMeseros;
    private javax.swing.JTable tblMeseros;
    private javax.swing.JTextField txtApellido;
    private javax.swing.JTextField txtContrasenia;
    private javax.swing.JTextField txtID;
    private javax.swing.JTextField txtNombre;
    private javax.swing.JTextField txtUsuario;
    // End of variables declaration//GEN-END:variables
}
