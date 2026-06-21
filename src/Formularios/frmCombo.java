package Formularios;

import clasesDAO.ComboDAO;
import clasesDAO.ProductoDAO;
import clases.Producto;
import clases.Detalle;
import java.awt.Color;
import java.awt.Font;
import java.awt.Image;
import java.util.ArrayList;
import java.util.List;
import javax.swing.BorderFactory;
import javax.swing.ImageIcon;
import javax.swing.JOptionPane;
import javax.swing.table.DefaultTableModel;

public class frmCombo extends javax.swing.JFrame {

    ComboDAO comboDAO = new ComboDAO();
    ProductoDAO productoDAO = new ProductoDAO();
    DefaultTableModel modeloCombos = new DefaultTableModel();
    DefaultTableModel modeloDetalle = new DefaultTableModel();
    List<Producto> listaProductos = new ArrayList<>();
    private static final java.util.logging.Logger logger = java.util.logging.Logger.getLogger(frmCombo.class.getName());

    public frmCombo() {
        this.setUndecorated(true); // Quita la barra de título completa
        initComponents();
        setIconImage(new ImageIcon(getClass().getResource("/Imagenes/logo.png")).getImage());
        setLocationRelativeTo(null); // Centra en pantalla
        // ── ID automático ────────────────────────────────────────────
        txtIdCombo.setEditable(false);
        txtPrecioTotal.setEditable(false);
        txtPrecioTotal.setText("0.00");
        int ultimo = comboDAO.obtenerUltimoIdCombo();
        txtIdCombo.setText(String.format("%03d", ultimo + 1));

// ── Logo ─────────────────────────────────────────────────────
        ImageIcon icon = new ImageIcon(getClass().getResource("/Imagenes/Mi_taqueria.png"));
        Image img = icon.getImage().getScaledInstance(150, 150, Image.SCALE_SMOOTH);
        lblLogo.setIcon(new ImageIcon(img));
        setIconImage(new ImageIcon(getClass().getResource("/Imagenes/logo.png")).getImage());

// ── Fondo general ────────────────────────────────────────────
        Color fondoOscuro = new Color(20, 28, 48);
        Color fondoCampo = new Color(45, 55, 80);
        Color azul = new Color(100, 149, 237);
        getContentPane().setBackground(fondoOscuro);

// ── Labels ───────────────────────────────────────────────────
        lblCombo.setForeground(Color.WHITE);
        lblCombo.setFont(new Font("Segoe UI", Font.BOLD, 16));

        for (javax.swing.JLabel lbl : new javax.swing.JLabel[]{
            lblIdCombo, lblNombreCombo, lblPrecioTotal,
            lblBuscar, lblPorProducto, lblProducto, lblCantidad,
            lblDetalleCombo, lblListaCombo}) {
            lbl.setForeground(azul);
            lbl.setFont(new Font("Segoe UI", Font.BOLD, 13));
        }

// ── TextFields ───────────────────────────────────────────────
        for (javax.swing.JTextField tf : new javax.swing.JTextField[]{
            txtIdCombo, txtNombreCombo, txtPrecioTotal,
            txtBuscar, txtBuscarPorProducto, txtCantidad}) {
            tf.setBackground(fondoCampo);
            tf.setForeground(Color.WHITE);
            tf.setCaretColor(Color.WHITE);
            tf.setBorder(BorderFactory.createCompoundBorder(
                    BorderFactory.createLineBorder(azul, 2),
                    BorderFactory.createEmptyBorder(4, 8, 4, 8)));
            tf.setFont(new Font("Segoe UI", Font.PLAIN, 13));
        }
        // ── Validación txtBuscar: solo 3 dígitos numéricos ────────────
        ((javax.swing.text.AbstractDocument) txtBuscar.getDocument())
                .setDocumentFilter(new javax.swing.text.DocumentFilter() {
                    @Override
                    public void insertString(javax.swing.text.DocumentFilter.FilterBypass fb,
                            int offset, String text, javax.swing.text.AttributeSet attr)
                            throws javax.swing.text.BadLocationException {
                        String current = fb.getDocument().getText(0, fb.getDocument().getLength());
                        String result = current.substring(0, offset) + text + current.substring(offset);
                        if (result.matches("\\d{0,3}")) {
                            super.insertString(fb, offset, text, attr);
                        }
                    }

                    @Override
                    public void replace(javax.swing.text.DocumentFilter.FilterBypass fb,
                            int offset, int length, String text, javax.swing.text.AttributeSet attr)
                            throws javax.swing.text.BadLocationException {
                        String current = fb.getDocument().getText(0, fb.getDocument().getLength());
                        String result = current.substring(0, offset) + text + current.substring(offset + length);
                        if (result.matches("\\d{0,3}")) {
                            super.replace(fb, offset, length, text, attr);
                        }
                    }
                });

// ── Validación txtBuscarPorProducto: solo letras y espacios ──
        ((javax.swing.text.AbstractDocument) txtBuscarPorProducto.getDocument())
                .setDocumentFilter(new javax.swing.text.DocumentFilter() {
                    @Override
                    public void insertString(javax.swing.text.DocumentFilter.FilterBypass fb,
                            int offset, String text, javax.swing.text.AttributeSet attr)
                            throws javax.swing.text.BadLocationException {
                        if (text.matches("[a-zA-ZáéíóúÁÉÍÓÚñÑ ]*")) {
                            super.insertString(fb, offset, text, attr);
                        }
                    }

                    @Override
                    public void replace(javax.swing.text.DocumentFilter.FilterBypass fb,
                            int offset, int length, String text, javax.swing.text.AttributeSet attr)
                            throws javax.swing.text.BadLocationException {
                        if (text.matches("[a-zA-ZáéíóúÁÉÍÓÚñÑ ]*")) {
                            super.replace(fb, offset, length, text, attr);
                        }
                    }
                });

// ── ComboBox ─────────────────────────────────────────────────
        cmbProducto.setBackground(fondoCampo);
        cmbProducto.setForeground(Color.WHITE);
        cmbProducto.setFont(new Font("Segoe UI", Font.PLAIN, 13));

// ── Botón Cerrar ─────────────────────────────────────────────
        btnCerrar.setBackground(Color.RED);
        btnCerrar.setForeground(Color.WHITE);
        btnCerrar.setFocusPainted(false);
        btnCerrar.setBorderPainted(false);
        btnCerrar.setFont(new Font("Segoe UI", Font.BOLD, 13));

// ── Botón Guardar (azul) ──────────────────────────────────────
        btnGuardar.setBackground(new Color(63, 94, 251));
        btnGuardar.setForeground(Color.WHITE);
        btnGuardar.setFont(new Font("Segoe UI", Font.BOLD, 13));
        btnGuardar.setFocusPainted(false);
        btnGuardar.setOpaque(true);
        btnGuardar.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        btnGuardar.setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createLineBorder(new Color(40, 60, 180), 2),
                BorderFactory.createEmptyBorder(6, 16, 6, 16)));

// ── Botón Actualizar (verde) ──────────────────────────────────
        btnActualizar.setBackground(new Color(40, 167, 69));
        btnActualizar.setForeground(Color.WHITE);
        btnActualizar.setFont(new Font("Segoe UI", Font.BOLD, 13));
        btnActualizar.setFocusPainted(false);
        btnActualizar.setOpaque(true);
        btnActualizar.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        btnActualizar.setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createLineBorder(new Color(30, 130, 50), 2),
                BorderFactory.createEmptyBorder(6, 16, 6, 16)));

// ── Botón Eliminar (rojo) ─────────────────────────────────────
        btnEliminar.setBackground(new Color(220, 53, 69));
        btnEliminar.setForeground(Color.WHITE);
        btnEliminar.setFont(new Font("Segoe UI", Font.BOLD, 13));
        btnEliminar.setFocusPainted(false);
        btnEliminar.setOpaque(true);
        btnEliminar.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        btnEliminar.setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createLineBorder(new Color(170, 30, 45), 2),
                BorderFactory.createEmptyBorder(6, 16, 6, 16)));

// ── Botón Limpiar (amarillo) ──────────────────────────────────
        btnLimpiar.setBackground(new Color(255, 193, 7));
        btnLimpiar.setForeground(new Color(30, 30, 30));
        btnLimpiar.setFont(new Font("Segoe UI", Font.BOLD, 13));
        btnLimpiar.setFocusPainted(false);
        btnLimpiar.setOpaque(true);
        btnLimpiar.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        btnLimpiar.setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createLineBorder(new Color(200, 150, 0), 2),
                BorderFactory.createEmptyBorder(6, 16, 6, 16)));

// ── Botón Agregar (azul claro) ────────────────────────────────
        btnAgregar.setBackground(new Color(100, 149, 237));
        btnAgregar.setForeground(Color.WHITE);
        btnAgregar.setFont(new Font("Segoe UI", Font.BOLD, 13));
        btnAgregar.setFocusPainted(false);
        btnAgregar.setOpaque(true);
        btnAgregar.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        btnAgregar.setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createLineBorder(new Color(70, 110, 200), 2),
                BorderFactory.createEmptyBorder(6, 16, 6, 16)));

// ── Botón Quitar (rojo) ───────────────────────────────────────
        btnQuitarFila.setBackground(new Color(220, 53, 69));
        btnQuitarFila.setForeground(Color.WHITE);
        btnQuitarFila.setFont(new Font("Segoe UI", Font.BOLD, 13));
        btnQuitarFila.setFocusPainted(false);
        btnQuitarFila.setOpaque(true);
        btnQuitarFila.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        btnQuitarFila.setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createLineBorder(new Color(170, 30, 45), 2),
                BorderFactory.createEmptyBorder(6, 16, 6, 16)));

// ── Botón Buscar (azul claro) ─────────────────────────────────
        btnBuscar.setBackground(new Color(100, 149, 237));
        btnBuscar.setForeground(Color.WHITE);
        btnBuscar.setFont(new Font("Segoe UI", Font.BOLD, 13));
        btnBuscar.setFocusPainted(false);
        btnBuscar.setOpaque(true);
        btnBuscar.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        btnBuscar.setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createLineBorder(new Color(70, 110, 200), 2),
                BorderFactory.createEmptyBorder(6, 16, 6, 16)));

// ── Tablas ────────────────────────────────────────────────────
        for (javax.swing.JTable tabla : new javax.swing.JTable[]{tblDetalle, tblCombos}) {
            tabla.setBackground(new Color(25, 35, 55));
            tabla.setForeground(Color.WHITE);
            tabla.setFont(new Font("Segoe UI", Font.PLAIN, 13));
            tabla.setRowHeight(28);
            tabla.setGridColor(new Color(50, 70, 110));
            tabla.setShowVerticalLines(false);
            tabla.getTableHeader().setBackground(new Color(63, 94, 251));
            tabla.getTableHeader().setForeground(Color.WHITE);
            tabla.getTableHeader().setFont(new Font("Segoe UI", Font.BOLD, 13));
            tabla.getTableHeader().setPreferredSize(new java.awt.Dimension(0, 35));
            tabla.setDefaultRenderer(Object.class, new javax.swing.table.DefaultTableCellRenderer() {
                @Override
                public java.awt.Component getTableCellRendererComponent(javax.swing.JTable t,
                        Object v, boolean sel, boolean foc, int row, int col) {
                    super.getTableCellRendererComponent(t, v, sel, foc, row, col);
                    if (sel) {
                        setBackground(new Color(63, 94, 251));
                        setForeground(Color.WHITE);
                    } else if (row % 2 == 0) {
                        setBackground(new Color(25, 35, 55));
                        setForeground(Color.WHITE);
                    } else {
                        setBackground(new Color(35, 48, 75));
                        setForeground(Color.WHITE);
                    }
                    setBorder(BorderFactory.createEmptyBorder(0, 8, 0, 8));
                    return this;
                }
            });
        }

// ── ScrollPanes ───────────────────────────────────────────────
        for (javax.swing.JScrollPane sp : new javax.swing.JScrollPane[]{jScrollPane1, jScrollPane}) {
            sp.getViewport().setBackground(new Color(25, 35, 55));
            sp.setBorder(BorderFactory.createLineBorder(azul, 2));
            sp.setVerticalScrollBarPolicy(javax.swing.JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED);
            sp.setHorizontalScrollBarPolicy(javax.swing.JScrollPane.HORIZONTAL_SCROLLBAR_AS_NEEDED);
        }
        tblDetalle.setFillsViewportHeight(true);
// ── Cargar datos ──────────────────────────────────────────────
        cargarProductos();
        listarCombos();
        iniciarTablaDetalle();

    }

    private void cargarProductos() {
        listaProductos = productoDAO.listar();
        cmbProducto.removeAllItems();
        for (Producto p : listaProductos) {
            cmbProducto.addItem(p.getIdProducto() + " - " + p.getNombre());
        }
    }

    public void configurarSegunRol(String rol) {
        // Al poner "SUPERVISOR" primero, si 'rol' llega nulo por error, el programa no se caerá
        if ("SUPERVISOR".equals(rol)) {
            btnGuardar.setEnabled(false);
            btnActualizar.setEnabled(false);
            btnEliminar.setEnabled(false);
            btnAgregar.setEnabled(false);
            btnQuitarFila.setEnabled(false);
            cmbProducto.setEnabled(false);
            txtPrecioTotal.setEnabled(false);
            txtCantidad.setEnabled(false);
            txtNombreCombo.setEnabled(false);
        }
    }

    private void listarCombos() {
        String[] titulos = {"ID Combo", "Nombre", "Precio Total"};
        modeloCombos = new DefaultTableModel(null, titulos) {
            @Override
            public boolean isCellEditable(int r, int c) {
                return false;
            }
        };
        for (clases.Combo c : comboDAO.listar()) {
            modeloCombos.addRow(new Object[]{
                c.getIdCombo(), c.getNombre(),
                String.format("%.2f", c.getPrecioCombo())
            });
        }
        tblCombos.setModel(modeloCombos);
    }

    private void iniciarTablaDetalle() {
        String[] titulos = {"ID Producto", "Nombre", "Cantidad", "Precio Unit.", "Subtotal"};
        modeloDetalle = new DefaultTableModel(null, titulos) {
            @Override
            public boolean isCellEditable(int r, int c) {
                return false;
            }
        };
        tblDetalle.setModel(modeloDetalle);
        tblDetalle.setAutoResizeMode(javax.swing.JTable.AUTO_RESIZE_OFF);
        tblDetalle.getColumnModel().getColumn(0).setPreferredWidth(80);
        tblDetalle.getColumnModel().getColumn(1).setPreferredWidth(180);
        tblDetalle.getColumnModel().getColumn(2).setPreferredWidth(65);
        tblDetalle.getColumnModel().getColumn(3).setPreferredWidth(85);
        tblDetalle.getColumnModel().getColumn(4).setPreferredWidth(75);
    }

// Variables declaration - do not modify
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jPanel1 = new javax.swing.JPanel();
        lblCombo = new javax.swing.JLabel();
        lblIdCombo = new javax.swing.JLabel();
        lblNombreCombo = new javax.swing.JLabel();
        lblPrecioTotal = new javax.swing.JLabel();
        lblBuscar = new javax.swing.JLabel();
        lblProducto = new javax.swing.JLabel();
        lblCantidad = new javax.swing.JLabel();
        lblDetalleCombo = new javax.swing.JLabel();
        lblListaCombo = new javax.swing.JLabel();
        lblLogo = new javax.swing.JLabel();
        txtIdCombo = new javax.swing.JTextField();
        txtNombreCombo = new javax.swing.JTextField();
        txtPrecioTotal = new javax.swing.JTextField();
        txtBuscar = new javax.swing.JTextField();
        txtCantidad = new javax.swing.JTextField();
        cmbProducto = new javax.swing.JComboBox<>();
        btnCerrar = new javax.swing.JButton();
        btnGuardar = new javax.swing.JButton();
        btnQuitarFila = new javax.swing.JButton();
        btnAgregar = new javax.swing.JButton();
        btnLimpiar = new javax.swing.JButton();
        btnBuscar = new javax.swing.JButton();
        btnActualizar = new javax.swing.JButton();
        btnEliminar = new javax.swing.JButton();
        jScrollPane1 = new javax.swing.JScrollPane();
        tblDetalle = new javax.swing.JTable();
        jScrollPane = new javax.swing.JScrollPane();
        tblCombos = new javax.swing.JTable();
        lblPorProducto = new javax.swing.JLabel();
        txtBuscarPorProducto = new javax.swing.JTextField();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);

        jPanel1.setBackground(new java.awt.Color(20, 28, 48));

        lblCombo.setBackground(new java.awt.Color(255, 255, 255));
        lblCombo.setText("Combos");

        lblIdCombo.setText("ID combo");

        lblNombreCombo.setText("Nombre");

        lblPrecioTotal.setText("Precio Total");

        lblBuscar.setText("Buscar");

        lblProducto.setText("Producto");

        lblCantidad.setText("Cantidad");

        lblDetalleCombo.setText("Detalle Combo");

        lblListaCombo.setText("Lista de Combos");

        cmbProducto.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Item 1", "Item 2", "Item 3", "Item 4" }));

        btnCerrar.setText("Cerrar");
        btnCerrar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnCerrarActionPerformed(evt);
            }
        });

        btnGuardar.setText("Guardar");
        btnGuardar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnGuardarActionPerformed(evt);
            }
        });

        btnQuitarFila.setText("Quitar");
        btnQuitarFila.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnQuitarFilaActionPerformed(evt);
            }
        });

        btnAgregar.setText("Agregar");
        btnAgregar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnAgregarActionPerformed(evt);
            }
        });

        btnLimpiar.setText("Limpiar");
        btnLimpiar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnLimpiarActionPerformed(evt);
            }
        });

        btnBuscar.setText("Buscar");
        btnBuscar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnBuscarActionPerformed(evt);
            }
        });

        btnActualizar.setText("Actualizar");
        btnActualizar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnActualizarActionPerformed(evt);
            }
        });

        btnEliminar.setText("Eliminar");
        btnEliminar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnEliminarActionPerformed(evt);
            }
        });

        tblDetalle.setModel(new javax.swing.table.DefaultTableModel(
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
        tblDetalle.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                tblDetalleMouseClicked(evt);
            }
        });
        jScrollPane1.setViewportView(tblDetalle);

        tblCombos.setModel(new javax.swing.table.DefaultTableModel(
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
        tblCombos.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                tblCombosMouseClicked(evt);
            }
        });
        jScrollPane.setViewportView(tblCombos);

        lblPorProducto.setText("Por Producto");

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addGap(14, 14, 14)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 502, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(111, 111, 111)
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                            .addComponent(btnCerrar)
                            .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                .addComponent(jScrollPane, javax.swing.GroupLayout.PREFERRED_SIZE, 380, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGroup(jPanel1Layout.createSequentialGroup()
                                    .addGap(21, 21, 21)
                                    .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                                        .addComponent(btnLimpiar)
                                        .addGroup(jPanel1Layout.createSequentialGroup()
                                            .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                                .addGroup(jPanel1Layout.createSequentialGroup()
                                                    .addComponent(lblBuscar, javax.swing.GroupLayout.PREFERRED_SIZE, 80, javax.swing.GroupLayout.PREFERRED_SIZE)
                                                    .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                                    .addComponent(txtBuscar, javax.swing.GroupLayout.PREFERRED_SIZE, 84, javax.swing.GroupLayout.PREFERRED_SIZE))
                                                .addComponent(lblListaCombo))
                                            .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                            .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                                .addComponent(btnEliminar)
                                                .addComponent(lblPorProducto, javax.swing.GroupLayout.PREFERRED_SIZE, 97, javax.swing.GroupLayout.PREFERRED_SIZE))
                                            .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                            .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                                .addComponent(btnBuscar)
                                                .addComponent(txtBuscarPorProducto, javax.swing.GroupLayout.PREFERRED_SIZE, 84, javax.swing.GroupLayout.PREFERRED_SIZE))))))))
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                            .addComponent(lblCantidad, javax.swing.GroupLayout.PREFERRED_SIZE, 56, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addGroup(jPanel1Layout.createSequentialGroup()
                                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                                    .addComponent(lblNombreCombo, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                    .addComponent(lblIdCombo, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                                .addGap(54, 54, 54)
                                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addComponent(txtIdCombo, javax.swing.GroupLayout.PREFERRED_SIZE, 103, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addComponent(txtNombreCombo, javax.swing.GroupLayout.PREFERRED_SIZE, 71, javax.swing.GroupLayout.PREFERRED_SIZE)))
                            .addGroup(jPanel1Layout.createSequentialGroup()
                                .addGap(107, 107, 107)
                                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addGroup(jPanel1Layout.createSequentialGroup()
                                        .addComponent(txtPrecioTotal, javax.swing.GroupLayout.PREFERRED_SIZE, 103, javax.swing.GroupLayout.PREFERRED_SIZE)
                                        .addGap(0, 0, Short.MAX_VALUE))
                                    .addGroup(jPanel1Layout.createSequentialGroup()
                                        .addComponent(txtCantidad, javax.swing.GroupLayout.PREFERRED_SIZE, 71, javax.swing.GroupLayout.PREFERRED_SIZE)
                                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                        .addComponent(btnAgregar, javax.swing.GroupLayout.PREFERRED_SIZE, 101, javax.swing.GroupLayout.PREFERRED_SIZE))
                                    .addComponent(cmbProducto, 0, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)))
                            .addComponent(lblProducto)
                            .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                                .addComponent(lblCombo, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.PREFERRED_SIZE, 97, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addComponent(lblPrecioTotal, javax.swing.GroupLayout.PREFERRED_SIZE, 97, javax.swing.GroupLayout.PREFERRED_SIZE))
                            .addGroup(jPanel1Layout.createSequentialGroup()
                                .addComponent(lblDetalleCombo, javax.swing.GroupLayout.PREFERRED_SIZE, 118, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 84, Short.MAX_VALUE)
                                .addComponent(btnGuardar)
                                .addGap(18, 18, 18)
                                .addComponent(btnQuitarFila)))
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(jPanel1Layout.createSequentialGroup()
                                .addGap(18, 18, 18)
                                .addComponent(btnActualizar))
                            .addGroup(jPanel1Layout.createSequentialGroup()
                                .addGap(70, 70, 70)
                                .addComponent(lblLogo, javax.swing.GroupLayout.PREFERRED_SIZE, 196, javax.swing.GroupLayout.PREFERRED_SIZE)))))
                .addContainerGap())
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(lblCombo, javax.swing.GroupLayout.PREFERRED_SIZE, 31, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(btnCerrar))
                .addGap(18, 18, 18)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel1Layout.createSequentialGroup()
                        .addGap(0, 0, Short.MAX_VALUE)
                        .addComponent(btnLimpiar)
                        .addGap(18, 18, 18)
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(lblPorProducto)
                            .addComponent(txtBuscarPorProducto, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(txtBuscar, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(lblBuscar))
                        .addGap(9, 9, 9))
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(jPanel1Layout.createSequentialGroup()
                                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                                    .addComponent(lblIdCombo)
                                    .addComponent(txtIdCombo, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                                .addGap(9, 9, 9)
                                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                                    .addComponent(lblNombreCombo)
                                    .addComponent(txtNombreCombo, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                                .addGap(21, 21, 21)
                                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                                    .addComponent(lblPrecioTotal)
                                    .addComponent(txtPrecioTotal, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                                .addGap(18, 18, 18)
                                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addComponent(lblProducto)
                                    .addComponent(cmbProducto, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                                .addGap(18, 18, 18)
                                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addComponent(lblCantidad)
                                    .addComponent(txtCantidad, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addComponent(btnAgregar)))
                            .addComponent(lblLogo, javax.swing.GroupLayout.PREFERRED_SIZE, 121, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGap(16, 92, Short.MAX_VALUE)))
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                        .addComponent(lblDetalleCombo)
                        .addComponent(btnQuitarFila)
                        .addComponent(btnActualizar)
                        .addComponent(btnGuardar))
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                        .addComponent(btnBuscar)
                        .addComponent(btnEliminar)
                        .addComponent(lblListaCombo)))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(jScrollPane1, javax.swing.GroupLayout.DEFAULT_SIZE, 138, Short.MAX_VALUE)
                    .addComponent(jScrollPane, javax.swing.GroupLayout.PREFERRED_SIZE, 0, Short.MAX_VALUE))
                .addGap(61, 61, 61))
        );

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jPanel1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jPanel1, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void btnCerrarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnCerrarActionPerformed
        this.dispose();
    }//GEN-LAST:event_btnCerrarActionPerformed

    private void btnLimpiarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnLimpiarActionPerformed
        int nuevoId = comboDAO.obtenerUltimoIdCombo();
        txtIdCombo.setText(String.format("%03d", nuevoId + 1));
        txtNombreCombo.setText("");
        txtPrecioTotal.setText("0.00");
        txtBuscar.setText("");
        txtBuscarPorProducto.setText("");
        txtCantidad.setText("");
        iniciarTablaDetalle();
        tblCombos.clearSelection();
        listarCombos();
    }//GEN-LAST:event_btnLimpiarActionPerformed

    private void btnAgregarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnAgregarActionPerformed
        int idx = cmbProducto.getSelectedIndex();
        if (idx < 0) {
            return;
        }

        Producto p = listaProductos.get(idx);
        String cantidad = txtCantidad.getText().trim();

        if (cantidad.isEmpty()) {
            JOptionPane.showMessageDialog(this, "Ingrese la cantidad.");
            return;
        }

        int cant;
        try {
            cant = Integer.parseInt(cantidad);
            if (cant <= 0) {
                throw new NumberFormatException();
            }
        } catch (NumberFormatException e) {
            JOptionPane.showMessageDialog(this, "Cantidad debe ser un número mayor a 0.");
            return;
        }

// Verificar duplicado
        for (int i = 0; i < modeloDetalle.getRowCount(); i++) {
            if (modeloDetalle.getValueAt(i, 0).toString().equals(p.getIdProducto())) {
                int cantExistente = Integer.parseInt(modeloDetalle.getValueAt(i, 2).toString());
                int nuevaCant = cantExistente + cant;
                double precio = Double.parseDouble(p.getPrecio());
                modeloDetalle.setValueAt(nuevaCant, i, 2);
                modeloDetalle.setValueAt(String.format("%.2f", precio * nuevaCant), i, 4);
                calcularTotal();
                txtCantidad.setText("");
                return;
            }
        }

// Nuevo producto
        double precio = Double.parseDouble(p.getPrecio());
        double subtotal = precio * cant;
        modeloDetalle.addRow(new Object[]{
            p.getIdProducto(), p.getNombre(), cant,
            String.format("%.2f", precio),
            String.format("%.2f", subtotal)
        });
        calcularTotal();
        txtCantidad.setText("");
    }//GEN-LAST:event_btnAgregarActionPerformed

    private void btnQuitarFilaActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnQuitarFilaActionPerformed
        int fila = tblDetalle.getSelectedRow();
        if (fila == -1) {
            JOptionPane.showMessageDialog(this, "Seleccione un producto del detalle para quitar.");
            return;
        }
        modeloDetalle.removeRow(fila);
        calcularTotal();
    }//GEN-LAST:event_btnQuitarFilaActionPerformed

    private void btnGuardarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnGuardarActionPerformed
        if (txtNombreCombo.getText().trim().isEmpty()) {
            JOptionPane.showMessageDialog(this, "Ingrese el nombre del combo.");
            return;
        }
        if (modeloDetalle.getRowCount() == 0) {
            JOptionPane.showMessageDialog(this, "Agregue al menos un producto al detalle.");
            return;
        }

        clases.Combo combo = new clases.Combo();
        combo.setIdCombo(txtIdCombo.getText());
        combo.setNombre(txtNombreCombo.getText().trim());
        double precio = 0.0;
        try {
            precio = Double.parseDouble(txtPrecioTotal.getText());
        } catch (Exception e) {
            precio = 0.0;
        }
        combo.setPrecioCombo(precio);

        java.util.List<Detalle> detalles = new java.util.ArrayList<>();
        for (int i = 0; i < modeloDetalle.getRowCount(); i++) {
            Detalle d = new Detalle();
            d.setIdProducto(modeloDetalle.getValueAt(i, 0).toString());
            d.setCantidad(Integer.parseInt(modeloDetalle.getValueAt(i, 2).toString()));
            detalles.add(d);
        }
        combo.setDetalle(detalles);

        int res = comboDAO.insertarCombo(combo);
        if (res > 0) {
            JOptionPane.showMessageDialog(this, "Combo guardado correctamente.");
            listarCombos();
            int nuevoId = comboDAO.obtenerUltimoIdCombo();
            txtIdCombo.setText(String.format("%03d", nuevoId + 1));
            txtNombreCombo.setText("");
            txtPrecioTotal.setText("0.00");
            txtCantidad.setText("");
            iniciarTablaDetalle();
        } else {
            JOptionPane.showMessageDialog(this, "Error al guardar el combo.");
        }
    }//GEN-LAST:event_btnGuardarActionPerformed

    private void btnEliminarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnEliminarActionPerformed
        if (tblCombos.getSelectedRow() == -1) {
            JOptionPane.showMessageDialog(this, "Seleccione un combo de la tabla para eliminar.");
            return;
        }

        int confirm = JOptionPane.showConfirmDialog(this,
                "¿Está seguro de eliminar el combo?", "Confirmar", JOptionPane.YES_NO_OPTION);
        if (confirm != JOptionPane.YES_OPTION) {
            return;
        }

        int res = comboDAO.eliminarCombo(txtIdCombo.getText());
        if (res > 0) {
            JOptionPane.showMessageDialog(this, "Combo eliminado correctamente.");
            listarCombos();
            int nuevoId = comboDAO.obtenerUltimoIdCombo();
            txtIdCombo.setText(String.format("%03d", nuevoId + 1));
            txtNombreCombo.setText("");
            txtPrecioTotal.setText("0.00");
            txtCantidad.setText("");
            iniciarTablaDetalle();
        } else {
            JOptionPane.showMessageDialog(this, "Error al eliminar el combo.");
        }
    }//GEN-LAST:event_btnEliminarActionPerformed

    private void btnActualizarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnActualizarActionPerformed
        if (tblCombos.getSelectedRow() == -1) {
            JOptionPane.showMessageDialog(this, "Seleccione un combo de la tabla para actualizar.");
            return;
        }
        if (txtNombreCombo.getText().trim().isEmpty()) {
            JOptionPane.showMessageDialog(this, "Ingrese el nombre del combo.");
            return;
        }
        if (modeloDetalle.getRowCount() == 0) {
            JOptionPane.showMessageDialog(this, "El detalle no puede estar vacío.");
            return;
        }

        int confirm = JOptionPane.showConfirmDialog(this,
                "¿Desea actualizar el combo?", "Confirmar", JOptionPane.YES_NO_OPTION);
        if (confirm != JOptionPane.YES_OPTION) {
            return;
        }

        clases.Combo combo = new clases.Combo();
        combo.setIdCombo(txtIdCombo.getText());
        combo.setNombre(txtNombreCombo.getText().trim());
        double precio2 = 0.0;
        try {
            precio2 = Double.parseDouble(txtPrecioTotal.getText());
        } catch (Exception e) {
            precio2 = 0.0;
        }
        combo.setPrecioCombo(precio2);

        java.util.List<Detalle> detalles = new java.util.ArrayList<>();
        for (int i = 0; i < modeloDetalle.getRowCount(); i++) {
            Detalle d = new Detalle();
            d.setIdProducto(modeloDetalle.getValueAt(i, 0).toString());
            d.setCantidad(Integer.parseInt(modeloDetalle.getValueAt(i, 2).toString()));
            detalles.add(d);
        }
        combo.setDetalle(detalles);

        int res = comboDAO.actualizarCombo(combo);
        if (res > 0) {
            JOptionPane.showMessageDialog(this, "Combo actualizado correctamente.");
            listarCombos();
            int nuevoId = comboDAO.obtenerUltimoIdCombo();
            txtIdCombo.setText(String.format("%03d", nuevoId + 1));
            txtNombreCombo.setText("");
            txtPrecioTotal.setText("0.00");
            txtCantidad.setText("");
            iniciarTablaDetalle();
        } else {
            JOptionPane.showMessageDialog(this, "Error al actualizar el combo.");
        }
    }//GEN-LAST:event_btnActualizarActionPerformed

    private void btnBuscarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnBuscarActionPerformed
        String porNombre = txtBuscar.getText().trim();
        String porProducto = txtBuscarPorProducto.getText().trim();

        String[] titulos = {"ID Combo", "Nombre", "Precio Total"};
        modeloCombos = new DefaultTableModel(null, titulos) {
            @Override
            public boolean isCellEditable(int r, int c) {
                return false;
            }
        };

        if (!porProducto.isEmpty()) {
            // Buscar por producto que forma parte del combo
            for (clases.Combo c : comboDAO.buscarPorProducto(porProducto)) {
                modeloCombos.addRow(new Object[]{
                    c.getIdCombo(), c.getNombre(),
                    String.format("%.2f", c.getPrecioCombo())
                });
            }
        } else {
            // Buscar por nombre del combo
            for (clases.Combo c : comboDAO.buscar(porNombre)) {
                modeloCombos.addRow(new Object[]{
                    c.getIdCombo(), c.getNombre(),
                    String.format("%.2f", c.getPrecioCombo())
                });
            }
        }
        tblCombos.setModel(modeloCombos);
    }//GEN-LAST:event_btnBuscarActionPerformed

    private void tblDetalleMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_tblDetalleMouseClicked

    }//GEN-LAST:event_tblDetalleMouseClicked

    private void ajustarColumnasDetalle() {
        tblDetalle.setAutoResizeMode(javax.swing.JTable.AUTO_RESIZE_OFF);
        tblDetalle.getColumnModel().getColumn(0).setPreferredWidth(80);
        tblDetalle.getColumnModel().getColumn(1).setPreferredWidth(180);
        tblDetalle.getColumnModel().getColumn(2).setPreferredWidth(65);
        tblDetalle.getColumnModel().getColumn(3).setPreferredWidth(85);
        tblDetalle.getColumnModel().getColumn(4).setPreferredWidth(75);
    }
    private void tblCombosMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_tblCombosMouseClicked
        int fila = tblCombos.getSelectedRow();
        if (fila == -1) {
            return;
        }

        txtIdCombo.setText(modeloCombos.getValueAt(fila, 0).toString());
        txtNombreCombo.setText(modeloCombos.getValueAt(fila, 1).toString());
        txtPrecioTotal.setText(modeloCombos.getValueAt(fila, 2).toString());

// Cargar detalle
        String[] titulos = {"ID Producto", "Nombre", "Cantidad", "Precio Unit.", "Subtotal"};
        modeloDetalle = new DefaultTableModel(null, titulos) {
            @Override
            public boolean isCellEditable(int r, int c) {
                return false;
            }
        };
        for (Detalle d : comboDAO.cargarDetalle(txtIdCombo.getText())) {
            modeloDetalle.addRow(new Object[]{
                d.getIdProducto(), d.getNombreProducto(), d.getCantidad(),
                String.format("%.2f", d.getPrecioUnitario()),
                String.format("%.2f", d.getSubtotal())
            });
        }
        tblDetalle.setModel(modeloDetalle);
        ajustarColumnasDetalle();
    }//GEN-LAST:event_tblCombosMouseClicked
    private void calcularTotal() {
        double total = 0;
        for (int i = 0; i < modeloDetalle.getRowCount(); i++) {
            total += Double.parseDouble(modeloDetalle.getValueAt(i, 4).toString());
        }
        // Descuento de $1.00 por ser combo
        double descuento = 1.00;
        double totalConDescuento = total > descuento ? total - descuento : total;
        txtPrecioTotal.setText(String.format("%.2f", totalConDescuento));
    }

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
        java.awt.EventQueue.invokeLater(() -> new frmCombo().setVisible(true));
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton btnActualizar;
    private javax.swing.JButton btnAgregar;
    private javax.swing.JButton btnBuscar;
    private javax.swing.JButton btnCerrar;
    private javax.swing.JButton btnEliminar;
    private javax.swing.JButton btnGuardar;
    private javax.swing.JButton btnLimpiar;
    private javax.swing.JButton btnQuitarFila;
    private javax.swing.JComboBox<String> cmbProducto;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JScrollPane jScrollPane;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JLabel lblBuscar;
    private javax.swing.JLabel lblCantidad;
    private javax.swing.JLabel lblCombo;
    private javax.swing.JLabel lblDetalleCombo;
    private javax.swing.JLabel lblIdCombo;
    private javax.swing.JLabel lblListaCombo;
    private javax.swing.JLabel lblLogo;
    private javax.swing.JLabel lblNombreCombo;
    private javax.swing.JLabel lblPorProducto;
    private javax.swing.JLabel lblPrecioTotal;
    private javax.swing.JLabel lblProducto;
    private javax.swing.JTable tblCombos;
    private javax.swing.JTable tblDetalle;
    private javax.swing.JTextField txtBuscar;
    private javax.swing.JTextField txtBuscarPorProducto;
    private javax.swing.JTextField txtCantidad;
    private javax.swing.JTextField txtIdCombo;
    private javax.swing.JTextField txtNombreCombo;
    private javax.swing.JTextField txtPrecioTotal;
    // End of variables declaration//GEN-END:variables
}
