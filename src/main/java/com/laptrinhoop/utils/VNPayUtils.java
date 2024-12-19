package com.laptrinhoop.utils;

import com.laptrinhoop.constants.VNPayConstants;
import lombok.experimental.UtilityClass;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.codec.digest.HmacAlgorithms;
import org.apache.commons.codec.digest.HmacUtils;

import java.io.UnsupportedEncodingException;
import java.util.Arrays;
import java.util.Map;
import java.util.stream.Collectors;
@UtilityClass
@Slf4j
public class VNPayUtils {

    public static Map<String, String> responseToMap(String response) {
        return Arrays
                .stream(response.split("&"))
                .map(item -> item.split("="))
                .collect(Collectors.toMap(key -> key[0], key -> key[1]));
    }

    public static Boolean isCheckSum(String secretKey, Map<String, String> params) {
        String vnPaySecureHash = params.getOrDefault(VNPayConstants.VNPayParams.SECURE_HASH, "");
        params.remove(VNPayConstants.VNPayParams.SECURE_HASH);
        String vnPaySecureHashType = params.getOrDefault(VNPayConstants.VNPayParams.SECURE_HASH_TYPE, HmacAlgorithms.HMAC_SHA_512.getName());
        params.remove(VNPayConstants.VNPayParams.SECURE_HASH_TYPE);
        try {
            String secureHash = new HmacUtils(vnPaySecureHashType, secretKey).hmacHex(QueryUtils.generateQuery(params));
            return secureHash.equals(vnPaySecureHash);
        } catch (UnsupportedEncodingException e) {
            log.info("[VNPayUtils] isCheckSum: " + e.getMessage());
            return Boolean.FALSE;
        }
    }
}
