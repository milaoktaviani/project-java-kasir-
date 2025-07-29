/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Aplikasi;


public class session {
private static String idpengguna;
private static String namapengguna;
private static String level;
private static String username;
private static String password;

public static String getIdpengguna() {
return idpengguna;
}

public static void setIdpengguna (String idpengguna) {
session.idpengguna = idpengguna;
}

public static String getNamaPengguna () {
return namapengguna;
}

public static void setNamaPengguna (String namapengguna) {
session.namapengguna = namapengguna;
}

public static String getLevel () {
return level;
}

public static void setLevel (String level) {
session.level = level;
}

public static String getPassword () {
return password;
}

public static void setPassword (String password) {
session.password = password;
}
}
/**
 *
 * @author user
 */
