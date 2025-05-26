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
        public PaymentTokenResponse getPaymentToken(Partner partner, PaymentRequest paymentRequest)
                        throws UnsupportedEncodingException {
                // Tạo đối tượng yêu cầu token từ VNPay
                VNPayGetTokenRequest request = VNPayGetTokenRequest.from(partner, paymentRequest);

                // Chuyển đổi đối tượng thành Map để tạo chuỗi tham số
                Map<String, String> vnpParams = Jksonizer.getObjectMapper().convertValue(request, Map.class);

                // Tạo chuỗi query string từ các tham số
                String query = QueryUtils.generateQuery(vnpParams);

                // Tính toán chữ ký bảo mật (vnp_SecureHash) bằng HMAC_SHA_512
                String secretKey = DataEncryptor.decrypt(partner.getSecretKey(), vnPayProperties.getSecretKey());
                String hash = new HmacUtils(HmacAlgorithms.HMAC_SHA_512, secretKey).hmacHex(query);

                // Tạo token thanh toán (paymentToken) bằng cách kết hợp các tham số và chữ ký
                // bảo mật
                String paymentToken = new StringBuilder(partner.getHost())
                                .append(partner.getGenerateUri())
                                .append("?")
                                .append(query)
                                .append("&vnp_SecureHash=")
                                .append(hash)
                                .toString();

                // Xây dựng đối tượng phản hồi với token thanh toán
                VNPayGetTokenResponse vnPayToken = VNPayGetTokenResponse
                                .builder()
                                .paymentToken(paymentToken)
                                .build();

                // Trả về token thanh toán
                return vnPayToken.to();
        }
}
