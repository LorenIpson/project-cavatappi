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
import java.util.*;

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

    public Page<OrderPreviewResponse> getAllOrdersPreviewByDate(LocalDate date, Pageable pageable) {

        LocalDate targetDate = (date != null) ? date : LocalDate.now();
        LocalDateTime startOfDay = targetDate.atStartOfDay();
        LocalDateTime endOfDay = targetDate.atTime(LocalTime.MAX);

        Page<Order> orders = orderRepos.findAllByReceiveDateBetween(startOfDay, endOfDay, pageable);
        List<OrderPreviewResponse> mainResponse = toOrderBriefResponse(orders);
        return new PageImpl<>(mainResponse, pageable, orders.getTotalElements());

    }

    public Page<OrderPreviewResponse> getAllOrdersPreviewByDateAndNotCompleted(LocalDate date, Pageable pageable) {

        LocalDate targetDate = (date != null) ? date : LocalDate.now();
        LocalDateTime startOfDay = targetDate.atStartOfDay();
        LocalDateTime endOfDay = targetDate.atTime(LocalTime.MAX);

        Page<Order> orders = orderRepos.findAllByReceiveDateBetweenAndIsCompleted(startOfDay, endOfDay, false, pageable);
        List<OrderPreviewResponse> mainResponse = toOrderBriefResponse(orders);
        return new PageImpl<>(mainResponse, pageable, orders.getTotalElements());

    }

    public Page<OrderPreviewResponse> getAllOrdersPreview(Pageable pageable) {

        Page<Order> allOrderList = orderRepos.findAll(pageable);
        List<OrderPreviewResponse> response = toOrderBriefResponse(allOrderList);
        return new PageImpl<>(response, pageable, allOrderList.getTotalElements());

    }

    public Page<OrderPreviewResponse> getAllOrdersPreviewByMemberId(UUID memberId, Pageable pageable) {

        Page<Order> byMemberId = orderRepos.findByMemberId(memberId, pageable);
        List<OrderPreviewResponse> response = toOrderBriefResponse(byMemberId);
        return new PageImpl<>(response, pageable, byMemberId.getTotalElements());

    }

    public Page<OrderPreviewResponse> getMemberOrdersPreviewByIsNotCompleted(UUID memberId, Pageable pageable) {

        Page<Order> byMemberIdAndIsCompleted = orderRepos.findByMemberIdAndIsCompleted(memberId, false, pageable);
        List<OrderPreviewResponse> response = toOrderBriefResponse(byMemberIdAndIsCompleted);
        return new PageImpl<>(response, pageable, byMemberIdAndIsCompleted.getTotalElements());

    }

    public Page<OrderPreviewResponse> getMemberOrdersPreviewByIsNotPaid(UUID memberId, Pageable pageable) {

        Page<Order> byMemberIdAndIsPaid = orderRepos.findByMemberIdAndIsPaid(memberId, false, pageable);
        List<OrderPreviewResponse> response = toOrderBriefResponse(byMemberIdAndIsPaid);
        return new PageImpl<>(response, pageable, byMemberIdAndIsPaid.getTotalElements());

    }

    private List<OrderPreviewResponse> toOrderBriefResponse(Page<Order> allOrderList) {

        List<OrderPreviewResponse> mainResponse = new ArrayList<>(); // 預先準備好整個回傳的 List<>

        allOrderList.forEach(order -> { // for each 一筆筆 order 分別處理
            OrderPreviewResponse orderResponse = new OrderPreviewResponse(); // 一筆 order

            orderResponse.setOrderId(order.getId());
            orderResponse.setBuyerName(order.getBuyerName());
            orderResponse.setBuyerPhone(order.getBuyerPhone());
            orderResponse.setBuyerMessage(order.getBuyerMessage());
            orderResponse.setOrderedDate(order.getOrderedDate());
            orderResponse.setReceiveDate(order.getReceiveDate());
            orderResponse.setEdited(order.getIsEdited());
            orderResponse.setCompleted(order.getIsCompleted());
            orderResponse.setOrderStatus(order.getOrderStatus());
            orderResponse.setPaid(order.getIsPaid());
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

    public OrderDetailResponse getOrderByOrderId(Long orderId) {

        Order targetOrder = orderRepos.findById(orderId).orElseThrow(EntityNotFoundException::new);
        return toOrderDetailResponse(targetOrder);

    }

    private OrderDetailResponse toOrderDetailResponse(Order targetOrder) {
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
        mainResponse.setCompleted(targetOrder.getIsCompleted());
        mainResponse.setOrderStatus(targetOrder.getOrderStatus());
        mainResponse.setPaid(targetOrder.getIsPaid());
        mainResponse.setPaymentStatus(targetOrder.getPaymentStatus());
        mainResponse.setPaymentMethod(targetOrderPayment.getPaymentMethod());
        mainResponse.setPaymentRedirectURL(targetOrderPayment.getRedirectUrl());
        mainResponse.setTotalPrice(targetOrder.getTotalPrice());

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

    public OrderDetailResponse getOrderByOrderIdAndMemberId(UUID memberId, Long orderId) {

        Order targetOrder = orderRepos.findByMemberIdAndId(memberId, orderId)
                .orElseThrow(EntityNotFoundException::new);
        return toOrderDetailResponse(targetOrder);

    }

}
