package clasesDAO;

import clases.Conexion;
import clases.Usuario;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

public class UsuarioDAO {
    // ── Obtener próximo ID de usuario (solo referencia visual) ────

    public int obtenerProximoIdUsuario() {
        int proximo = 1;
        try {
            Connection cn = Conexion.conectar();
            String sql = "SELECT AUTO_INCREMENT FROM information_schema.tables "
                    + "WHERE table_schema = 'negocio' AND table_name = 'usuario'";
            PreparedStatement ps = cn.prepareStatement(sql);
            ResultSet rs = ps.executeQuery();
            if (rs.next()) {
                proximo = rs.getInt(1);
            }
            Conexion.desconectar();
        } catch (Exception e) {
            System.out.println("Error obtenerProximoIdUsuario: " + e.getMessage());
        }
        return proximo;
    }

    // ── Login ────────────────────────────────────────────────────
    public Usuario login(String usuario, String contrasenia) {
        Usuario u = null;
        try {
            Connection cn = Conexion.conectar();
            String sql = "SELECT u.idUsuario, u.nombre, u.apellido, "
                    + "u.usuario, u.idRol, r.rol "
                    + "FROM usuario u "
                    + "INNER JOIN rol r ON u.idRol = r.idRol "
                    + "WHERE u.usuario = ? "
                    + "AND u.contrasenia = SHA2(?, 256)";
            PreparedStatement ps = cn.prepareStatement(sql);
            ps.setString(1, usuario);
            ps.setString(2, contrasenia);
            ResultSet rs = ps.executeQuery();
            if (rs.next()) {
                u = new Usuario();
                u.setIdUsuario(rs.getInt("idUsuario"));
                u.setNombre(rs.getString("nombre"));
                u.setApellido(rs.getString("apellido"));
                u.setUsuario(rs.getString("usuario"));
                u.setIdRol(rs.getInt("idRol"));
                u.setRol(rs.getString("rol"));
            }
            Conexion.desconectar();
        } catch (Exception e) {
            System.out.println("Error login: " + e.getMessage());
        }
        return u;
    }

    // ── Listar usuarios tipo MESERO únicamente ────────────────────
    // (el Administrador solo gestiona Meseros, no a sí mismo ni a otros roles)
    public List<Usuario> listarMeseros() {
        List<Usuario> lista = new ArrayList<>();
        try {
            Connection cn = Conexion.conectar();
            String sql = "SELECT u.idUsuario, u.nombre, u.apellido, u.usuario, "
                    + "u.idRol, r.rol "
                    + "FROM usuario u "
                    + "INNER JOIN rol r ON u.idRol = r.idRol "
                    + "WHERE r.rol = 'MESERO' "
                    + "ORDER BY u.idUsuario";
            PreparedStatement ps = cn.prepareStatement(sql);
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                Usuario u = new Usuario();
                u.setIdUsuario(rs.getInt("idUsuario"));
                u.setNombre(rs.getString("nombre"));
                u.setApellido(rs.getString("apellido"));
                u.setUsuario(rs.getString("usuario"));
                u.setIdRol(rs.getInt("idRol"));
                u.setRol(rs.getString("rol"));
                lista.add(u);
            }
            Conexion.desconectar();
        } catch (Exception e) {
            System.out.println("Error listarMeseros: " + e.getMessage());
        }
        return lista;
    }

    // ── Obtener el idRol correspondiente a 'MESERO' ───────────────
    public int obtenerIdRolMesero() {
        int idRol = 0;
        try {
            Connection cn = Conexion.conectar();
            String sql = "SELECT idRol FROM rol WHERE rol = 'MESERO'";
            PreparedStatement ps = cn.prepareStatement(sql);
            ResultSet rs = ps.executeQuery();
            if (rs.next()) {
                idRol = rs.getInt("idRol");
            }
            Conexion.desconectar();
        } catch (Exception e) {
            System.out.println("Error obtenerIdRolMesero: " + e.getMessage());
        }
        return idRol;
    }

    // ── Verificar si un nombre de usuario ya existe ───────────────
    public boolean existeUsuario(String usuario) {
        boolean existe = false;
        try {
            Connection cn = Conexion.conectar();
            String sql = "SELECT idUsuario FROM usuario WHERE usuario = ?";
            PreparedStatement ps = cn.prepareStatement(sql);
            ps.setString(1, usuario);
            ResultSet rs = ps.executeQuery();
            existe = rs.next();
            Conexion.desconectar();
        } catch (Exception e) {
            System.out.println("Error existeUsuario: " + e.getMessage());
        }
        return existe;
    }

    // ── Insertar usuario (contraseña se guarda con SHA2-256) ──────
    public boolean insertar(Usuario u, String contraseniaPlana) {
        boolean exito = false;
        try {
            Connection cn = Conexion.conectar();
            String sql = "INSERT INTO usuario (nombre, apellido, usuario, contrasenia, idRol) "
                    + "VALUES (?, ?, ?, SHA2(?, 256), ?)";
            PreparedStatement ps = cn.prepareStatement(sql);
            ps.setString(1, u.getNombre());
            ps.setString(2, u.getApellido());
            ps.setString(3, u.getUsuario());
            ps.setString(4, contraseniaPlana);
            ps.setInt(5, u.getIdRol());
            exito = ps.executeUpdate() > 0;
            Conexion.desconectar();
        } catch (Exception e) {
            System.out.println("Error insertar usuario: " + e.getMessage());
        }
        return exito;
    }

    // ── Actualizar usuario (sin cambiar contraseña) ───────────────
    public boolean actualizar(Usuario u) {
        boolean exito = false;
        try {
            Connection cn = Conexion.conectar();
            String sql = "UPDATE usuario SET nombre=?, apellido=?, usuario=?, idRol=? "
                    + "WHERE idUsuario=?";
            PreparedStatement ps = cn.prepareStatement(sql);
            ps.setString(1, u.getNombre());
            ps.setString(2, u.getApellido());
            ps.setString(3, u.getUsuario());
            ps.setInt(4, u.getIdRol());
            ps.setInt(5, u.getIdUsuario());
            exito = ps.executeUpdate() > 0;
            Conexion.desconectar();
        } catch (Exception e) {
            System.out.println("Error actualizar usuario: " + e.getMessage());
        }
        return exito;
    }

    // ── Actualizar usuario y contraseña ────────────────────────────
    public boolean actualizarConContrasenia(Usuario u, String contraseniaPlana) {
        boolean exito = false;
        try {
            Connection cn = Conexion.conectar();
            String sql = "UPDATE usuario SET nombre=?, apellido=?, usuario=?, "
                    + "idRol=?, contrasenia=SHA2(?, 256) WHERE idUsuario=?";
            PreparedStatement ps = cn.prepareStatement(sql);
            ps.setString(1, u.getNombre());
            ps.setString(2, u.getApellido());
            ps.setString(3, u.getUsuario());
            ps.setInt(4, u.getIdRol());
            ps.setString(5, contraseniaPlana);
            ps.setInt(6, u.getIdUsuario());
            exito = ps.executeUpdate() > 0;
            Conexion.desconectar();
        } catch (Exception e) {
            System.out.println("Error actualizarConContrasenia: " + e.getMessage());
        }
        return exito;
    }

    // ── Eliminar usuario ───────────────────────────────────────────
    // Nota: si el usuario ya tiene órdenes registradas, la FK de
    // 'orden' impedirá borrarlo (orden_ibfk_1 referencia idUsuario).
    public boolean eliminar(int idUsuario) {
        boolean exito = false;
        try {
            Connection cn = Conexion.conectar();
            String sql = "DELETE FROM usuario WHERE idUsuario=?";
            PreparedStatement ps = cn.prepareStatement(sql);
            ps.setInt(1, idUsuario);
            exito = ps.executeUpdate() > 0;
            Conexion.desconectar();
        } catch (Exception e) {
            System.out.println("Error eliminar usuario: " + e.getMessage());
        }
        return exito;
    }
}
