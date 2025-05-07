package com.lorenipson.gateway_service.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class TestController {

    @GetMapping("/api/gateway/hello")
    public String testJwt() {
        return ("Hello World");
    }

}
