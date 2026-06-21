package CU24003;

import Formularios.frmLogin;
import clases.Conexion;
import java.sql.Connection;

public class CU24003 {

    public static void main(String[] args) {
        
        Connection con = Conexion.conectar();
        
        if(con != null){
            System.out.println("Conexion exitosa a la base de datos");
            
        }else{
            System.out.println("Error en la conexion");
        }
        new frmLogin().setVisible(true);
        
    }
    
}