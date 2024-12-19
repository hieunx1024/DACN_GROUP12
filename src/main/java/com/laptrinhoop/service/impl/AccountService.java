package com.laptrinhoop.service.impl;

import java.text.MessageFormat;
import java.util.*;

import com.laptrinhoop.constants.Constant;
import com.laptrinhoop.converter.Jksonizer;
import com.laptrinhoop.dto.SendGripDto;
import com.laptrinhoop.dto.vnpay.VNPayCallBackResponse;
import com.laptrinhoop.entity.Order;
import com.laptrinhoop.enums.EnumFunctionType;
import com.laptrinhoop.enums.EnumTemplate;
import com.laptrinhoop.properties.TemplateProperties;
import com.laptrinhoop.utils.CustomerUtils;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.ObjectUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import com.laptrinhoop.dao.ICustomerDAO;
import com.laptrinhoop.entity.Customer;
import com.laptrinhoop.service.IAccountService;
import com.laptrinhoop.service.IHttpService;
import com.laptrinhoop.service.IMailService;

@Service
@Slf4j
public class AccountService extends GeneralService<Customer, String> implements IAccountService {

    @Autowired
    private ICustomerDAO dao;

    @Autowired
    private IHttpService http;

    @Autowired
    private IMailService emailService;

    @Autowired
    private TemplateProperties templateProperties;

    @Value("${server.port}")
    private int port;


    @Override
    public Customer findById(String id) {
        return dao.findById(id);
    }

    @Override
    public void updateUser(Customer user) {
        dao.update(user);
    }

    @Override
    public boolean sendActivedUser(Customer user) {
        SendGripDto.SendGripRequest emailRequest = this.createDataSendRequest(EnumFunctionType.CREATE_ACCOUNT, user);
        return emailService.send(emailRequest).isSuccess();
    }

    @Override
    public boolean sendForgotPassword(Customer user) {
        SendGripDto.SendGripRequest emailRequest = this.createDataSendRequest(EnumFunctionType.FORGOT_PASSWORD, user);
        return emailService.send(emailRequest).isSuccess();
    }

    @Override
    public boolean sendResetPassword(Customer user) {
        SendGripDto.SendGripRequest emailRequest = this.createDataSendRequest(EnumFunctionType.RESET_PASSWORD, user);
        return emailService.send(emailRequest).isSuccess();
    }

    @Override
    public boolean sendOnlinePayment(Customer user, Map<String, String> items) {
        SendGripDto.SendGripRequest emailRequest = this.createDataSendRequest(EnumFunctionType.ONLINE_PAYMENT, user, items);
        return emailService.send(emailRequest).isSuccess();
    }

    @Override
    public boolean sendConfirmOrder(Customer user, Map<String, String> items) {
        SendGripDto.SendGripRequest emailRequest = this.createDataSendRequest(EnumFunctionType.CONFIRM_ORDER, user,items);
        return emailService.send(emailRequest).isSuccess();
    }


    @Override
    public List<Customer> findByRoles(boolean roles) {
        return dao.findByRoles(roles);
    }

    private SendGripDto.SendGripRequest createDataSendRequest(EnumFunctionType funcType, Customer user) {
        return this.createDataSendRequest(funcType, user, null);

    }


    private SendGripDto.SendGripRequest createDataSendRequest(EnumFunctionType funcType, Customer user, Map<String, String> items) {
        String templateId = "";
        SendGripDto.FromEmail fromEmail = SendGripDto.FromEmail
                .builder()
                .email(templateProperties.getFromAddress())
                .build();
        SendGripDto.ToEmail toEmail = SendGripDto.ToEmail
                .builder()
                .email(user.getEmail())
                .build();
        Map<String, String> replaceDats = new LinkedHashMap<>();
        replaceDats.put(Constant.EmailTemplateData.CUSTOMER_NAME_KEY, user.getFullname());
        if (EnumFunctionType.CREATE_ACCOUNT == funcType) {
            templateId = templateProperties.getTemplateId().getCreateAccount();
            replaceDats.put(Constant.EmailTemplateData.USERNAME_KEY, user.getId());
            replaceDats.put(Constant.EmailTemplateData.PASSWORD_KEY, user.getPassword());
            String domainActivate = new StringBuilder("http://localhost:")
                    .append(port)
                    .append("/")
                    .append(MessageFormat.format(Constant.ContextPath.ACTIVATE, http.encode(user.getId())))
                    .toString();
            replaceDats.put(Constant.EmailTemplateData.URL_ACTIVATE_KEY, domainActivate);
        } else if (Arrays.asList(EnumFunctionType.FORGOT_PASSWORD, EnumFunctionType.RESET_PASSWORD).contains(funcType)) {
            templateId = templateProperties.getTemplateId().getForgotPassword();
            String password = CustomerUtils.generateRandomPassword(2, 2, 2, 2);
            user.setPassword(password);
            user.setChangePassword(Boolean.FALSE);
            this.updateUser(user);
            replaceDats.put(Constant.EmailTemplateData.PASSWORD_KEY, user.getPassword());
        } else if (EnumFunctionType.ONLINE_PAYMENT.equals(funcType)) {
            templateId = templateProperties.getTemplateId().getOnlinePayment();
            replaceDats.putAll(items);
        } else if (EnumFunctionType.CONFIRM_ORDER.equals(funcType)) {
            templateId = templateProperties.getTemplateId().getConfirmOrder();
            replaceDats.putAll(items);
        }
        SendGripDto.Personalization personalization = SendGripDto.Personalization
                .from(Arrays.asList(toEmail), replaceDats);

        return SendGripDto.SendGripRequest.from(
                fromEmail,
                Arrays.asList(personalization),
                templateId);

    }


}
