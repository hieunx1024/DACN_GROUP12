package com.laptrinhoop.dto;

import com.laptrinhoop.enums.PartnerCode;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotNull;
import java.math.BigDecimal;
import java.time.LocalDateTime;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class PaymentRequest {
    private String locale;
    @NotNull(message = "partnerCode cannot null")
    private PartnerCode partnerCode;
    private String invoiceId;
    private String description;
    @NotNull(message = "amount cannot null")
    private BigDecimal amount;
    private String currencyCode = "VND";
    private String ipAddress;
    private LocalDateTime requestTime = LocalDateTime.now();
    private String username;
    @NotNull(message = "redirectUrl can not null or empty")
    private String redirectUrl;


}