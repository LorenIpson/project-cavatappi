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

    public String confirmOrder(Long orderId) {

        Order targetOrder = orderRepos.findById(orderId).orElseThrow(EntityNotFoundException::new);
        targetOrder.setOrderStatus("已確認訂單");
        orderRepos.save(targetOrder);
        return "訂單：" + orderId + "，已確認。";

    }

    public String setOrderStatus(Long orderId, SetOrderStatusRequest request) {

        Order targetOrder = orderRepos.findById(orderId).orElseThrow(EntityNotFoundException::new);
        targetOrder.setOrderStatus(request.getStatus());
        orderRepos.save(targetOrder);
        return "Order status updated";

    }

}
