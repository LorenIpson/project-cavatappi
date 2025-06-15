package com.lorenipson.order_service.controller.admin;

import com.lorenipson.order_service.dto.request.OrderStatusRequest;
import com.lorenipson.order_service.service.order.OrderStatusService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
public class AdminOrderStatusController {

    private final OrderStatusService orderStatusService;

    public AdminOrderStatusController(OrderStatusService orderStatusService) {
        this.orderStatusService = orderStatusService;
    }

    /**
     * 管理員確認收到訂單時使用。
     */
    @PutMapping("/api/admin/order/{id}/confirmed")
    public ResponseEntity<?> confirmOrder(@PathVariable("id") long id) {
        String response = orderStatusService.confirmOrder(id);
        return ResponseEntity.ok(response);
    }

    @PutMapping("/api/admin/order/{id}/preparing")
    public ResponseEntity<?> preparingOrder(@PathVariable("id") long id) {
        String response = orderStatusService.preparingOrder(id);
        return ResponseEntity.ok(response);
    }

    @PutMapping("/api/admin/order/{id}/prepared")
    public ResponseEntity<?> orderPrepared(@PathVariable("id") long id) {
        String response = orderStatusService.orderPrepared(id);
        return ResponseEntity.ok(response);
    }

    @PutMapping("/api/admin/order/{id}/completed")
    public ResponseEntity<?> orderCompleted(@PathVariable("id") long id) {
        String response = orderStatusService.orderCompleted(id);
        return ResponseEntity.ok(response);
    }

    /**
     * 管理員設定訂單目前狀態用。<br>
     * 保留用來做下拉選單方式。
     */
    @PutMapping("/api/admin/order/{id}/status")
    public ResponseEntity<?> setOrderStatus(@PathVariable("id") Long orderId, @RequestBody OrderStatusRequest request) {
        String response = orderStatusService.setOrderStatus(orderId, request);
        return ResponseEntity.ok(response);
    }

}
