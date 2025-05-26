package com.laptrinhoop.constants;

public enum VNPayCallBackResponseCode {

    SUCCESS("00", "Success"),
    INVALID_AMOUNT("01", "Invalid Amount"),
    INVALID_TRANSACTION("02", "Invalid Transaction"),
    ERROR_SYSTEM("03", "System Error"),
    REQUEST_PROCESSED("04", "Request Processed"),
    CANCEL_TRANSACTION("05", "Transaction Canceled"),
    INVALID_SIGNATURE("06", "Invalid Signature"); // Thêm vào nếu chưa có

    private final String code;
    private final String message;

    VNPayCallBackResponseCode(String code, String message) {
        this.code = code;
        this.message = message;
    }

    public String getCode() {
        return code;
    }

    public String getMessage() {
        return message;
    }

    public static VNPayCallBackResponseCode from(String code) {
        for (VNPayCallBackResponseCode responseCode : values()) {
            if (responseCode.getCode().equals(code)) {
                return responseCode;
            }
        }
        return ERROR_SYSTEM; // Default value if code is not found
    }
}
