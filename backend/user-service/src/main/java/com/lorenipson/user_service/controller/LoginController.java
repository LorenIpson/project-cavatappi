package com.lorenipson.user_service.controller;

import com.lorenipson.user_service.dto.LoginRequest;
import com.lorenipson.user_service.dto.LoginResponse;
import com.lorenipson.user_service.service.LoginService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class LoginController {

    private final LoginService loginService;

    public LoginController(LoginService loginService) {
        this.loginService = loginService;
    }

    @PostMapping("/api/login")
    public ResponseEntity<LoginResponse> login(@RequestBody LoginRequest request) {
        LoginResponse response = loginService.login(request);
        return ResponseEntity.ok(response);
    }

    @GetMapping("/home")
    public String home() {
        return "Henlo Shibe";
    }

}
