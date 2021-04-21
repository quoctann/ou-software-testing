/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.ou_software_testing.ou_software_testing.services;

import com.ou_software_testing.ou_software_testing.pojo.ListProduct;
import com.ou_software_testing.ou_software_testing.pojo.Product;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

/**
 *
 * @author Admin
 */
public class ProductServices {
    private Connection conn;
    
    public ProductServices(Connection conn) {
        this.conn = conn;
    }
    
    public boolean deleleProduct(int id){
        return true;
    }
    
    public Product getProductById (int productId) throws SQLException {
        String sql = "SELECT * FROM product WHERE id = ?";
        PreparedStatement stm = this.conn.prepareStatement(sql);
        stm.setInt(1, productId);

        Product p = new Product();
        ResultSet rs = stm.executeQuery();
        if (!rs.isBeforeFirst() ) {    
            return null;
        } 
        if (rs.next()) {            
            p.setId(rs.getInt("id"));
            p.setName(rs.getString("name"));
            p.setOrigin(rs.getString("origin"));
            p.setSize(rs.getString("size"));
            p.setCount(rs.getInt("count"));
            p.setCategory(rs.getInt("category"));
            p.setPrice(rs.getBigDecimal("price"));
        }  
        
        return p;
    }
    
    public ListProduct getProductByName(String productName) throws SQLException{
        ListProduct listProduct = new ListProduct();
        
        String sql = "SELECT * FROM product WHERE name like '%?%'";
        
        PreparedStatement stm = this.conn.prepareStatement(sql);
        stm.setString(1, productName);

        
        ResultSet rs = stm.executeQuery();
        if (!rs.isBeforeFirst() ) {    
            return null;
        } 
        while (rs.next()) {            
            Product p = new Product();
            
            p.setId(rs.getInt("id"));
            p.setName(rs.getString("name"));
            p.setOrigin(rs.getString("origin"));
            p.setSize(rs.getString("size"));
            p.setCount(rs.getInt("count"));
            p.setCategory(rs.getInt("category"));
            p.setPrice(rs.getBigDecimal("price"));
            
            listProduct.addProduct(p);
        }
        
        return listProduct;
    }
}
