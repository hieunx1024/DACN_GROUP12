package com.laptrinhoop.service.impl;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

import org.apache.commons.codec.digest.HmacAlgorithms;
import org.apache.commons.codec.digest.HmacUtils;
import org.springframework.stereotype.Service;

import com.laptrinhoop.converter.Jksonizer;
import com.laptrinhoop.dao.impl.TransactionDAO;
import com.laptrinhoop.dto.CallBackRequest;
import com.laptrinhoop.dto.CallBackResponse;
import com.laptrinhoop.dto.PaymentRequest;
import com.laptrinhoop.dto.PaymentTokenResponse;
import com.laptrinhoop.dto.vnpay.VNPayGetTokenRequest;
import com.laptrinhoop.dto.vnpay.VNPayGetTokenResponse;
import com.laptrinhoop.entity.Partner;
import com.laptrinhoop.entity.Transaction;
import com.laptrinhoop.enums.TransactionStatus;
import com.laptrinhoop.properties.VNPayProperties;
import com.laptrinhoop.service.IPaymentGatewayDecorator;
import com.laptrinhoop.service.IPaymentTrackingService;
import com.laptrinhoop.utils.DataEncryptor;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Service
@RequiredArgsConstructor
@Slf4j
public class VNPaymentGatewayService implements IPaymentGatewayDecorator, IPaymentTrackingService {

    private final VNPayProperties vnPayProperties;
    private final TransactionDAO transactionDAO;

    @Override
    public PaymentTokenResponse getPaymentToken(Partner partner, PaymentRequest paymentRequest)
            throws UnsupportedEncodingException {
        // Tạo đối tượng yêu cầu token từ VNPay
        VNPayGetTokenRequest request = VNPayGetTokenRequest.from(partner, paymentRequest);

        // Chuyển đổi đối tượng thành Map để tạo chuỗi tham số
        Map<String, String> vnpParams = Jksonizer.getObjectMapper().convertValue(request, Map.class);

        // Tạo chuỗi query string từ các tham số đã sắp xếp
        String query = generateQuery(vnpParams);

        // Tính toán chữ ký bảo mật (vnp_SecureHash) bằng HMAC_SHA_512
        String secretKey = DataEncryptor.decrypt(partner.getSecretKey(), vnPayProperties.getSecretKey());
        // Đảm bảo không có khoảng trắng dư thừa
        secretKey = secretKey.trim();

        String hash = new HmacUtils(HmacAlgorithms.HMAC_SHA_512, secretKey).hmacHex(query);

        // Log ra thông tin để debug (xóa hoặc comment lại khi hoạt động đúng)
        log.debug("Query string: {}", query);
        log.debug("SecretKey (đã giải mã): {}", secretKey);
        log.debug("Hash được tạo: {}", hash);

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

    @Override
    public CallBackResponse redirectTracking(CallBackRequest redirectRequest) {
        // Xác thực chữ ký từ VNPAY
        if (!validateVNPayCallback(redirectRequest)) {
            return CallBackResponse.create(CallBackResponse.CallBackResponseCode.INVALID_SIGNATURE);
        }
        // Lấy mã giao dịch từ yêu cầu callback
        String transactionId = redirectRequest.getInvoiceNo();
        Optional<Transaction> optionalTransaction = transactionDAO.findByTransactionId(transactionId);

        // Mã trả về khi không có lỗi
        CallBackResponse.CallBackResponseCode codeResponse = CallBackResponse.CallBackResponseCode.SUCCESS;

        // Kiểm tra nếu không tìm thấy giao dịch
        if (!optionalTransaction.isPresent()) {
            return CallBackResponse.create(CallBackResponse.CallBackResponseCode.INVALID_TRANSACTION);
        }

        // Lấy thông tin giao dịch cũ
        Transaction oldTransaction = optionalTransaction.get();

        // Kiểm tra số tiền giao dịch có khớp không
        if (redirectRequest.getAmount().compareTo(oldTransaction.getAmount()) != 0) {
            return CallBackResponse.create(CallBackResponse.CallBackResponseCode.INVALID_AMOUNT);
        }

        // Kiểm tra trạng thái giao dịch (nếu đã xử lý thì không xử lý lại)
        if (!TransactionStatus.NEW.equals(oldTransaction.getStatus())) {
            return CallBackResponse.create(CallBackResponse.CallBackResponseCode.REQUEST_PROCESSED);
        }

        // Đồng bộ thông tin giao dịch cũ với yêu cầu callback mới
        Transaction update = oldTransaction.sync(redirectRequest);

        // Lưu giao dịch đã được cập nhật vào cơ sở dữ liệu
        Transaction saveTransaction = transactionDAO.create(update);

        // Kiểm tra nếu lưu không thành công
        if (saveTransaction.getId() <= 0) {
            return CallBackResponse.create(CallBackResponse.CallBackResponseCode.ERROR_SYSTEM);
        }

        // Kiểm tra nếu mã đối tác là "24", thì hủy giao dịch
        if (redirectRequest.getPartnerCode() != null && redirectRequest.getPartnerCode().equals("24")) {
            codeResponse = CallBackResponse.CallBackResponseCode.CANCEL_TRANSACTION;
        }

        // Trả về mã trạng thái callback
        return CallBackResponse.create(codeResponse);
    }

    /**
     * Xác thực chữ ký từ VNPAY khi nhận callback
     * 
     * @param request Yêu cầu callback từ VNPAY
     * @return true nếu chữ ký hợp lệ, false nếu không hợp lệ
     */
    private boolean validateVNPayCallback(CallBackRequest request) {
        try {
            // Lấy chữ ký từ VNPAY
            String vnpSecureHash = request.getSecureHash();

            if (vnpSecureHash == null || vnpSecureHash.isEmpty()) {
                log.error("Chữ ký VNPAY không tồn tại trong request");
                return false;
            }

            // Tạo Map chứa tất cả các tham số ngoại trừ chữ ký
            Map<String, String> vnpParams = Jksonizer.getObjectMapper().convertValue(request, Map.class);

            // Loại bỏ tham số chữ ký nếu có
            vnpParams.remove("secureHash");
            vnpParams.remove("secureHashType");

            // Tìm partner dựa trên request để lấy secretKey
            // (Giả định rằng có một cách nào đó để lấy Partner từ CallBackRequest)
            String partnerCode = request.getPartnerCode();
            Partner partner = getPartnerByCode(partnerCode);

            if (partner == null) {
                log.error("Không tìm thấy partner với mã: {}", partnerCode);
                return false;
            }

            // Giải mã khóa bí mật
            String secretKey = DataEncryptor.decrypt(partner.getSecretKey(), vnPayProperties.getSecretKey()).trim();

            // Tạo chuỗi query đã sắp xếp
            String query = generateQuery(vnpParams);

            // Tính toán chữ ký
            String calculatedHash = new HmacUtils(HmacAlgorithms.HMAC_SHA_512, secretKey).hmacHex(query);

            // Log thông tin để debug
            log.debug("VNPAY Callback - Received hash: {}", vnpSecureHash);
            log.debug("VNPAY Callback - Calculated hash: {}", calculatedHash);
            log.debug("VNPAY Callback - Query string: {}", query);

            // So sánh chữ ký
            return calculatedHash.equals(vnpSecureHash);
        } catch (Exception e) {
            log.error("Lỗi khi xác thực chữ ký VNPAY: ", e);
            return false;
        }
    }

    /**
     * Tạo chuỗi query từ các tham số đã sắp xếp theo thứ tự alphabet
     * 
     * @param params Map chứa các tham số
     * @return Chuỗi query đã sắp xếp
     */
    private String generateQuery(Map<String, String> params) {
        try {
            // Sắp xếp các tham số theo tên (alphabet)
            List<String> fieldNames = new ArrayList<>(params.keySet());
            Collections.sort(fieldNames);

            // Tạo chuỗi query đã sắp xếp và mã hóa
            StringBuilder query = new StringBuilder();
            boolean first = true;

            for (String fieldName : fieldNames) {
                String fieldValue = params.get(fieldName);
                if (fieldValue != null && !fieldValue.isEmpty()) {
                    if (first) {
                        first = false;
                    } else {
                        query.append("&");
                    }
                    query.append(fieldName).append("=")
                            .append(URLEncoder.encode(fieldValue, StandardCharsets.UTF_8.toString()));
                }
            }

            return query.toString();
        } catch (UnsupportedEncodingException e) {
            log.error("Lỗi khi mã hóa URL: ", e);
            throw new RuntimeException("Lỗi khi tạo query string", e);
        }
    }

    /**
     * Phương thức giả định để lấy thông tin Partner từ mã đối tác
     * Thay thế phương thức này bằng việc tích hợp với DAO hoặc Repository thực tế
     * của bạn
     * 
     * @param partnerCode Mã đối tác
     * @return Đối tượng Partner
     */
    private Partner getPartnerByCode(String partnerCode) {
        // TODO: Thay thế bằng code lấy Partner từ database
        // Ví dụ: return partnerRepository.findByCode(partnerCode);
        return null; // Thay thế bằng logic thực tế
    }
}