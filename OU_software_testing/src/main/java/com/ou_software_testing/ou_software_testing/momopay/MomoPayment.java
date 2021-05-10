/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.ou_software_testing.ou_software_testing.momopay;

import com.google.zxing.BarcodeFormat;
import com.google.zxing.MultiFormatWriter;
import com.google.zxing.client.j2se.MatrixToImageWriter;
import com.google.zxing.common.BitMatrix;
import com.mservice.allinone.models.CaptureMoMoResponse;
import com.mservice.allinone.models.QueryStatusTransactionResponse;
import com.mservice.allinone.processor.allinone.CaptureMoMo;
import com.mservice.allinone.processor.allinone.QueryStatusTransaction;
import com.mservice.shared.sharedmodels.Environment;
import com.mservice.shared.sharedmodels.PartnerInfo;
import com.mservice.shared.utils.Encoder;
import com.ou_software_testing.ou_software_testing.pojo.CheckStatusTransaction;
import java.nio.file.FileSystems;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author Admin
 */
public class MomoPayment {
    private static  String domain = "https://test-payment.momo.vn";
    private static final String orderInfo = "Pay With MoMo";
    private static final String returnURL = "https://google.com.vn";
    private static final String notifyURL = "https://google.com.vn";
    String extraData = "";
    String bankCode = "SML";
    private static final String customerNumber = "0383793662";
    private static final String publicKey = "MIICIjANBgkqhkiG9w0BAQEFAAOCAg8AMIICCgKCAgEAqV9mfDfJ8L1yU+aniWvzKQ2kUmH6RvEqv+mzllgn3oJV7oU9u0WULghqNsE4Xn9sDvo9AFC9+1t1v94TlZ/zLDQgHY5MPLSTxsk0Zsu2mfmWrnMSI8DdAjz/rAngbsu9ZOFjX0lJIWTgJCtGfpMqwOWAHXy1BGjQtrpNPtXCfJAUNf9+0ffe4fF8b10U7nBP5gs9GCSRuIp6lN6RMePjrn8dMUt8ZxJB40dVvELdUzhK9q+L03fvwujvJLCfV2ELHLkAuPzf2DlalYpZGCCKaYNhafYKvbaGxpEGaDPSJQCY85aZ/jDYZe6r7P9HFY5Itw+PtLMtx5RwpJvW9BR/rNTZPcfyRATlCa9/zBayrAf7A8viky8eaRVrLDJ6gt7d2KcZ9fgPtDQIgDiE4/Hh8SdTeyQTigUzHpqnpThEQ+WUzqu7NuW1FhHUACZU+RNPHcYA6BuPimah0pDNI8nbekkDojV74CrZCDQp20vLxTkm4gKbZKGX2hn/D/kshvanibt3ZGm3sDFui9y4ILZDj3f9wElB7+nCsJG5jpUeqIHcrtar0cEKrkGm67QgVI++jjEbfvsBf3ENEq9AYZkz6v2g1kWYs347TNNEEBN/EReI/FNPuW1U/qGJmko7iF4EqJqFkGxI9+1TKwZl9aNxEEV/JD2xUv1BsWL/KOXOoo8CAwEAAQ==";
    private static final String privateKey = "Xc5PCdswGelbZ6LkZa0YeXX4mnkAEzdq";
    private static final String accessKey = "1ROHprlNx7dcgXIB";
    private static final String partnerCode = "MOMOM4E120210510";
    private static final String username = "OU";
    private static final String storeId = "abcdefABCDEF0123";
    private static final String storeSlug = partnerCode+'-'+storeId;
    private static Environment environment = Environment.selectEnv(Environment.EnvTarget.DEV, Environment.ProcessType.PAY_GATE);
    
    {
        environment.setPartnerInfo(new PartnerInfo(partnerCode, accessKey, privateKey));
    }
    
    public static void main(String... args) {
        try {
            LogUtils.init();
            String orderId = String.valueOf(System.currentTimeMillis());
            String requestId = String.valueOf(System.currentTimeMillis());
            long amount = 100000;
            
            Environment environment = Environment.selectEnv(Environment.EnvTarget.DEV, Environment.ProcessType.PAY_GATE);
            environment.setPartnerInfo(new PartnerInfo(partnerCode, accessKey, privateKey));
            
            CaptureMoMoResponse captureMoMoResponse = CaptureMoMo.process(
                    environment, orderId, requestId, Long.toString(amount), 
                    orderInfo, returnURL, notifyURL, "");
//            saveQR(captureMoMoResponse.getDeeplink());
            
//            QueryStatusTransactionResponse queryStatusTransactionResponse = QueryStatusTransaction.process(
//                    environment, orderId, requestId);
//        
//            PayGateResponse payGateResponse = PaymentResult.process(environment,new PayGateResponse());
            System.out.println("deeplink:" + captureMoMoResponse.getDeeplink());
        } catch (Exception ex) {
            Logger.getLogger(MomoPayment.class.getName()).log(Level.SEVERE, null, ex);
        }
         
    }            
    public static CheckStatusTransaction check(String orderId) throws Exception{
        QueryStatusTransactionResponse queryStatus = QueryStatusTransaction.process(
                    environment, orderId, orderId);
        return new CheckStatusTransaction(queryStatus.getMessage(), queryStatus.getErrorCode());
    }
    public static void pay(String orderId, long amount){
        try {
            CaptureMoMoResponse captureMoMoResponse = CaptureMoMo.process(
                    environment, orderId, orderId, Long.toString(amount), 
                    orderInfo, returnURL, notifyURL, "");
            saveQR(captureMoMoResponse.getDeeplink());
        } catch (Exception e) {
        }
    }
    public static String getPathQR() {
         try {
            Path root = FileSystems.getDefault().getPath("").toAbsolutePath();
            String filePath = Paths.get(root.toString(),"src", "main", "resources", "QRPaycode.jpg").toString();
            return filePath;
        } catch (Exception ex) {
            Logger.getLogger(MomoPayment.class.getName()).log(Level.SEVERE, null, ex);
        }
        return null;
    }
    public static void saveQR(String linkPay){
        try {
            Path root = FileSystems.getDefault().getPath("").toAbsolutePath();
            String filePath = Paths.get(root.toString(),"src", "main", "resources", "QRPaycode.jpg").toString();
            BitMatrix matrix = new MultiFormatWriter().encode(linkPay, BarcodeFormat.QR_CODE, 400, 400);
            MatrixToImageWriter.writeToPath(matrix, "jpg", Paths.get(filePath));
        } catch (Exception ex) {
            Logger.getLogger(MomoPayment.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
}
// id store abcdefABCDEF0123
// id store abcdefABCDEF0189
// pass store: 2FdZ@kw2wZYUcyR

