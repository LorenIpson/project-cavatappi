package com.lorenipson.order_service.controller;

import com.lorenipson.order_service.service.payment.impl.CashPayService;
import com.lorenipson.order_service.service.payment.impl.LinePayService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class PaymentController {

    private final CashPayService cashPayService;
    private final LinePayService linePayService;

    public PaymentController(CashPayService cashPayService, LinePayService linePayService) {
        this.cashPayService = cashPayService;
        this.linePayService = linePayService;
    }

    @PutMapping("/api/order/payment/confirm/local")
    public ResponseEntity<?> confirmLocalPayment(Long orderId) {
        String response = cashPayService.confirmLocalPayment(orderId);
        return ResponseEntity.ok(response);
    }

    @PostMapping("/api/order/payment/confirm")
    public ResponseEntity<String> confirmLinePayment(@RequestParam("orderId") Long orderId) {
        String response = linePayService.confirmOnlinePay(orderId);
        return ResponseEntity.ok(response);
    }

}
