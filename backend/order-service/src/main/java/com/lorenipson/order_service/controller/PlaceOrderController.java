package com.lorenipson.order_service.controller;

import com.lorenipson.order_service.dto.request.PlaceOrderRequest;
import com.lorenipson.order_service.service.PlaceOrderService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.UUID;

@RestController
public class PlaceOrderController {

    private final PlaceOrderService placeOrderService;

    public PlaceOrderController(PlaceOrderService placeOrderService) {
        this.placeOrderService = placeOrderService;
    }

    /**
     * 前端按下購物車中的送出訂單時，會呼叫這一個 API。<br>
     */
    @PostMapping("/api/order/placeNewOrder")
    public ResponseEntity<String> placeOrder(@RequestBody PlaceOrderRequest request) {
        // TODO: RequestHeader "X-Username"
        placeOrderService.placeOrder(UUID.randomUUID(), "yolo420", request);
        return ResponseEntity.ok("Order placed successfully");
        // TODO: 下一步要進入付款頁面
    }

}
