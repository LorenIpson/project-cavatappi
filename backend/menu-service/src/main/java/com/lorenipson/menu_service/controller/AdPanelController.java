package com.lorenipson.menu_service.controller;

import com.lorenipson.menu_service.dto.retrieve.LatestPizzaResponse;
import com.lorenipson.menu_service.service.AdPanelService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class AdPanelController {

    private final AdPanelService adPanelService;

    public AdPanelController(AdPanelService adPanelService) {
        this.adPanelService = adPanelService;
    }

    @GetMapping("/api/menu/ad/newest-pizza")
    public ResponseEntity<?> newestPizza(){
        System.out.println("triggered");
        LatestPizzaResponse response = adPanelService.newestPizza();
        return ResponseEntity.ok(response);
    }

}
