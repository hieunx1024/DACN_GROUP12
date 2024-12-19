package com.laptrinhoop.service;

import com.laptrinhoop.dto.PaymentRequest;
import com.laptrinhoop.dto.UrlGeneratorResponse;
import com.laptrinhoop.entity.Partner;
import com.laptrinhoop.enums.PartnerCode;

import java.util.List;

public interface IPaymentService {
    UrlGeneratorResponse generateLink(PaymentRequest paymentRequest);

    Partner findByCode(String code);

    List<Partner> findAll();
}
