/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package DBHandling;

import Models.Conexion;
import java.sql.PreparedStatement;
import java.sql.SQLException;

public class Deleter extends Conexion {
    
    public boolean deleteToUser(String User) throws SQLException{
        String sql = "DELETE FROM Usuarios WHERE idUsuarios like ?;";
        try{
            conectar();
            PreparedStatement sent = conexion.prepareStatement(sql);
            sent.setString(1, User);
            int rest = sent.executeUpdate();
            if(rest > 0){
                conexion.commit();
                return true;
            } else {
                return false;
            }
        }catch(Exception e){
            conexion.rollback();
            e.printStackTrace();
            return false;
        }
    }
}
