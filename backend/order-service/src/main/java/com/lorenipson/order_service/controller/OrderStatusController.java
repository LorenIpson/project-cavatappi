package com.lorenipson.order_service.controller;

import com.lorenipson.order_service.dto.request.SetOrderStatusRequest;
import com.lorenipson.order_service.service.OrderStatusService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
public class OrderStatusController {

    private final OrderStatusService orderStatusService;

    public OrderStatusController(OrderStatusService orderStatusService) {
        this.orderStatusService = orderStatusService;
    }

    @PutMapping("/api/order/{id}/confirm")
    public ResponseEntity<?> confirmOrder(@PathVariable("id") long id) {
        String response = orderStatusService.confirmOrder(id);
        return ResponseEntity.ok(response);
    }

    @PutMapping("/api/order/{id}/status")
    public ResponseEntity<?> setOrderStatus(@PathVariable("id") Long orderId, @RequestBody SetOrderStatusRequest request) {
        String response = orderStatusService.setOrderStatus(orderId, request);
        return ResponseEntity.ok(response);
    }

    @GetMapping("/api/order/{id}/status/test")
    public String testo(@PathVariable("id") Long orderId) {
        return "Yolo" + orderId;
    }

}
