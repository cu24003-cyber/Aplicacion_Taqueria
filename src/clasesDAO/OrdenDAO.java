package clasesDAO;

import clases.Conexion;
import clases.DetalleOrden;
import clases.Orden;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

public class OrdenDAO {

    // ── Obtener último ID de orden ───────────────────────────────
    public int obtenerUltimoIdOrden() {
        int ultimo = 0;
        try {
            Connection cn = Conexion.conectar();
            String sql = "SELECT AUTO_INCREMENT FROM information_schema.tables "
                    + "WHERE table_schema = 'negocio' AND table_name = 'orden'";
            PreparedStatement ps = cn.prepareStatement(sql);
            ResultSet rs = ps.executeQuery();
            if (rs.next()) {
                ultimo = rs.getInt(1) - 1;
            }
            Conexion.desconectar();
        } catch (Exception e) {
            System.out.println("Error obtenerUltimoIdOrden: " + e.getMessage());
        }
        return ultimo;
    }

    // ── Listar órdenes del día actual ────────────────────────────
    public List<Orden> listar() {
        List<Orden> lista = new ArrayList<>();
        try {
            Connection cn = Conexion.conectar();
            String sql = "SELECT o.idOrden, o.fechaHora, o.total, o.estado, "
                    + "u.nombre, u.apellido "
                    + "FROM orden o "
                    + "INNER JOIN usuario u ON o.idUsuario = u.idUsuario "
                    + "WHERE DATE(o.fechaHora) = CURDATE() "
                    + "ORDER BY o.idOrden DESC";
            PreparedStatement ps = cn.prepareStatement(sql);
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                Orden ord = new Orden();
                ord.setIdOrden(rs.getInt("idOrden"));
                ord.setFechaHora(rs.getString("fechaHora"));
                ord.setTotal(rs.getDouble("total"));
                ord.setEstado(rs.getString("estado"));
                ord.setNombreUsuario(rs.getString("nombre") + " " + rs.getString("apellido"));
                lista.add(ord);
            }
            Conexion.desconectar();
        } catch (Exception e) {
            System.out.println("Error listar ordenes: " + e.getMessage());
        }
        return lista;
    }

    // ── Insertar orden + detalle (estado inicial: PROCESADA) ─────
    public int insertarOrden(Orden orden) {
        int idGenerado = 0;
        try {
            Connection cn = Conexion.conectar();
            cn.setAutoCommit(false);

            // 1. Insertar encabezado
            String sqlOrden = "INSERT INTO orden (idUsuario, total, estado) VALUES (?, ?, 'PROCESADA')";
            PreparedStatement ps = cn.prepareStatement(sqlOrden,
                    PreparedStatement.RETURN_GENERATED_KEYS);
            ps.setInt(1, orden.getIdUsuario());
            ps.setDouble(2, orden.getTotal());
            ps.executeUpdate();

            // Obtener ID generado
            ResultSet rs = ps.getGeneratedKeys();
            if (rs.next()) {
                idGenerado = rs.getInt(1);
            }

            // 2. Insertar detalle
            String sqlDetalle = "INSERT INTO detalleorden "
                    + "(idOrden, idLinea, idProducto, idCombo, cantidad, precioUnitario) "
                    + "VALUES (?, ?, ?, ?, ?, ?)";
            PreparedStatement psD = cn.prepareStatement(sqlDetalle);
            int linea = 1;
            for (DetalleOrden d : orden.getDetalle()) {
                psD.setInt(1, idGenerado);
                psD.setInt(2, linea++);
                if (d.getIdProducto() > 0) {
                    psD.setInt(3, d.getIdProducto());
                    psD.setNull(4, java.sql.Types.INTEGER);
                } else {
                    psD.setNull(3, java.sql.Types.INTEGER);
                    psD.setInt(4, d.getIdCombo());
                }
                psD.setInt(5, d.getCantidad());
                psD.setDouble(6, d.getPrecioUnitario());
                psD.executeUpdate();
            }

            cn.commit();
            Conexion.desconectar();
        } catch (Exception e) {
            System.out.println("Error insertarOrden: " + e.getMessage());
            idGenerado = 0;
        }
        return idGenerado;
    }

    // ── Cargar detalle de una orden ──────────────────────────────
    public List<DetalleOrden> cargarDetalle(int idOrden) {
        List<DetalleOrden> lista = new ArrayList<>();
        try {
            Connection cn = Conexion.conectar();
            String sql = "SELECT d.idLinea, d.idProducto, d.idCombo, "
                    + "CASE WHEN d.idProducto IS NOT NULL THEN p.nombre "
                    + "     ELSE c.combo END AS nombre, "
                    + "d.cantidad, d.precioUnitario, "
                    + "(d.cantidad * d.precioUnitario) AS subtotal "
                    + "FROM detalleorden d "
                    + "LEFT JOIN producto p ON d.idProducto = p.idProducto "
                    + "LEFT JOIN combo c ON d.idCombo = c.idCombo "
                    + "WHERE d.idOrden = ? "
                    + "ORDER BY d.idLinea";
            PreparedStatement ps = cn.prepareStatement(sql);
            ps.setInt(1, idOrden);
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                DetalleOrden d = new DetalleOrden();
                d.setIdLinea(rs.getInt("idLinea"));
                d.setIdProducto(rs.getInt("idProducto"));
                d.setIdCombo(rs.getInt("idCombo"));
                d.setNombre(rs.getString("nombre"));
                d.setCantidad(rs.getInt("cantidad"));
                d.setPrecioUnitario(rs.getDouble("precioUnitario"));
                d.setSubtotal(rs.getDouble("subtotal"));
                lista.add(d);
            }
            Conexion.desconectar();
        } catch (Exception e) {
            System.out.println("Error cargarDetalle orden: " + e.getMessage());
        }
        return lista;
    }

    // ── Despachar orden (cualquier Mesero o Administrador) ───────
    // Solo se permite si la orden está actualmente PROCESADA.
    public boolean despacharOrden(int idOrden) {
        boolean exito = false;
        try {
            Connection cn = Conexion.conectar();
            String sql = "UPDATE orden SET estado='DESPACHADA' "
                    + "WHERE idOrden=? AND estado='PROCESADA'";
            PreparedStatement ps = cn.prepareStatement(sql);
            ps.setInt(1, idOrden);
            exito = ps.executeUpdate() > 0;
            Conexion.desconectar();
        } catch (Exception e) {
            System.out.println("Error despacharOrden: " + e.getMessage());
        }
        return exito;
    }

    // ── Anular orden (solo Administrador, solo el mismo día) ─────
    // La validación de rol se hace en el formulario; aquí se refuerza
    // la regla de fecha actual y que la orden no esté ya Anulada.
    public boolean anularOrden(int idOrden) {
        boolean exito = false;
        try {
            Connection cn = Conexion.conectar();
            String sql = "UPDATE orden SET estado='ANULADA' "
                    + "WHERE idOrden=? AND estado != 'ANULADA' "
                    + "AND DATE(fechaHora) = CURDATE()";
            PreparedStatement ps = cn.prepareStatement(sql);
            ps.setInt(1, idOrden);
            exito = ps.executeUpdate() > 0;
            Conexion.desconectar();
        } catch (Exception e) {
            System.out.println("Error anularOrden: " + e.getMessage());
        }
        return exito;
    }

    // ── Listar órdenes del día actual de un usuario específico ───
    public List<Orden> listarPorUsuario(int idUsuario) {
        List<Orden> lista = new ArrayList<>();
        try {
            Connection cn = Conexion.conectar();
            String sql = "SELECT o.idOrden, o.fechaHora, o.total, o.estado, "
                    + "u.nombre, u.apellido "
                    + "FROM orden o "
                    + "INNER JOIN usuario u ON o.idUsuario = u.idUsuario "
                    + "WHERE DATE(o.fechaHora) = CURDATE() "
                    + "AND o.idUsuario = ? "
                    + "ORDER BY o.idOrden DESC";
            PreparedStatement ps = cn.prepareStatement(sql);
            ps.setInt(1, idUsuario);
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                Orden ord = new Orden();
                ord.setIdOrden(rs.getInt("idOrden"));
                ord.setFechaHora(rs.getString("fechaHora"));
                ord.setTotal(rs.getDouble("total"));
                ord.setEstado(rs.getString("estado"));
                ord.setNombreUsuario(rs.getString("nombre") + " " + rs.getString("apellido"));
                lista.add(ord);
            }
            Conexion.desconectar();
        } catch (Exception e) {
            System.out.println("Error listarPorUsuario: " + e.getMessage());
        }
        return lista;
    }

    // ── Listar histórico completo (todas las fechas) ─────────────
    public List<Orden> listarTodas() {
        List<Orden> lista = new ArrayList<>();
        try {
            Connection cn = Conexion.conectar();
            String sql = "SELECT o.idOrden, o.fechaHora, o.total, o.estado, "
                    + "u.nombre, u.apellido "
                    + "FROM orden o "
                    + "INNER JOIN usuario u ON o.idUsuario = u.idUsuario "
                    + "ORDER BY o.idOrden DESC";
            PreparedStatement ps = cn.prepareStatement(sql);
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                Orden ord = new Orden();
                ord.setIdOrden(rs.getInt("idOrden"));
                ord.setFechaHora(rs.getString("fechaHora"));
                ord.setTotal(rs.getDouble("total"));
                ord.setEstado(rs.getString("estado"));
                ord.setNombreUsuario(rs.getString("nombre") + " " + rs.getString("apellido"));
                lista.add(ord);
            }
            Conexion.desconectar();
        } catch (Exception e) {
            System.out.println("Error listarTodas: " + e.getMessage());
        }
        return lista;
    }
}
