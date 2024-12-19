package com.laptrinhoop.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.laptrinhoop.dto.CallBackRequest;
import com.laptrinhoop.dto.PaymentRequest;
import com.laptrinhoop.enums.TransactionStatus;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;

import javax.persistence.*;
import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.Objects;

@Entity
@Table(name = "transactions", schema = "dbo")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Accessors(chain = true)
public class Transaction extends AuditableEntity {
    @JsonIgnore
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @ManyToOne
    @JoinColumn(name = "partner_id")
    Partner partner;


    private BigDecimal amount;
    private String description;

    @Column(name = "client_invoice_id")
    private String clientInvoiceId;

    @Column(name = "transaction_id")
    private String transactionId;

    @Column(name = "username")
    private String username;


    @Column(name = "partner_transaction_id")
    private String partnerTransactionId;

    @Column(name = "bank_transaction_id")
    private String bankTransactionId;

    @Column(name = "bank_code")
    private String bankCode;

    @Column(name = "currency_code")
    private String currencyCode;


    @Enumerated(EnumType.STRING)
    private TransactionStatus status;

    @Column(name = "partner_status")
    private String partnerStatus;

    @Column(name = "card_type")
    private String cardType;

    @Column(name = "card_no")
    private String cardNo;

    @Column(name = "partner_transaction_date")
    private String partnerTransactionDate;


    @Column(name = "location")
    private String location;


    public static Transaction from(Partner partner, PaymentRequest paymentRequest) {
        return Transaction.builder()
                .currencyCode(paymentRequest.getCurrencyCode())
                .amount(paymentRequest.getAmount())
                .username(paymentRequest.getUsername())
                .clientInvoiceId(paymentRequest.getInvoiceId())
                .transactionId(paymentRequest.getInvoiceId())
                .partner(partner)
                //.addressClient(paymentRequest.getIpAddress())
                .description(paymentRequest.getDescription())
                .status(TransactionStatus.NEW)
                .location(Objects.isNull(paymentRequest.getLocale()) ? "vi" : paymentRequest.getLocale())
                .build();
    }



    public Transaction sync(CallBackRequest callBackRequest) {
        TransactionStatus transactionStatus = Boolean.TRUE.equals(callBackRequest.getIsSuccess()) ? TransactionStatus.SUCCESS : TransactionStatus.FAIL;
        this.setStatus(transactionStatus);
        this.setCardNo(callBackRequest.getCardNo());
        this.setPartnerTransactionId(callBackRequest.getPartnerTransactionId());
        this.setBankTransactionId(callBackRequest.getBankTransactionId());
        this.setPartnerStatus(callBackRequest.getPartnerCode());
        this.setBankCode(callBackRequest.getBankCode());
        this.setPartnerTransactionDate(String.valueOf(callBackRequest.getPartnerTransactionTime()));
        this.setModifiedDate(String.valueOf(LocalDateTime.now()));
        this.setCardType(callBackRequest.getCardType());
        return this;
    }
    public Transaction update(PaymentRequest paymentRequest, Partner partner) {
        this.partner = partner;
        this.description = paymentRequest.getDescription();
        this.currencyCode = paymentRequest.getCurrencyCode();
        return this;

    }


}