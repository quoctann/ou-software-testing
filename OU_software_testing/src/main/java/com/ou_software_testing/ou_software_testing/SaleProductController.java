/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.ou_software_testing.ou_software_testing;

import com.ou_software_testing.ou_software_testing.pojo.ListProduct;
import com.ou_software_testing.ou_software_testing.pojo.Product;
import com.ou_software_testing.ou_software_testing.pojo.User;
import com.ou_software_testing.ou_software_testing.services.JdbcServices;
import com.ou_software_testing.ou_software_testing.services.ProductServices;
import java.io.IOException;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.text.Text;
import javafx.stage.Stage;

/**
 *
 * @author Admin
 */
public class SaleProductController extends Controller {
    @FXML private TextField txt_pid;
    @FXML private TextField txt_quantity;
    @FXML private Button btn_add_adjust;
    @FXML private Button btn_abort;
    @FXML private Button btn_confirm_order;
    @FXML private Text txt_sum;
    
    private ListProduct listProduct = new ListProduct();
    private String pid, quantity;

    @FXML void HandleAbort(ActionEvent actionEvent) throws IOException{
        this.switchToMain(actionEvent);
    }
    
    @FXML
    private void HandleAdd(ActionEvent actionEvent) {
        pid = txt_pid.getText();
        quantity = txt_quantity.getText();
        
        try {
            Connection conn = JdbcServices.getConnection();
            ProductServices productServices = new ProductServices(conn);

            Product product = productServices.getProductById(Integer.parseInt(pid));
            listProduct.addProduct(product);
            
            txt_sum.setText(listProduct.getTotalPrice().toString());
        } catch (NumberFormatException | SQLException ex) {
            System.err.println(ex);
        }
    }
}
