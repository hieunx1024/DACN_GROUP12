package com.laptrinhoop.service;

import com.laptrinhoop.dto.vnpay.VNPayCallBackResponse;

import javax.servlet.http.HttpServletRequest;
import java.util.Map;

public interface IVNPayService {

    VNPayCallBackResponse redirect(Map<String, String> paymentResponse, HttpServletRequest request) ;
}
