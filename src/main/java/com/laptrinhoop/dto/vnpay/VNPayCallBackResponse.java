package com.laptrinhoop.dto.vnpay;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.laptrinhoop.constants.VNPayCallBackResponseCode;
import com.laptrinhoop.constants.VNPayConstants;
import com.laptrinhoop.dto.CallBackResponse;
import com.laptrinhoop.utils.DateTimeUtil;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.Getter;

import java.math.BigDecimal;

@AllArgsConstructor
@Data
public class VNPayCallBackResponse {
    @JsonProperty("RspCode")
    private String response;
    @JsonProperty("Message")
    private String message;

    private String transactionId;

    private String transactionNo;

    private String amount;

    private String transactionInfo;

    private String bankCode;

    private String cardType;

    private String transactionDate;

    private VNPayCallBackResponse(String code, String mes){
        this.response = code;
        this.message = mes;
    }

    public static VNPayCallBackResponse from(CallBackResponse callBackResponse,VNPayCallBackRequest item) {
        VNPayCallBackResponseCode vnPayCallBackResponseCode = null;
        switch (callBackResponse.getResult()) {
            case SUCCESS:
                vnPayCallBackResponseCode = VNPayCallBackResponseCode.SUCCESS;
                break;
            case INVALID_AMOUNT:
                vnPayCallBackResponseCode = VNPayCallBackResponseCode.INVALID_AMOUNT;
                break;
            case INVALID_TRANSACTION:
                vnPayCallBackResponseCode = VNPayCallBackResponseCode.INVALID_TRANSACTION;
                break;
            case ERROR_SYSTEM:
                vnPayCallBackResponseCode = VNPayCallBackResponseCode.ERROR_SYSTEM;
                break;
            case REQUEST_PROCESSED:
                vnPayCallBackResponseCode = VNPayCallBackResponseCode.REQUEST_PROCESSED;
                break;
            case CANCEL_TRANSACTION:
                vnPayCallBackResponseCode = VNPayCallBackResponseCode.CANCEL_TRANSACTION;
                break;
            default:
                vnPayCallBackResponseCode = VNPayCallBackResponseCode.ERROR_SYSTEM;
        }
        return VNPayCallBackResponse.from(vnPayCallBackResponseCode,item);
    }

    public static VNPayCallBackResponse from(VNPayCallBackResponseCode vnPayCallBackResponseCode,VNPayCallBackRequest item) {
        VNPayCallBackResponse response =   new VNPayCallBackResponse(vnPayCallBackResponseCode.getCode(), vnPayCallBackResponseCode.getMessage());
        response.setAmount(item.getAmount().toString());
        response.setBankCode(item.getBankCode());
        response.setCardType(item.getCardType());
        response.setTransactionId(item.getTxnRef());
        response.setTransactionInfo(item.getTransactionInfo());
        response.setTransactionNo(item.getPartnerTransNo());
        response.setTransactionDate(item.getTransactionDate());
        response.setAmount(item.getAmount().divide(BigDecimal.valueOf(VNPayConstants.MULTIPLY_AMOUNT)).toString());
        response.setTransactionDate(DateTimeUtil.stringToLocalDateTime(item.getTransactionDate(), VNPayConstants.FORMAT_DATE).toString().replace("T"," "));
        return response;
    }
}
