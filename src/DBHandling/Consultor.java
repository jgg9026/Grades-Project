/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package DBHandling;

import Models.Conexion;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JComboBox;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JTable;
import javax.swing.table.DefaultTableModel;

public class Consultor extends Conexion {
    
    //----------------------------------------------------------------------------------
    public boolean consultaCursoTotable(String a,JTable tlbEst, DefaultTableModel modelo){
        conectar();
        String sql = "select Materia, Grado from materias where Grado = '"+a+"';";
        String[] s = new String[2];
        try {
            Statement sent = conexion.createStatement();
            ResultSet rest = sent.executeQuery(sql);
            while(rest.next()){
                s[0] = rest.getString("Materia");
                s[1] = rest.getString("Grado");
                modelo.addRow(s);
                tlbEst.setModel(modelo);
            }
            return true;
        } catch (SQLException ex) {
            return false;
        } finally {
            desconectar();
        }
    }
    //---------------------------------------------------
    public String[] selectToUsers(String User, String Pass) throws SQLException{
        String[] s = new String[3];
        String sql = "SELECT u.nombres, u.apellidos, p.Descripcion "
                + "FROM usuarios u INNER JOIN perfiles p ON u.idPerfiles = p.idPerfiles "
                + "WHERE u.idUsuarios like '"+User+"';";
        try {
            conectar();
            Statement sent;
            sent = conexion.createStatement();
            ResultSet rest = sent.executeQuery(sql);
            while(rest.next()){
                s[0] = rest.getString("Nombres");
                s[1] = rest.getString("Apellidos");
                s[2] = rest.getString("Descripcion");
            }
            return s;
        } catch (SQLException ex) {
            ex.printStackTrace();
            conexion.rollback();
            return null;
        } finally {
            desconectar();
        }
    }
   
    public void selectToGrados(JTable tblGrad, DefaultTableModel modelo) throws SQLException{
        String[] s = new String[3];
        String sql = "select g.idGrados, c.Curso , count(e.idEstudiantes) " +
                     "from estudiantes e INNER JOIN cursos c ON e.idCursos = c.idCursos INNER JOIN grados g ON g.idGrados = c.idGrados INNER JOIN aniolectivos a ON a.idAnio = g.idAnio " +
                     "group by (e.idCursos)";
        try {
            conectar();
            Statement sent;
            sent = conexion.createStatement();
            ResultSet rest = sent.executeQuery(sql);
            while(rest.next()){
                s[0] = rest.getString("g.idGrados");
                s[1] = rest.getString("c.Curso");
                s[2] = rest.getString("count(e.idEstudiantes)");
                modelo.addRow(s);
                tblGrad.setModel(modelo);
            }
        } catch (SQLException ex) {
            ex.printStackTrace();
            conexion.rollback();
        } finally {
            desconectar();
        }        
    }
    public String selectToEstudiantesid(String document) throws SQLException{
        
        String sql = "select idEstudiantes from estudiantes where Documento = '"+document+"'";
        String t = null;
        try{
            conectar();
            Statement sent;
            sent = conexion.createStatement();
            ResultSet rest = sent.executeQuery(sql);
            while(rest.next()){
                t= rest.getString("idEstudiantes"); 
            }
            
            
        } catch (SQLException ex) {
            ex.printStackTrace();
            conexion.rollback();
        } 
       
        finally {
            desconectar();
        } 
        
        return t;
    }
    public void selectToGradosCount(JTable tblGrad, DefaultTableModel modelo, String grado, String anio ) throws SQLException{
        String[] t = new String [2];
        System.out.print("kakakakakkaka121212");
       // String sql = "Select * cursos where  
        String sql = "select count(idEstudiantes), Curso from estudiantes inner join cursos on estudiantes.idCursos = cursos.idCursos inner join grados on cursos.idGrados = grados.idGrados inner join aniolectivos on grados.idAnio = aniolectivos.idAnio where grados.Grado = "+grado+" and aniolectivos.idAnio = "+anio+" group by Curso;";
        try{
            conectar();
            Statement sent;
            sent = conexion.createStatement();
            ResultSet rest = sent.executeQuery(sql);
            if (!rest.isBeforeFirst() ) {    
                System.out.println("vacio");
                String sql2 = "select Curso from cursos inner join grados on cursos.idGrados = grados.idGrados inner join aniolectivos on grados.idAnio = aniolectivos.idAnio where aniolectivos.idAnio = "+anio+" and grados.Grado ="+grado;
                ResultSet rest2 = sent.executeQuery(sql2);
                System.out.print(rest2);
                while(rest2.next()){
                    t[0] = "0";
                    t[1] = rest2.getString("Curso");
                    modelo.addRow(t);
                    tblGrad.setModel(modelo);
                }
            }
            else
            {
               System.out.print(rest);
                while(rest.next()){
                    t[0] = rest.getString("count(idEstudiantes)");
                    t[1] = rest.getString("Curso");
                    modelo.addRow(t);
                    tblGrad.setModel(modelo);
                }
            }
        } catch (SQLException ex) {
            ex.printStackTrace();
            conexion.rollback();
        } finally {
            desconectar();
        }  
        
    }
    
    
    
    public void selecToAniobox(JComboBox anio)  throws SQLException{
        String sql = "SELECT idAnio FROM aniolectivos";
        try{
            conectar();
            Statement sent;
            sent = conexion.createStatement();
            ResultSet rest = sent.executeQuery(sql);
            while(rest.next()){
                anio.addItem(rest.getString("idAnio"));
                
            }
            } catch (SQLException ex) {
            ex.printStackTrace();
            conexion.rollback();
        } finally {
            desconectar();
        }  
        }
    public String selectToCurso(String curso, String grado, String anio) throws SQLException{
        String sql = "select idCursos from cursos join grados on cursos.idGrados = grados.idGrados where idAnio = "+anio+" and Curso= '"+curso+"' and Grado = '"+grado+"';";
        //String sql1 = "select count(idEstudiantes), Curso from estudiantes inner join cursos on estudiantes.idCursos = cursos.idCursos inner join grados on cursos.idGrados = grados.idGrados inner join aniolectivos on grados.idAnio = aniolectivos.idAnio where grados.Grado = "+grado+" and aniolectivos.idAnio = "+anio+" group by Curso;";
         String[] s = new String[3];
        try {
            conectar();
            Statement sent;
            sent = conexion.createStatement();
            ResultSet rest = sent.executeQuery(sql);
            while(rest.next()){
                //id =rest.getString("idCursos");
                s[0] = rest.getString("idCursos");
               
               
            }
            //System.out.print("el id es:");
            //System.out.print(s[0]);
           // System.out.print(s[1]);
            //s[0].toString();
            //d=s[0];
          
          //  System.out.print("------");
        } catch (SQLException ex) {
            ex.printStackTrace();
            conexion.rollback();
        } finally {
            desconectar();
        }
        return s[0];
    
    }
    public void selectToGradosAll(JComboBox Grad, String anio) throws SQLException{
        String sql = "SELECT Grado FROM grados where idAnio = '"+anio+"' ";
                  // + "ORDER BY Grado ASC;";
            // int cant=Grad.getItemCount();
        //System.out.print("--+--+--");
        // System.out.print(Grad.getItemCount());
         //   System.out.print("------");
        try {
            conectar();
            Statement sent;
            sent = conexion.createStatement();
            ResultSet rest = sent.executeQuery(sql);
            while(rest.next()){
                Grad.addItem(rest.getString("Grado"));
            }
           
            //System.out.print(Grad.getItemCount());
          //  System.out.print("------");
        } catch (SQLException ex) {
            ex.printStackTrace();
            conexion.rollback();
        } finally {
            desconectar();
        }        
    }
    
    public void selectToMaterias(JTable tblGrad, DefaultTableModel modelo, String LV_Anio, String LV_Grado) throws SQLException{
        String[] s = new String[1];
        String sql = "SELECT Materia FROM materias "
                   + "WHERE idGrados = (SELECT idGrados FROM grados WHERE Grado = '"+LV_Grado+"' AND idAnio = '"+LV_Anio+"');";
        try {
            conectar();
            Statement sent;
            sent = conexion.createStatement();
            ResultSet rest = sent.executeQuery(sql);
            while(rest.next()){
                s[0] = rest.getString("Materia");
                modelo.addRow(s);
                tblGrad.setModel(modelo);
            }
        } catch (SQLException ex) {
            ex.printStackTrace();
            conexion.rollback();
        } finally {
            desconectar();
        }
    }
    
    public void selectToEst(JTable tblGrad, DefaultTableModel modelo, String LV_Anio, String LV_Grado, String LV_Curso) throws SQLException{
        String[] s = new String[3];
        String sql = "SELECT e.Documento,e.Nombres, e.Apellidos " +
                     "FROM estudiantes e INNER JOIN cursos c ON e.idCursos = c.idCursos INNER JOIN grados g ON g.idGrados = c.idGrados INNER JOIN aniolectivos a ON a.idAnio = g.idAnio " +
                     "WHERE c.Curso = '"+LV_Curso+"' AND g.idGrados = '"+LV_Grado+"' and a.idAnio = '"+LV_Anio+"';";
        try {
            conectar();
            Statement sent;
            sent = conexion.createStatement();
            ResultSet rest = sent.executeQuery(sql);
            while(rest.next()){
                s[0] = rest.getString("e.Documento");
                s[1] = rest.getString("e.Nombres");
                s[2] = rest.getString("e.Apellidos");
                modelo.addRow(s);
                tblGrad.setModel(modelo);
            }
        } catch (SQLException ex) {
            ex.printStackTrace();
            conexion.rollback();
        } finally {
            desconectar();
        }
    }
    
    public void selectToUsuarios(JLabel txtNombre, JLabel txtApellidos, JLabel txtPerfil) throws SQLException{
        String[] s = new String[2]; 
        String sql = "SELECT u.Nombres, u.Apellidos, p.Descripcion " +
                     "FROM usuarios u INNER JOIN perfiles p ON u.idPerfiles = p.idPerfiles " +
                     "WHERE u.idUsuarios = 'Pascuas102'";
        try {
            conectar();
            Statement sent;
            sent = conexion.createStatement();
            ResultSet rest = sent.executeQuery(sql);
            while(rest.next()){
                s[0] = rest.getString("u.Nombres");
                s[1] = rest.getString("u.Apellidos");
                s[2] = rest.getString("p.Descripcion");
            }
        } catch (SQLException ex) {
            ex.printStackTrace();
            conexion.rollback();
        } finally {
            desconectar();
        }
    }

    private void puts(String[] t) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }
}
