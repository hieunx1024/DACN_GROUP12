package com.laptrinhoop.dto;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

@Data
@AllArgsConstructor(access = AccessLevel.PRIVATE)
@Builder
public class UrlGeneratorResponse {
    private boolean isSuccess;
    private String errorMessage;
    private String url;

    public static UrlGeneratorResponse create(String url) {
        return new UrlGeneratorResponse(Boolean.TRUE,null, url);
    }

    public static UrlGeneratorResponse failedWith(String errorMessage){
        return new UrlGeneratorResponse(Boolean.FALSE,errorMessage, "");
    }
}
