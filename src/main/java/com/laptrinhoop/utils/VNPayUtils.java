package com.laptrinhoop.utils;

import com.laptrinhoop.constants.VNPayConstants;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.codec.digest.HmacUtils;

import java.util.Map;
import java.util.stream.Collectors;
import java.util.Arrays;

@Slf4j
public class VNPayUtils {

    /**
     * Kiểm tra tính hợp lệ của chữ ký HMAC
     * 
     * @param secretKey Secret key của đối tác VNPay
     * @param params    Tham số trả về từ VNPay
     * @return true nếu chữ ký hợp lệ, false nếu không
     */
    public static Boolean isCheckSum(String secretKey, Map<String, String> params) {
        // Lấy chữ ký từ VNPay
        String vnPaySecureHash = params.getOrDefault(VNPayConstants.VNPayParams.SECURE_HASH, "");
        log.info("VNPay secure hash: " + vnPaySecureHash); // Log chữ ký nhận được từ VNPay

        // Loại bỏ chữ ký khỏi các tham số để không bị ảnh hưởng trong quá trình kiểm
        // tra
        params.remove(VNPayConstants.VNPayParams.SECURE_HASH);

        // Lấy thuật toán chữ ký (thường là HMAC-SHA-512)
        String vnPaySecureHashType = params.getOrDefault(VNPayConstants.VNPayParams.SECURE_HASH_TYPE, "HMAC_SHA_512");
        log.info("VNPay secure hash type: " + vnPaySecureHashType); // Log thuật toán chữ ký

        // Loại bỏ chữ ký type khỏi các tham số
        params.remove(VNPayConstants.VNPayParams.SECURE_HASH_TYPE);

        // Tạo chuỗi tham số theo thứ tự từ điển
        String queryString = generateQuery(params);
        log.info("Generated query string for checksum: " + queryString); // Log chuỗi tham số tạo ra

        // Thực hiện ký HMAC với secretKey và chuỗi tham số
        try {
            String secureHash = new HmacUtils(vnPaySecureHashType, secretKey).hmacHex(queryString);
            log.info("Generated secure hash: " + secureHash); // Log chữ ký được tạo ra từ HMAC

            // So sánh chữ ký đã tạo với chữ ký VNPay gửi đến
            return secureHash.equals(vnPaySecureHash);
        } catch (Exception e) {
            log.error("[VNPayUtils] Error while checking checksum: ", e); // Log lỗi nếu có
            return false;
        }
    }

    /**
     * Hàm tạo chuỗi tham số từ Map và sắp xếp theo thứ tự tăng dần
     * 
     * @param params Tham số cần tạo chuỗi
     * @return Chuỗi query string
     */
    private static String generateQuery(Map<String, String> params) {
        return params.entrySet().stream()
                .sorted(Map.Entry.comparingByKey()) // Sắp xếp tham số theo thứ tự từ điển
                .map(entry -> entry.getKey() + "=" + entry.getValue()) // Kết hợp key-value thành chuỗi
                .collect(Collectors.joining("&")); // Nối chuỗi các tham số
    }

    /**
     * Chuyển đổi chuỗi response thành Map, dùng cho việc debug
     * 
     * @param response Chuỗi response từ VNPay
     * @return Map các tham số từ response
     */
    public static Map<String, String> responseToMap(String response) {
        return Arrays.stream(response.split("&"))
                .map(item -> item.split("="))
                .collect(Collectors.toMap(key -> key[0], key -> key[1]));
    }
}
