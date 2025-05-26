package com.laptrinhoop.dto;

import lombok.Data;

public class CallBackResponse {

    // Enum to represent different response statuses
    public enum Result {
        SUCCESS,
        INVALID_AMOUNT,
        INVALID_TRANSACTION,
        ERROR_SYSTEM,
        REQUEST_PROCESSED,
        CANCEL_TRANSACTION
    }

    // Field to store the result of the callback response
    private Result result;

    // Getter for result
    public Result getResult() {
        return result;
    }

    // Setter for result
    public void setResult(Result result) {
        this.result = result;
    }

    // Other fields and methods related to the callback response

    // Factory method to create a new CallBackResponse based on a result
    public static CallBackResponse create(Result result) {
        CallBackResponse response = new CallBackResponse();
        response.setResult(result);
        return response;
    }
}
