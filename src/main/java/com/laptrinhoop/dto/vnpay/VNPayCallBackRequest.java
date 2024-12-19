package com.laptrinhoop.dto.vnpay;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.laptrinhoop.constants.VNPayCallBackResponseCode;
import com.laptrinhoop.constants.VNPayConstants;
import com.laptrinhoop.dto.CallBackRequest;
import com.laptrinhoop.utils.DateTimeUtil;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;

@NoArgsConstructor
@Data
public class VNPayCallBackRequest {

    @JsonProperty("vnp_ResponseCode")
    private String responseCode;
    @JsonProperty("vnp_Amount")
    private BigDecimal amount;
    @JsonProperty("vnp_BankCode")
    private String bankCode;
    @JsonProperty("vnp_BankTranNo")
    private String bankTransNo;
    @JsonProperty("vnp_CardType")
    private String cardType;
    @JsonProperty("vnp_PayDate")
    private String transactionDate;
    @JsonProperty("vnp_TransactionNo")
    private String partnerTransNo;
    @JsonProperty("vnp_TxnRef")
    private String txnRef;
    @JsonProperty("vnp_OrderInfo")
    private String transactionInfo;



    public CallBackRequest to() {
        VNPayCallBackResponseCode vnPayResponse = VNPayCallBackResponseCode.from(responseCode);
        return CallBackRequest
                .builder()
                .partnerCode(responseCode)
                .invoiceNo(txnRef)
                .partnerDesc(vnPayResponse.getMessage())
                .partnerTransactionId(partnerTransNo)
                .bankTransactionId(bankTransNo)
                .isSuccess(vnPayResponse.equals(VNPayCallBackResponseCode.SUCCESS))
                .cardType(cardType)
                .bankCode(bankCode)
                .partnerInfo(transactionInfo)
                .amount(amount.divide(BigDecimal.valueOf(VNPayConstants.MULTIPLY_AMOUNT)))
                .partnerTransactionTime(DateTimeUtil.stringToLocalDateTime(transactionDate, VNPayConstants.FORMAT_DATE))
                .build();
    }


}
