package com.lorenipson.order_service.controller;

import com.lorenipson.order_service.service.payment.impl.CashPayService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class PaymentController {

    private final CashPayService cashPayService;

    public PaymentController(CashPayService cashPayService) {
        this.cashPayService = cashPayService;
    }

    @PutMapping("/api/order/payment/confirm/local")
    public ResponseEntity<?> confirmLocalPayment(Long orderId) {
        String response = cashPayService.confirmLocalPayment(orderId);
        return ResponseEntity.ok(response);
    }

}
