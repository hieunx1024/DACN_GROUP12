package com.laptrinhoop.service;



import com.laptrinhoop.dto.PaymentRequest;
import com.laptrinhoop.dto.PaymentTokenResponse;
import com.laptrinhoop.entity.Partner;

import java.io.UnsupportedEncodingException;


public interface IPaymentGatewayDecorator {

    PaymentTokenResponse getPaymentToken(Partner partner, PaymentRequest paymentRequest) throws UnsupportedEncodingException;

}
