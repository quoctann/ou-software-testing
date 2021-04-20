/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.ou_software_testing.ou_software_testing.services;

import com.ou_software_testing.ou_software_testing.pojo.User;
import java.sql.*;
/**
 *
 * @author Admin
 */
public class UserServices {
    private Connection conn;
    
    public UserServices(Connection conn) {
        this.conn = conn;
    }
    
    public User getUserInfo (String info, String pw) throws SQLException {
        String sql = "SELECT * FROM user WHERE email = ? or phone = ? and password = ?";
        PreparedStatement stm = this.conn.prepareStatement(sql);
        stm.setString(1, info);
        stm.setString(2, info);
        stm.setString(3, pw);

        User u = new User();
        ResultSet rs = stm.executeQuery();
        if (rs.next()) {
            u.setId(rs.getInt("id"));
            u.setName(rs.getString("name"));
            u.setEmail(rs.getString("email"));
            u.setLocation(rs.getString("location"));
            u.setPhone(rs.getString("phone"));
            u.setSex(rs.getString("sex"));
            u.setRole(rs.getObject("role").toString());
        }  
        
        return u;
    }
}
