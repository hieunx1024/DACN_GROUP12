package com.laptrinhoop.dto;

import lombok.Builder;
import lombok.Getter;


import javax.validation.constraints.NotNull;
import java.math.BigDecimal;
import java.time.LocalDateTime;

@Builder
@Getter
public class CallBackRequest {

    String merchantID;
    @NotNull
    String invoiceNo;
    String cardNo;
    BigDecimal amount;
    String currencyCode;
    String partnerTransactionId;
    String bankTransactionId;
    LocalDateTime partnerTransactionTime;
    String partnerCode;
    Boolean isSuccess;
    String cardType;
    String partnerDesc;
    String bankCode;
    String partnerInfo;

}
