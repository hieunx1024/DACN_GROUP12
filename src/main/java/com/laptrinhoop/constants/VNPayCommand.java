package com.laptrinhoop.constants;

import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
public enum VNPayCommand {

    PAY("pay"),
    QUERY("querydr"),
    REFUND("refund");

    @Getter
    private String vnpCommand;
}
