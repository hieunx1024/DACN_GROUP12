package com.laptrinhoop.controller.web;

import com.laptrinhoop.converter.Jksonizer;
import com.laptrinhoop.dto.vnpay.VNPayCallBackResponse;
import com.laptrinhoop.service.IVNPayService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import javax.servlet.http.HttpServletRequest;
import java.util.Map;

@Controller
@RequestMapping(value = "/api/external/")
@Slf4j
@RequiredArgsConstructor
public class VNPayController {

    private final IVNPayService vnPayService;


    @GetMapping("redirect")
    public String redirect(@RequestParam Map<String, String> paymentResponse, HttpServletRequest request, Model model) {
        VNPayCallBackResponse response = vnPayService.redirect(paymentResponse, request);
        log.info(Jksonizer.toJson(response));
        model.addAttribute("vnpay",response);
        return "order/payment";
    }


}
