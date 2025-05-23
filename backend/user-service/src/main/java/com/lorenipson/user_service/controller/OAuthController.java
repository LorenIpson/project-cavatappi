package com.lorenipson.user_service.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Map;

@RestController
public class OAuthController {

    @GetMapping("/api/user/oauth/authorization/github")
    public ResponseEntity<?> redirectGitHubLogin() {
        String redirectUri = "http://localhost:8080/oauth2/authorization/github";
        return ResponseEntity.ok(Map.of("redirectUri", redirectUri)); // TODO: 檢查看看和 DTO 寫法的差異。
    }

}
