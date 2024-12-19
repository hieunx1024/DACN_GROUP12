//package com.laptrinhoop.dto.vnpay;
//
//import com.fasterxml.jackson.annotation.JsonProperty;
//import com.vietanlife.renewal.constants.vnpay.VNPayCommand;
//import com.vietanlife.renewal.constants.vnpay.VNPayConstants;
//import com.vietanlife.renewal.constants.vnpay.VNPayVersion;
//import com.vietanlife.renewal.dto.FetchInquiryRequest;
//import com.vietanlife.renewal.utils.DateTimeUtil;
//import lombok.experimental.SuperBuilder;
//import org.jetbrains.annotations.NotNull;
//
//
//@SuperBuilder(builderMethodName = "getInquiryRequestBuilder")
//public class VNPayGetInquiryRequest extends VNPayBaseRequest {
//    @JsonProperty("vnp_TransDate")
//    @NotNull
//    String transDate;
//
//
//    public static VNPayGetInquiryRequest from(FetchInquiryRequest fetchInquiryRequest) {
//        return VNPayGetInquiryRequest
//                .getInquiryRequestBuilder()
//                .version(VNPayVersion.VERSION_2_1_0.getVersion())
//                .command(VNPayCommand.QUERY.getVnpCommand())
//                .tmdCode(fetchInquiryRequest.getMerchantId())
//                .orderInfo("Get info transaction")
//                .ipAddress(fetchInquiryRequest.getIpAddress())
//                .createDate(DateTimeUtil.nowToString(VNPayConstants.FORMAT_DATE))
//                .txnRef(fetchInquiryRequest.getInvoiceNo())
//                .transDate(DateTimeUtil.nowToString(VNPayConstants.FORMAT_DATE))
//                .build();
//    }
//}