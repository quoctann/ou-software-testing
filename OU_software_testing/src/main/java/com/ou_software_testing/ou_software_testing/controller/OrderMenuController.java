/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.ou_software_testing.ou_software_testing.controller;

import com.ou_software_testing.ou_software_testing.App;
import com.ou_software_testing.ou_software_testing.DataTemporary;
import com.ou_software_testing.ou_software_testing.GlobalContext;
import com.ou_software_testing.ou_software_testing.Rule;
import com.ou_software_testing.ou_software_testing.Utils;
import com.ou_software_testing.ou_software_testing.pojo.ListProduct;
import com.ou_software_testing.ou_software_testing.pojo.Product;
import java.io.IOException;
import java.math.BigDecimal;
import java.net.URL;
import java.util.Optional;
import java.util.ResourceBundle;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.text.Text;

/**
 *
 * @author Admin
 */
public class OrderMenuController extends ManageProductTableController{
    
    @FXML private Button btn_adjust, btn_delete, btn_abort, btn_confirm_order;
    @FXML private Text txt_sum;
    @FXML private TableView<Product> tb_orders;
    private ListProduct listProductOrder = DataTemporary.getListProductSelection();
    
    @FXML private TextField txt_quantity;
    
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        loadColumns(tb_orders);
        updateTable();
        
        if(listProductOrder.getListProduct().size() > 0) {
            btn_confirm_order.setDisable(false);
            btn_abort.setDisable(false);
        }
        
        tb_orders.setOnMouseClicked(event -> {
            Product p = tb_orders.getSelectionModel().getSelectedItem();  
            if(p != null) {
                btn_adjust.setDisable(false);
                btn_delete.setDisable(false);
                txt_quantity.setText(String.valueOf(p.getCount()));
            } else {
                btn_adjust.setDisable(true);
                btn_delete.setDisable(true);
            }
        });
    }
    
    private void updateTable()  {
        tb_orders.getItems().clear();
        loadProducts(tb_orders, listProductOrder);
        UpdateSum();
    }
    
    private void UpdateSum() {
        BigDecimal sum = new BigDecimal(0);
        for( Product p : listProductOrder.getListProduct()) {
            sum = sum.add(p.getPrice().multiply(new BigDecimal(p.getCount())));
        }
        txt_sum.setText(sum.toString());
    }
    
    @FXML 
    private void onDelete() {
        for(Product p: listProductOrder.getListProduct()) {
            if  (p.getId() ==  tb_orders.getSelectionModel().getSelectedItem().getId()) {
                listProductOrder.removeProductById(p.getId());
            }
        }
        DataTemporary.setListProductSelection(listProductOrder);
        updateTable();
    }
    
    @FXML
    private void onAdjust() {
        if (Utils.ParseIntWithTryCatch(txt_quantity.getText())== -1){
            Alert a = Utils.makeAlert(Alert.AlertType.ERROR, "Nh???p sai th??ng tin", 
                    "Nh???p sai th??ng tin s??? l?????ng", "Vui l??ng nh???p ????ng ?????nh d???ng s???");
            a.show();
            return;
        }
        int quantity = Integer.parseInt(txt_quantity.getText());
        Product product = tb_orders.getSelectionModel().getSelectedItem();
        int totalProduct = product.getTotalProduct();
        if(quantity <= 0) {
            Alert a = Utils.makeAlert(Alert.AlertType.ERROR, "Nh???p sai th??ng tin","Nh???p sai th??ng tin s??? l?????ng", "Vui l??ng nh???p l???i th??ng tin s??? l?????ng ????ng.");
            a.show();
            return;
        }
        
        if((totalProduct - quantity) < 3 ) {
            Alert a = Utils.makeAlert(Alert.AlertType.ERROR, "Nh???p sai th??ng tin", 
                "Nh???p sai th??ng tin s??? l?????ng", "S??? l?????ng h??ng trong kho sau khi ?????t ph???i l???n h??n 3.\n"
                        + "S??? l?????ng h??ng trong kho sau khi ?????t l?? " +  
                        String.valueOf(totalProduct - quantity)
                        + ".\n Vui l??ng nh???p l???i th??ng tin s??? l?????ng ????ng.");
            a.show();
            return;
        }
        if(quantity > 0 &&  quantity <= totalProduct ) { 
            
//            product.setCount(quantity);
            listProductOrder.getProductById(product.getId()).setCount(quantity);
            updateTable();
        } else {
            Alert a = Utils.makeAlert(Alert.AlertType.ERROR, "Nh???p sai th??ng tin", 
                    "Nh???p sai th??ng tin s??? l?????ng", "Vui l??ng nh???p l???i th??ng tin s??? l?????ng ????ng");
            a.show();
        }
    }
    
    @FXML 
    private void onAbort() {
        Alert a = Utils.makeAlert(Alert.AlertType.CONFIRMATION, "Th??ng tin", "H???y ????n h??ng", "B???n c?? th???t s??? mu???n h???y ????n h??ng n??y kh??ng?");
        
        Optional<ButtonType>  rs = a.showAndWait();
        
        if(rs.get() == ButtonType.OK) {
            listProductOrder.getListProduct().clear();
            DataTemporary.setListProductSelection(listProductOrder);
            updateTable();
        }       
    }
    
    
    //switch to Payment controller which dont exist yet - dung
    @FXML 
    private void onConfirmOrder() throws IOException {
        if(GlobalContext.getUser().getRole() == "user" 
                && listProductOrder.getListProduct().size() > Rule.getMAX_PRODUCT_ORDER()) {
            Alert a = Utils.makeAlert(Alert.AlertType.ERROR, "Th??ng tin kh??ng h???p l???", 
                "S??? l?????ng s???n ph???m sai", "Kh??ch h??ng ch??? ???????c ?????t kh??ng qu?? "+ String.valueOf(Rule.getMAX_PRODUCT_ORDER())
                        + " s???n ph???m trong m???t h??a ????n");
            a.show();
            return;
        }
        DataTemporary.setListProductSelection(listProductOrder);
        App.setRoot("notify_success_menu");
    }
}
