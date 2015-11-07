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
import javax.swing.JTable;
import javax.swing.table.DefaultTableModel;

public class Consultor extends Conexion {
    
    public String[] selectToUsers(String User, String Pass) throws SQLException{
        String[] s = new String[3];
        String sql = "SELECT u.nombres, u.apellidos, p.Descripcion "
                + "FROM usuarios u INNER JOIN perfiles p ON u.idPerfiles = p.idPerfiles "
                + "WHERE u.idUsuarios like '"+User+"' AND u.Contraseña like '"+Pass+"';";
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
    
    public void selectToGradosAll(JComboBox Grad, String anio) throws SQLException{
        String sql = "SELECT Grado FROM grados where idAnio = '"+anio+"' "
                   + "ORDER BY Grado ASC;";
        try {
            conectar();
            Statement sent;
            sent = conexion.createStatement();
            ResultSet rest = sent.executeQuery(sql);
            while(rest.next()){
                Grad.addItem(rest.getString("Grado"));
            }
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
    
    public boolean selectToUsers(JLabel txtNombre, JLabel txtApellidos, JLabel txtPerfil, String User) throws SQLException{
        String[] s = new String[3];
        String sql = "SELECT u.Nombres, u.Apellidos, p.Descripcion " +
                     "FROM usuarios u INNER JOIN perfiles p ON u.idPerfiles = p.idPerfiles " +
                     "WHERE u.idUsuarios = '"+User+"';";
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
            if(s[0] != null){
                txtNombre.setText(s[0]);
                txtApellidos.setText(s[1]);
                txtPerfil.setText(s[2]);
                return true;
            }else {
                return false;
            }
        } catch (SQLException ex) {
            ex.printStackTrace();
            conexion.rollback();
            return false;
        } finally {
            desconectar();
        }
    }
    
    public String selectToUsers(String User) throws SQLException{
        String ret = "";
        String sql = "SELECT Estado FROM Usuarios WHERE idUsuarios like '"+User+"';";
        try {
            conectar();
            Statement sent;
            sent = conexion.createStatement();
            ResultSet rest = sent.executeQuery(sql);
            while(rest.next()){
                ret = rest.getString("Estado");
            }
            return ret;
        }catch (SQLException ex) {
            ex.printStackTrace();
            conexion.rollback();
            return null;
        } finally {
            desconectar();
        }        
    }
    
    public String[] selectToUsersDescription(String User) throws SQLException{
        String[] ret = new String[3];
        String sql = "SELECT u.Nombres, u.Apellidos, p.Descripcion FROM usuarios u INNER JOIN perfiles p ON u.idPerfiles = p.idPerfiles WHERE idUsuarios like '"+User+"';";
        try {
            
            conectar();
            Statement sent;
            sent = conexion.createStatement();
            ResultSet rest = sent.executeQuery(sql);
            while(rest.next()){
                ret[0] = rest.getString("u.Nombres");
                ret[1] = rest.getString("u.Apellidos");
                ret[2] = rest.getString("p.Descripcion");
            }
            return ret;
        }catch (SQLException ex) {
            ex.printStackTrace();
            conexion.rollback();
            return null;
        } finally {
            desconectar();
        }
    }
}
