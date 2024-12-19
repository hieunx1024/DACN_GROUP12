package com.laptrinhoop.dto;

import lombok.*;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class UserDto {
    @NotNull
    private Long customer_no;

    private String username;
    @NotNull
    @Size(max = 255)
    private String fullname;

    private String customerId;

    private int accountType;

    // Synch from DMCS
    private String birthday;

    // Synch from DMCS
    private String gender;

    @Email(message = "{validate.agent.email.fomat}")
    private String email;

    private String phone;


    private String representativeName;

    private String license;

    private int faceIdStatus;


    @Builder.Default
    private Integer countLoginFail = 0;



    private String createdBy;


    private String updatedBy;
}
