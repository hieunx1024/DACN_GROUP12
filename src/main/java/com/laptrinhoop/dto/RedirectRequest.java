package com.laptrinhoop.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;

@NoArgsConstructor
@Data
public class RedirectRequest {
    @JsonProperty("vnp_ResponseCode")
    private String responseCode;

    @JsonProperty("vnp_Amount")
    private BigDecimal amount;

    @JsonProperty("vnp_BankCode")
    private String bankCode;

    @JsonProperty("vnp_TxnRef")
    private String txnRef;


}
