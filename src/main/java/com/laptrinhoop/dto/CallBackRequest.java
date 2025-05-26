package com.laptrinhoop.dto;

import lombok.Builder;
import lombok.Getter;

import javax.validation.constraints.NotNull;
import java.math.BigDecimal;
import java.time.LocalDateTime;

@Builder
@Getter
public class CallBackRequest {
    private String invoiceNo;
    private BigDecimal amount;
    private String partnerCode; // Thêm trường partnerCode
    private String secureHash;

    // Getter cho partnerCode
    public String getPartnerCode() {
        return partnerCode;
    }

    public void setPartnerCode(String partnerCode) {
        this.partnerCode = partnerCode;
    }

    // Getter và Setter cho các trường khác
    public String getInvoiceNo() {
        return invoiceNo;
    }

    public void setInvoiceNo(String invoiceNo) {
        this.invoiceNo = invoiceNo;
    }

    public BigDecimal getAmount() {
        return amount;
    }

    public void setAmount(BigDecimal amount) {
        this.amount = amount;
    }

    public String getSecureHash() {
        return secureHash;
    }

    public void setSecureHash(String secureHash) {
        this.secureHash = secureHash;
    }
}
