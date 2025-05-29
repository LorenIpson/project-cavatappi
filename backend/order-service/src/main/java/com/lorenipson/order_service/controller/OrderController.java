package com.lorenipson.order_service.controller;

import com.lorenipson.order_service.dto.response.get.GetOrderBriefResponse;
import com.lorenipson.order_service.dto.response.get.GetOrderResponse;
import com.lorenipson.order_service.service.OrderService;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class OrderController {

    private final OrderService orderService;

    public OrderController(OrderService orderService) {
        this.orderService = orderService;
    }

    @GetMapping("/api/order/get-details/{id}")
    public ResponseEntity<GetOrderResponse> getOrder(@PathVariable("id") Long id) {
        return ResponseEntity.ok(orderService.getOrder(id));
    }

    @GetMapping("/api/order/get/all")
    public ResponseEntity<Page<GetOrderBriefResponse>> getAllOrders(@PageableDefault Pageable pageable) {
        Page<GetOrderBriefResponse> response = orderService.getAllOrders(pageable);
        return ResponseEntity.ok(response);
    }

}
