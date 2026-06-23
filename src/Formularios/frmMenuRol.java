package Formularios;

import clases.Usuario;
import java.awt.Color;
import java.awt.Font;
import java.awt.Image;
import javax.swing.BorderFactory;
import javax.swing.ImageIcon;

public class frmMenuRol extends javax.swing.JFrame {

    Usuario usuarioActivo = new Usuario();
    private static final java.util.logging.Logger logger = java.util.logging.Logger.getLogger(frmMenuRol.class.getName());

    public frmMenuRol() {
        this.setUndecorated(true);
        initComponents();
        this.setSize(520, 580);
        setLocationRelativeTo(null);
        setIconImage(new ImageIcon(getClass().getResource("/Imagenes/logo.png")).getImage());
        lblTitulo.setText("Taquería El Metapaneco");

// ── Logo ─────────────────────────────────────────────────────
        ImageIcon icon = new ImageIcon(getClass().getResource("/Imagenes/Mi_taqueria.png"));
        Image img = icon.getImage().getScaledInstance(120, 120, Image.SCALE_SMOOTH);
        lblLogo.setIcon(new ImageIcon(img));

// ── Fondo ─────────────────────────────────────────────────────
        Color fondoOscuro = new Color(20, 28, 48);
        Color azul = new Color(100, 149, 237);
        getContentPane().setBackground(fondoOscuro);

// ── Título ────────────────────────────────────────────────────
        lblTitulo.setForeground(Color.WHITE);
        lblTitulo.setFont(new Font("Segoe UI", Font.BOLD, 22));

// ── Labels ────────────────────────────────────────────────────
        lblBienvenido.setForeground(azul);
        lblBienvenido.setFont(new Font("Segoe UI", Font.BOLD, 14));
        lblRol.setForeground(azul);
        lblRol.setFont(new Font("Segoe UI", Font.BOLD, 14));

// ── Botón Cerrar ──────────────────────────────────────────────
        btnCerrar.setBackground(Color.RED);
        btnCerrar.setForeground(Color.WHITE);
        btnCerrar.setFocusPainted(false);
        btnCerrar.setBorderPainted(false);
        btnCerrar.setFont(new Font("Segoe UI", Font.BOLD, 13));

// ── Botón Productos ───────────────────────────────────────────
        btnProductos.setBackground(new Color(63, 94, 251));
        btnProductos.setForeground(Color.WHITE);
        btnProductos.setFont(new Font("Segoe UI", Font.BOLD, 16));
        btnProductos.setFocusPainted(false);
        btnProductos.setOpaque(true);
        btnProductos.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        btnProductos.setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createLineBorder(new Color(40, 60, 180), 2),
                BorderFactory.createEmptyBorder(20, 30, 20, 30)));

// ── Botón Combos ──────────────────────────────────────────────
        btnCombos.setBackground(new Color(40, 167, 69));
        btnCombos.setForeground(Color.WHITE);
        btnCombos.setFont(new Font("Segoe UI", Font.BOLD, 16));
        btnCombos.setFocusPainted(false);
        btnCombos.setOpaque(true);
        btnCombos.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        btnCombos.setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createLineBorder(new Color(30, 130, 50), 2),
                BorderFactory.createEmptyBorder(20, 30, 20, 30)));

// ── Botón Órdenes ─────────────────────────────────────────────
        btnOrdenes.setBackground(new Color(100, 149, 237));
        btnOrdenes.setForeground(Color.WHITE);
        btnOrdenes.setFont(new Font("Segoe UI", Font.BOLD, 16));
        btnOrdenes.setFocusPainted(false);
        btnOrdenes.setOpaque(true);
        btnOrdenes.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        btnOrdenes.setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createLineBorder(new Color(70, 110, 200), 2),
                BorderFactory.createEmptyBorder(20, 30, 20, 30)));
        // ── Botón Usuarios ────────────────────────────────────────────
        btnUsuarios.setBackground(new Color(155, 89, 182));
        btnUsuarios.setForeground(Color.WHITE);
        btnUsuarios.setFont(new Font("Segoe UI", Font.BOLD, 16));
        btnUsuarios.setFocusPainted(false);
        btnUsuarios.setOpaque(true);
        btnUsuarios.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        btnUsuarios.setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createLineBorder(new Color(110, 60, 140), 2),
                BorderFactory.createEmptyBorder(20, 30, 20, 30)));
    }

    public void configurarSegunRol(Usuario u) {
        usuarioActivo = u;
        lblBienvenido.setText("Bienvenido: " + u.getNombre() + " " + u.getApellido());
        lblRol.setText("Rol: " + u.getRol());

        // Desactivar todos primero
        btnProductos.setEnabled(false);
        btnCombos.setEnabled(false);
        btnOrdenes.setEnabled(false);
        btnUsuarios.setEnabled(false);
        btnUsuarios.setVisible(false);

        // Activar según rol
        switch (u.getRol()) {
            case "ADMINISTRADOR":
                btnProductos.setEnabled(true);
                btnCombos.setEnabled(true);
                btnOrdenes.setEnabled(true);
                btnUsuarios.setEnabled(true);
                btnUsuarios.setVisible(true);
                break;
            case "SUPERVISOR":
                btnProductos.setEnabled(true);
                btnCombos.setEnabled(true);
                btnOrdenes.setEnabled(true);
                break;
            case "MESERO":
            case "COCINERO":
                btnOrdenes.setEnabled(true);
                break;
        }

        // Estilo botones desactivados
        for (javax.swing.JButton btn : new javax.swing.JButton[]{
            btnProductos, btnCombos, btnOrdenes}) {
            if (!btn.isEnabled()) {
                btn.setBackground(new Color(45, 55, 80));
                btn.setForeground(new Color(80, 90, 110));
                btn.setBorder(BorderFactory.createCompoundBorder(
                        BorderFactory.createLineBorder(new Color(60, 70, 90), 2),
                        BorderFactory.createEmptyBorder(20, 30, 20, 30)));
                btn.setCursor(new java.awt.Cursor(java.awt.Cursor.DEFAULT_CURSOR));
            }
        }
    }

    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        lblLogo = new javax.swing.JLabel();
        lblBienvenido = new javax.swing.JLabel();
        lblTitulo = new javax.swing.JLabel();
        lblRol = new javax.swing.JLabel();
        btnProductos = new javax.swing.JButton();
        btnCombos = new javax.swing.JButton();
        btnOrdenes = new javax.swing.JButton();
        btnCerrar = new javax.swing.JButton();
        btnUsuarios = new javax.swing.JButton();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
        getContentPane().setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());
        getContentPane().add(lblLogo, new org.netbeans.lib.awtextra.AbsoluteConstraints(190, 20, 200, 120));

        lblBienvenido.setText("Bienvenido:");
        getContentPane().add(lblBienvenido, new org.netbeans.lib.awtextra.AbsoluteConstraints(100, 190, 200, 25));

        lblTitulo.setText("Taqueria el Metapaneco");
        getContentPane().add(lblTitulo, new org.netbeans.lib.awtextra.AbsoluteConstraints(100, 150, 300, 30));

        lblRol.setText("Rol:");
        getContentPane().add(lblRol, new org.netbeans.lib.awtextra.AbsoluteConstraints(100, 220, 200, 25));

        btnProductos.setText("Productos");
        btnProductos.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnProductosActionPerformed(evt);
            }
        });
        getContentPane().add(btnProductos, new org.netbeans.lib.awtextra.AbsoluteConstraints(30, 390, 140, 80));

        btnCombos.setText("Combos");
        btnCombos.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnCombosActionPerformed(evt);
            }
        });
        getContentPane().add(btnCombos, new org.netbeans.lib.awtextra.AbsoluteConstraints(190, 390, 130, 80));

        btnOrdenes.setText("Ordenes");
        btnOrdenes.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnOrdenesActionPerformed(evt);
            }
        });
        getContentPane().add(btnOrdenes, new org.netbeans.lib.awtextra.AbsoluteConstraints(340, 390, 130, 80));

        btnCerrar.setText("Cerrar");
        btnCerrar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnCerrarActionPerformed(evt);
            }
        });
        getContentPane().add(btnCerrar, new org.netbeans.lib.awtextra.AbsoluteConstraints(410, 10, 80, 28));

        btnUsuarios.setText("Agregar Usuarios");
        btnUsuarios.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnUsuariosActionPerformed(evt);
            }
        });
        getContentPane().add(btnUsuarios, new org.netbeans.lib.awtextra.AbsoluteConstraints(30, 493, 290, 80));

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void btnCerrarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnCerrarActionPerformed
        new frmLogin().setVisible(true);
        this.dispose();


    }//GEN-LAST:event_btnCerrarActionPerformed

    private void btnProductosActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnProductosActionPerformed
        Formularios.frmProducto producto = new Formularios.frmProducto();
        producto.configurarSegunRol(usuarioActivo.getRol());
        producto.setVisible(true);
    }//GEN-LAST:event_btnProductosActionPerformed

    private void btnCombosActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnCombosActionPerformed
        Formularios.frmCombo combo = new Formularios.frmCombo();
        combo.configurarSegunRol(usuarioActivo.getRol());
        combo.setVisible(true);

    }//GEN-LAST:event_btnCombosActionPerformed

    private void btnOrdenesActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnOrdenesActionPerformed
        frmOrden orden = new frmOrden();
        orden.setUsuarioActivo(usuarioActivo);
        orden.configurarSegunRol(usuarioActivo.getRol());
        orden.setVisible(true);
    }//GEN-LAST:event_btnOrdenesActionPerformed

    private void btnUsuariosActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnUsuariosActionPerformed
        Formularios.frmUsuarios usuario = new Formularios.frmUsuarios();
    usuario.setVisible(true);
    }//GEN-LAST:event_btnUsuariosActionPerformed

    public static void main(String args[]) {

        java.awt.EventQueue.invokeLater(() -> new frmMenuRol().setVisible(true));
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton btnCerrar;
    private javax.swing.JButton btnCombos;
    private javax.swing.JButton btnOrdenes;
    private javax.swing.JButton btnProductos;
    private javax.swing.JButton btnUsuarios;
    private javax.swing.JLabel lblBienvenido;
    private javax.swing.JLabel lblLogo;
    private javax.swing.JLabel lblRol;
    private javax.swing.JLabel lblTitulo;
    // End of variables declaration//GEN-END:variables
}
