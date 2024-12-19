package com.laptrinhoop.controller.web;

import com.laptrinhoop.dto.PaymentRequest;
import com.laptrinhoop.dto.UrlGeneratorResponse;
import com.laptrinhoop.service.IPaymentService;
import lombok.RequiredArgsConstructor;

import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;
import java.math.BigDecimal;
import java.util.Objects;

@Controller
@RequiredArgsConstructor
public class PaymentController {
    private final IPaymentService paymentService;


    @ResponseBody
    @PostMapping("/generate-url")
    public ResponseEntity<UrlGeneratorResponse> generateUrl(@Valid @RequestBody PaymentRequest generateRequest, HttpServletRequest request) {
        if (StringUtils.isEmpty(generateRequest.getIpAddress())) {
            generateRequest.setIpAddress(request.getRemoteAddr());
        }

        if (Objects.isNull(generateRequest.getAmount()) || generateRequest.getAmount().compareTo(BigDecimal.ZERO) == 0) {

        }
        UrlGeneratorResponse response = paymentService.generateLink(generateRequest);
        return ResponseEntity.ok().body(response);
    }
}
