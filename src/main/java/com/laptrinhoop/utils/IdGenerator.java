package com.laptrinhoop.utils;


import com.laptrinhoop.enums.PartnerCode;
import lombok.experimental.UtilityClass;

import java.security.SecureRandom;

@UtilityClass
public class IdGenerator {

    public String getRedirectProxy(String uriService) {
        return new StringBuffer(uriService)
                .append("api/external/redirect")
                .toString();
    }

    public static String from(String invoiceNo) {
        return new StringBuffer()
                .append(invoiceNo)
                .toString();

    }

    public String generateInvoiceId(PartnerCode partnerCode) {
        final String number = "0123456789";
        SecureRandom random = new SecureRandom();
        StringBuilder sb = new StringBuilder(partnerCode.name());

        for (int i = 0; i < 5; i++) {
            int randomIndex = random.nextInt(number.length());
            sb.append(number.charAt(randomIndex));
        }
        return sb.toString();

    }

}
