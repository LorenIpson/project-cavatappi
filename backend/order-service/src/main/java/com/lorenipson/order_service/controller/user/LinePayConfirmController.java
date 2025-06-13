package com.lorenipson.order_service.controller.user;

import com.lorenipson.order_service.service.payment.impl.CashPayService;
import com.lorenipson.order_service.service.payment.impl.LinePayService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class LinePayConfirmController {

    private final CashPayService cashPayService;
    private final LinePayService linePayService;

    public LinePayConfirmController(CashPayService cashPayService, LinePayService linePayService) {
        this.cashPayService = cashPayService;
        this.linePayService = linePayService;
    }

    /**
     * 到店付款確認使用。<br>
     * */
    @PutMapping("/api/order/payment/confirm/local")
    public ResponseEntity<?> confirmLocalPayment(Long orderId) {
        String response = cashPayService.confirmLocalPayment(orderId);
        return ResponseEntity.ok(response);
    }

    /**
     * Line Pay 確認付款使用。<br>
     * 由前端 <code>LinePayConfirmView</code> 使用。
     * */
    @PostMapping("/api/order/payment/line-pay/confirm")
    public ResponseEntity<String> confirmLinePayment(@RequestParam("orderId") Long orderId) {
        String response = linePayService.confirmOnlinePay(orderId);
        return ResponseEntity.ok(response);
    }

}
