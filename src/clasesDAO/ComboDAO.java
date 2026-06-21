package clasesDAO;

import clases.Combo;
import clases.Conexion;
import clases.Detalle;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

public class ComboDAO {

    Conexion conexion = new Conexion();

    // ── Obtener último ID ────────────────────────────────────────
    public int obtenerUltimoIdCombo() {
        int ultimo = 0;
        try {
            Connection cn = Conexion.conectar();
            String sql = "SELECT MAX(CAST(idCombo AS UNSIGNED)) FROM combo";
            PreparedStatement ps = cn.prepareStatement(sql);
            ResultSet rs = ps.executeQuery();
            if (rs.next()) {
                ultimo = rs.getInt(1);
            }
            Conexion.desconectar();
        } catch (Exception e) {
            System.out.println("Error obtenerUltimoIdCombo: " + e.getMessage());
        }
        return ultimo;
    }

    // ── Listar todos los combos ──────────────────────────────────
    public List<Combo> listar() {
        List<Combo> lista = new ArrayList<>();
        try {
            Connection cn = Conexion.conectar();
            String sql = "SELECT idCombo, combo, precioCombo FROM combo ORDER BY idCombo";
            PreparedStatement ps = cn.prepareStatement(sql);
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                Combo c = new Combo();
                c.setIdCombo(rs.getString("idCombo"));
                c.setNombre(rs.getString("combo"));
                c.setPrecioCombo(rs.getDouble("precioCombo"));
                lista.add(c);
            }
            Conexion.desconectar();
        } catch (Exception e) {
            System.out.println("Error listar combos: " + e.getMessage());
        }
        return lista;
    }

    // ── Insertar combo + detalle ─────────────────────────────────
    public int insertarCombo(Combo combo) {
        int resultado = 0;
        Connection cn = null;
        try {
            cn = Conexion.conectar();
            cn.setAutoCommit(false);

            // 1. Insertar en combo
            String sqlCombo = "INSERT INTO combo (idCombo, combo, precioCombo) VALUES (?, ?, ?)";
            PreparedStatement psCombo = cn.prepareStatement(sqlCombo);
            psCombo.setString(1, combo.getIdCombo());
            psCombo.setString(2, combo.getNombre());
            psCombo.setDouble(3, combo.getPrecioCombo());
            resultado = psCombo.executeUpdate();

            // 2. Insertar cada fila del detalle
            String sqlDetalle = "INSERT INTO detalle (idCombo, idProducto, cantidad) VALUES (?, ?, ?)";
            PreparedStatement psDetalle = cn.prepareStatement(sqlDetalle);
            for (Detalle d : combo.getDetalle()) {
                psDetalle.setString(1, combo.getIdCombo());
                psDetalle.setString(2, d.getIdProducto());
                psDetalle.setInt(3, d.getCantidad());
                psDetalle.executeUpdate();
            }

            cn.commit();
        } catch (Exception e) {
            System.out.println("Error insertarCombo: " + e.getMessage());
            resultado = 0;
            try {
                if (cn != null) {
                    cn.rollback();
                }
            } catch (Exception ex) {
                ex.printStackTrace();
            }
        } finally {
            try {
                if (cn != null) {
                    cn.setAutoCommit(true);
                    cn.close();
                }
            } catch (Exception ex) {
                ex.printStackTrace();
            }
        }
        return resultado;
    }

    // ── Cargar detalle de un combo ───────────────────────────────
    public List<Detalle> cargarDetalle(String idCombo) {
        List<Detalle> lista = new ArrayList<>();
        try {
            Connection cn = Conexion.conectar();
            String sql = "SELECT d.idCombo, d.idProducto, p.nombre, d.cantidad, p.precio "
                    + "FROM detalle d "
                    + "INNER JOIN producto p ON d.idProducto = p.idProducto "
                    + "WHERE d.idCombo = ?";
            PreparedStatement ps = cn.prepareStatement(sql);
            ps.setString(1, idCombo);
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                Detalle det = new Detalle();
                det.setIdCombo(rs.getString("idCombo"));
                det.setIdProducto(rs.getString("idProducto"));
                det.setNombreProducto(rs.getString("nombre"));
                det.setCantidad(rs.getInt("cantidad"));
                det.setPrecioUnitario(rs.getDouble("precio"));
                det.setSubtotal(det.getCantidad() * det.getPrecioUnitario());
                lista.add(det);
            }
            Conexion.desconectar();
        } catch (Exception e) {
            System.out.println("Error cargarDetalle: " + e.getMessage());
        }
        return lista;
    }

    // ── Actualizar combo + detalle ───────────────────────────────
    public int actualizarCombo(Combo combo) {
        int resultado = 0;
        try {
            Connection cn = Conexion.conectar();
            cn.setAutoCommit(false);

            // 1. Actualizar encabezado
            String sqlCombo = "UPDATE combo SET combo=?, precioCombo=? WHERE idCombo=?";
            PreparedStatement psCombo = cn.prepareStatement(sqlCombo);
            psCombo.setString(1, combo.getNombre());
            psCombo.setDouble(2, combo.getPrecioCombo());
            psCombo.setString(3, combo.getIdCombo());
            resultado = psCombo.executeUpdate();

            // 2. Borrar detalle viejo y reinsertar
            String sqlDel = "DELETE FROM detalle WHERE idCombo=?";
            PreparedStatement psDel = cn.prepareStatement(sqlDel);
            psDel.setString(1, combo.getIdCombo());
            psDel.executeUpdate();

            String sqlDetalle = "INSERT INTO detalle (idCombo, idProducto, cantidad) VALUES (?, ?, ?)";
            PreparedStatement psDetalle = cn.prepareStatement(sqlDetalle);
            for (Detalle d : combo.getDetalle()) {
                psDetalle.setString(1, combo.getIdCombo());
                psDetalle.setString(2, d.getIdProducto());
                psDetalle.setInt(3, d.getCantidad());
                psDetalle.executeUpdate();
            }

            cn.commit();
            Conexion.desconectar();
        } catch (Exception e) {
            System.out.println("Error actualizarCombo: " + e.getMessage());
            resultado = 0;
        }
        return resultado;
    }

    // ── Eliminar combo (el detalle se borra por CASCADE) ─────────
    public int eliminarCombo(String idCombo) {
        int resultado = 0;
        try {
            Connection cn = Conexion.conectar();
            String sql = "DELETE FROM combo WHERE idCombo=?";
            PreparedStatement ps = cn.prepareStatement(sql);
            ps.setString(1, idCombo);
            resultado = ps.executeUpdate();
            Conexion.desconectar();
        } catch (Exception e) {
            System.out.println("Error eliminarCombo: " + e.getMessage());
        }
        return resultado;
    }

    // ── Buscar por ID o nombre ───────────────────────────────────
    public List<Combo> buscar(String criterio) {
        List<Combo> lista = new ArrayList<>();
        try {
            Connection cn = Conexion.conectar();
            String sql = "SELECT idCombo, combo, precioCombo FROM combo "
                    + "WHERE idCombo LIKE ? OR combo LIKE ?";
            PreparedStatement ps = cn.prepareStatement(sql);
            ps.setString(1, "%" + criterio + "%");
            ps.setString(2, "%" + criterio + "%");
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                Combo c = new Combo();
                c.setIdCombo(rs.getString("idCombo"));
                c.setNombre(rs.getString("combo"));
                c.setPrecioCombo(rs.getDouble("precioCombo"));
                lista.add(c);
            }
            Conexion.desconectar();
        } catch (Exception e) {
            System.out.println("Error buscarCombo: " + e.getMessage());
        }
        return lista;
    }

    public List<Combo> buscarPorProducto(String nombreProducto) {
        List<Combo> lista = new ArrayList<>();
        try {
            Connection cn = Conexion.conectar();
            String sql = "SELECT DISTINCT c.idCombo, c.combo, c.precioCombo "
                    + "FROM combo c "
                    + "INNER JOIN detalle d ON c.idCombo = d.idCombo "
                    + "INNER JOIN producto p ON d.idProducto = p.idProducto "
                    + "WHERE p.nombre LIKE ?";
            PreparedStatement ps = cn.prepareStatement(sql);
            ps.setString(1, "%" + nombreProducto + "%");
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                Combo c = new Combo();
                c.setIdCombo(rs.getString("idCombo"));
                c.setNombre(rs.getString("combo"));
                c.setPrecioCombo(rs.getDouble("precioCombo"));
                lista.add(c);
            }
            Conexion.desconectar();
        } catch (Exception e) {
            System.out.println("Error buscarPorProducto: " + e.getMessage());
        }
        return lista;
    }
}
