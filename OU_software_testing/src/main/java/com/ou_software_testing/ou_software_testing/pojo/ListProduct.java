/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.ou_software_testing.ou_software_testing.pojo;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author Admin
 */
public class ListProduct {
    private List<Product> listProduct;
    
    public ListProduct(List<Product> listProduct){
        this.listProduct = listProduct;
    }
    public ListProduct(){
        this.listProduct = new ArrayList<>();
    }
    
    public BigDecimal getTotalPrice(){
        BigDecimal totalPrice = new BigDecimal(0);
        
        for (Product p : this.getListProduct()) {
            totalPrice.add(p.getPrice());
        };
        
        return totalPrice;
    }
    public void addProduct(Product p){
        if (listProduct == null) listProduct = new ArrayList<>();
        this.listProduct.add(p);
    }

    /**
     * @return the listProduct
     */
    public List<Product> getListProduct() {
        return listProduct;
    }

    /**
     * @param listProduct the listProduct to set
     */
    public void setListProduct(List<Product> listProduct) {
        this.listProduct = listProduct;
    }
}
