package Formularios;

import clasesDAO.OrdenDAO;
import clasesDAO.ProductoDAO;
import clasesDAO.ComboDAO;
import clases.Orden;
import clases.DetalleOrden;
import clases.Producto;
import clases.Usuario;
import clasesDAO.CategoriaDAO;
import java.awt.Color;
import java.awt.Font;
import java.awt.Image;
import java.util.ArrayList;
import java.util.List;
import javax.swing.BorderFactory;
import javax.swing.ImageIcon;
import javax.swing.table.DefaultTableModel;

public class frmOrden extends javax.swing.JFrame {

    private clases.TicketPDF ticketPDF = new clases.TicketPDF();
    OrdenDAO ordenDAO = new OrdenDAO();
    ProductoDAO productoDAO = new ProductoDAO();
    ComboDAO comboDAO = new ComboDAO();
    DefaultTableModel modeloOrdenes = new DefaultTableModel();
    DefaultTableModel modeloDetalle = new DefaultTableModel();
    List<Producto> listaProductos = new ArrayList<>();
    List<clases.Combo> listaCombos = new ArrayList<>();
    Usuario usuarioActivo = new Usuario();
    List<clases.Categoria> listaCategorias = new ArrayList<>();

    private static final java.util.logging.Logger logger = java.util.logging.Logger.getLogger(frmOrden.class.getName());

    public frmOrden() {
        this.setUndecorated(true);
        initComponents();
        this.setSize(740, 600);
        setLocationRelativeTo(null);
        setIconImage(new ImageIcon(getClass().getResource("/Imagenes/logo.png")).getImage());
        // Cargar fecha actual
        txtFecha.setText(new java.text.SimpleDateFormat("yyyy-MM-dd HH:mm:ss")
                .format(new java.util.Date()));

// Cargar próximo ID
        int ultimoId = ordenDAO.obtenerUltimoIdOrden();
        txtIdOrden.setText(String.valueOf(ultimoId + 1));

// ── Logo ─────────────────────────────────────────────────────
        ImageIcon icon = new ImageIcon(getClass().getResource("/Imagenes/Mi_taqueria.png"));
        Image img = icon.getImage().getScaledInstance(150, 150, Image.SCALE_SMOOTH);
        lblLogo.setIcon(new ImageIcon(img));

// ── Campos no editables ───────────────────────────────────────
        txtIdOrden.setEditable(false);
        txtFecha.setEditable(false);
        txtUsuario.setEditable(false);
        txtTotal.setEditable(false);
        txtTotal.setText("0.00");

// ── Estilos ───────────────────────────────────────────────────
        Color fondoOscuro = new Color(20, 28, 48);
        Color fondoCampo = new Color(45, 55, 80);
        Color azul = new Color(100, 149, 237);
        getContentPane().setBackground(fondoOscuro);

// ── Labels título ─────────────────────────────────────────────
        lblOrdenes.setForeground(Color.WHITE);
        lblOrdenes.setFont(new Font("Segoe UI", Font.BOLD, 16));
        lblDetalleOrden.setForeground(Color.WHITE);
        lblDetalleOrden.setFont(new Font("Segoe UI", Font.BOLD, 14));

// ── Labels de campos (los jLabel) ────────────────────────────
        for (javax.swing.JLabel lbl : new javax.swing.JLabel[]{
            jLabel1, jLabel2, jLabel3, jLabel4, jLabel5, jLabel6,
            lblFecha, lblIdOrden}) {
            lbl.setForeground(azul);
            lbl.setFont(new Font("Segoe UI", Font.BOLD, 13));
        }

// ── TextFields ────────────────────────────────────────────────
        for (javax.swing.JTextField tf : new javax.swing.JTextField[]{
            txtIdOrden, txtFecha, txtUsuario, txtTotal, txtCantidad}) {
            tf.setBackground(fondoCampo);
            tf.setForeground(Color.WHITE);
            tf.setCaretColor(Color.WHITE);
            tf.setBorder(BorderFactory.createCompoundBorder(
                    BorderFactory.createLineBorder(azul, 2),
                    BorderFactory.createEmptyBorder(4, 8, 4, 8)));
            tf.setFont(new Font("Segoe UI", Font.PLAIN, 13));
        }

// ── ComboBox ──────────────────────────────────────────────────
        for (javax.swing.JComboBox cb : new javax.swing.JComboBox[]{cmbTipo, cmbItem}) {
            cb.setBackground(fondoCampo);
            cb.setForeground(Color.WHITE);
            cb.setFont(new Font("Segoe UI", Font.PLAIN, 13));
        }
// boton Historial
        btnHistorial.setBackground(new Color(100, 149, 237));
        btnHistorial.setForeground(Color.WHITE);
        btnHistorial.setFont(new Font("Segoe UI", Font.BOLD, 13));
        btnHistorial.setFocusPainted(false);
        btnHistorial.setOpaque(true);
        btnHistorial.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        btnHistorial.setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createLineBorder(new Color(70, 110, 200), 2),
                BorderFactory.createEmptyBorder(6, 16, 6, 16)));
        btnHistorial.setVisible(false); // oculto por defecto
// ── Botón Cerrar ──────────────────────────────────────────────
        btnCerrar.setBackground(Color.RED);
        btnCerrar.setForeground(Color.WHITE);
        btnCerrar.setFocusPainted(false);
        btnCerrar.setBorderPainted(false);
        btnCerrar.setFont(new Font("Segoe UI", Font.BOLD, 13));

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

// ── Botón Agregar ─────────────────────────────────────────────
        btnAgregar.setBackground(new Color(100, 149, 237));
        btnAgregar.setForeground(Color.WHITE);
        btnAgregar.setFont(new Font("Segoe UI", Font.BOLD, 13));
        btnAgregar.setFocusPainted(false);
        btnAgregar.setOpaque(true);
        btnAgregar.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        btnAgregar.setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createLineBorder(new Color(70, 110, 200), 2),
                BorderFactory.createEmptyBorder(6, 16, 6, 16)));

// ── Botón Quitar ──────────────────────────────────────────────
        btnQuitar.setBackground(new Color(220, 53, 69));
        btnQuitar.setForeground(Color.WHITE);
        btnQuitar.setFont(new Font("Segoe UI", Font.BOLD, 13));
        btnQuitar.setFocusPainted(false);
        btnQuitar.setOpaque(true);
        btnQuitar.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        btnQuitar.setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createLineBorder(new Color(170, 30, 45), 2),
                BorderFactory.createEmptyBorder(6, 16, 6, 16)));

// ── Tablas ────────────────────────────────────────────────────
        for (javax.swing.JTable tabla : new javax.swing.JTable[]{tblDetalleOrden, tblListaOrdenes}) {
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
        for (javax.swing.JScrollPane sp : new javax.swing.JScrollPane[]{
            scrllDetalleOrden, scrllListaOrdenes}) {
            sp.getViewport().setBackground(new Color(25, 35, 55));
            sp.setBorder(BorderFactory.createLineBorder(azul, 2));
        }

// ── cmbTipo listener ──────────────────────────────────────────
        cmbTipo.addActionListener(e -> cargarItems());

// ── Cargar datos ──────────────────────────────────────────────
        cargarTipo();
        listarOrdenes();
        iniciarTablaDetalle();
    }

    private void cargarTipo() {
        cmbTipo.removeAllItems();
        CategoriaDAO categoriaDAO = new CategoriaDAO();
        listaCategorias = categoriaDAO.listarCategorias();
        for (clases.Categoria cat : listaCategorias) {
            cmbTipo.addItem(cat.getDescripcion());
        }
        cmbTipo.addItem("Combo");
        cargarItems();
    }

    private void cargarItems() {
        cmbItem.removeAllItems();
        if (cmbTipo.getSelectedItem() == null) {
            return;
        }

        String tipoSeleccionado = cmbTipo.getSelectedItem().toString();

        if (tipoSeleccionado.equals("Combo")) {
            listaCombos = comboDAO.listar();
            for (clases.Combo c : listaCombos) {
                cmbItem.addItem(c.getIdCombo() + " - " + c.getNombre());
            }
        } else {
            listaProductos = productoDAO.listarPorCategoria(tipoSeleccionado);
            for (Producto p : listaProductos) {
                cmbItem.addItem(p.getIdProducto() + " - " + p.getNombre());
            }
        }
    }

    private void listarOrdenes() {
        String[] titulos = {"ID Orden", "Fecha y Hora", "Usuario", "Total", "Estado"};
        modeloOrdenes = new DefaultTableModel(null, titulos) {
            @Override
            public boolean isCellEditable(int r, int c) {
                return false;
            }
        };

        List<Orden> lista;
        if (usuarioActivo.getRol() == null
                || usuarioActivo.getRol().equals("ADMINISTRADOR")
                || usuarioActivo.getRol().equals("SUPERVISOR")) {
            lista = ordenDAO.listar(); // Ven todas las órdenes del día
        } else {
            lista = ordenDAO.listarPorUsuario(usuarioActivo.getIdUsuario()); // Solo las suyas
        }

        for (Orden o : lista) {
            modeloOrdenes.addRow(new Object[]{
                o.getIdOrden(),
                o.getFechaHora(),
                o.getNombreUsuario(),
                String.format("%.2f", o.getTotal()),
                o.getEstado()
            });
        }
        tblListaOrdenes.setModel(modeloOrdenes);
    }

    private void iniciarTablaDetalle() {
        String[] titulos = {"Tipo", "ID", "Nombre", "Cantidad", "Precio Unit.", "Subtotal"};
        modeloDetalle = new DefaultTableModel(null, titulos) {
            @Override
            public boolean isCellEditable(int r, int c) {
                return false;
            }
        };
        tblDetalleOrden.setModel(modeloDetalle);
    }

    private void calcularTotal() {
        double total = 0;
        for (int i = 0; i < modeloDetalle.getRowCount(); i++) {
            total += Double.parseDouble(modeloDetalle.getValueAt(i, 5).toString());
        }
        txtTotal.setText(String.format("%.2f", total));
    }

    private void agregarItem() {
        if (cmbItem.getSelectedIndex() < 0) {
            return;
        }

        String cantidad = txtCantidad.getText().trim();
        if (cantidad.isEmpty()) {
            javax.swing.JOptionPane.showMessageDialog(this, "Ingrese la cantidad.");
            return;
        }

        int cant;
        try {
            cant = Integer.parseInt(cantidad);
            if (cant <= 0) {
                throw new NumberFormatException();
            }
        } catch (NumberFormatException e) {
            javax.swing.JOptionPane.showMessageDialog(this, "Cantidad debe ser mayor a 0.");
            return;
        }

        String tipoSeleccionado = cmbTipo.getSelectedItem().toString();
        String tipo = tipoSeleccionado.equals("Combo") ? "Combo" : "Producto";
        String idStr;
        String nombre;
        double precio;

        if (tipoSeleccionado.equals("Combo")) {
            clases.Combo c = listaCombos.get(cmbItem.getSelectedIndex());
            idStr = String.valueOf(c.getIdCombo());
            nombre = c.getNombre();
            precio = c.getPrecioCombo();
        } else {
            Producto p = listaProductos.get(cmbItem.getSelectedIndex());
            idStr = String.valueOf(Integer.parseInt(p.getIdProducto().trim()));
            nombre = p.getNombre();
            precio = Double.parseDouble(p.getPrecio());
        }

        // Verificar duplicado
        for (int i = 0; i < modeloDetalle.getRowCount(); i++) {
            String tipoTabla = modeloDetalle.getValueAt(i, 0).toString().trim();
            String idTabla = modeloDetalle.getValueAt(i, 1).toString().trim();
            if (tipoTabla.equals(tipo) && idTabla.equals(idStr)) {
                int cantExistente = Integer.parseInt(modeloDetalle.getValueAt(i, 3).toString());
                int nuevaCant = cantExistente + cant;
                modeloDetalle.setValueAt(nuevaCant, i, 3);
                modeloDetalle.setValueAt(String.format("%.2f", precio * nuevaCant), i, 5);
                calcularTotal();
                txtCantidad.setText("");
                return;
            }
        }

        // Nuevo item
        double subtotal = precio * cant;
        modeloDetalle.addRow(new Object[]{
            tipo, idStr, nombre, cant,
            String.format("%.2f", precio),
            String.format("%.2f", subtotal)
        });
        calcularTotal();
        txtCantidad.setText("");
    }

    private void limpiarCampos() {
        txtIdOrden.setText("");
        txtFecha.setText("");
        txtTotal.setText("0.00");
        txtCantidad.setText("");
        cmbTipo.setSelectedIndex(0);
        iniciarTablaDetalle();
        tblListaOrdenes.clearSelection();
        listarOrdenes();
        // txtUsuario NO se limpia — mantiene la sesión activa
        // Refrescar fecha e ID
        txtFecha.setText(new java.text.SimpleDateFormat("yyyy-MM-dd HH:mm:ss")
                .format(new java.util.Date()));
        int ultimoId = ordenDAO.obtenerUltimoIdOrden();
        txtIdOrden.setText(String.valueOf(ultimoId + 1));
    }

    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        lblOrdenes = new javax.swing.JLabel();
        lblLogo = new javax.swing.JLabel();
        btnCerrar = new javax.swing.JButton();
        lblIdOrden = new javax.swing.JLabel();
        txtIdOrden = new javax.swing.JTextField();
        lblFecha = new javax.swing.JLabel();
        txtFecha = new javax.swing.JTextField();
        jLabel1 = new javax.swing.JLabel();
        txtUsuario = new javax.swing.JTextField();
        jLabel2 = new javax.swing.JLabel();
        txtTotal = new javax.swing.JTextField();
        jLabel3 = new javax.swing.JLabel();
        cmbTipo = new javax.swing.JComboBox<>();
        jLabel4 = new javax.swing.JLabel();
        cmbItem = new javax.swing.JComboBox<>();
        jLabel5 = new javax.swing.JLabel();
        txtCantidad = new javax.swing.JTextField();
        btnAgregar = new javax.swing.JButton();
        btnQuitar = new javax.swing.JButton();
        lblDetalleOrden = new javax.swing.JLabel();
        scrllDetalleOrden = new javax.swing.JScrollPane();
        tblDetalleOrden = new javax.swing.JTable();
        jLabel6 = new javax.swing.JLabel();
        scrllListaOrdenes = new javax.swing.JScrollPane();
        tblListaOrdenes = new javax.swing.JTable();
        btnGuardar = new javax.swing.JButton();
        btnLimpiar = new javax.swing.JButton();
        btnEliminar = new javax.swing.JButton();
        btnActualizar = new javax.swing.JButton();
        btnHistorial = new javax.swing.JButton();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
        getContentPane().setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        lblOrdenes.setText("Ordenes");
        getContentPane().add(lblOrdenes, new org.netbeans.lib.awtextra.AbsoluteConstraints(6, 6, 128, -1));
        getContentPane().add(lblLogo, new org.netbeans.lib.awtextra.AbsoluteConstraints(280, 0, 130, 120));

        btnCerrar.setText("Cerrar");
        btnCerrar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnCerrarActionPerformed(evt);
            }
        });
        getContentPane().add(btnCerrar, new org.netbeans.lib.awtextra.AbsoluteConstraints(634, 6, -1, -1));

        lblIdOrden.setText("ID Orden");
        getContentPane().add(lblIdOrden, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 120, 54, -1));
        getContentPane().add(txtIdOrden, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 140, 71, -1));

        lblFecha.setText("Fecha");
        getContentPane().add(lblFecha, new org.netbeans.lib.awtextra.AbsoluteConstraints(120, 120, 41, -1));
        getContentPane().add(txtFecha, new org.netbeans.lib.awtextra.AbsoluteConstraints(120, 140, 180, -1));

        jLabel1.setText("Usuario");
        getContentPane().add(jLabel1, new org.netbeans.lib.awtextra.AbsoluteConstraints(320, 120, 60, -1));
        getContentPane().add(txtUsuario, new org.netbeans.lib.awtextra.AbsoluteConstraints(320, 140, 175, -1));

        jLabel2.setText("Total:");
        getContentPane().add(jLabel2, new org.netbeans.lib.awtextra.AbsoluteConstraints(540, 120, 37, -1));
        getContentPane().add(txtTotal, new org.netbeans.lib.awtextra.AbsoluteConstraints(540, 140, 71, -1));

        jLabel3.setText("Tipo");
        getContentPane().add(jLabel3, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 180, 37, -1));

        cmbTipo.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Item 1", "Item 2", "Item 3", "Item 4" }));
        getContentPane().add(cmbTipo, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 200, 120, -1));

        jLabel4.setText("Item");
        getContentPane().add(jLabel4, new org.netbeans.lib.awtextra.AbsoluteConstraints(150, 180, 37, -1));

        cmbItem.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Item 1", "Item 2", "Item 3", "Item 4" }));
        getContentPane().add(cmbItem, new org.netbeans.lib.awtextra.AbsoluteConstraints(150, 200, 180, -1));

        jLabel5.setText("Cantidad");
        getContentPane().add(jLabel5, new org.netbeans.lib.awtextra.AbsoluteConstraints(340, 180, 60, -1));

        txtCantidad.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyTyped(java.awt.event.KeyEvent evt) {
                txtCantidadKeyTyped(evt);
            }
        });
        getContentPane().add(txtCantidad, new org.netbeans.lib.awtextra.AbsoluteConstraints(340, 200, 74, -1));

        btnAgregar.setText("Agregar");
        btnAgregar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnAgregarActionPerformed(evt);
            }
        });
        getContentPane().add(btnAgregar, new org.netbeans.lib.awtextra.AbsoluteConstraints(450, 200, -1, -1));

        btnQuitar.setText("Quitar");
        btnQuitar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnQuitarActionPerformed(evt);
            }
        });
        getContentPane().add(btnQuitar, new org.netbeans.lib.awtextra.AbsoluteConstraints(540, 200, -1, -1));

        lblDetalleOrden.setText("Detalle de la Orden");
        getContentPane().add(lblDetalleOrden, new org.netbeans.lib.awtextra.AbsoluteConstraints(30, 240, 160, -1));

        tblDetalleOrden.setModel(new javax.swing.table.DefaultTableModel(
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
        scrllDetalleOrden.setViewportView(tblDetalleOrden);

        getContentPane().add(scrllDetalleOrden, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 260, 710, 110));

        jLabel6.setText("Lista de Ordenes ");
        getContentPane().add(jLabel6, new org.netbeans.lib.awtextra.AbsoluteConstraints(30, 400, -1, -1));

        tblListaOrdenes.setModel(new javax.swing.table.DefaultTableModel(
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
        tblListaOrdenes.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                tblListaOrdenesMouseClicked(evt);
            }
        });
        scrllListaOrdenes.setViewportView(tblListaOrdenes);

        getContentPane().add(scrllListaOrdenes, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 430, 710, 130));

        btnGuardar.setText("Guardar");
        btnGuardar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnGuardarActionPerformed(evt);
            }
        });
        getContentPane().add(btnGuardar, new org.netbeans.lib.awtextra.AbsoluteConstraints(620, 380, -1, -1));

        btnLimpiar.setText("Limpiar");
        btnLimpiar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnLimpiarActionPerformed(evt);
            }
        });
        getContentPane().add(btnLimpiar, new org.netbeans.lib.awtextra.AbsoluteConstraints(530, 380, -1, -1));

        btnEliminar.setText("Anular");
        btnEliminar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnEliminarActionPerformed(evt);
            }
        });
        getContentPane().add(btnEliminar, new org.netbeans.lib.awtextra.AbsoluteConstraints(430, 380, -1, -1));

        btnActualizar.setText("Despachar");
        btnActualizar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnActualizarActionPerformed(evt);
            }
        });
        getContentPane().add(btnActualizar, new org.netbeans.lib.awtextra.AbsoluteConstraints(320, 380, -1, -1));

        btnHistorial.setText("Historail de Ordenes");
        btnHistorial.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnHistorialActionPerformed(evt);
            }
        });
        getContentPane().add(btnHistorial, new org.netbeans.lib.awtextra.AbsoluteConstraints(470, 10, -1, 20));

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void btnCerrarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnCerrarActionPerformed
        this.dispose();
    }//GEN-LAST:event_btnCerrarActionPerformed

    private void btnLimpiarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnLimpiarActionPerformed
        limpiarCampos();
    }//GEN-LAST:event_btnLimpiarActionPerformed

    private void btnAgregarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnAgregarActionPerformed
        agregarItem();
    }//GEN-LAST:event_btnAgregarActionPerformed

    private void btnQuitarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnQuitarActionPerformed
        int fila = tblDetalleOrden.getSelectedRow();
        if (fila == -1) {
            javax.swing.JOptionPane.showMessageDialog(this, "Seleccione un item del detalle para quitar.");
            return;
        }
        modeloDetalle.removeRow(fila);
        calcularTotal();
    }//GEN-LAST:event_btnQuitarActionPerformed

    private void btnGuardarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnGuardarActionPerformed
        if (txtUsuario.getText().trim().isEmpty()) {
            javax.swing.JOptionPane.showMessageDialog(this, "No hay usuario en sesión.");
            return;
        }
        if (modeloDetalle.getRowCount() == 0) {
            javax.swing.JOptionPane.showMessageDialog(this, "Agregue al menos un item a la orden.");
            return;
        }

        Orden orden = new Orden();
        orden.setIdUsuario(usuarioActivo.getIdUsuario());
        orden.setTotal(Double.parseDouble(txtTotal.getText()));

        List<DetalleOrden> detalles = new ArrayList<>();
        for (int i = 0; i < modeloDetalle.getRowCount(); i++) {
            DetalleOrden d = new DetalleOrden();
            String tipo = modeloDetalle.getValueAt(i, 0).toString();
            String id = modeloDetalle.getValueAt(i, 1).toString();

            if (tipo.equals("Producto")) {
                d.setIdProducto(Integer.parseInt(id));
                d.setIdCombo(0);
            } else {
                d.setIdCombo(Integer.parseInt(id));
                d.setIdProducto(0);
            }
            d.setCantidad(Integer.parseInt(modeloDetalle.getValueAt(i, 3).toString()));
            d.setPrecioUnitario(Double.parseDouble(modeloDetalle.getValueAt(i, 4).toString()));
            detalles.add(d);
        }
        orden.setDetalle(detalles);

        int res = ordenDAO.insertarOrden(orden);
        if (res > 0) {
            javax.swing.JOptionPane.showMessageDialog(this, "Orden guardada correctamente.");

            ticketPDF.generarTicket(res);

            listarOrdenes();
            limpiarCampos();
        } else {
            javax.swing.JOptionPane.showMessageDialog(this, "Error al guardar la orden.");
        }
    }//GEN-LAST:event_btnGuardarActionPerformed

    private void btnEliminarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnEliminarActionPerformed
        if (!usuarioActivo.getRol().equals("ADMINISTRADOR")) {
            javax.swing.JOptionPane.showMessageDialog(this,
                    "Solo un Administrador puede anular órdenes.");
            return;
        }
        if (tblListaOrdenes.getSelectedRow() == -1) {
            javax.swing.JOptionPane.showMessageDialog(this, "Seleccione una orden para anular.");
            return;
        }

        int fila = tblListaOrdenes.getSelectedRow();
        String estadoActual = modeloOrdenes.getValueAt(fila, 4).toString();
        if (estadoActual.equals("ANULADA")) {
            javax.swing.JOptionPane.showMessageDialog(this, "Esta orden ya está anulada.");
            return;
        }

        int confirm = javax.swing.JOptionPane.showConfirmDialog(this,
                "¿Está seguro de anular la orden?", "Confirmar", javax.swing.JOptionPane.YES_NO_OPTION);
        if (confirm != javax.swing.JOptionPane.YES_OPTION) {
            return;
        }

        int idOrden = Integer.parseInt(txtIdOrden.getText());
        boolean res = ordenDAO.anularOrden(idOrden);
        if (res) {
            javax.swing.JOptionPane.showMessageDialog(this, "Orden anulada correctamente.");
            listarOrdenes();
            limpiarCampos();
        } else {
            javax.swing.JOptionPane.showMessageDialog(this,
                    "No se pudo anular. Solo se permite anular órdenes del día actual.");
        }
    }//GEN-LAST:event_btnEliminarActionPerformed

    private void btnActualizarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnActualizarActionPerformed
        if (tblListaOrdenes.getSelectedRow() == -1) {
            javax.swing.JOptionPane.showMessageDialog(this, "Seleccione una orden para despachar.");
            return;
        }

        int fila = tblListaOrdenes.getSelectedRow();
        String estadoActual = modeloOrdenes.getValueAt(fila, 4).toString();
        if (!estadoActual.equals("PROCESADA")) {
            javax.swing.JOptionPane.showMessageDialog(this,
                    "Solo se pueden despachar órdenes en estado Procesada.");
            return;
        }

        int confirm = javax.swing.JOptionPane.showConfirmDialog(this,
                "¿Desea marcar esta orden como Despachada?", "Confirmar", javax.swing.JOptionPane.YES_NO_OPTION);
        if (confirm != javax.swing.JOptionPane.YES_OPTION) {
            return;
        }

        int idOrden = Integer.parseInt(txtIdOrden.getText());
        boolean res = ordenDAO.despacharOrden(idOrden);
        if (res) {
            javax.swing.JOptionPane.showMessageDialog(this, "Orden despachada correctamente.");
            listarOrdenes();
            limpiarCampos();
        } else {
            javax.swing.JOptionPane.showMessageDialog(this, "Error al despachar la orden.");
        }
    }//GEN-LAST:event_btnActualizarActionPerformed

    private void tblListaOrdenesMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_tblListaOrdenesMouseClicked
        int fila = tblListaOrdenes.getSelectedRow();
        if (fila == -1) {
            return;
        }

        txtIdOrden.setText(modeloOrdenes.getValueAt(fila, 0).toString());
        txtFecha.setText(modeloOrdenes.getValueAt(fila, 1).toString());
        txtUsuario.setText(modeloOrdenes.getValueAt(fila, 2).toString());
        txtTotal.setText(modeloOrdenes.getValueAt(fila, 3).toString());

// Cargar detalle de la orden seleccionada
        String[] titulos = {"Tipo", "ID", "Nombre", "Cantidad", "Precio Unit.", "Subtotal"};
        modeloDetalle = new DefaultTableModel(null, titulos) {
            @Override
            public boolean isCellEditable(int r, int c) {
                return false;
            }
        };
        int idOrden = Integer.parseInt(txtIdOrden.getText());
        for (DetalleOrden d : ordenDAO.cargarDetalle(idOrden)) {
            String tipo = d.getIdProducto() > 0 ? "Producto" : "Combo";
            String id = d.getIdProducto() > 0
                    ? String.valueOf(d.getIdProducto()) : String.valueOf(d.getIdCombo());
            modeloDetalle.addRow(new Object[]{
                tipo, id, d.getNombre(), d.getCantidad(),
                String.format("%.2f", d.getPrecioUnitario()),
                String.format("%.2f", d.getSubtotal())
            });
        }
        tblDetalleOrden.setModel(modeloDetalle);
        // Mantener siempre el usuario en sesión visible
        txtUsuario.setText(usuarioActivo.getNombre() + " " + usuarioActivo.getApellido());
    }//GEN-LAST:event_tblListaOrdenesMouseClicked

    private void txtCantidadKeyTyped(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtCantidadKeyTyped
        char c = evt.getKeyChar();
        if (!Character.isDigit(c)) {
            evt.consume();
        }
    }//GEN-LAST:event_txtCantidadKeyTyped

    private void btnHistorialActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnHistorialActionPerformed
        // Crear dialog
        javax.swing.JDialog dialog = new javax.swing.JDialog(this, "Historial de Órdenes", true);
        dialog.setSize(800, 500);
        dialog.setLocationRelativeTo(this);
        dialog.getContentPane().setBackground(new Color(20, 28, 48));

// Tabla
        String[] titulos = {"ID Orden", "Fecha y Hora", "Usuario", "Total", "Estado"};
        DefaultTableModel modeloHistorial = new DefaultTableModel(null, titulos) {
            @Override
            public boolean isCellEditable(int r, int c) {
                return false;
            }
        };

// Cargar TODAS las órdenes sin filtro
        for (Orden o : ordenDAO.listarTodas()) {
            modeloHistorial.addRow(new Object[]{
                o.getIdOrden(),
                o.getFechaHora(),
                o.getNombreUsuario(),
                String.format("%.2f", o.getTotal()),
                o.getEstado()
            });
        }

        javax.swing.JTable tablaHistorial = new javax.swing.JTable(modeloHistorial);
        tablaHistorial.setBackground(new Color(25, 35, 55));
        tablaHistorial.setForeground(Color.WHITE);
        tablaHistorial.setFont(new Font("Segoe UI", Font.PLAIN, 13));
        tablaHistorial.setRowHeight(28);
        tablaHistorial.getTableHeader().setBackground(new Color(63, 94, 251));
        tablaHistorial.getTableHeader().setForeground(Color.WHITE);
        tablaHistorial.getTableHeader().setFont(new Font("Segoe UI", Font.BOLD, 13));
        tablaHistorial.setDefaultRenderer(Object.class, new javax.swing.table.DefaultTableCellRenderer() {
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
                return this;
            }
        });

        javax.swing.JScrollPane scroll = new javax.swing.JScrollPane(tablaHistorial);
        scroll.setBorder(BorderFactory.createLineBorder(new Color(100, 149, 237), 2));
        scroll.getViewport().setBackground(new Color(25, 35, 55));

// Botón cerrar dialog
        javax.swing.JButton btnCerrarDialog = new javax.swing.JButton("Cerrar");
        btnCerrarDialog.setBackground(Color.RED);
        btnCerrarDialog.setForeground(Color.WHITE);
        btnCerrarDialog.setFocusPainted(false);
        btnCerrarDialog.setBorderPainted(false);
        btnCerrarDialog.addActionListener(e -> dialog.dispose());

// Panel botón
        javax.swing.JPanel panelBtn = new javax.swing.JPanel();
        panelBtn.setBackground(new Color(20, 28, 48));
        panelBtn.add(btnCerrarDialog);

        dialog.add(scroll, java.awt.BorderLayout.CENTER);
        dialog.add(panelBtn, java.awt.BorderLayout.SOUTH);
        dialog.setVisible(true);
    }//GEN-LAST:event_btnHistorialActionPerformed

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
        java.awt.EventQueue.invokeLater(() -> new frmOrden().setVisible(true));
    }

    public void configurarSegunRol(String rol) {
        if (rol.equals("COCINERO")) {
            btnGuardar.setEnabled(false);
            btnActualizar.setEnabled(false);
            btnEliminar.setEnabled(false);
            btnAgregar.setEnabled(false);
            btnQuitar.setEnabled(false);
            cmbTipo.setEnabled(false);
            cmbItem.setEnabled(false);
            txtCantidad.setEnabled(false);
        }
        if (rol.equals("SUPERVISOR")) {
            btnGuardar.setEnabled(false);
            btnActualizar.setEnabled(false);
            btnEliminar.setEnabled(false);
            btnAgregar.setEnabled(false);
            btnQuitar.setEnabled(false);
            cmbTipo.setEnabled(false);
            cmbItem.setEnabled(false);
            txtCantidad.setEnabled(false);
        }
    }

    public void setUsuarioActivo(Usuario u) {
        usuarioActivo = u;
        txtUsuario.setText(u.getNombre() + " " + u.getApellido());
        if (u.getRol().equals("ADMINISTRADOR")) {
            btnHistorial.setVisible(true);
        }
        if (u.getRol().equals("SUPERVISOR")) {
            btnHistorial.setVisible(true);
        }
    }
    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton btnActualizar;
    private javax.swing.JButton btnAgregar;
    private javax.swing.JButton btnCerrar;
    private javax.swing.JButton btnEliminar;
    private javax.swing.JButton btnGuardar;
    private javax.swing.JButton btnHistorial;
    private javax.swing.JButton btnLimpiar;
    private javax.swing.JButton btnQuitar;
    private javax.swing.JComboBox<String> cmbItem;
    private javax.swing.JComboBox<String> cmbTipo;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JLabel jLabel6;
    private javax.swing.JLabel lblDetalleOrden;
    private javax.swing.JLabel lblFecha;
    private javax.swing.JLabel lblIdOrden;
    private javax.swing.JLabel lblLogo;
    private javax.swing.JLabel lblOrdenes;
    private javax.swing.JScrollPane scrllDetalleOrden;
    private javax.swing.JScrollPane scrllListaOrdenes;
    private javax.swing.JTable tblDetalleOrden;
    private javax.swing.JTable tblListaOrdenes;
    private javax.swing.JTextField txtCantidad;
    private javax.swing.JTextField txtFecha;
    private javax.swing.JTextField txtIdOrden;
    private javax.swing.JTextField txtTotal;
    private javax.swing.JTextField txtUsuario;
    // End of variables declaration//GEN-END:variables
}
