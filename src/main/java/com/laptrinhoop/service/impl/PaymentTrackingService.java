package com.laptrinhoop.service.impl;

import com.laptrinhoop.constants.Constant;
import com.laptrinhoop.dao.impl.TransactionDAO;
import com.laptrinhoop.dto.CallBackRequest;
import com.laptrinhoop.dto.CallBackResponse;
import com.laptrinhoop.entity.Customer;
import com.laptrinhoop.entity.Transaction;
import com.laptrinhoop.enums.TransactionStatus;
import com.laptrinhoop.service.IAccountService;
import com.laptrinhoop.service.IPaymentTrackingService;
import lombok.RequiredArgsConstructor;
import org.apache.commons.lang3.ObjectUtils;
import org.springframework.stereotype.Service;
import com.laptrinhoop.dto.CallBackResponse;

import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class PaymentTrackingService implements IPaymentTrackingService {
    private final TransactionDAO transactionDAO;

    @Override
    public CallBackResponse redirectTracking(CallBackRequest redirectRequest) {
        // Lấy mã giao dịch từ yêu cầu callback
        String transactionId = redirectRequest.getInvoiceNo();
        Optional<Transaction> optionalTransaction = transactionDAO.findByTransactionId(transactionId);

        // Mã trả về khi không có lỗi
        CallBackResponse.CallBackResponseCode codeResponse = CallBackResponse.CallBackResponseCode.SUCCESS;

        // Kiểm tra nếu không tìm thấy giao dịch
        if (!optionalTransaction.isPresent()) {
            return CallBackResponse.create(codeResponse.INVALID_TRANSACTION);
        }

        // Lấy thông tin giao dịch cũ
        Transaction oldTransaction = optionalTransaction.get();

        // Kiểm tra số tiền giao dịch có khớp không
        if (redirectRequest.getAmount().compareTo(oldTransaction.getAmount()) != 0) {
            return CallBackResponse.create(codeResponse.INVALID_AMOUNT);
        }

        // Kiểm tra trạng thái giao dịch (nếu đã xử lý thì không xử lý lại)
        if (!TransactionStatus.NEW.equals(oldTransaction.getStatus())) {
            return CallBackResponse.create(codeResponse.REQUEST_PROCESSED);
        }

        // Đồng bộ thông tin giao dịch cũ với yêu cầu callback mới
        Transaction update = oldTransaction.sync(redirectRequest);

        // Lưu giao dịch đã được cập nhật vào cơ sở dữ liệu
        Transaction saveTransaction = transactionDAO.create(update);

        // Kiểm tra nếu lưu không thành công
        if (saveTransaction.getId() <= 0) {
            return CallBackResponse.create(codeResponse.ERROR_SYSTEM);
        }

        // Kiểm tra nếu mã đối tác là "24", thì hủy giao dịch
        if (redirectRequest.getPartnerCode() != null && redirectRequest.getPartnerCode().equals("24")) {
            codeResponse = codeResponse.CANCEL_TRANSACTION;
        }

        // Trả về mã trạng thái callback
        return CallBackResponse.create(codeResponse);
    }
}
