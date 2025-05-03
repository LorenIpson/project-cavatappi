package com.lorenipson.user_service.dto;

import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class EasyRegisterRequest {

    private String email;
    private String firstName;
    private String lastName;
    private String phone;
    private String address;

}
