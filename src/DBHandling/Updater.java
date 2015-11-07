/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package DBHandling;

import Models.Conexion;
import java.sql.PreparedStatement;
import java.sql.SQLException;

public class Updater extends Conexion{
    
    public void updateToProfesores(){
        
    }
    
    public boolean updateToUsuarios(int Est, String User) throws SQLException{
        try{
            conectar();    
            String sql = "UPDATE Usuarios SET Estado = ? WHERE idUsuarios = ?;";
            PreparedStatement sent = conexion.prepareStatement(sql);
            sent.setString(1, Integer.toString(Est));
            sent.setString(2, User);
            int rest = sent.executeUpdate();
            conexion.commit();
            if(rest > 1){
                return true;
            } else {
                return false;
                }
            } catch(Exception e){
                conexion.rollback();
                e.printStackTrace();
                return false;
            } finally {
            desconectar();
        }
    }
}
