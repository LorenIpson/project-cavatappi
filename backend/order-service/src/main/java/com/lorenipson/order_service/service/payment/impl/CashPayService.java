package com.lorenipson.order_service.service.payment.impl;

import com.lorenipson.order_service.entity.Order;
import com.lorenipson.order_service.entity.OrderPayment;
import com.lorenipson.order_service.repository.OrderPaymentRepository;
import com.lorenipson.order_service.repository.OrderRepository;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;

@Service
public class CashPayService {

    private final OrderRepository orderRepos;
    private final OrderPaymentRepository orderPaymentRepos;

    public CashPayService(OrderRepository orderRepos,
                          OrderPaymentRepository orderPaymentRepos) {
        this.orderRepos = orderRepos;
        this.orderPaymentRepos = orderPaymentRepos;
    }

    public String confirmLocalPayment(Long orderId) {

        Order targetOrder = orderRepos.findById(orderId).orElseThrow(EntityNotFoundException::new);
        OrderPayment targetPayment = orderPaymentRepos.findById(orderId).orElseThrow(EntityNotFoundException::new);

        targetOrder.setPaymentStatus("已付款");
        orderRepos.save(targetOrder);

        targetPayment.setPaymentTime(LocalDateTime.now());
        orderPaymentRepos.save(targetPayment);

        return "Local payment successful";

    }

}
