package com.lorenipson.order_service.service.order;

import com.lorenipson.order_service.dto.request.OrderStatusRequest;
import com.lorenipson.order_service.entity.Order;
import com.lorenipson.order_service.repository.OrderRepository;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.stereotype.Service;

/**
 * 管理員操作訂單狀態更新時使用的服務。
 */
@Service
public class OrderStatusService {

    private final OrderRepository orderRepos;

    public OrderStatusService(OrderRepository orderRepos) {
        this.orderRepos = orderRepos;
    }

    public String confirmOrder(Long orderId) {

        Order targetOrder = orderRepos.findById(orderId).orElseThrow(EntityNotFoundException::new);
        targetOrder.setOrderStatus("收到訂單");
        orderRepos.save(targetOrder);
        return "訂單：" + orderId + "，已確認。";

    }

    public String preparingOrder(Long orderId) {

        Order targetOrder = orderRepos.findById(orderId).orElseThrow(EntityNotFoundException::new);
        targetOrder.setOrderStatus("正在準備餐點");
        orderRepos.save(targetOrder);
        return "訂單：" + orderId + "，正在準備。";

    }

    public String orderPrepared(Long orderId) {

        Order targetOrder = orderRepos.findById(orderId).orElseThrow(EntityNotFoundException::new);
        targetOrder.setOrderStatus("等待取餐");
        orderRepos.save(targetOrder);
        return "訂單：" + orderId + "，已製作完成。";

    }

    public String orderCompleted(Long orderId) {

        Order targetOrder = orderRepos.findById(orderId).orElseThrow(EntityNotFoundException::new);
        targetOrder.setOrderStatus("等待取餐");
        orderRepos.save(targetOrder);
        return "訂單：" + orderId + "，已完成結帳取餐。";

    }

    public String setOrderStatus(Long orderId, OrderStatusRequest request) {

        Order targetOrder = orderRepos.findById(orderId).orElseThrow(EntityNotFoundException::new);
        targetOrder.setOrderStatus(request.getStatus());
        orderRepos.save(targetOrder);
        return "Order status updated";

    }

}
