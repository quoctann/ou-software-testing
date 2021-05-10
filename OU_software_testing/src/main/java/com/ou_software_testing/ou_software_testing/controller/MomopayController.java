/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.ou_software_testing.ou_software_testing.controller;

import com.ou_software_testing.ou_software_testing.DataTemporary;
import com.ou_software_testing.ou_software_testing.GlobalContext;
import com.ou_software_testing.ou_software_testing.momopay.MomoPayment;
import com.ou_software_testing.ou_software_testing.pojo.CheckStatusTransaction;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.paint.Color;
import javafx.scene.text.Text;

/**
 *
 * @author Admin
 */
public class MomopayController extends ManageProductTableController{
    @FXML private Text txt_orderId, txt_staff_name, txt_date, txt_time, txt_total, txt_status;
    @FXML private ImageView image_QR;
    
    String orderId;
    
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        listProduct = DataTemporary.getListProductSelection();
        Date dNow = new Date();
        SimpleDateFormat dmy = new SimpleDateFormat ("dd-MM-yyyy");
        SimpleDateFormat hm = new SimpleDateFormat ("hh:mm");
        
        loadColumns();
        loadProducts();
        
        txt_staff_name.setText(GlobalContext.getUser().getName());
        txt_date.setText(dmy.format(dNow));
        txt_time.setText(hm.format(dNow));
        txt_orderId.setText(orderId);
        txt_total.setText(DataTemporary.getListProductSelection().getTotalPrice().toString());
        
        
        refreshQRcode();
    }
    
    @FXML
    public void refreshQRcode(){
        orderId = String.valueOf(System.currentTimeMillis());
        MomoPayment.pay(orderId, DataTemporary.getListProductSelection().getTotalPrice().longValue());
        
        try {
            InputStream stream = new FileInputStream(MomoPayment.getPathQR());
            Image image = new Image(stream);
            image_QR.setImage(image);
        } catch (FileNotFoundException ex) {
            Logger.getLogger(MomopayController.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
    @FXML
    public void cancelPay(ActionEvent actionEvent) throws IOException{
        DataTemporary.setListProductSelection(null);
        switchToMain(actionEvent);
    }
    
    @FXML
    public void checkTransaction(ActionEvent actionEvent) throws Exception{
        CheckStatusTransaction check = MomoPayment.check(orderId);
        if (check.getCode() == 0){
            txt_status.setText("Success");
            txt_status.setFill(Color.GREEN);
        }
    }
}
