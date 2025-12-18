
package Clases;

import AccesoDatos.ConexionBD;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import javax.swing.JOptionPane;
import javax.swing.table.DefaultTableModel;

public class Computadora {
    private int idcomputadora;
    private String procesador;
    private int memoriaram;
    private String tipodiscoduro;
    private int capacidaddiscoduro;
    private String marcaplacamadre;
    private String modeloplacamadre;
    private int precio;

    public int getIdcomputadora() {
        return idcomputadora;
    }

    public void setIdcomputadora(int idcomputadora) {
        this.idcomputadora = idcomputadora;
    }

    public String getProcesador() {
        return procesador;
    }

    public void setProcesador(String procesador) {
        this.procesador = procesador;
    }

    public int getMemoriaram() {
        return memoriaram;
    }

    public void setMemoriaram(int memoriaram) {
        this.memoriaram = memoriaram;
    }

    public String getTipodiscoduro() {
        return tipodiscoduro;
    }

    public void setTipodiscoduro(String tipodiscoduro) {
        this.tipodiscoduro = tipodiscoduro;
    }

    public int getCapacidaddiscoduro() {
        return capacidaddiscoduro;
    }

    public void setCapacidaddiscoduro(int capacidaddiscoduro) {
        this.capacidaddiscoduro = capacidaddiscoduro;
    }

    public String getMarcaplacamadre() {
        return marcaplacamadre;
    }

    public void setMarcaplacamadre(String marcaplacamadre) {
        this.marcaplacamadre = marcaplacamadre;
    }

    public String getModeloplacamadre() {
        return modeloplacamadre;
    }

    public void setModeloplacamadre(String modeloplacamadre) {
        this.modeloplacamadre = modeloplacamadre;
    }

    public int getPrecio() {
        return precio;
    }

    public void setPrecio(int precio) {
        this.precio = precio;
    }
    
    
    
    
    //objetos necesarios para insertar
    private final ConexionBD cnn;
    private PreparedStatement sentencia;
    private final String SQL_INSERT = "INSERT INTO computadora"
    + "(idcomputadora,procesador,memoriaram,tipodiscoduro,capacidaddiscoduro,marcaplacamadre,modeloplacamadre,precio) VALUES(?,?,?,?,?,?,?,?)";
    
    public Computadora(){
        sentencia = null;
        cnn = new ConexionBD();
    }
    
    public int InsertarDatos(int idcomp, String proc, int memori, String tipodis, int capc, String marc, String modelop, int perc){
        int resp = 0;
        try {
            sentencia = cnn.Conectar().prepareStatement(SQL_INSERT);
            sentencia.setInt(1,idcomp);
            sentencia.setString(2,proc);
            sentencia.setInt(3, memori);
            sentencia.setString(4, tipodis);
            sentencia.setInt(5, capc);
            sentencia.setString(6, marc);
            sentencia.setString(7, modelop);
            sentencia.setInt(8, perc);
            resp = sentencia.executeUpdate();
            
            if(resp>0)
                JOptionPane.showMessageDialog(null, "Registro guardado");
            
            
        }catch (SQLException e){
            System.err.println("Error al grabar en la BD" + e.getMessage());
        }
        finally{
            sentencia = null;
            cnn.cerrar_conexion();
        }
        
        return resp;
    }
    
    //crear objetos necesarios para listar
    //objetos necesarios para listar al cargar el formulario
    private final String SQL_SELECT = "SELECT * FROM computadora";
    private DefaultTableModel modelo;
    private ResultSet registros;
    
    private DefaultTableModel setTitulos(){
        modelo = new DefaultTableModel();
        modelo.addColumn("ID Computadora");
        modelo.addColumn("Procesador");
        modelo.addColumn("Memoria Ram");
        modelo.addColumn("Tip. Disco Duro");
        modelo.addColumn("Cap. Disco Duro");
        modelo.addColumn("Marc. P. Madre");
        modelo.addColumn("Mod. P. Madre");
        modelo.addColumn("S/ Precio");
        return modelo;
    }
    
    public DefaultTableModel getDatos(){
        try{
            setTitulos();
            sentencia = cnn.Conectar().prepareStatement(SQL_SELECT);
            registros = sentencia.executeQuery();
            Object[] fila = new Object[8];
            while(registros.next()){
                fila[0] = registros.getInt(1);
                fila[1] = registros.getString(2);
                fila[2] = registros.getInt(3);
                fila[3] = registros.getString(4);
                fila[4] = registros.getInt(5);
                fila[5] = registros.getString(6);
                fila[6] = registros.getString(7);
                fila[7] = registros.getInt(8);
                modelo.addRow(fila);
            }
        }
        catch(SQLException e){
            System.err.println("Error al listar los datos" + e.getMessage());
        }
        finally{
            sentencia = null;
            registros = null;
            cnn.cerrar_conexion();
        }
        return modelo;
    }
    
    //OBJETOS NECESARIOS PARA ACTUALIZAR
    public int ActualizarDatos(int idcomp, String proc, int memori, String tipodis, int capc, String marc, String modelop, int perc){
        String SQL_UPDATE = "UPDATE computadora SET procesador = '" + proc + "',"
                + "memoriaram = " + memori + ", tipodiscoduro = '" + tipodis + "', capacidaddiscoduro = " + capc +
                ", marcaplacamadre = '" + marc + "', modeloplacamadre = '" + modelop + "', precio = " + perc +
                " WHERE " + "idcomputadora = " + idcomp;
        int resp = 0;
        
        try{
            sentencia = cnn.Conectar().prepareStatement(SQL_UPDATE);
            resp = sentencia.executeUpdate();//
            if(resp>0){
                JOptionPane.showMessageDialog(null, "Registro actualizado");
            }
                
        }
        catch(SQLException e) {
            System.err.println("Error al modificar los datos" + e.getMessage());
        }
        finally{
            sentencia = null;
            cnn.cerrar_conexion();
        }      
        return resp;
    }
    
    //objetos necesarios para la eliminación
    public void EliminarDatos(String idcomp){
        String SQL_BORRAR = "DELETE from computadora WHERE idcomputadora = " + idcomp;
        int resp = 0;
        
        try{
            sentencia = cnn.Conectar().prepareStatement(SQL_BORRAR);
            resp = sentencia.executeUpdate();
            if(resp>0){
                JOptionPane.showMessageDialog(null, "Registro borrado");
            }
        }
        catch(SQLException e) {
            System.err.println("Error al eliminar los datos" + e.getMessage());
        }
        finally{
            sentencia = null;
            cnn.cerrar_conexion();
        }
    }
    
    //objetos necesarios para buscar
    public DefaultTableModel getBuscarDatos(int criterio, String desc){
        String SQL_BUSCAR;
        if(criterio == 0)
            SQL_BUSCAR = "Select * from computadora WHERE idcomputadora = " + desc;
        else
            SQL_BUSCAR = "Select * from computadora WHERE procesador LIKE '%" + desc + "%'";
        
        try{
            setTitulos();
            sentencia = cnn.Conectar().prepareStatement(SQL_BUSCAR);
            registros = sentencia.executeQuery();
            Object[] fila = new Object[8];
            while(registros.next()){
                fila[0] = registros.getInt(1);
                fila[1] = registros.getString(2);
                fila[2] = registros.getInt(3);
                fila[3] = registros.getString(4);
                fila[4] = registros.getInt(5);
                fila[5] = registros.getString(6);
                fila[6] = registros.getString(7);
                fila[7] = registros.getInt(8);
                modelo.addRow(fila);
            }
        }
        catch(SQLException e) {
            System.err.println("Error al buscar los datos" + e.getMessage());
        }
        finally{
            sentencia = null;
            registros = null;
            cnn.cerrar_conexion();
        }
        return modelo;
    }
    
}
