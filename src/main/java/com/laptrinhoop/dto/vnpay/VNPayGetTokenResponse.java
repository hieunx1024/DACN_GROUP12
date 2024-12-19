package com.laptrinhoop.dto.vnpay;


import com.laptrinhoop.constants.VNPayConstants;
import com.laptrinhoop.dto.PaymentTokenResponse;
import lombok.Builder;
import lombok.Data;
import lombok.Getter;

@Data
@Builder
@Getter
public class VNPayGetTokenResponse {
    private final String paymentToken;

    public PaymentTokenResponse to(){
        return PaymentTokenResponse
                .builder()
                .webPaymentUrl(paymentToken)
                .partnerDesc("Get VNPay token successfully")
                .partnerCode(VNPayConstants.ResponseCode.SUCCESS)
                .isSuccess(Boolean.TRUE)
                .build();
    }
}
