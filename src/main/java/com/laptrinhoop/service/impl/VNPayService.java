package com.laptrinhoop.service.impl;

import com.laptrinhoop.constants.Constant;
import com.laptrinhoop.constants.VNPayCallBackResponseCode;
import com.laptrinhoop.converter.Jksonizer;
import com.laptrinhoop.dao.impl.PartnerDAO;
import com.laptrinhoop.dto.CallBackResponse;
import com.laptrinhoop.dto.vnpay.VNPayCallBackRequest;
import com.laptrinhoop.dto.vnpay.VNPayCallBackResponse;
import com.laptrinhoop.entity.Customer;
import com.laptrinhoop.entity.Order;
import com.laptrinhoop.entity.Partner;
import com.laptrinhoop.enums.PartnerCode;
import com.laptrinhoop.properties.VNPayProperties;
import com.laptrinhoop.service.*;
import com.laptrinhoop.utils.DataEncryptor;
import com.laptrinhoop.utils.VNPayUtils;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpServletRequest;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

@Service
@Slf4j
@RequiredArgsConstructor
public class VNPayService implements IVNPayService {

    private final PartnerDAO partnerDAO;
    private final VNPayProperties vnPayProperties;
    private final IPaymentTrackingService paymentTrackingService;
    private final IAccountService accountService;
    private final IHttpService http;
    private final IOrderSevice orderSevice;

    @Value("${server.port}")
    private int port;

    @Override
    public VNPayCallBackResponse redirect(Map<String, String> paymentResponse, HttpServletRequest request) {
        // Chuyển đổi dữ liệu từ response thành đối tượng VNPayCallBackRequest
        VNPayCallBackRequest vnPayCallBackRequest = Jksonizer.getObjectMapper().convertValue(paymentResponse,
                VNPayCallBackRequest.class);

        // Kiểm tra sự tồn tại của đối tác VNPay
        Optional<Partner> optionalPartner = partnerDAO.findByCode(PartnerCode.VNPAY.name());
        if (!optionalPartner.isPresent()) {
            log.error("Partner with code VNPAY not found");
            return VNPayCallBackResponse.from(VNPayCallBackResponseCode.ERROR_SYSTEM, vnPayCallBackRequest);
        }

        Partner vnPayPartner = optionalPartner.get();
        String secretKey = DataEncryptor.decrypt(vnPayPartner.getSecretKey(), vnPayProperties.getSecretKey());

        // Kiểm tra tính hợp lệ của chữ ký (checksum)
        boolean isCheckSum = VNPayUtils.isCheckSum(secretKey, paymentResponse);
        if (!isCheckSum) {
            log.error("Invalid checksum for payment response: {}", paymentResponse);
            return VNPayCallBackResponse.from(VNPayCallBackResponseCode.INVALID_SIGNATURE, vnPayCallBackRequest);
        }

        // Xử lý thông tin callback và tạo phản hồi
        CallBackResponse backResponse = paymentTrackingService.redirectTracking(vnPayCallBackRequest.to());
        VNPayCallBackResponse result = VNPayCallBackResponse.from(backResponse, vnPayCallBackRequest);

        // Gửi email thông báo thanh toán trực tuyến
        sendEmailOnlinePayment(result);

        // Gửi email xác nhận đơn hàng
        sendEmailConfirmOrder(result);

        return result;
    }

    // Phương thức gửi email thông báo thanh toán trực tuyến
    private void sendEmailOnlinePayment(VNPayCallBackResponse result) {
        log.info("[VNPayService] redirect -- begin send email online payment");
        Map<String, String> replaceData = new HashMap<>();
        replaceData.put(Constant.EmailTemplateData.AMOUNT_PAYMENT_KEY, result.getAmount());
        replaceData.put(Constant.EmailTemplateData.STATUS_PAYMENT_KEY, result.getMessage());
        replaceData.put(Constant.EmailTemplateData.BANK_PAYMENT_KEY, result.getBankCode());
        replaceData.put(Constant.EmailTemplateData.TRANSACTION_NO_KEY, result.getTransactionNo());
        replaceData.put(Constant.EmailTemplateData.TRANSACTION_INFO_PAYMENT_KEY, result.getTransactionInfo());
        replaceData.put(Constant.EmailTemplateData.TRANSACTION_DATE_PAYMENT_KEY, result.getTransactionDate());

        Customer user = http.getSession("user");
        accountService.sendOnlinePayment(user, replaceData);
        log.info("[VNPayService] redirect -- end send email online payment");
    }

    // Phương thức gửi email xác nhận đơn hàng
    private void sendEmailConfirmOrder(VNPayCallBackResponse result) {
        Customer user = http.getSession("user");
        List<Order> orders = orderSevice.findAllByUsernameOrderByIdDesc(user);
        Order order = orders.get(0);

        log.info("[VNPayService] redirect -- begin send email confirm order");
        Map<String, String> replaceData = new HashMap<>();
        replaceData.put(Constant.EmailTemplateData.ORDER_ID_KEY, String.valueOf(order.getId()));
        replaceData.put(Constant.EmailTemplateData.PAYMENT_METHOD_KEY, result.getMessage());
        replaceData.put(Constant.EmailTemplateData.EMAIL_KEY, user.getEmail());
        replaceData.put(Constant.EmailTemplateData.ADDRESS_KEY, user.getAddress());
        replaceData.put(Constant.EmailTemplateData.PHONE_NUMBER_KEY, user.getPhoneNumber());
        replaceData.put(Constant.EmailTemplateData.TOTAL_AMOUNT_KEY, result.getAmount());
        replaceData.put(Constant.EmailTemplateData.DELIVERY_KEY, order.getOrderDate().toString());

        String domain = "http://localhost:" + port + "/";
        String domainViewDetail = domain + "order/detail/" + order.getId();
        replaceData.put(Constant.EmailTemplateData.URL_ORDER_DETAIL_KEY, domainViewDetail);
        String domainViewShop = domain + "home/index";
        replaceData.put(Constant.EmailTemplateData.URL_VIEW_SHOP_KEY, domainViewShop);

        accountService.sendConfirmOrder(user, replaceData);
        log.info("[VNPayService] redirect -- end send email confirm order");
    }
}
