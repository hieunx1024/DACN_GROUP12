package com.laptrinhoop.dto;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class PaymentTokenResponse {
    private String webPaymentUrl;
    private String partnerCode;
    private String partnerDesc;
    private Boolean isSuccess;

    public Boolean isSuccess() {
        return this.isSuccess;
    }

    public static PaymentTokenResponse fail(String errorCode, String msg) {
        return PaymentTokenResponse
                .builder()
                .isSuccess(false)
                .partnerDesc(msg)
                .partnerCode(errorCode)
                .build();
    }

}
