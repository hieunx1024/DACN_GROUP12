package com.laptrinhoop.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
public enum EnumTemplate {
    NEW_ACCOUNT(0);

    @Getter
    private int code;

}
