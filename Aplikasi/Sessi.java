/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Aplikasi;

/**
 *
 * @author 
 */
public class Sessi {
    private static String idpengguna;
    private static String namapengguna;
    private static String level;
    private static String username;
    private static String password;
    
    public static String getIdPengguna() {
        return idpengguna;
    }
    
    public static void setIdPengguna(String idpengguna) {
        Sessi.idpengguna = idpengguna;
    }
    
    public static String getNamaPengguna() {
        return namapengguna;
    }
    
    public static void setNamaPengguna(String namapengguna) {
        Sessi.namapengguna = namapengguna;
    }
    
    public static String getLevel() {
        return level;
    }
    
    public static void setLevel(String level) {
        Sessi.level = level;
    }
    
    public static String getUsername() {
        return username;
    }
    
    public static void setUsername(String username) {
        Sessi.username = username;
    }
    
    public static String getPassword() {
        return password;
    }
    
    public static void setPassword(String password) {
        Sessi.password = password;
    }
    
    public static void hapus() {
        idpengguna="";
        namapengguna="";
        username="";
        password="";
        level="";
    }
}
