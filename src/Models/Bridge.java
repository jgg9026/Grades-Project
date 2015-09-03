/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Models;

import javax.swing.JOptionPane;

public class Bridge extends Conexion {
    
    public boolean setter(String[] data){
        return setterData(data);
    }
    
    public boolean deSetter(String[] data){
        return setterData(data);
    }
}
