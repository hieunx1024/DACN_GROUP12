package com.laptrinhoop.controller.web;

import com.laptrinhoop.entity.Customer;
import com.laptrinhoop.service.IAccountService;
import com.laptrinhoop.service.IHttpService;
import lombok.RequiredArgsConstructor;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.Objects;

@Controller
@RequestMapping(value = "/otp/")
@RequiredArgsConstructor
public class OTPController {
    private final IHttpService http;

    private final IAccountService accountService;

    @GetMapping(value = "generate")
    public String generateOtp(@RequestParam("userId") String userId, Model model) {
       String username =  http.decode(userId);
        Customer user = accountService.findById(username);
        if(Objects.isNull(user)){
            model.addAttribute("message", "Thông tin tài khoản không chính xác");
            return "redirect:/account/login";
        }
        System.out.println("otp chinh xasc");
        return "redirect:/home/index";
    }
}
