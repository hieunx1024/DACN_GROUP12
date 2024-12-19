//package com.laptrinhoop.dto.vnpay;
//
//import com.fasterxml.jackson.annotation.JsonProperty;
//import com.laptrinhoop.constants.VNPayConstants;
//import com.vietanlife.renewal.constants.vnpay.VNPayConstants;
//import com.vietanlife.renewal.dto.FetchInquiryResponse;
//import com.vietanlife.renewal.utils.DateTimeUtil;
//import lombok.AllArgsConstructor;
//import lombok.Builder;
//import lombok.Getter;
//import lombok.NoArgsConstructor;
//import org.apache.commons.lang3.StringUtils;
//
//import java.math.BigDecimal;
//
//@Getter
//@Builder
//@NoArgsConstructor
//@AllArgsConstructor
//public class VNPayGetInquiryResponse {
//    @JsonProperty("vnp_TmnCode")
//    String tmnCode;
//    @JsonProperty("vnp_TxnRef")
//    String txnRef;
//    @JsonProperty("vnp_Amount")
//    BigDecimal amount;
//    @JsonProperty("vnp_OrderInfo")
//    String orderInfo;
//    @JsonProperty("vnp_ResponseCode")
//    String responseCode;
//    @JsonProperty("vnp_BankCode")
//    String bankCode;
//    @JsonProperty("vnp_Message")
//    String responseDesc;
//    @JsonProperty("vnp_PayDate")
//    String payDate;
//    @JsonProperty("vnp_TransactionNo")
//    String transactionNo;
//    @JsonProperty("vnp_TransactionType")
//    String transactionType;
//    @JsonProperty("vnp_TransactionStatus")
//    String transactionStatus;
//
//
//    public boolean isSuccess() {
//        return StringUtils.isNotEmpty(responseCode) && responseCode.equals(VNPayConstants.ResponseCode.SUCCESS);
//    }
//
//
//
//
//
//}
