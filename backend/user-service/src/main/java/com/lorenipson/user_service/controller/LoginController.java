package com.lorenipson.user_service.controller;

import com.lorenipson.user_service.dto.LoginRequest;
import com.lorenipson.user_service.dto.LoginResponse;
import com.lorenipson.user_service.service.LoginService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.web.bind.annotation.*;

@RestController
public class LoginController {

    private final LoginService loginService;

    public LoginController(LoginService loginService) {
        this.loginService = loginService;
    }

    @PostMapping("/api/user/login")
    public ResponseEntity<?> login(@RequestBody LoginRequest request) {
        try {
            LoginResponse response = loginService.login(request);
            return ResponseEntity.ok(response);
        } catch (BadCredentialsException e) {
            // TODO: 之後實作 GlobalException。
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED)
                    .body("登入失敗ㄌ！");
        }
    }

    @GetMapping("/api/user/home")
    public String home(@RequestHeader("X-Username") String header) {
        System.out.println("HEADER =================================================================================");
        System.out.println(header);
        return header;
    }

}
