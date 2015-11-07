/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Models;

import net.sf.jasperreports.engine.*;
import net.sf.jasperreports.view.JasperViewer;


public class ReportGenerator extends Conexion implements Runnable {
        
    public void UserReport(){
        try { 
            JasperReport report = JasperCompileManager.compileReport("src\\Reports\\report2.jrxml");
            JasperPrint print = JasperFillManager.fillReport(report, null, JConectar());
            JasperViewer.viewReport(print,false);
        } catch (JRException ex) {
            ex.getMessage();
        }
    }

    @Override
    public void run() {
        this.UserReport();
    }
    
}
