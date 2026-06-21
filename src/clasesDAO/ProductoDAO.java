package clasesDAO;

import clases.Categoria;
import clases.Conexion;
import clases.Producto;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;
import java.sql.SQLException;
import javax.swing.JOptionPane;

public class ProductoDAO {

    public int insertarProducto(Producto producto) {

        int resultado = 0;

        producto.getCategoria();

        String sql = "INSERT INTO producto (idProducto, nombre, descripcion, precio, idCategoria) VALUES (?, ?, ?, ?, ?)";

        try (Connection con = Conexion.conectar(); PreparedStatement ps = con.prepareStatement(sql)) {

            ps.setString(1, producto.getIdProducto());
            ps.setString(2, producto.getNombre());
            ps.setString(3, producto.getDescripcion());
            ps.setString(4, producto.getPrecio());
            ps.setString(5, producto.getCategoria().getIdCategoria());

            resultado = ps.executeUpdate();

        } catch (Exception e) {
            System.out.println("Error: " + e.getMessage());
        }

        return resultado;
    }

    public int obtenerUltimoIdProducto() {
        int id = 0;
        // CAST para comparación numérica, y manejo de NULL cuando la tabla está vacía
        String sql = "SELECT MAX(CAST(idProducto AS UNSIGNED)) AS ultimoId FROM producto";

        try (Connection con = Conexion.conectar(); PreparedStatement ps = con.prepareStatement(sql); ResultSet rs = ps.executeQuery()) {

            if (rs.next()) {
                String val = rs.getString("ultimoId");
                if (val != null) {          // Si la tabla está vacía, MAX devuelve NULL
                    id = Integer.parseInt(val);
                }
            }

        } catch (Exception e) {
            e.printStackTrace();
        }

        return id;
    }

    public List<Producto> listar() {

        List<Producto> lista = new ArrayList<>();

        String sql = "SELECT p.idProducto, p.nombre, p.descripcion, p.precio, "
                + "c.idCategoria, c.categoria AS categoria "
                + "FROM producto p "
                + "INNER JOIN categoria c ON p.idCategoria = c.idCategoria";

        try (Connection con = Conexion.conectar(); PreparedStatement ps = con.prepareStatement(sql); ResultSet rs = ps.executeQuery()) {

            while (rs.next()) {

                Categoria categoria = new Categoria(
                        rs.getString("idCategoria"),
                        rs.getString("categoria")
                );

                Producto producto = new Producto(
                        rs.getString("idProducto"),
                        rs.getString("nombre"),
                        rs.getString("descripcion"),
                        rs.getString("precio"),
                        categoria
                );

                lista.add(producto);
                System.out.println(lista.size());
            }

        } catch (SQLException e) {
            JOptionPane.showMessageDialog(null, "Error al listar productos: " + e.getMessage());
        }

        return lista;
    }

    public int actualizarProducto(Producto producto) {
        int resultado = 0;
        String sql = "UPDATE producto SET nombre=?, descripcion=?, precio=?, idCategoria=? WHERE idProducto=?";
        try (Connection con = Conexion.conectar(); PreparedStatement ps = con.prepareStatement(sql)) {
            ps.setString(1, producto.getNombre());
            ps.setString(2, producto.getDescripcion());
            ps.setString(3, producto.getPrecio());
            ps.setString(4, producto.getCategoria().getIdCategoria());
            ps.setString(5, producto.getIdProducto());
            resultado = ps.executeUpdate();
        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, "Error al actualizar: " + e.getMessage());
        }
        return resultado;
    }

    public int eliminarProducto(String idProducto) {
        int resultado = 0;
        String sql = "DELETE FROM producto WHERE idProducto=?";
        try (Connection con = Conexion.conectar(); PreparedStatement ps = con.prepareStatement(sql)) {
            ps.setString(1, idProducto);
            resultado = ps.executeUpdate();
        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, "Error al eliminar: " + e.getMessage());
        }
        return resultado;
    }

    public List<Producto> listarPorCategoria(String categoria) {
        List<Producto> lista = new ArrayList<>();
        try {
            Connection cn = Conexion.conectar();
            String sql = "SELECT p.idProducto, p.nombre, p.descripcion, p.precio, "
                    + "p.idCategoria, c.categoria "
                    + "FROM producto p "
                    + "INNER JOIN categoria c ON p.idCategoria = c.idCategoria "
                    + "WHERE c.categoria = ?";
            PreparedStatement ps = cn.prepareStatement(sql);
            ps.setString(1, categoria);
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                Producto p = new Producto();
                p.setIdProducto(rs.getString("idProducto"));
                p.setNombre(rs.getString("nombre"));
                p.setDescripcion(rs.getString("descripcion"));
                p.setPrecio(rs.getString("precio"));
                Categoria cat = new Categoria();
                cat.setIdCategoria(rs.getString("idCategoria"));
                cat.setDescripcion(rs.getString("categoria"));
                p.setCategoria(cat);
                lista.add(p);
            }
            Conexion.desconectar();
        } catch (Exception e) {
            System.out.println("Error listarPorCategoria: " + e.getMessage());
        }
        return lista;
    }
}
