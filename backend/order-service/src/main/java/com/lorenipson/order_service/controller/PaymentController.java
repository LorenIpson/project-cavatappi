package com.lorenipson.order_service.controller;

import com.lorenipson.order_service.service.PaymentService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class PaymentController {

    private final PaymentService paymentService;

    public PaymentController(PaymentService paymentService) {
        this.paymentService = paymentService;
    }

    @PutMapping("/api/order/payment/confirm/local")
    public ResponseEntity<?> confirmLocalPayment(Long orderId) {
        String response = paymentService.confirmLocalPayment(orderId);
        return ResponseEntity.ok(response);
    }

}
