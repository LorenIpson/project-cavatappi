package com.lorenipson.user_service.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class MemberRegisterRequest {

    private String username;

    private String email;

    private String firstName;

    private String lastName;

    private Short age;

    private String phone;

    private String address;

    private String password;

}
