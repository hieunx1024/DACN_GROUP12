package com.laptrinhoop.service.impl;

import com.laptrinhoop.dao.impl.PartnerDAO;
import com.laptrinhoop.dao.impl.TransactionDAO;
import com.laptrinhoop.dto.PaymentRequest;
import com.laptrinhoop.dto.PaymentTokenResponse;
import com.laptrinhoop.dto.UrlGeneratorResponse;
import com.laptrinhoop.entity.Partner;
import com.laptrinhoop.entity.Transaction;
import com.laptrinhoop.enums.PartnerCode;
import com.laptrinhoop.service.IPaymentService;
import com.laptrinhoop.utils.IdGenerator;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.text.MessageFormat;
import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
@Slf4j
public class PaymentService implements IPaymentService {
    private final PartnerDAO partnerDAO;

    private final TransactionDAO transactionDAO;

    @Override
    public Partner findByCode(String code) {
        return partnerDAO.findByCode(code).get();
    }

    private final VNPaymentGatewayDecorator paymentProxy;

    @Override
    public UrlGeneratorResponse generateLink(PaymentRequest paymentRequest) {
        Optional<Partner> partnerOps = partnerDAO.findByCode(paymentRequest.getPartnerCode().name());
        if (!partnerOps.isPresent()) {
            return UrlGeneratorResponse.builder()
                    .errorMessage(MessageFormat.format("Không tìm thấy {0}", paymentRequest.getPartnerCode().name()))
                    .isSuccess(Boolean.FALSE)
                    .build();
        }
        String transactionId = IdGenerator.generateInvoiceId(paymentRequest.getPartnerCode());

        Optional<Transaction> transOps = transactionDAO.findByTransactionIdAndUsername(transactionId, paymentRequest.getUsername());
        if (!transOps.isPresent()) {
            paymentRequest.setInvoiceId(transactionId);
            Transaction newTran = Transaction.from(partnerOps.get(), paymentRequest);
            transactionDAO.create(newTran);
            this.getPaymentUrl(partnerOps.get(), paymentRequest);
        }

        return this.getPaymentUrl(partnerOps.get(), paymentRequest);
    }

    private UrlGeneratorResponse getPaymentUrl(Partner partner, PaymentRequest paymentRequest) {
        try {
            PaymentTokenResponse paymentTokenResponse = paymentProxy.getPaymentToken(partner, paymentRequest);
            if (!paymentTokenResponse.isSuccess()) {
                log.error("[PaymentClientService] cannot get payment token, reason:" + paymentTokenResponse.getPartnerDesc() + ", error code: " + paymentTokenResponse.getPartnerCode());
                return UrlGeneratorResponse.failedWith(MessageFormat.format("Không thể chuyển hướng thanh toán qua {0}", paymentRequest.getPartnerCode().name()));
            }
            return UrlGeneratorResponse.create(paymentTokenResponse.getWebPaymentUrl());
        } catch (Exception ex) {
            log.error("[PaymnetService] getPaymentUrl -- exception:{}", ex.getMessage(), ex);
            return UrlGeneratorResponse.failedWith(MessageFormat.format("Có lỗi xảy ra trong quá trình kết nối với {0}", paymentRequest.getPartnerCode().name()));
        }
    }

    @Override
    public List<Partner> findAll() {
        return partnerDAO.findAll();
    }
}
