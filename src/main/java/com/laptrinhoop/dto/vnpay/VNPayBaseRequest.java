package com.laptrinhoop.dto.vnpay;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.experimental.SuperBuilder;


import javax.validation.constraints.NotNull;

@AllArgsConstructor
@SuperBuilder
public class VNPayBaseRequest {
    @JsonProperty("vnp_Command")
    @NotNull
    String command;
    @JsonProperty("vnp_Version")
    @NotNull
    String version;
    @JsonProperty("vnp_TmnCode")
    @NotNull
    String tmdCode;
    @JsonProperty("vnp_TxnRef")
    @NotNull
    String txnRef;
    @JsonProperty("vnp_OrderInfo")
    @NotNull
    String orderInfo;
    @JsonProperty("vnp_IpAddr")
    @NotNull
    String ipAddress;
    @JsonProperty("vnp_CreateDate")
    @NotNull
    String createDate;
}
