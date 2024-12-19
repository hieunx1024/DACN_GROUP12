package com.laptrinhoop.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
@Getter
public enum EnumFunctionType {
    CREATE_ACCOUNT(0),

    FORGOT_PASSWORD(1),

    RESET_PASSWORD(2),

    CONFIRM_ORDER(3),

    ONLINE_PAYMENT(4);

    private int code;


}
