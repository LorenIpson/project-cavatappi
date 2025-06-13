package com.lorenipson.order_service.controller.user;

import com.lorenipson.order_service.dto.request.OrderPlacementRequest;
import com.lorenipson.order_service.dto.response.OrderPlacementResponse;
import com.lorenipson.order_service.service.order.OrderPlacementService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RestController;

import java.util.UUID;

/**
 * 使用者購物車結帳功能。
 */
@RestController
public class PlaceOrderController {

    private final OrderPlacementService orderPlacementService;

    public PlaceOrderController(OrderPlacementService orderPlacementService) {
        this.orderPlacementService = orderPlacementService;
    }

    /**
     * 前端按下購物車中的送出訂單時，會呼叫這一個 API。<br>
     * 注意，不包含實際的付款邏輯。
     */
    @SuppressWarnings("UastIncorrectHttpHeaderInspection")
    @PostMapping("/api/order/place-new-order")
    public ResponseEntity<OrderPlacementResponse> placeOrder(@RequestHeader("X-Member-Id") UUID memberUUID,
                                                             @RequestHeader("X-Username") String username,
                                                             @RequestBody OrderPlacementRequest request) {
        OrderPlacementResponse response = orderPlacementService.placeOrder(memberUUID, username, request);
        return ResponseEntity.ok(response);
    }

}
