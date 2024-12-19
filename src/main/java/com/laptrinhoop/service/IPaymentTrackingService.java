package com.laptrinhoop.service;

import com.laptrinhoop.dto.CallBackRequest;
import com.laptrinhoop.dto.CallBackResponse;

public interface IPaymentTrackingService {

    CallBackResponse redirectTracking(CallBackRequest paymentRequest);
}
