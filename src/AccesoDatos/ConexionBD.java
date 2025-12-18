
package AccesoDatos;

import javax.swing.JOptionPane;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
        
public class ConexionBD {
    private  static final String DRIVER = "com.mysql.cj.jdbc.Driver";
    private  static final String USER = "root";
    private  static final String CLAVE = "123456789";        
    private  static final String URL = "jdbc:mysql://localhost:3306/equipo";
    
    private Connection cnn;
    
    public ConexionBD(){
        cnn = null;
    }
        
    public Connection Conectar() {
        try{
            Class.forName(DRIVER);
            cnn = (Connection)DriverManager.getConnection(URL,USER,CLAVE);
            
        }catch(Exception e){
            System.out.print("Error" + e.getMessage());
            System.exit(0);
        }
        return cnn;
    }

    public void cerrar_conexion(){
        try{
            cnn.close();
        }catch(SQLException ex){
            JOptionPane.showMessageDialog(null, ex.getMessage(),
            "Error al cerrar la conexion",JOptionPane.INFORMATION_MESSAGE);
        }
    }
}
