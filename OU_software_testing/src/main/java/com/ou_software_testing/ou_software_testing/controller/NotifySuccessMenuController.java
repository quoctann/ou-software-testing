/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.ou_software_testing.ou_software_testing.controller;

import com.ou_software_testing.ou_software_testing.DataTemporary;
import com.ou_software_testing.ou_software_testing.GlobalContext;
import com.ou_software_testing.ou_software_testing.Utils;
import java.net.URL;
import java.util.ResourceBundle;
import java.util.regex.Pattern;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.input.KeyCode;
import javafx.scene.layout.GridPane;
import javafx.scene.text.Text;

/**
 *
 * @author Admin
 */
public class NotifySuccessMenuController extends Controller implements Initializable{
    @FXML private Text txt_order_price,txt_money_received, txt_money_left;
    @FXML private TextField txt_money_amount;
    @FXML private Button btn_pay, btn_pay_momo;
    @FXML private GridPane gp_count_excess;

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        txt_order_price.setText(DataTemporary.getListProductSelection().getTotalPrice().toString());
        
        if(!"user".equals(GlobalContext.getUser().getRole())) {
            txt_money_amount.textProperty().addListener((ObservableValue<? extends String> ov, String t, String t1) -> {
            if(!t1.matches("\\d*"))  {
                txt_money_amount.setText(t1.replaceAll("[^\\d]", ""));
            }
            });
            txt_money_amount.setOnKeyPressed( event -> {
                if(event.getCode() == KeyCode.ENTER) {
                    countExcessAmount();
                }
            });

            btn_pay.setOnMouseClicked(event -> {
                Alert a = Utils.makeAlert(Alert.AlertType.INFORMATION, "Đang thực hiện in hóa đơn");
                a.show();
            });
        } else {
            gp_count_excess.setDisable(true);
            gp_count_excess.setStyle("-fx-opacity: 0");
        }
        
    }
    
    @FXML 
    private void checkIfNumber()  {
        if(txt_money_amount.getText().length() != 0)  { 
            double amount = Double.parseDouble(txt_money_amount.getText());
            txt_money_received.setText(String.format("%,.2f", amount));
        }
    }
    
    private void countExcessAmount() {
        double amount = Double.parseDouble(txt_money_amount.getText()) - Double.parseDouble(txt_order_price.getText());
        txt_money_left.setText(String.format("%,.2f", amount));
    }
    
    
}
