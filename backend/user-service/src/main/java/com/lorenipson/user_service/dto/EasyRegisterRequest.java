package com.lorenipson.user_service.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class EasyRegisterRequest {

    private String email;

    private String firstName;

    private String lastName;

    private String phone;

    private String address;

}
