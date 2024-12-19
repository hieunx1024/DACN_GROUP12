package com.laptrinhoop.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
@AllArgsConstructor
public class CallBackResponse {

    public enum CallBackResponseCode {
        SUCCESS(0),
        INVALID_TRANSACTION(1),
        REQUEST_PROCESSED(2),
        INVALID_AMOUNT(3),
        ERROR_SYSTEM(4),
        CANCEL_TRANSACTION(5);
        int code;

        CallBackResponseCode(int code) {
            this.code = code;
        }

    }

    private CallBackResponseCode result;

    public static CallBackResponse create(CallBackResponseCode result) {
        return new CallBackResponse(result);
    }
}
