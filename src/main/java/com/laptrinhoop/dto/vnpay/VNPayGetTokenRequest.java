package com.laptrinhoop.dto.vnpay;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.laptrinhoop.constants.Constant;
import com.laptrinhoop.constants.VNPayCommand;
import com.laptrinhoop.constants.VNPayConstants;
import com.laptrinhoop.constants.VNPayVersion;
import com.laptrinhoop.dto.PaymentRequest;
import com.laptrinhoop.entity.Partner;
import com.laptrinhoop.utils.DateTimeUtil;
import lombok.experimental.SuperBuilder;

import javax.validation.constraints.NotNull;
import java.util.Optional;

@SuperBuilder(builderMethodName = "getTokenRequestBuilder")
public class VNPayGetTokenRequest {

    @JsonProperty("vnp_Amount")
    @NotNull
    String amount;

    @JsonProperty("vnp_Command")
    @NotNull
    String command;

    @JsonProperty("vnp_CreateDate")
    @NotNull
    String createDate;

    @JsonProperty("vnp_CurrCode")
    @NotNull
    String currCode;

    @JsonProperty("vnp_ExpireDate")
    @NotNull
    String expireDate;

    @JsonProperty("vnp_IpAddr")
    @NotNull
    String ipAddress;

    @JsonProperty("vnp_Locale")
    String locale;

    @JsonProperty("vnp_OrderInfo")
    @NotNull
    String orderInfo;

    @JsonProperty("vnp_OrderType")
    @NotNull
    String orderType;

    @JsonProperty("vnp_ReturnUrl")
    @NotNull
    String returnUrl;

    @JsonProperty("vnp_TmnCode")
    @NotNull
    String tmdCode;

    @JsonProperty("vnp_TxnRef")
    @NotNull
    String txnRef;

    @JsonProperty("vnp_Version")
    @NotNull
    String version;

    public static VNPayGetTokenRequest from(Partner partner, PaymentRequest paymentRequest) {
        return VNPayGetTokenRequest
                .getTokenRequestBuilder()
                .version(VNPayVersion.VERSION_2_1_0.getVersion())
                .command(VNPayCommand.PAY.getVnpCommand())
                .tmdCode(partner.getMerchantId())
                .orderInfo(paymentRequest.getDescription())
                .orderType("topup")
                .expireDate(DateTimeUtil.expireTimeInMinutes(15, VNPayConstants.FORMAT_DATE)) // Expiration time set to
                                                                                              // 15 minutes
                .ipAddress(Optional.ofNullable(paymentRequest.getIpAddress()).orElse("127.0.0.1")) // Get IP from
                                                                                                   // paymentRequest if
                                                                                                   // available
                .createDate(DateTimeUtil.nowToString(VNPayConstants.FORMAT_DATE))
                .txnRef(paymentRequest.getInvoiceId())
                .amount(String.valueOf(paymentRequest.getAmount().longValue() * 100)) // Amount in cents
                .currCode(paymentRequest.getCurrencyCode().toUpperCase())
                .locale(Optional.ofNullable(paymentRequest.getLocale()).orElse("vi")) // Default locale to "vi"
                .returnUrl(paymentRequest.getRedirectUrl())
                .build();
    }
}
