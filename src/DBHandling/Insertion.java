/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package DBHandling;

import Models.Conexion;
import java.io.FileNotFoundException;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

public class Insertion extends Conexion{
    
    public boolean insertToAnioLectivo(String LV_anio) throws SQLException{
        try{
            conectar();
            String sql = "INSERT INTO AnioLectivo(idAnio) VALUES(?)";
            PreparedStatement sent = conexion.prepareStatement(sql);
            sent.setString(1, LV_anio);
            int rest = sent.executeUpdate();
            if(rest > 0){
                conexion.commit();
                return true;
            } else {
                return false;
            }
        } catch(Exception e) {
            conexion.rollback();
            e.printStackTrace();
            return false;
        } finally {
            desconectar();
        }
    }
    //---------------------------------------------
    
    public boolean insertToAcudientes(String nombre, String apellidos, String documento, String telefono, String direccion, String idest) throws SQLException{
        try{
            conectar();
            String sql = "INSERT INTO acudientes (Nombres, Apellidos, Documento, Telefono, Direccion, idEstudiantes) VALUES (?,?,?,?,?,?)";
            PreparedStatement sent = conexion.prepareStatement(sql);
            sent.setString(1, nombre);
            sent.setString(2, apellidos);
            sent.setString(3, documento);
            sent.setString(4, telefono);
            sent.setString(5, direccion);
            sent.setString(6, idest);
 
            int rest1 = sent.executeUpdate();
            if(rest1 > 0){
                conexion.commit();
                return true;
            } else {
                conexion.rollback();
                return false;
            }
        }
        catch(Exception e) {
            conexion.rollback();
            e.printStackTrace();
            return false;
        } finally {
            desconectar();
        }
    
    }
    
    
    
    //--------------------------------------------
    
    public boolean insertToEstudiantes (String nombre, String apellido, String doc, String eps, String rh, String tel, String dir, String idcurso, String estado )throws SQLException{
        try{
            conectar();
            String sql = "INSERT INTO estudiantes(Nombres, Apellidos, Documento, EPS, RH, Telefono, Direccion, idCursos, Estado) VALUES (?,?,?,?,?,?,?,?,?)";
            PreparedStatement sent = conexion.prepareStatement(sql);
            sent.setString(1, nombre);
            sent.setString(2, apellido);
            sent.setString(3, doc);
            sent.setString(4, eps);
            sent.setString(5, rh);
            sent.setString(6, tel);
            sent.setString(7, dir);
            sent.setString(8, idcurso);
            sent.setString(9, estado);
            int rest1 = sent.executeUpdate();
            if(rest1 > 0){
                conexion.commit();
                return true;
            } else {
                conexion.rollback();
                return false;
            }
        }
        catch(Exception e) {
            conexion.rollback();
            e.printStackTrace();
            return false;
        } finally {
            desconectar();
        }
        
    }
    //----------------------------------------------
    
    
    
    
    public boolean insertToGrados(String LV_Grado, String LV_Anio) throws SQLException{
        try{
            conectar();
            String sql = "INSERT INTO Grados(Grado,idAnio) VALUES(?,(SELECT idAnio FROM AnioLectivos WHERE idAnio = ?))";
            PreparedStatement sent = conexion.prepareStatement(sql);
            sent.setString(1, LV_Grado);
            sent.setString(2, LV_Anio);
            int rest1 = sent.executeUpdate();
            if(rest1 > 0){
                conexion.commit();
                return true;
            } else {
                conexion.rollback();
                return false;
            }
        } catch(Exception e){
            e.printStackTrace();
            conexion.rollback();
            return false;
        } finally {
            desconectar();
        }
    }
    
    public boolean insertToCursos(String LV_Anio, String LV_Grado, String LV_Curso) throws SQLException{
        try{
            conectar();
            String sql = "INSERT INTO cursos(Curso, idGrados) "
                        + "VALUES (?,(select idGrados from Grados where Grado = ? and idAnio = ?));";
            PreparedStatement sent = conexion.prepareStatement(sql);
            sent.setString(1, LV_Curso);
            sent.setString(2, LV_Grado);
            sent.setString(3, LV_Anio);
            int rest1 = sent.executeUpdate();
            if(rest1 > 0){
                conexion.commit();
                return true;
            } else {
                return false;
            }
        } catch (Exception e){
            e.printStackTrace();
            return false;
        } finally {
            desconectar();
        }        
    }
    
    public boolean insertToProfesores(String LV_Name, String LV_Last, String LV_Phone, String LV_ID) throws SQLException{
        try{
            conectar();
            String sql = "INSERT INTO Profesores(Nombres,Apellidos,Telefono,Documento) VALUES(?,?,?,?)";
            PreparedStatement sent = conexion.prepareStatement(sql);
            sent.setString(1, LV_Name);
            sent.setString(2, LV_Last);
            sent.setString(3, LV_Phone);
            sent.setString(4, LV_ID);
            int rest = sent.executeUpdate();
            if(rest > 0){
                conexion.commit();
                return true;
            } else {
                return false;
            }
        } catch(Exception e) {
            conexion.rollback();
            e.printStackTrace();
            return false;
        } finally {
            desconectar();
        }
    }
    
    public boolean insertToMaterias(String LV_Materia, String LV_Grado, String LV_Anio) throws SQLException{
        try{
            conectar();
            String sql = "INSERT INTO materias(Materia,idGrados) VALUES "
                       + "(?,(SELECT idGrados FROM grados WHERE Grado = ? AND idAnio = ?))";
            PreparedStatement sent = conexion.prepareStatement(sql);
            sent.setString(1, LV_Materia);
            sent.setString(2, LV_Grado);
            sent.setString(3, LV_Anio);
            int rest1 = sent.executeUpdate();
            if(rest1 > 0){
                conexion.commit();
                return true;
            } else {
                return false;
            }
        } catch (Exception e){
            e.printStackTrace();
            return false;
        } finally {
            desconectar();
        }
    }
    
    public boolean insertToLogros(String LV_logro) throws SQLException{
        
        return false;
    }
    
    public boolean insertToUsuarios(String LV_Usuario, String LV_Pass, String LV_Nombre, String LV_Apellido, String LV_Perfil) throws SQLException{
        try{
            conectar();
            String sql= "INSERT INTO usuarios(idUsuarios, Clave, Nombres, Apellidos, idPerfiles) VALUES (?,?,?,?, (select idPerfiles from perfiles where Descripcion = ?))";
            PreparedStatement sent = conexion.prepareStatement(sql);
                sent.setString(1, LV_Usuario);
                sent.setString(2, LV_Pass);
                sent.setString(3, LV_Nombre);
                sent.setString(4, LV_Apellido);
                sent.setString(5, LV_Perfil);
                int rest1 = sent.executeUpdate();
                if(rest1 > 0){
                    conexion.commit();
                    return true;
                } else {
                    return false;
                }
        } catch (Exception e){
            e.printStackTrace();
            return false;
        } finally {
            desconectar();
        }
    }
}
