package com.lorenipson.order_service.controller;

import com.lorenipson.order_service.dto.request.PlaceOrderRequest;
import com.lorenipson.order_service.dto.response.PlaceOrderResponse;
import com.lorenipson.order_service.service.PlaceOrderService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
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
     * Payment: Request。
     */
    @SuppressWarnings("UastIncorrectHttpHeaderInspection")
    @PostMapping("/api/order/place-new-order")
    public ResponseEntity<PlaceOrderResponse> placeOrder(@RequestHeader("X-Member-Id") UUID memberUUID,
                                                         @RequestHeader("X-Username") String username,
                                                         @RequestBody PlaceOrderRequest request) {
        PlaceOrderResponse response = placeOrderService.placeOrder(memberUUID, username, request);
        return ResponseEntity.ok(response);
    }

}
