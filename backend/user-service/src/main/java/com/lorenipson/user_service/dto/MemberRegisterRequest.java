package com.lorenipson.user_service.dto;

import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@Data
@NoArgsConstructor
public class MemberRegisterRequest {

    private String username;
    private String email;
    private String firstName;
    private String lastName;
    private LocalDate birthDate;
    private String phone;
    private String address;
    private String password;

}
