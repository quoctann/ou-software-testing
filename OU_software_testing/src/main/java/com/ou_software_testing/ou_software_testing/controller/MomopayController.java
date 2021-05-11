/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.ou_software_testing.ou_software_testing.controller;

import com.google.zxing.BarcodeFormat;
import com.google.zxing.MultiFormatWriter;
import com.google.zxing.client.j2se.MatrixToImageWriter;
import com.google.zxing.common.BitMatrix;
import com.ou_software_testing.ou_software_testing.DataTemporary;
import com.ou_software_testing.ou_software_testing.GlobalContext;
import com.ou_software_testing.ou_software_testing.momopay.CaptureMoMo;
import com.ou_software_testing.ou_software_testing.momopay.CaptureMoMoResponse;
import com.ou_software_testing.ou_software_testing.momopay.Environment;
import com.ou_software_testing.ou_software_testing.momopay.MomoPay;
import com.ou_software_testing.ou_software_testing.pojo.CheckStatusTransaction;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.math.RoundingMode;
import java.net.URL;
import java.nio.file.FileSystems;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.paint.Color;
import javafx.scene.text.Text;

/**
 *
 * @author Admin
 */
public class MomopayController extends ManageProductTableController{
    @FXML private Text txt_orderId, txt_staff_name, txt_date, txt_time, 
            txt_total, txt_status, txt_amount_paid;
    @FXML private Button btn_print;
    @FXML private ImageView image_QR;
    
    String orderId;
    MomoPay momoPay;
    String amount = DataTemporary.getListProductSelection().getTotalPrice()
                .setScale(0, RoundingMode.UP).toString();
    
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
        txt_total.setText(DataTemporary.getListProductSelection().getTotalPrice().toString());
        
        createQRcode();
    }
    
    @FXML
    public void createQRcode(){
        orderId = String.valueOf(System.currentTimeMillis());
        String URL = "https://google.com.vn";
        
        try {
            MomoPay.main(orderId, amount);
            InputStream stream = new FileInputStream(MomoPay.getPathQR());
            Image image = new Image(stream);
            image_QR.setImage(image);
        } catch (Exception ex) {
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
        if (MomoPay.check(orderId)){
            txt_status.setText("Success");
            txt_status.setFill(Color.GREEN);
            txt_amount_paid.setText(amount);
            btn_print.setDisable(false);
        }
    }
}
