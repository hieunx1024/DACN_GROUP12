package com.laptrinhoop.constants;

import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
public enum VNPayCallBackResponseCode {
    SUCCESS("00", "Giao dịch thành công"),
    INVALID_TRANSACTION("01", "Giao dịch không tìm thấy"),
    REQUEST_PROCESSED("02", "Giao dịch gần đây đã được xác nhận"),
    INVALID_AMOUNT("04", "Số tiền không đúng"),
    INVALID_SIGNATURE("97", "Giao dịch không chính xác"),
    CANCEL_TRANSACTION("24","Giao dịch bị huỷ bỏ"),
    ERROR_SYSTEM("99", "Có lỗi xảy ra trong quá trình thanh toán");

    @Getter
    private String code;
    @Getter
    private String message;

    public static VNPayCallBackResponseCode from(String code) {
        for (VNPayCallBackResponseCode i : VNPayCallBackResponseCode.values()) {
            if (i.getCode().equals(code)) {
                return i;
            }
        }
        return INVALID_TRANSACTION;
    }
}
