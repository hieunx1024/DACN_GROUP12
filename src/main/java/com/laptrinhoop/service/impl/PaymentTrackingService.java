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

import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class PaymentTrackingService implements IPaymentTrackingService {
    private final TransactionDAO transactionDAO;
    @Override
    public CallBackResponse redirectTracking(CallBackRequest redirectRequest) {
        String transactionId = redirectRequest.getInvoiceNo();
        Optional<Transaction> optionalTransaction = transactionDAO.findByTransactionId(transactionId);
        CallBackResponse.CallBackResponseCode codeResponse = CallBackResponse.CallBackResponseCode.SUCCESS;
        if (!optionalTransaction.isPresent()) {
            return CallBackResponse.create(codeResponse.INVALID_TRANSACTION);
        }

        Transaction oldTransaction = optionalTransaction.get();
        if (redirectRequest.getAmount().compareTo(oldTransaction.getAmount()) != 0) {
            return CallBackResponse.create(codeResponse.INVALID_AMOUNT);
        }
        if (!TransactionStatus.NEW.equals(oldTransaction.getStatus())) {
            return CallBackResponse.create(codeResponse.REQUEST_PROCESSED);
        }
        Transaction update = oldTransaction.sync(redirectRequest);
        Transaction saveTransaction = transactionDAO.create(update);
        if (saveTransaction.getId() <= 0) {
            return CallBackResponse.create(codeResponse.ERROR_SYSTEM);
        }

        if(redirectRequest.getPartnerCode() != null &&  redirectRequest.getPartnerCode().equals("24")){
            codeResponse = codeResponse.CANCEL_TRANSACTION;
        }
        return CallBackResponse.create(codeResponse);
    }
}
