package com.ou_software_testing.ou_software_testing.controller;

import com.ou_software_testing.ou_software_testing.App;
import com.ou_software_testing.ou_software_testing.DataTemporary;
import com.ou_software_testing.ou_software_testing.Utils;
import com.ou_software_testing.ou_software_testing.pojo.ListProduct;
import com.ou_software_testing.ou_software_testing.pojo.Product;
import java.io.IOException;
import java.net.URL;
import java.util.List;
import java.util.ResourceBundle;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.SelectionMode;
import javafx.scene.control.TablePosition;
import javafx.scene.input.MouseEvent;
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
    
    private ListProduct listChoose = new ListProduct();
    ObservableList<TablePosition> selectedCells = FXCollections.observableArrayList();
    
    @FXML
    private void switchToSellMenu(ActionEvent actionEvent) throws IOException {
        try {
            listChoose.setCount1();
            DataTemporary.setListProductSelection(listChoose);
            this.getNotify(true);
            App.setRoot("sell_menu");
        } catch (IOException ex) {
            this.getNotify(false);
            System.out.println("Error while switching to sell_menu");
            System.err.println(ex);
        }
    }

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        super.initialize(url, rb); //To change body of generated methods, choose Tools | Templates.
        
        tb_search_product.getSelectionModel().setSelectionMode(
            SelectionMode.MULTIPLE
        );
        tb_search_product.setOnMousePressed(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent t) {
                Product p = tb_search_product.getSelectionModel().getSelectedItem();
            
                List<Product> list = tb_search_product.getSelectionModel().getSelectedItems();

                listChoose.getListProduct().clear();
                for (int i = 0; i < list.size(); i++)
                    listChoose.addProduct(list.get(i));

                txt_quantity.setText(String.valueOf(p.getCount()));
                txt_pid.setText(String.valueOf(p.getId()));
                txt_product_name.setText(p.getName());
                txt_price.setText(p.getPrice().toString());
            }
        });
    }
    
    //Use for checking order orders success or not.
    private void getNotify(Boolean success) {
        if(success) {
            Utils.makeAlert(Alert.AlertType.INFORMATION, "Order success", 
                    "Information", "Order successful, please go to order to check list");
        } else 
            Utils.makeAlert(Alert.AlertType.ERROR, "Fail ordering ", 
                    "Error", "Order fail, please check products list");
    }
}
