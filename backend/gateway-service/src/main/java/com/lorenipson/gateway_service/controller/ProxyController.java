package com.lorenipson.gateway_service.controller;

import com.lorenipson.gateway_service.security.JwtUtils;
import io.jsonwebtoken.Claims;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestClient;

@RestController
public class ProxyController {

    private final RestClient restClient;
    private final JwtUtils jwtUtils;

    public ProxyController(JwtUtils jwtUtils) {
        this.restClient = RestClient.builder().baseUrl("http://localhost:8081").build();
        this.jwtUtils = jwtUtils;
    }

    @GetMapping("/proxy/api/user/home")
    public ResponseEntity<?> forwardProxy(@RequestHeader("Authorization") String header) {

        System.out.println("HEADER ==================================================================================");
        System.out.println(header);

        System.out.println("CLAIMS SUBJ =============================================================================");
        Claims claims = jwtUtils.parseToken(header.replace("Bearer ", ""));
        System.out.println(claims.getSubject());

        System.out.println("USERNAME ================================================================================");
        String username = claims.getSubject();
        System.out.println(username);

        String body = restClient.get()
                .uri("/api/user/home")
                .header("X-Username", username)
                .retrieve()
                .body(String.class);

        return ResponseEntity.ok(body);

    }

}
