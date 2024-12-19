package com.laptrinhoop.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class FetchInquiryRequest {
    String invoiceNo;
    String ipAddress;
    String merchantId;

}
