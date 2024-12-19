package com.laptrinhoop.properties;

import lombok.AllArgsConstructor;
import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
@ConfigurationProperties(prefix = "service-discovery.sendgrip")
@Data
public class TemplateProperties {
    private String fromAddress;
    private String apiKey;
    private String baseUrl;
    private String secretKey;
    private final Template templateId = new Template();

    @Data
    public static class Template{
        private String createAccount;
        private String forgotPassword;
        private String resetPassword;
        private String onlinePayment;
        private String confirmOrder;
    }
}
