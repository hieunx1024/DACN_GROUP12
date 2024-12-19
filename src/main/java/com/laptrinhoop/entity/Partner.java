package com.laptrinhoop.entity;


import com.laptrinhoop.properties.VNPayProperties;
import com.laptrinhoop.utils.DataEncryptor;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;

import javax.persistence.*;
import java.io.Serializable;
import java.util.List;
import java.util.UUID;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Accessors(chain = true)
@Entity
@Table(name = "partners",schema = "dbo")
public class Partner extends AuditableEntity implements Serializable {
    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    private String uuid = UUID.randomUUID().toString();

    private String code;

    private String name;

    private String host;

    @Column(name = "merchant_id")
    private String merchantId;

    @Column(name = "secret_key")
    private String secretKey;

    @Column(name = "generate_uri")
    private String generateUri;

    @Column(name = "redirect_proxy")
    private String redirectProxy;

    @OneToMany(mappedBy = "partner", fetch = FetchType.EAGER)
    List<Order> orders;

    public static Partner from(VNPayProperties item){
        Partner partner = new Partner();
        partner.setCode("VNPAY");
        partner.setHost(item.getHost());
        partner.setGenerateUri(item.getGenerateURI());
        partner.setName("VNPAY");
        partner.setMerchantId(item.getMerchantCode());
        partner.setSecretKey(DataEncryptor.encrypt(item.getSecretKey(),item.getSecretKey()));
        partner.setRedirectProxy(item.getRedirectProxy());
        return partner;

    }

    public Partner sync(VNPayProperties item){
        this.setRedirectProxy(item.getRedirectProxy());
        this.setHost(item.getHost());
        this.setMerchantId(item.getMerchantCode());
        this.setSecretKey(DataEncryptor.encrypt(item.getSecretKey(),item.getSecretKey()));
        this.setGenerateUri(item.getGenerateURI());
        return this;
    }


}
