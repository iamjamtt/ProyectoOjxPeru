package Conexion;

import java.sql.Connection;
import java.sql.DriverManager;
import javax.swing.JOptionPane;

public class ConexionSQL {
    public static Connection conexionn() {
        Connection conect = null;
        try {
            String controlador = "com.microsoft.sqlserver.jdbc.SQLServerDriver";
            String driver = "jdbc:sqlserver://localhost:1433;databaseName=OjxPeru";
            String user = "sa";
            String pass = "123";
            try {
                Class.forName(controlador);
                conect = DriverManager.getConnection(driver, user, pass);
                if(conect!=null){
                    System.out.println("Conexion Ok");
                }
            } catch (Exception e) {
            System.out.println("Error Conexion: "+e.getMessage());
            }  
        } catch (Exception e) {
            JOptionPane.showMessageDialog(null,"Error Error "+e);
        }
        return conect;
    }
}
