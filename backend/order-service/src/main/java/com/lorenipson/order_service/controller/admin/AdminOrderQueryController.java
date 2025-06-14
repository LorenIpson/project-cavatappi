package com.lorenipson.order_service.controller.admin;

import com.lorenipson.order_service.dto.response.OrderPreviewResponse;
import com.lorenipson.order_service.dto.response.OrderDetailResponse;
import com.lorenipson.order_service.service.order.OrderQueryService;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.time.LocalDate;

@RestController
public class AdminOrderQueryController {

    private final OrderQueryService orderQueryService;

    public AdminOrderQueryController(OrderQueryService orderQueryService) {
        this.orderQueryService = orderQueryService;
    }

    @GetMapping("/api/admin/order/get/by/date")
    public ResponseEntity<Page<OrderPreviewResponse>> getOrderBriefByDate(@RequestParam(required = false) LocalDate date, @PageableDefault Pageable pageable) {
        Page<OrderPreviewResponse> response = orderQueryService.getAllOrdersByDate(date, pageable);
        return ResponseEntity.ok(response);
    }

    @GetMapping("/api/admin/order/get/all")
    public ResponseEntity<Page<OrderPreviewResponse>> getAllOrderPreview(@PageableDefault Pageable pageable) {
        Page<OrderPreviewResponse> response = orderQueryService.getAllOrdersPreview(pageable);
        return ResponseEntity.ok(response);
    }

    @GetMapping("/api/admin/order/get/details/{id}")
    public ResponseEntity<OrderDetailResponse> getOrder(@PathVariable("id") Long orderId) {
        return ResponseEntity.ok(orderQueryService.getOrderByOrderId(orderId));
    }

}
