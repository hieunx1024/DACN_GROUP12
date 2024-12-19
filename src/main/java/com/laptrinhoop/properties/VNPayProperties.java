package com.laptrinhoop.properties;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.experimental.FieldDefaults;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

@Component
@Getter
@FieldDefaults(level = AccessLevel.PRIVATE)
public class VNPayProperties {
    @Value("${integration.vnpay.host}")
    private String host;

    @Value("${integration.vnpay.secretKey}")
    private String secretKey;

    @Value("${integration.vnpay.merchantCode}")
    private String merchantCode;

    @Value("${integration.vnpay.generateURI}")
    private String generateURI;

    @Value("${integration.vnpay.redirectProxy}")
    private String redirectProxy;
}
