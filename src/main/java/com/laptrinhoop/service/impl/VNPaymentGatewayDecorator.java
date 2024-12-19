package com.laptrinhoop.service.impl;

import com.laptrinhoop.converter.Jksonizer;
import com.laptrinhoop.dto.PaymentRequest;
import com.laptrinhoop.dto.PaymentTokenResponse;
import com.laptrinhoop.dto.vnpay.VNPayGetTokenRequest;
import com.laptrinhoop.dto.vnpay.VNPayGetTokenResponse;
import com.laptrinhoop.entity.Partner;
import com.laptrinhoop.properties.VNPayProperties;
import com.laptrinhoop.service.IPaymentGatewayDecorator;
import com.laptrinhoop.utils.DataEncryptor;
import com.laptrinhoop.utils.QueryUtils;
import lombok.RequiredArgsConstructor;
import org.apache.commons.codec.digest.HmacAlgorithms;
import org.apache.commons.codec.digest.HmacUtils;
import org.springframework.stereotype.Service;

import java.io.UnsupportedEncodingException;
import java.util.Map;

@Service
@RequiredArgsConstructor
public class VNPaymentGatewayDecorator implements IPaymentGatewayDecorator {

    private final VNPayProperties vnPayProperties;


    @Override
    public PaymentTokenResponse getPaymentToken(Partner partner, PaymentRequest paymentRequest) throws UnsupportedEncodingException {
        VNPayGetTokenRequest request = VNPayGetTokenRequest.from(partner, paymentRequest);
        Map<String, String> vnpParams = Jksonizer.getObjectMapper().convertValue(request, Map.class);
        String query = QueryUtils.generateQuery(vnpParams);
        String hash = new HmacUtils(HmacAlgorithms.HMAC_SHA_512, DataEncryptor.decrypt(partner.getSecretKey(), vnPayProperties.getSecretKey()))
                .hmacHex(query);
        String paymentToken = new StringBuilder(partner.getHost())
                .append(partner.getGenerateUri())
                .append("?")
                .append(query)
                .append("&vnp_SecureHash=")
                .append(hash)
                .toString();
        VNPayGetTokenResponse vnPayToken = VNPayGetTokenResponse
                .builder()
                .paymentToken(paymentToken)
                .build();

        return vnPayToken.to();
    }
}
