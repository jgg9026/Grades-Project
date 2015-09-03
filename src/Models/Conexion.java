/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Models;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JOptionPane;


public class Conexion {
    
    protected static Connection conexion;
    protected static String[] GV_data;
         
    protected boolean conectar() {
        try {
            Class.forName("com.mysql.jdbc.Driver");
            //conexion = DriverManager.getConnection(GV_data[0], GV_data[1], GV_data[2]);
            conexion = DriverManager.getConnection("jdbc:mysql://127.0.0.1:3306/mydb","root","");
            conexion.setAutoCommit(false);
            return true;
        } catch (ClassNotFoundException | SQLException e) {
            e.printStackTrace();
            return false;
        }
    }
    
    protected void desconectar(){
        try {
            conexion.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    
    protected boolean setterData(String[] data){
        try{
            GV_data = data;
            return true;
        }catch (Exception e){
            e.printStackTrace();
            return false;
        }
    }

    
}
