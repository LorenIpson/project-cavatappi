package com.lorenipson.order_service.controller.user;

import com.lorenipson.order_service.dto.response.OrderDetailResponse;
import com.lorenipson.order_service.dto.response.OrderPreviewResponse;
import com.lorenipson.order_service.service.order.OrderQueryService;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RestController;

import java.util.UUID;

@RestController
public class UserOrderQueryController {

    private final OrderQueryService orderQueryService;

    public UserOrderQueryController(OrderQueryService orderQueryService) {
        this.orderQueryService = orderQueryService;
    }

    @GetMapping("/api/order/my-order/all")
    public ResponseEntity<?> getAllOrdersPreviewByMemberId(@RequestHeader("X-Member-Id") UUID memberId, Pageable pageable) {
        Page<OrderPreviewResponse> response = orderQueryService.getAllOrdersPreviewByMemberId(memberId, pageable);
        return ResponseEntity.ok(response);
    }

    @GetMapping("/api/order/my-order/all/is-not-completed")
    public ResponseEntity<?> getMemberOrdersPreviewByIsNotCompleted(@RequestHeader("X-Member-Id") UUID memberId, Pageable pageable) {
        Page<OrderPreviewResponse> response = orderQueryService.getMemberOrdersPreviewByIsNotCompleted(memberId, pageable);
        return ResponseEntity.ok(response);
    }

    @GetMapping("/api/order/my-order/all/is-not-paid")
    public ResponseEntity<?> getMemberOrdersPreviewByIsNotPaid(@RequestHeader("X-Member-Id") UUID memberId, Pageable pageable) {
        Page<OrderPreviewResponse> response = orderQueryService.getMemberOrdersPreviewByIsNotPaid(memberId, pageable);
        return ResponseEntity.ok(response);
    }

    @GetMapping("/api/order/my-order/detail/{orderId}")
    public ResponseEntity<?> getMemberOrderDetail(@RequestHeader("X-Member-Id") UUID memberId, @PathVariable("orderId") Long orderId) {
        OrderDetailResponse response = orderQueryService.getOrderByOrderIdAndMemberId(memberId, orderId);
        return ResponseEntity.ok(response);
    }

}
