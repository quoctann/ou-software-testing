package com.ou_software_testing.ou_software_testing.momopay;

import com.mservice.allinone.models.PayGateResponse;
import com.mservice.shared.constants.Parameter;
import com.mservice.shared.exception.MoMoException;
import com.mservice.shared.sharedmodels.AbstractProcess;
import com.mservice.shared.sharedmodels.Environment;
import com.mservice.shared.utils.Encoder;

public class PaymentResult extends AbstractProcess<PayGateResponse, PayGateResponse>{
    public PaymentResult(Environment environment) {
        super(environment);
    }

    public static PayGateResponse process(Environment env, PayGateResponse momoPaymentResult) {
        try {
            PaymentResult paymentResult = new PaymentResult(env);
            if(momoPaymentResult !=null){
                PayGateResponse responseMoMo = paymentResult.execute(momoPaymentResult);
                return responseMoMo;
            }
        } catch (Exception exception) {
            LogUtils.error("[Process Payment Result] "+ exception);
        }
        return null;
    }

    @Override
    public PayGateResponse execute(PayGateResponse payGateResponse) {
        try {
            String rawData = Parameter.PARTNER_CODE + "=" + partnerInfo.getPartnerCode() +
                    "&" + Parameter.ACCESS_KEY + "=" + partnerInfo.getAccessKey() +

                    "&" + Parameter.REQUEST_ID + "=" + payGateResponse.getRequestId() +
                    "&" + Parameter.AMOUNT + "=" + payGateResponse.getAmount() +
                    "&" + Parameter.ORDER_ID + "=" + payGateResponse.getOrderId() +
                    "&" + Parameter.ORDER_INFO + "=" + payGateResponse.getOrderInfo() +
                    "&" + Parameter.ORDER_TYPE + "=" + payGateResponse.getOrderType() +
                    "&" + Parameter.TRANS_ID + "=" + payGateResponse.getTransId() +
                    "&" + Parameter.MESSAGE + "=" + payGateResponse.getMessage() +
                    "&" + Parameter.LOCAL_MESSAGE + "=" + payGateResponse.getLocalMessage() +
                    "&" + Parameter.DATE + "=" + payGateResponse.getResponseDate() +
                    "&" + Parameter.ERROR_CODE + "=" + payGateResponse.getErrorCode() +
                    "&" + Parameter.PAY_TYPE + "=" + payGateResponse.getPayType() +
                    "&" + Parameter.EXTRA_DATA + "=" + payGateResponse.getExtraData();

            String signatureClient = Encoder.signHmacSHA256(rawData, partnerInfo.getSecretKey());

            LogUtils.debug("[Payment Result] rawData: " + rawData + ", [Signature] -> " + signatureClient);
            if (signatureClient.equals(payGateResponse.getSignature())) {
                int errorCode = 0;
                String message = "Success";
                String localMessage = "Thành công";
//                Xử lý kết quả thanh toán - Process Payment Result
                       
//        String orderType,                         
//        String payType,                         
//        Date responseDate
                PayGateResponse responseIPNMoMo = new PayGateResponse(
                        payGateResponse.getPartnerCode(), 
                        payGateResponse.getOrderId(), 
                        payGateResponse.getOrderInfo(), 
                        payGateResponse.getAccessKey(),
                        payGateResponse.getAmount(), 
                        payGateResponse.getSignature(), 
                        payGateResponse.getExtraData(), 
                        payGateResponse.getRequestId(),
                        payGateResponse.getNotifyUrl(),
                        payGateResponse.getReturnUrl(),
                        payGateResponse.getRequestType(),
                        errorCode, message, localMessage, 
                        payGateResponse.getTransId(),
                        payGateResponse.getOrderType(),
                        payGateResponse.getPayType(),
                        payGateResponse.getResponseDate()
                );
                
                return responseIPNMoMo;
            }
            PayGateResponse responseIPNMoMo = new PayGateResponse(
                    payGateResponse.getPartnerCode(), 
                        payGateResponse.getOrderId(), 
                        payGateResponse.getOrderInfo(), 
                        payGateResponse.getAccessKey(),
                        payGateResponse.getAmount(), 
                        payGateResponse.getSignature(), 
                        payGateResponse.getExtraData(), 
                        payGateResponse.getRequestId(),
                        payGateResponse.getNotifyUrl(),
                        payGateResponse.getReturnUrl(),
                        payGateResponse.getRequestType(),
                        101, "Lỗi", "error", 
                        payGateResponse.getTransId(),
                        payGateResponse.getOrderType(),
                        payGateResponse.getPayType(),
                        payGateResponse.getResponseDate()
                    );
//          200 - Everything will be 200 Oke

            return responseIPNMoMo;
        } catch (Exception exception) {
            LogUtils.error("[Payment Result] " + exception);
            throw new IllegalArgumentException("Invalid params capture MoMo Request");
        }
    }
}
