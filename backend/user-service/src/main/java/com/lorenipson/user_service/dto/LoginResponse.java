package com.lorenipson.user_service.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;
import java.util.UUID;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class LoginResponse {

    private String token;
    private UUID userId; // TODO: 除錯用。
    private String username;
    private List<String> authorities;
    private Integer age;

}
