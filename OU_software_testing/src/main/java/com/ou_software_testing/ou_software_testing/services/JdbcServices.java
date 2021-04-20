package com.ou_software_testing.ou_software_testing.services;

import java.sql.*;
import java.util.logging.Level;
import java.util.logging.Logger;
/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

/**
 *
 * @author Admin
 */
public class JdbcServices {
    //Change the comment to your password and leave the first line
    private static String[] info = {"root", "123456"};

    
    static {
        try {
            Class.forName("com.mysql.jdbc.Driver");
        } catch (ClassNotFoundException ex) {
            System.out.println(ex);
//            ex.printStackTrace();
        }
    }
    public static Connection getConnection() {
        try {
            return DriverManager.getConnection("jdbc:mysql://localhost/saledb", info[0], info[1]);
        } catch (SQLException ex) {
            System.out.println(ex);
//            Logger.getLogger(JdbcServices.class.getName()).log(Level.SEVERE, null, ex);
        }
        return null;
    }
}
