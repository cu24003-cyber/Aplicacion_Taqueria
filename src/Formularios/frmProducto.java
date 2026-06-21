package Formularios;

import clasesDAO.CategoriaDAO;
import clases.Categoria;
import clases.Producto;
import clases.Validaciones;
import clasesDAO.ProductoDAO;
import java.awt.Color;
import java.awt.Component;
import java.awt.Container;
import java.awt.Cursor;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Image;
import java.awt.Toolkit;
import java.util.ArrayList;
import java.util.List;
import javax.swing.BorderFactory;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.table.DefaultTableModel;

public class frmProducto extends javax.swing.JFrame {

    private static final java.util.logging.Logger logger = java.util.logging.Logger.getLogger(frmProducto.class.getName());

    ImageIcon icon = new ImageIcon(getClass().getResource("/Imagenes/Mi_taqueria.png"));
    Image img = icon.getImage().getScaledInstance(200, 200, Image.SCALE_SMOOTH);
    Validaciones validacion = new Validaciones();
    ProductoDAO dao = new ProductoDAO();
    Categoria categoria = new Categoria();
    //Para obtener el ultimo id de producto
    int idProducto = dao.obtenerUltimoIdProducto();
    DefaultTableModel modeloTabla = new DefaultTableModel();

    public frmProducto() {
        this.setUndecorated(true);
        initComponents();
        cargarCategoria();
        setLocationRelativeTo(null);  // Centra en pantalla
        fijarPosicion();//fijar en el centro
        txtIdProducto.setText(String.format("%03d", idProducto + 1));
        listar();
        tblMostrar.setEnabled(true);
        lblLogo.setIcon(new ImageIcon(img));
        setIconImage(new ImageIcon(getClass().getResource("/Imagenes/logo.png")).getImage());

        Color fondoCampo = new Color(45, 55, 80);
        Color colorTexto = Color.WHITE;
        Color colorAccento = new Color(100, 149, 237);
        aplicarEstilos(getContentPane(),
                new Color(45, 55, 80), // fondo
                Color.WHITE, // texto
                new Color(100, 149, 237) // acento azul
        );

        btnCerrar.setBackground(Color.RED);
        btnCerrar.setForeground(Color.WHITE);
        btnCerrar.setFocusPainted(false);
        btnCerrar.setBorderPainted(false);
        btnGuardar.setBackground(new Color(63, 94, 251));
        btnGuardar.setForeground(Color.WHITE);
        btnGuardar.setFont(new Font("Segoe UI", Font.BOLD, 14));
        btnGuardar.setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createLineBorder(new Color(40, 60, 180), 2),
                BorderFactory.createEmptyBorder(8, 24, 8, 24)
        ));
        btnGuardar.setCursor(new Cursor(Cursor.HAND_CURSOR));
        btnGuardar.setOpaque(true);
        // Estilo btnActualizar
        btnActualizar.setBackground(new Color(40, 167, 69));
        btnActualizar.setForeground(Color.WHITE);
        btnActualizar.setFont(new Font("Segoe UI", Font.BOLD, 14));
        btnActualizar.setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createLineBorder(new Color(30, 130, 50), 2),
                BorderFactory.createEmptyBorder(8, 24, 8, 24)
        ));
        btnActualizar.setCursor(new Cursor(Cursor.HAND_CURSOR));
        btnActualizar.setOpaque(true);
        btnActualizar.addMouseListener(new java.awt.event.MouseAdapter() {
            @Override
            public void mouseEntered(java.awt.event.MouseEvent evt) {
                btnActualizar.setBackground(new Color(30, 130, 50));
            }

            @Override
            public void mouseExited(java.awt.event.MouseEvent evt) {
                btnActualizar.setBackground(new Color(40, 167, 69));
            }
        });

// Estilo btnEliminar
        btnEliminar.setBackground(new Color(220, 53, 69));
        btnEliminar.setForeground(Color.WHITE);
        btnEliminar.setFont(new Font("Segoe UI", Font.BOLD, 14));
        btnEliminar.setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createLineBorder(new Color(170, 30, 45), 2),
                BorderFactory.createEmptyBorder(8, 24, 8, 24)
        ));
        btnEliminar.setCursor(new Cursor(Cursor.HAND_CURSOR));
        btnEliminar.setOpaque(true);
        btnEliminar.addMouseListener(new java.awt.event.MouseAdapter() {
            @Override
            public void mouseEntered(java.awt.event.MouseEvent evt) {
                btnEliminar.setBackground(new Color(170, 30, 45));
            }

            @Override
            public void mouseExited(java.awt.event.MouseEvent evt) {
                btnEliminar.setBackground(new Color(220, 53, 69));
            }
        });

// Estilo btnLimpiar
        btnLimpiar.setBackground(new Color(255, 193, 7));
        btnLimpiar.setForeground(new Color(30, 30, 30));
        btnLimpiar.setFont(new Font("Segoe UI", Font.BOLD, 14));
        btnLimpiar.setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createLineBorder(new Color(200, 150, 0), 2),
                BorderFactory.createEmptyBorder(8, 24, 8, 24)
        ));
        btnLimpiar.setCursor(new Cursor(Cursor.HAND_CURSOR));
        btnLimpiar.setOpaque(true);
        btnLimpiar.addMouseListener(new java.awt.event.MouseAdapter() {
            @Override
            public void mouseEntered(java.awt.event.MouseEvent evt) {
                btnLimpiar.setBackground(new Color(200, 150, 0));
            }

            @Override
            public void mouseExited(java.awt.event.MouseEvent evt) {
                btnLimpiar.setBackground(new Color(255, 193, 7));
            }
        });

        // Efecto hover al pasar el mouse
        btnGuardar.addMouseListener(new java.awt.event.MouseAdapter() {
            @Override
            public void mouseEntered(java.awt.event.MouseEvent evt) {
                btnGuardar.setBackground(new Color(40, 70, 220));
            }

            @Override
            public void mouseExited(java.awt.event.MouseEvent evt) {
                btnGuardar.setBackground(new Color(63, 94, 251));
            }
        });
        // Colores de la tabla
        tblMostrar.setBackground(new Color(25, 35, 55));
        tblMostrar.setForeground(Color.WHITE);
        tblMostrar.setFont(new Font("Segoe UI", Font.PLAIN, 13));
        tblMostrar.setRowHeight(30);
        tblMostrar.setGridColor(new Color(50, 70, 110));
        tblMostrar.setShowVerticalLines(false); // Solo líneas horizontales
        tblMostrar.setIntercellSpacing(new Dimension(0, 1));

// Encabezado de la tabla
        tblMostrar.getTableHeader().setBackground(new Color(63, 94, 251));
        tblMostrar.getTableHeader().setForeground(Color.WHITE);
        tblMostrar.getTableHeader().setFont(new Font("Segoe UI", Font.BOLD, 13));
        tblMostrar.getTableHeader().setPreferredSize(new Dimension(0, 35));
        tblMostrar.getTableHeader().setBorder(BorderFactory.createEmptyBorder());

// Color alterno en filas
        tblMostrar.setDefaultRenderer(Object.class, new javax.swing.table.DefaultTableCellRenderer() {
            @Override
            public Component getTableCellRendererComponent(JTable table, Object value,
                    boolean isSelected, boolean hasFocus, int row, int column) {
                super.getTableCellRendererComponent(table, value, isSelected, hasFocus, row, column);
                if (isSelected) {
                    setBackground(new Color(63, 94, 251));
                    setForeground(Color.WHITE);
                } else if (row % 2 == 0) {
                    setBackground(new Color(25, 35, 55));  // Fila par
                    setForeground(Color.WHITE);
                } else {
                    setBackground(new Color(35, 48, 75));  // Fila impar
                    setForeground(Color.WHITE);
                }
                setBorder(BorderFactory.createEmptyBorder(0, 8, 0, 8));
                return this;
            }
        });

// Fondo del ScrollPane
        jScrollPane1.getViewport().setBackground(new Color(25, 35, 55));
        jScrollPane1.setBorder(BorderFactory.createLineBorder(new Color(63, 94, 251), 2));
        txtPrecio.addFocusListener(new java.awt.event.FocusAdapter() {
            @Override
            public void focusLost(java.awt.event.FocusEvent evt) {
                String texto = txtPrecio.getText().trim();
                try {
                    double valor = Double.parseDouble(texto);
                    if (valor == 0.00) {
                        JOptionPane.showMessageDialog(null,
                                "El precio no puede ser 0.00",
                                "Valor inválido",
                                JOptionPane.WARNING_MESSAGE);
                        txtPrecio.setText("");
                        txtPrecio.requestFocus();
                    }
                } catch (NumberFormatException e) {
                }
            }
        });

    }

    private void fijarPosicion() {
        Dimension pantalla = Toolkit.getDefaultToolkit().getScreenSize();
        int x = (pantalla.width - getWidth()) / 2;
        int y = (pantalla.height - getHeight()) / 2;
        setLocation(x, y);

        addComponentListener(new java.awt.event.ComponentAdapter() {
            @Override
            public void componentMoved(java.awt.event.ComponentEvent evt) {
                setLocation(x, y); // Cada vez que se mueva, regresa al centro
            }
        });
    }

    private void aplicarEstilos(Container container, Color fondo, Color texto, Color acento) {
        for (Component comp : container.getComponents()) {

            // ── JTextField ──────────────────────────────────────
            if (comp instanceof JTextField) {
                JTextField tf = (JTextField) comp;
                tf.setBackground(new Color(45, 55, 80));
                tf.setForeground(Color.WHITE);
                tf.setCaretColor(Color.WHITE);
                tf.setBorder(BorderFactory.createCompoundBorder(
                        BorderFactory.createLineBorder(new Color(100, 149, 237), 2),
                        BorderFactory.createEmptyBorder(4, 8, 4, 8)
                ));
                tf.setFont(new Font("Segoe UI", Font.PLAIN, 13));
            }

            // ── JComboBox ───────────────────────────────────────
            if (comp instanceof JComboBox) {
                JComboBox cb = (JComboBox) comp;
                cb.setBackground(new Color(45, 55, 80));
                cb.setForeground(Color.WHITE);
                cb.setFont(new Font("Segoe UI", Font.PLAIN, 13));
                cb.setBorder(BorderFactory.createLineBorder(new Color(100, 149, 237), 2));
            }

            // ── JButton ─────────────────────────────────────────
            if (comp instanceof JButton) {
                JButton btn = (JButton) comp;
                btn.setBackground(new Color(100, 149, 237));
                btn.setForeground(Color.WHITE);
                btn.setFont(new Font("Segoe UI", Font.BOLD, 13));
                btn.setFocusPainted(false);
                btn.setOpaque(true);
                btn.setCursor(new Cursor(Cursor.HAND_CURSOR));
                btn.setBorder(BorderFactory.createCompoundBorder(
                        BorderFactory.createLineBorder(new Color(70, 110, 200), 2),
                        BorderFactory.createEmptyBorder(6, 16, 6, 16)
                ));
            }

            // ── JLabel ──────────────────────────────────────────
            if (comp instanceof JLabel) {
                JLabel lbl = (JLabel) comp;
                String txt = lbl.getText();
                if (txt.equals("Productos") || txt.equals("Lista de Productos")) {
                    lbl.setForeground(Color.WHITE);
                    lbl.setFont(new Font("Segoe UI", Font.BOLD, 16));
                } else {
                    lbl.setForeground(new Color(100, 149, 237));
                    lbl.setFont(new Font("Segoe UI", Font.BOLD, 13));
                }
            }

            // ── JPanel ──────────────────────────────────────────
            if (comp instanceof JPanel) {
                JPanel panel = (JPanel) comp;
                String name = panel.getName() != null ? panel.getName() : "";
                if (name.equals("header")) {
                    panel.setBackground(new Color(30, 40, 60));
                } else {
                    panel.setBackground(new Color(20, 28, 48));
                }
            }

            // ── JTable ──────────────────────────────────────────
            if (comp instanceof JScrollPane) {
                JScrollPane sp = (JScrollPane) comp;
                Component view = sp.getViewport().getView();
                if (view instanceof JTable) {
                    JTable tabla = (JTable) view;
                    tabla.setBackground(new Color(30, 40, 60));
                    tabla.setForeground(Color.WHITE);
                    tabla.setFont(new Font("Segoe UI", Font.PLAIN, 12));
                    tabla.setRowHeight(28);
                    tabla.setGridColor(new Color(60, 80, 120));
                    tabla.getTableHeader().setBackground(new Color(100, 149, 237));
                    tabla.getTableHeader().setForeground(Color.WHITE);
                    tabla.getTableHeader().setFont(new Font("Segoe UI", Font.BOLD, 13));
                    tabla.setSelectionBackground(new Color(70, 110, 200));
                    tabla.setSelectionForeground(Color.WHITE);
                }
            }

            // ── Recursivo ────────────────────────────────────────
            if (comp instanceof Container) {
                aplicarEstilos((Container) comp, fondo, texto, acento);
            }
        }
    }

    //Metodo para listar
    private void listar() {

        String[] titulos = {"ID Producto", "Nombre", "Descripcion", "Precio", "ID Categoria", "Categoria"};
        modeloTabla = new DefaultTableModel(null, titulos);

        List<Producto> lista = dao.listar();

        for (Producto p : lista) {

            Object[] fila = {
                p.getIdProducto(),
                p.getNombre(),
                p.getDescripcion(),
                p.getPrecio(),
                p.getCategoria().getIdCategoria(),
                p.getCategoria().getDescripcion()
            };

            modeloTabla.addRow(fila);
        }

        tblMostrar.setModel(modeloTabla);
        ajustarColumnas(); // Al final

    }

    //Metodo para cargar las categoria
    List<Categoria> listaCategorias = new ArrayList<>();

    private void cargarCategoria() {
        CategoriaDAO dao = new CategoriaDAO();
        listaCategorias = dao.listarCategorias();

        for (Categoria objeto : listaCategorias) {
            cmbCategorias.addItem(objeto.getDescripcion());
        }
    }

    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jPanel1 = new javax.swing.JPanel();
        jPanel3 = new javax.swing.JPanel();
        lblImagen = new javax.swing.JLabel();
        jPanel4 = new javax.swing.JPanel();
        jPanel5 = new javax.swing.JPanel();
        jPanel6 = new javax.swing.JPanel();
        jPanel2 = new javax.swing.JPanel();
        txtIdProducto = new javax.swing.JTextField();
        lblIdProducto = new javax.swing.JLabel();
        txtNombre = new javax.swing.JTextField();
        lblNombre = new javax.swing.JLabel();
        txtPrecio = new javax.swing.JTextField();
        lblPrecio = new javax.swing.JLabel();
        txtDescripcion = new javax.swing.JTextField();
        cmbCategorias = new javax.swing.JComboBox<>();
        lblCategoria = new javax.swing.JLabel();
        jPanel7 = new javax.swing.JPanel();
        lblListaProductos = new javax.swing.JLabel();
        btnGuardar = new javax.swing.JButton();
        btnActualizar = new javax.swing.JButton();
        btnEliminar = new javax.swing.JButton();
        btnLimpiar = new javax.swing.JButton();
        lblLogo = new javax.swing.JLabel();
        jScrollPane1 = new javax.swing.JScrollPane();
        tblMostrar = new javax.swing.JTable();
        lblDescripcion = new javax.swing.JLabel();
        btnCerrar = new javax.swing.JButton();
        lblProductos = new javax.swing.JLabel();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);

        lblImagen.setBackground(new java.awt.Color(255, 255, 255));
        lblImagen.setToolTipText("");

        javax.swing.GroupLayout jPanel3Layout = new javax.swing.GroupLayout(jPanel3);
        jPanel3.setLayout(jPanel3Layout);
        jPanel3Layout.setHorizontalGroup(
            jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel3Layout.createSequentialGroup()
                .addComponent(lblImagen, javax.swing.GroupLayout.PREFERRED_SIZE, 78, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(0, 0, Short.MAX_VALUE))
        );
        jPanel3Layout.setVerticalGroup(
            jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(lblImagen, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );

        javax.swing.GroupLayout jPanel5Layout = new javax.swing.GroupLayout(jPanel5);
        jPanel5.setLayout(jPanel5Layout);
        jPanel5Layout.setHorizontalGroup(
            jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 1027, Short.MAX_VALUE)
        );
        jPanel5Layout.setVerticalGroup(
            jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 32, Short.MAX_VALUE)
        );

        javax.swing.GroupLayout jPanel4Layout = new javax.swing.GroupLayout(jPanel4);
        jPanel4.setLayout(jPanel4Layout);
        jPanel4Layout.setHorizontalGroup(
            jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jPanel5, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );
        jPanel4Layout.setVerticalGroup(
            jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel4Layout.createSequentialGroup()
                .addComponent(jPanel5, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(13, Short.MAX_VALUE))
        );

        txtIdProducto.setEditable(false);

        lblIdProducto.setFont(new java.awt.Font("Segoe UI", 0, 14)); // NOI18N
        lblIdProducto.setText("ID_Producto");

        txtNombre.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyTyped(java.awt.event.KeyEvent evt) {
                txtNombreKeyTyped(evt);
            }
        });

        lblNombre.setFont(new java.awt.Font("Segoe UI", 0, 14)); // NOI18N
        lblNombre.setText("Nombre");

        txtPrecio.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyTyped(java.awt.event.KeyEvent evt) {
                txtPrecioKeyTyped(evt);
            }
        });

        lblPrecio.setFont(new java.awt.Font("Segoe UI", 0, 14)); // NOI18N
        lblPrecio.setText("Precio");

        lblCategoria.setFont(new java.awt.Font("Segoe UI", 0, 14)); // NOI18N
        lblCategoria.setText("Categoria");

        jPanel7.setBorder(javax.swing.BorderFactory.createEmptyBorder(10, 20, 10, 20));
        jPanel7.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        jPanel7.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                jPanel7MouseClicked(evt);
            }
            public void mouseEntered(java.awt.event.MouseEvent evt) {
                jPanel7MouseEntered(evt);
            }
            public void mouseExited(java.awt.event.MouseEvent evt) {
                jPanel7MouseExited(evt);
            }
        });

        javax.swing.GroupLayout jPanel7Layout = new javax.swing.GroupLayout(jPanel7);
        jPanel7.setLayout(jPanel7Layout);
        jPanel7Layout.setHorizontalGroup(
            jPanel7Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 65, Short.MAX_VALUE)
        );
        jPanel7Layout.setVerticalGroup(
            jPanel7Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 20, Short.MAX_VALUE)
        );

        lblListaProductos.setBackground(new java.awt.Color(255, 255, 255));
        lblListaProductos.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N
        lblListaProductos.setForeground(new java.awt.Color(255, 255, 255));
        lblListaProductos.setText("Lista de Productos");

        btnGuardar.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N
        btnGuardar.setForeground(new java.awt.Color(255, 255, 255));
        btnGuardar.setText("Guardar");
        btnGuardar.setBorderPainted(false);
        btnGuardar.setContentAreaFilled(false);
        btnGuardar.setCursor(new java.awt.Cursor(java.awt.Cursor.CROSSHAIR_CURSOR));
        btnGuardar.setFocusPainted(false);
        btnGuardar.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                btnGuardarMouseClicked(evt);
            }
            public void mouseEntered(java.awt.event.MouseEvent evt) {
                btnGuardarMouseEntered(evt);
            }
            public void mouseExited(java.awt.event.MouseEvent evt) {
                btnGuardarMouseExited(evt);
            }
        });
        btnGuardar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnGuardarActionPerformed(evt);
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

        btnLimpiar.setText("Limpiar");
        btnLimpiar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnLimpiarActionPerformed(evt);
            }
        });

        tblMostrar.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {},
                {},
                {},
                {}
            },
            new String [] {

            }
        ));
        tblMostrar.setAutoResizeMode(javax.swing.JTable.AUTO_RESIZE_ALL_COLUMNS);
        tblMostrar.setEnabled(false);
        tblMostrar.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                tblMostrarMouseClicked(evt);
            }
        });
        jScrollPane1.setViewportView(tblMostrar);

        lblDescripcion.setFont(new java.awt.Font("Segoe UI", 0, 14)); // NOI18N
        lblDescripcion.setText("Descripcion");

        javax.swing.GroupLayout jPanel2Layout = new javax.swing.GroupLayout(jPanel2);
        jPanel2.setLayout(jPanel2Layout);
        jPanel2Layout.setHorizontalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(lblListaProductos, javax.swing.GroupLayout.PREFERRED_SIZE, 222, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addGroup(jPanel2Layout.createSequentialGroup()
                        .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(lblNombre, javax.swing.GroupLayout.PREFERRED_SIZE, 62, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(lblPrecio, javax.swing.GroupLayout.PREFERRED_SIZE, 41, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(lblIdProducto))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(txtPrecio, javax.swing.GroupLayout.PREFERRED_SIZE, 102, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(txtNombre, javax.swing.GroupLayout.PREFERRED_SIZE, 189, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(txtIdProducto, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(lblLogo, javax.swing.GroupLayout.PREFERRED_SIZE, 207, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel2Layout.createSequentialGroup()
                        .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(jPanel2Layout.createSequentialGroup()
                                .addComponent(lblDescripcion, javax.swing.GroupLayout.PREFERRED_SIZE, 83, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGap(18, 18, 18)
                                .addComponent(txtDescripcion, javax.swing.GroupLayout.PREFERRED_SIZE, 328, javax.swing.GroupLayout.PREFERRED_SIZE))
                            .addGroup(jPanel2Layout.createSequentialGroup()
                                .addComponent(lblCategoria, javax.swing.GroupLayout.PREFERRED_SIZE, 83, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGap(18, 18, 18)
                                .addComponent(cmbCategorias, javax.swing.GroupLayout.PREFERRED_SIZE, 328, javax.swing.GroupLayout.PREFERRED_SIZE)))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                    .addGroup(jPanel2Layout.createSequentialGroup()
                        .addComponent(btnActualizar)
                        .addGap(26, 26, 26)
                        .addComponent(btnEliminar)
                        .addGap(18, 18, 18)
                        .addComponent(btnLimpiar)
                        .addGap(18, 18, 18)
                        .addComponent(btnGuardar)
                        .addGap(0, 0, Short.MAX_VALUE)))
                .addComponent(jPanel7, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap())
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addGap(12, 12, 12)
                .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 1006, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        jPanel2Layout.setVerticalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel2Layout.createSequentialGroup()
                        .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(jPanel2Layout.createSequentialGroup()
                                .addGap(12, 12, 12)
                                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                                    .addComponent(txtIdProducto, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addComponent(lblIdProducto))
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                                    .addComponent(lblNombre)
                                    .addComponent(txtNombre, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                                    .addComponent(lblPrecio, javax.swing.GroupLayout.PREFERRED_SIZE, 23, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addComponent(txtPrecio, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                                .addGap(0, 57, Short.MAX_VALUE))
                            .addComponent(lblLogo, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(lblListaProductos, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(btnLimpiar)
                            .addComponent(btnActualizar)
                            .addComponent(btnGuardar)
                            .addComponent(btnEliminar)))
                    .addGroup(jPanel2Layout.createSequentialGroup()
                        .addGap(14, 14, 14)
                        .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(txtDescripcion, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(lblDescripcion, javax.swing.GroupLayout.PREFERRED_SIZE, 29, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGap(18, 18, 18)
                        .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(cmbCategorias, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(lblCategoria, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))))
                .addGap(16, 16, 16)
                .addComponent(jPanel7, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 176, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(228, 228, 228))
        );

        btnCerrar.setText("Cerrar");
        btnCerrar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnCerrarActionPerformed(evt);
            }
        });

        lblProductos.setBackground(new java.awt.Color(204, 255, 255));
        lblProductos.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N
        lblProductos.setForeground(new java.awt.Color(255, 255, 255));
        lblProductos.setText("Productos");

        javax.swing.GroupLayout jPanel6Layout = new javax.swing.GroupLayout(jPanel6);
        jPanel6.setLayout(jPanel6Layout);
        jPanel6Layout.setHorizontalGroup(
            jPanel6Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel6Layout.createSequentialGroup()
                .addGap(14, 14, 14)
                .addGroup(jPanel6Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel6Layout.createSequentialGroup()
                        .addComponent(lblProductos, javax.swing.GroupLayout.PREFERRED_SIZE, 108, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(btnCerrar)
                        .addGap(36, 36, 36))
                    .addGroup(jPanel6Layout.createSequentialGroup()
                        .addComponent(jPanel2, javax.swing.GroupLayout.PREFERRED_SIZE, 1035, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addContainerGap(12, Short.MAX_VALUE))))
        );
        jPanel6Layout.setVerticalGroup(
            jPanel6Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel6Layout.createSequentialGroup()
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addGroup(jPanel6Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(btnCerrar)
                    .addComponent(lblProductos))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jPanel2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(20, 20, 20))
        );

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jPanel3, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addGap(20, 20, 20)
                        .addComponent(jPanel4, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addContainerGap()
                        .addComponent(jPanel6, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jPanel6, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(22, 22, 22)
                .addComponent(jPanel3, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jPanel4, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jPanel1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jPanel1, javax.swing.GroupLayout.PREFERRED_SIZE, 577, Short.MAX_VALUE)
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents


    private void btnGuardarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnGuardarActionPerformed
        Producto producto = new Producto();

        producto.setIdProducto(txtIdProducto.getText());
        producto.setNombre(txtNombre.getText());
        producto.setDescripcion(txtDescripcion.getText());
        producto.setPrecio(txtPrecio.getText());

        int index = cmbCategorias.getSelectedIndex();
        categoria = listaCategorias.get(index);

        producto.setCategoria(categoria);

        int resultado = dao.insertarProducto(producto);

        if (resultado > 0) {
            JOptionPane.showMessageDialog(null, "Producto guardado correctamente");
            listar();
            // actualizar el siguiente ID
            int nuevoId = dao.obtenerUltimoIdProducto();
            txtIdProducto.setText(String.format("%03d", nuevoId + 1));

        } else {
            JOptionPane.showMessageDialog(null, "Debe asegurarse que todos los campos esten llenos");
        }

        limpiarCampos();


    }//GEN-LAST:event_btnGuardarActionPerformed

    private void btnGuardarMouseEntered(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_btnGuardarMouseEntered

    }//GEN-LAST:event_btnGuardarMouseEntered

    private void btnGuardarMouseExited(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_btnGuardarMouseExited

    }//GEN-LAST:event_btnGuardarMouseExited

    private void btnGuardarMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_btnGuardarMouseClicked

    }//GEN-LAST:event_btnGuardarMouseClicked

    private void jPanel7MouseEntered(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jPanel7MouseEntered

    }//GEN-LAST:event_jPanel7MouseEntered

    private void jPanel7MouseExited(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jPanel7MouseExited

    }//GEN-LAST:event_jPanel7MouseExited

    private void jPanel7MouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jPanel7MouseClicked

    }//GEN-LAST:event_jPanel7MouseClicked

    private void txtNombreKeyTyped(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtNombreKeyTyped
        validacion.validarLetras(evt, txtNombre);
    }//GEN-LAST:event_txtNombreKeyTyped

    private void txtPrecioKeyTyped(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtPrecioKeyTyped
        String texto = txtPrecio.getText();
        char c = evt.getKeyChar();

        // Solo permite dígitos y punto
        if (!Character.isDigit(c) && c != '.') {
            evt.consume(); // Bloquea el carácter
            return;
        }

        // Solo un punto permitido
        if (c == '.' && texto.contains(".")) {
            evt.consume();
            return;
        }

        // Máximo 2 dígitos antes del punto
        if (!texto.contains(".") && Character.isDigit(c) && texto.length() >= 2) {
            evt.consume();
            return;
        }

        // Máximo 2 decimales después del punto
        if (texto.contains(".")) {
            int puntoIndex = texto.indexOf(".");
            if (texto.length() - puntoIndex > 2) {
                evt.consume();
                return;
            }
        }


    }//GEN-LAST:event_txtPrecioKeyTyped


    private void btnActualizarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnActualizarActionPerformed
        int fila = tblMostrar.getSelectedRow();
        if (fila == -1) {
            JOptionPane.showMessageDialog(null, "Seleccione un producto de la tabla para actualizar");
            return;
        }

        Producto producto = new Producto();
        producto.setIdProducto(txtIdProducto.getText());
        producto.setNombre(txtNombre.getText());
        producto.setDescripcion(txtDescripcion.getText());
        producto.setPrecio(txtPrecio.getText());

        int index = cmbCategorias.getSelectedIndex();
        categoria = listaCategorias.get(index);
        producto.setCategoria(categoria);

        int confirm = JOptionPane.showConfirmDialog(null,
                "¿Desea actualizar el producto?", "Confirmar", JOptionPane.YES_NO_OPTION);

        if (confirm == JOptionPane.YES_OPTION) {
            int resultado = dao.actualizarProducto(producto);
            if (resultado > 0) {
                JOptionPane.showMessageDialog(null, "Producto actualizado correctamente");
                listar();
                limpiarCampos();
            } else {
                JOptionPane.showMessageDialog(null, "Error al actualizar el producto");
            }
        }
    }//GEN-LAST:event_btnActualizarActionPerformed

    private void btnEliminarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnEliminarActionPerformed
        int fila = tblMostrar.getSelectedRow();
        if (fila == -1) {
            JOptionPane.showMessageDialog(null, "Seleccione un producto de la tabla para eliminar");
            return;
        }

        String idProducto = txtIdProducto.getText();

        int confirm = JOptionPane.showConfirmDialog(null,
                "¿Está seguro de eliminar el producto?", "Confirmar", JOptionPane.YES_NO_OPTION);

        if (confirm == JOptionPane.YES_OPTION) {
            int resultado = dao.eliminarProducto(idProducto);
            if (resultado > 0) {
                JOptionPane.showMessageDialog(null, "Producto eliminado correctamente");
                listar();
                limpiarCampos();
            } else {
                JOptionPane.showMessageDialog(null, "Error al eliminar el producto");
            }
        }
    }//GEN-LAST:event_btnEliminarActionPerformed

    private void btnLimpiarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnLimpiarActionPerformed
        limpiarCampos();
        tblMostrar.clearSelection();
    }//GEN-LAST:event_btnLimpiarActionPerformed

    private void btnCerrarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnCerrarActionPerformed
        this.dispose();
    }//GEN-LAST:event_btnCerrarActionPerformed

    private void tblMostrarMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_tblMostrarMouseClicked
        int fila = tblMostrar.getSelectedRow();
        if (fila == -1) {
            return;
        }

        txtIdProducto.setText(modeloTabla.getValueAt(fila, 0).toString());
        txtNombre.setText(modeloTabla.getValueAt(fila, 1).toString());
        txtDescripcion.setText(modeloTabla.getValueAt(fila, 2).toString());
        txtPrecio.setText(modeloTabla.getValueAt(fila, 3).toString());

        // Cargar la categoría correcta en el ComboBox
        String idCategoria = modeloTabla.getValueAt(fila, 4).toString();
        for (int i = 0; i < listaCategorias.size(); i++) {
            if (listaCategorias.get(i).getIdCategoria().equals(idCategoria)) {
                cmbCategorias.setSelectedIndex(i);
                break;
            }
        }
    }//GEN-LAST:event_tblMostrarMouseClicked
    private void limpiarCampos() {
        txtNombre.setText("");
        txtDescripcion.setText("");
        txtPrecio.setText("");
        cmbCategorias.setSelectedIndex(0);
        int nuevoId = dao.obtenerUltimoIdProducto();
        txtIdProducto.setText(String.format("%03d", nuevoId + 1));

    }

    public void configurarSegunRol(String rol) {
        // Si el rol es SUPERVISOR, desactivamos los botones de modificación
        if ("SUPERVISOR".equals(rol)) {

            btnGuardar.setEnabled(false);
            btnActualizar.setEnabled(false);
            btnEliminar.setEnabled(false);
            txtPrecio.setEnabled(false);
            txtNombre.setEnabled(false);
            txtDescripcion.setEnabled(false);
            cmbCategorias.setEnabled(false);

        }
    }

    private void ajustarColumnas() {
        for (int col = 0; col < tblMostrar.getColumnCount(); col++) {
            int maxAncho = 0;

            // Medir el encabezado
            javax.swing.table.TableColumn column = tblMostrar.getColumnModel().getColumn(col);
            java.awt.Component header = tblMostrar.getTableHeader()
                    .getDefaultRenderer()
                    .getTableCellRendererComponent(tblMostrar, column.getHeaderValue(), false, false, 0, col);
            maxAncho = header.getPreferredSize().width;

            // Medir cada celda
            for (int row = 0; row < tblMostrar.getRowCount(); row++) {
                java.awt.Component cell = tblMostrar.getDefaultRenderer(
                        tblMostrar.getColumnClass(col))
                        .getTableCellRendererComponent(tblMostrar,
                                tblMostrar.getValueAt(row, col), false, false, row, col);
                maxAncho = Math.max(maxAncho, cell.getPreferredSize().width);
            }

            column.setPreferredWidth(maxAncho + 20); // +20 de margen
        }
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
        java.awt.EventQueue.invokeLater(() -> new frmProducto().setVisible(true));
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton btnActualizar;
    private javax.swing.JButton btnCerrar;
    private javax.swing.JButton btnEliminar;
    private javax.swing.JButton btnGuardar;
    private javax.swing.JButton btnLimpiar;
    private javax.swing.JComboBox<String> cmbCategorias;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPanel jPanel2;
    private javax.swing.JPanel jPanel3;
    private javax.swing.JPanel jPanel4;
    private javax.swing.JPanel jPanel5;
    private javax.swing.JPanel jPanel6;
    private javax.swing.JPanel jPanel7;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JLabel lblCategoria;
    private javax.swing.JLabel lblDescripcion;
    private javax.swing.JLabel lblIdProducto;
    private javax.swing.JLabel lblImagen;
    private javax.swing.JLabel lblListaProductos;
    private javax.swing.JLabel lblLogo;
    private javax.swing.JLabel lblNombre;
    private javax.swing.JLabel lblPrecio;
    private javax.swing.JLabel lblProductos;
    private javax.swing.JTable tblMostrar;
    private javax.swing.JTextField txtDescripcion;
    private javax.swing.JTextField txtIdProducto;
    private javax.swing.JTextField txtNombre;
    private javax.swing.JTextField txtPrecio;
    // End of variables declaration//GEN-END:variables
}
