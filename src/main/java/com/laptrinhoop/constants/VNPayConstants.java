package com.laptrinhoop.constants;

import lombok.experimental.UtilityClass;


@UtilityClass
public final class VNPayConstants {

    public static final Integer MULTIPLY_AMOUNT = 100;
    public static final String VND_CURRENCY_CODE = "VND";

    public static final String SYSTEM = "System";
    public static final String FORMAT_DATE = "yyyyMMddHHmmss";
    public static final String DEFAULT_LOCALE = "vi";

    @UtilityClass
    public static final class VNPayParams {
        public static final String SECURE_HASH = "vnp_SecureHash";
        public static final String SECURE_HASH_TYPE = "vnp_SecureHashType";

    }

    @UtilityClass
    public static final class ResponseCode {
        public static final String SUCCESS = "00";

    }


}
