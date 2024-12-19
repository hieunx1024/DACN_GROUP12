package com.laptrinhoop.service;

import java.util.List;
import java.util.Map;

import com.laptrinhoop.dto.vnpay.VNPayCallBackResponse;
import com.laptrinhoop.entity.Customer;
import com.laptrinhoop.entity.Order;

public interface IAccountService extends IGeneralService<Customer, String> {
    boolean sendActivedUser(Customer user);

    boolean sendForgotPassword(Customer user);

    boolean sendResetPassword(Customer user);

    boolean sendOnlinePayment(Customer user, Map<String, String> items);

    boolean sendConfirmOrder(Customer user, Map<String, String> items);

    void updateUser(Customer user);

    List<Customer> findByRoles(boolean admin);

}
