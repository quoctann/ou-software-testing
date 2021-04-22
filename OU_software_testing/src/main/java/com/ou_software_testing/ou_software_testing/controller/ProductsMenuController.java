package com.ou_software_testing.ou_software_testing.controller;

import com.ou_software_testing.ou_software_testing.pojo.Category;
import com.ou_software_testing.ou_software_testing.pojo.Product;
import com.ou_software_testing.ou_software_testing.services.CategoryServices;
import com.ou_software_testing.ou_software_testing.services.JdbcServices;
import com.ou_software_testing.ou_software_testing.services.ProductServices;
import java.net.URL;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TextField;

public class ProductsMenuController extends ManageProductTableController{
    @FXML protected TextField txt_product_name;
    @FXML protected TextField txt_pid;
    @FXML protected TextField txt_quantity;
    @FXML protected TextField txt_price;
    @FXML protected ComboBox cb_category;
    
    @FXML
    public void HandleAdd(ActionEvent actionEvent) {
        String pid = txt_pid.getText();
        String quantity = txt_quantity.getText();
        
        try {
            Connection conn = JdbcServices.getConnection();
            ProductServices productServices = new ProductServices(conn);

            Product product = productServices.getProductById(Integer.parseInt(pid));
            if (product == null) 
                return;
            
            product.setCount(Integer.parseInt(txt_quantity.getText()));
            
            listProduct.removeProductById(product.getId());
            listProduct.addProduct(product);
            loadProducts();
        } catch (NumberFormatException | SQLException ex) {
            System.err.println(ex);
        }
    } 
    
    @FXML
    public void HandleDelete(ActionEvent actionEvent) {
        tb_search_product.setOnMouseClicked(event -> {
            Product p = tb_search_product.getSelectionModel().getSelectedItem();
            
            listProduct.removeProductById(p.getId());
        });            
    }

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        super.initialize(url, rb); 
        
        tb_search_product.setOnMouseClicked(event -> {
            Product p = tb_search_product.getSelectionModel().getSelectedItem();
            
            
            txt_quantity.setText(String.valueOf(p.getCount()));
            txt_pid.setText(String.valueOf(p.getId()));
            txt_product_name.setText(p.getName());
            txt_price.setText(p.getPrice().toString());
        });
    }
    
    @FXML
    private void getCategoryList() {
        try {
            Connection conn = JdbcServices.getConnection();
            CategoryServices categoryServices = new CategoryServices(conn);
            
            List<Category> catesList = new ArrayList<>();
            catesList = categoryServices.getCategorys();
            
            ObservableList obList = FXCollections.observableList(catesList);
            cb_category.setItems(obList);
            
            conn.close();
        } catch (SQLException ex) {
            Logger.getLogger(ProductsMenuController.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
}
