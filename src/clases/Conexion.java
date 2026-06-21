package clases;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import javax.swing.JOptionPane;

public class Conexion {

    private static final String URL = "jdbc:mariadb://localhost:3306/negocio";
    private static final String USER = "root";
    private static final String PASSWORD = "";

    // Cada llamada devuelve una conexión NUEVA e independiente
    public static Connection conectar() {
        try {
            Class.forName("org.mariadb.jdbc.Driver");
            return DriverManager.getConnection(URL, USER, PASSWORD);
        } catch (ClassNotFoundException e) {
            JOptionPane.showMessageDialog(null, "Error: No se encontró el driver de MariaDB.");
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(null, "Error al conectar: " + e.getMessage());
        }
        return null;
    }

    // Se mantiene por compatibilidad pero ya no hace falta llamarla
    // porque cada DAO cierra su propia conexión con cn.close()
    public static void desconectar() {
        // No-op: cada DAO maneja su propia conexión
    }

}
