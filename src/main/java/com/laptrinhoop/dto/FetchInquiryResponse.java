package com.laptrinhoop.dto;

import lombok.Builder;
import lombok.Getter;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Builder
@Getter
public class FetchInquiryResponse {
    private String invoiceNo;
    private BigDecimal amount;
    private LocalDateTime transactionDateTime;
    private String bankCode;
    private String cardType;
    private String partnerStatus;
    private String partnerTransactionId;
    private String bankTransactionId;
    private boolean isSuccess;
    private String respCode;
    private String respDesc;


}
