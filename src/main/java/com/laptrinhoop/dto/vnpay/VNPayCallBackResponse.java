package com.laptrinhoop.dto.vnpay;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.laptrinhoop.constants.VNPayCallBackResponseCode;
import com.laptrinhoop.constants.VNPayConstants;
import com.laptrinhoop.dto.CallBackResponse;
import com.laptrinhoop.utils.DateTimeUtil;
import lombok.AllArgsConstructor;
import lombok.Data;

import java.math.BigDecimal;

@AllArgsConstructor
@Data
public class VNPayCallBackResponse {

    @JsonProperty("RspCode")
    private String response;

    @JsonProperty("Message")
    private String message;

    private String transactionId;

    private String transactionNo;

    private String amount;

    private String transactionInfo;

    private String bankCode;

    private String cardType;

    private String transactionDate;

    private VNPayCallBackResponse(String code, String mes) {
        this.response = code;
        this.message = mes;
    }

    /**
     * Tạo đối tượng VNPayCallBackResponse từ CallBackResponse và
     * VNPayCallBackRequest.
     */
    public static VNPayCallBackResponse from(CallBackResponse callBackResponse, VNPayCallBackRequest item) {
        // Ánh xạ kết quả từ CallBackResponse sang VNPayCallBackResponseCode
        VNPayCallBackResponseCode responseCode = mapToResponseCode(callBackResponse);

        return from(responseCode, item);
    }

    /**
     * Tạo đối tượng VNPayCallBackResponse từ VNPayCallBackResponseCode và
     * VNPayCallBackRequest.
     */
    public static VNPayCallBackResponse from(VNPayCallBackResponseCode vnPayCallBackResponseCode,
            VNPayCallBackRequest item) {
        // Tạo phản hồi với mã và thông điệp đã cung cấp
        VNPayCallBackResponse response = new VNPayCallBackResponse(
                vnPayCallBackResponseCode.getCode(),
                vnPayCallBackResponseCode.getMessage());

        // Chuyển đổi số tiền sang định dạng đúng (chia cho MULTIPLY_AMOUNT)
        BigDecimal amount = item.getAmount().divide(BigDecimal.valueOf(VNPayConstants.MULTIPLY_AMOUNT));

        // Gán các trường vào đối tượng phản hồi
        response.setAmount(amount.toString());
        response.setBankCode(item.getBankCode());
        response.setCardType(item.getCardType());
        response.setTransactionId(item.getTxnRef());
        response.setTransactionInfo(item.getTransactionInfo());
        response.setTransactionNo(item.getPartnerTransNo());

        // Chuyển đổi ngày giao dịch và gán vào
        String formattedDate = DateTimeUtil.stringToLocalDateTime(item.getTransactionDate(), VNPayConstants.FORMAT_DATE)
                .toString().replace("T", " ");
        response.setTransactionDate(formattedDate);

        return response;
    }

    /**
     * Ánh xạ kết quả từ CallBackResponse sang VNPayCallBackResponseCode.
     */
    private static VNPayCallBackResponseCode mapToResponseCode(CallBackResponse callBackResponse) {
        // Truy cập đến enum Result từ CallBackResponse
        CallBackResponse.Result result = callBackResponse.getResult();

        switch (result) {
            case SUCCESS:
                return VNPayCallBackResponseCode.SUCCESS;
            case INVALID_AMOUNT:
                return VNPayCallBackResponseCode.INVALID_AMOUNT;
            case INVALID_TRANSACTION:
                return VNPayCallBackResponseCode.INVALID_TRANSACTION;
            case ERROR_SYSTEM:
                return VNPayCallBackResponseCode.ERROR_SYSTEM;
            case REQUEST_PROCESSED:
                return VNPayCallBackResponseCode.REQUEST_PROCESSED;
            case CANCEL_TRANSACTION:
                return VNPayCallBackResponseCode.CANCEL_TRANSACTION;
            default:
                return VNPayCallBackResponseCode.ERROR_SYSTEM; // Default case
        }
    }
}
