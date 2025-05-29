package com.lorenipson.order_service.service;

import com.lorenipson.order_service.dto.request.SetOrderStatusRequest;
import com.lorenipson.order_service.entity.Order;
import com.lorenipson.order_service.repository.OrderRepository;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.stereotype.Service;

@Service
public class OrderStatusService {

    private final OrderRepository orderRepos;

    public OrderStatusService(OrderRepository orderRepos) {
        this.orderRepos = orderRepos;
    }

    public String setOrderStatus(SetOrderStatusRequest request) {

        Order targetOrder = orderRepos.findById(request.getOrderId()).orElseThrow(EntityNotFoundException::new);
        targetOrder.setOrderStatus(request.getStatus());
        orderRepos.save(targetOrder);
        return "Order status updated";

    }

}
