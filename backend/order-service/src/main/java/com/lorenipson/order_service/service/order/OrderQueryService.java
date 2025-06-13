package com.lorenipson.order_service.service.order;

import com.lorenipson.order_service.dto.response.OrderPreviewResponse;
import com.lorenipson.order_service.dto.response.OrderPreviewItemResponse;
import com.lorenipson.order_service.dto.response.OrderDetailItemResponse;
import com.lorenipson.order_service.dto.response.OrderDetailResponse;
import com.lorenipson.order_service.entity.Order;
import com.lorenipson.order_service.entity.OrderDetail;
import com.lorenipson.order_service.entity.OrderPayment;
import com.lorenipson.order_service.repository.OrderDetailsRepository;
import com.lorenipson.order_service.repository.OrderPaymentRepository;
import com.lorenipson.order_service.repository.OrderRepository;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class OrderQueryService {

    private final OrderRepository orderRepos;
    private final OrderDetailsRepository orderDetailsRepos;
    private final OrderPaymentRepository orderPaymentRepos;

    public OrderQueryService(OrderRepository orderRepos, OrderDetailsRepository orderDetailsRepos, OrderPaymentRepository orderPaymentRepos) {
        this.orderRepos = orderRepos;
        this.orderDetailsRepos = orderDetailsRepos;
        this.orderPaymentRepos = orderPaymentRepos;
    }

    public Page<OrderPreviewResponse> getAllOrdersByDate(LocalDate date, Pageable pageable) {

        LocalDate targetDate = (date != null) ? date : LocalDate.now();
        LocalDateTime startOfDay = targetDate.atStartOfDay();
        LocalDateTime endOfDay = targetDate.atTime(LocalTime.MAX);

        Page<Order> orders = orderRepos.findAllByReceiveDateBetween(startOfDay, endOfDay, pageable);
        List<OrderPreviewResponse> mainResponse = toOrderBriefResponse(orders);
        return new PageImpl<>(mainResponse, pageable, orders.getTotalElements());

    }

    public Page<OrderPreviewResponse> getAllOrders(Pageable pageable) {

        Page<Order> allOrderList = orderRepos.findAll(pageable); // 取得所有ㄉ訂單
        List<OrderPreviewResponse> mainResponse = toOrderBriefResponse(allOrderList);
        return new PageImpl<>(mainResponse, pageable, allOrderList.getTotalElements());

    }

    private List<OrderPreviewResponse> toOrderBriefResponse(Page<Order> allOrderList) {

        List<OrderPreviewResponse> mainResponse = new ArrayList<>(); // 預先準備好整個回傳的 List<>

        allOrderList.forEach(order -> { // for each 一筆筆 order 分別處理
            OrderPreviewResponse orderResponse = new OrderPreviewResponse(); // 一筆 order

            orderResponse.setOrderId(order.getId());
            orderResponse.setBuyerName(order.getBuyerName());
            orderResponse.setBuyerMessage(order.getBuyerMessage());
            orderResponse.setOrderedDate(order.getOrderedDate());
            orderResponse.setReceiveDate(order.getReceiveDate());
            orderResponse.setEdited(order.getIsEdited());
            orderResponse.setOrderStatus(order.getOrderStatus());
            orderResponse.setPaymentStatus(order.getPaymentStatus());
            orderResponse.setTotalPrice(order.getTotalPrice());

            List<OrderPreviewItemResponse> itemList = new ArrayList<>(); // 一筆 order 會有很多 item
            List<OrderDetail> byOrder = orderDetailsRepos.findByOrder(order); // TODO: 大概會有 N+1 問題
            byOrder.forEach(orderDetail -> {
                OrderPreviewItemResponse item = new OrderPreviewItemResponse();

                item.setItemName(orderDetail.getItemName());

                Map<String, Object> originItemSpecs = orderDetail.getItemSpecs(); // 重新組裝 jsonb 內的資料
                Map<String, Object> responseItemSpecs = new HashMap<>();
                responseItemSpecs.put("size", originItemSpecs.get("size"));
                responseItemSpecs.put("doughType", originItemSpecs.get("doughType"));
                item.setItemSpecs(responseItemSpecs);

                itemList.add(item);
            });
            orderResponse.setItems(itemList);

            mainResponse.add(orderResponse);
        });
        return mainResponse;

    }

    public OrderDetailResponse getOrder(Long orderId) {

        Order targetOrder = orderRepos.findById(orderId).orElseThrow(EntityNotFoundException::new);
        List<OrderDetail> targetOrderItems = orderDetailsRepos.findByOrder(targetOrder);
        OrderPayment targetOrderPayment = orderPaymentRepos.findByOrder(targetOrder)
                .orElseThrow(EntityNotFoundException::new);

        OrderDetailResponse mainResponse = new OrderDetailResponse();

        mainResponse.setOrderId(targetOrder.getId());
        mainResponse.setUserId(targetOrder.getMemberId());
        mainResponse.setBuyerName(targetOrder.getBuyerName());
        mainResponse.setBuyerPhone(targetOrder.getBuyerPhone());
        mainResponse.setBuyerMessage(targetOrder.getBuyerMessage());
        mainResponse.setOrderedDate(targetOrder.getOrderedDate());
        mainResponse.setReceiveDate(targetOrder.getReceiveDate());
        mainResponse.setEdited(targetOrder.getIsEdited());
        mainResponse.setEditedAt(targetOrder.getEditedAt());
        mainResponse.setOrderStatus(targetOrder.getOrderStatus());
        mainResponse.setPaymentStatus(targetOrder.getPaymentStatus());
        mainResponse.setTotalPrice(targetOrder.getTotalPrice());
        mainResponse.setPaymentMethod(targetOrderPayment.getPaymentMethod());

        List<OrderDetailItemResponse> mainResponseItems = new ArrayList<>();
        targetOrderItems.forEach(orderItem -> {
            OrderDetailItemResponse orderItems = new OrderDetailItemResponse();
            orderItems.setItemId(orderItem.getItemId());
            orderItems.setItemName(orderItem.getItemName());
            orderItems.setItemBasePrice(orderItem.getItemBasePrice());
            orderItems.setItemSpecs(orderItem.getItemSpecs());
            orderItems.setItemAddons(orderItem.getItemAddons());
            mainResponseItems.add(orderItems);
        });
        mainResponse.setItems(mainResponseItems);

        return mainResponse;

    }

}
