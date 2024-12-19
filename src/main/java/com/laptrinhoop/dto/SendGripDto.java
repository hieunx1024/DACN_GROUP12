package com.laptrinhoop.dto;

import lombok.*;
import lombok.experimental.FieldDefaults;

import java.util.List;
import java.util.Map;

public class SendGripDto {
    @Builder
    @Data
    @FieldDefaults(level = AccessLevel.PRIVATE)
    public static class FromEmail {
        private String email;

    }

    @Builder
    @Data
    @FieldDefaults(level = AccessLevel.PRIVATE)
    public static class ToEmail {
        private String email;
    }


    @Builder
    @Data
    @FieldDefaults(level = AccessLevel.PRIVATE)
    @AllArgsConstructor
    public static class Personalization {
        private List<ToEmail> to;
        private Map<String, String> dynamic_template_data;

        public static Personalization from(List<ToEmail> toEmails, Map<String, String> dynamic_template_data) {
            return new Personalization(toEmails, dynamic_template_data);
        }

    }

    @Data
    @AllArgsConstructor
    @FieldDefaults(level = AccessLevel.PRIVATE)
    public static class SendGripRequest {
        private FromEmail from;
        private List<Personalization> personalizations;
        private String template_id;

        public static SendGripRequest from(FromEmail from, List<Personalization> personalizations, String templateId) {
            return new SendGripRequest(from, personalizations, templateId);
        }

    }

    @Builder
    @AllArgsConstructor
    @NoArgsConstructor
    @Data
    public static class SendGripResponse {
        private boolean isSuccess;
        private DeliveryFailureCode deliveryFailureCode;
        private String message;

        public static SendGripResponse successWith(){
            return SendGripResponse.builder()
                    .isSuccess(Boolean.TRUE)
                    .build();
        }

        public static SendGripResponse failedWith(int code,String message){
            return SendGripResponse.builder()
                    .isSuccess(Boolean.FALSE)
                    .deliveryFailureCode(DeliveryFailureCode.getDeliveryStatusByCode(code))
                    .message(message)
                    .build();

        }


    }

    public static enum DeliveryFailureCode {
        INTERNAL_ERROR,
        APIS_CALL_FAILED,
        API_CALLS_ERROR;

        public static DeliveryFailureCode getDeliveryStatusByCode(int code) {
            switch (code) {
                case 400:
                case 401:
                case 404:
                case 429:
                    return API_CALLS_ERROR;
                case 408:
                case 503:
                case 504:
                    return APIS_CALL_FAILED;
                default:
                    return INTERNAL_ERROR;
            }
        }


    }
}
