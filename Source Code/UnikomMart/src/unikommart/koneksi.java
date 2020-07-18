/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package unikommart;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

/**
 *
 * @author Exception
 */
public class koneksi {
    private static final String host="localhost";
   private static final String db="kasir2";  
   private static final String user="root";
   private static final String pass=""; 
   private static Connection conn;  
   
   public static Connection getConnection(){ 
        if(conn==null){ 
            try{
                Class.forName("com.mysql.jdbc.Driver");
                conn = (Connection) DriverManager.getConnection("jdbc:mysql://"+host+"/"+db,user,pass);
            }catch(ClassNotFoundException cnfe){
                System.out.println("Driver Tidak Ditemukan ="+ cnfe);
            }
            catch(SQLException ex){
                System.out.println("Koneksi Gagal ="+ ex);
            }
        } 
       return conn;
     }
    
}
