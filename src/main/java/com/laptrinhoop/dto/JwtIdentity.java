package com.laptrinhoop.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.google.gson.annotations.SerializedName;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;
import lombok.experimental.FieldDefaults;

@Data
@Accessors(chain = true)
@AllArgsConstructor
@NoArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
public class JwtIdentity {
    @SerializedName("sub")
    @JsonProperty("sub")
    String userId;

    @SerializedName("client_id")
    @JsonProperty("client_id")
    String clientId;

    @SerializedName("origin_jti")
    @JsonProperty("origin_jti")
    String originJti;

    @SerializedName("event_id")
    @JsonProperty("event_id")
    String eventId;

    @SerializedName("token_use")
    @JsonProperty("token_use")
    String tokenUse;

    @SerializedName("scope")
    @JsonProperty("scope")
    String scope;

    @SerializedName("auth_time")
    @JsonProperty("auth_time")
    Long authTime;

    @SerializedName("exp")
    @JsonProperty("exp")
    Long exp;


    @SerializedName("username")
    @JsonProperty("username")
    String username;


    @SerializedName("iat")
    @JsonProperty("iat")
    Long issuedAt;

    @SerializedName("iss")
    @JsonProperty("iss")
    String issuedAddress;
}
