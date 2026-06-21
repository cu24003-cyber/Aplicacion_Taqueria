package clasesDAO;

import java.sql.Connection;
import clases.Categoria;
import clases.Conexion;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;
import java.sql.SQLException;

public class CategoriaDAO {

    public List<Categoria> listarCategoria;

   String sql = "SELECT idCategoria, categoria FROM categoria ORDER BY categoria";

    public List<Categoria> listarCategorias() {

        List<Categoria> lista = new ArrayList<>();

        try (Connection cn = Conexion.conectar(); PreparedStatement ps = cn.prepareStatement(sql); ResultSet rs = ps.executeQuery()) {
            while (rs.next()) {

                Categoria c = new Categoria();

                c.setIdCategoria(rs.getString("idCategoria"));
                c.setDescripcion(rs.getString("categoria"));

                lista.add(c);
               
            }

        } catch (Exception e) {
            e.printStackTrace();
        }

        return lista;
    }

}
