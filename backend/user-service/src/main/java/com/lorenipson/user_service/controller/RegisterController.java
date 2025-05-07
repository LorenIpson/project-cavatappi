package com.lorenipson.user_service.controller;

import com.lorenipson.user_service.dto.EasyRegisterRequest;
import com.lorenipson.user_service.dto.MemberRegisterRequest;
import com.lorenipson.user_service.service.RegisterService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class RegisterController {

    private final RegisterService registerService;

    public RegisterController(RegisterService registerService) {
        this.registerService = registerService;
    }

    @PostMapping("/api/user/register/easyRegister")
    public ResponseEntity<String> easyRegister(@RequestBody EasyRegisterRequest request) {
        registerService.easyRegister(request);
        return ResponseEntity.ok("Registered");
    }

    @PostMapping("/api/user/register/memberRegister")
    public ResponseEntity<String> memberRegister(@RequestBody MemberRegisterRequest request) {
        registerService.memberRegister(request);
        return ResponseEntity.ok("Registered");
    }

}
