/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Aplikasi;
import java.sql.Connection;
import java.sql.DriverManager;
import javax.swing.JOptionPane;
        
/**
 *
 * @author user
 */
public class koneksi {
    Connection koneksi = null;
    public static Connection koneksiDB() {
        
    
    try {
    Class.forName("com.mysql.jdbc.Driver");
    Connection koneksi = DriverManager.getConnection("jdbc:mysql://localhost/dbresto_mila1","root","");
    return koneksi;
}
    catch (Exception e) {
    JOptionPane.showMessageDialog(null, e);
    return null;
}
}
}