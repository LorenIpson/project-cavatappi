package com.lorenipson.user_service.controller;

import com.lorenipson.user_service.dto.LoginRequest;
import com.lorenipson.user_service.dto.LoginResponse;
import com.lorenipson.user_service.service.LoginService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
public class LoginController {

    private final LoginService loginService;

    public LoginController(LoginService loginService) {
        this.loginService = loginService;
    }

    @PostMapping("/api/user/login")
    public ResponseEntity<LoginResponse> login(@RequestBody LoginRequest request) {
        LoginResponse response = loginService.login(request);
        return ResponseEntity.ok(response);
    }

    @GetMapping("/api/user/home")
    public String home(@RequestHeader("X-Username") String header) {
        System.out.println("HEADER =================================================================================");
        System.out.println(header);
        return header;
    }

}
