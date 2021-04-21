package com.ou_software_testing.ou_software_testing.controller;

import com.ou_software_testing.ou_software_testing.App;
import com.ou_software_testing.ou_software_testing.DataTemporary;
import com.ou_software_testing.ou_software_testing.pojo.Product;
import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.text.Text;


/**
 *
 * @author Admin
 */
public class SearchMenuController extends ManageProductTableController{
    @FXML protected Text txt_product_name;
    @FXML protected Text txt_pid;
    @FXML protected Text txt_quantity;
    @FXML protected Text txt_price;
    
    @FXML
    private void switchToSellMenu(ActionEvent actionEvent) throws IOException {      
        try {
            DataTemporary.setListProductSelection(listProduct);
            App.setRoot("sell_menu");
        } catch (IOException ex) {
            System.out.println("Error while switching to sell_menu");
            System.err.println(ex);
        }
    }

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        super.initialize(url, rb); //To change body of generated methods, choose Tools | Templates.
        
        tb_search_product.setOnMouseClicked(event -> {
            Product p = tb_search_product.getSelectionModel().getSelectedItem();
            
            
            txt_quantity.setText(String.valueOf(p.getCount()));
            txt_pid.setText(String.valueOf(p.getId()));
            txt_product_name.setText(p.getName());
            txt_price.setText(p.getPrice().toString());
        });
    }
    
    
}
