package com.lorenipson.order_service.service;

import com.lorenipson.order_service.dto.internal.InternalItemRequest;
import com.lorenipson.order_service.dto.request.PlaceOrderRequest;
import com.lorenipson.order_service.dto.response.AddonResponse;
import com.lorenipson.order_service.dto.response.ItemSnapshotResponse;
import com.lorenipson.order_service.entity.Order;
import com.lorenipson.order_service.entity.OrderDetail;
import com.lorenipson.order_service.entity.OrderPayment;
import com.lorenipson.order_service.repository.OrderDetailsRepository;
import com.lorenipson.order_service.repository.OrderPaymentRepository;
import com.lorenipson.order_service.repository.OrderRepository;
import jakarta.transaction.Transactional;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestClient;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

@Service
public class PlaceOrderService {

    private final OrderRepository orderRepos;
    private final OrderDetailsRepository orderDetailsRepos;
    private final OrderPaymentRepository orderPaymentRepos;

    public PlaceOrderService(OrderRepository orderRepos,
                             OrderDetailsRepository orderDetailsRepos,
                             OrderPaymentRepository orderPaymentRepos) {
        this.orderRepos = orderRepos;
        this.orderDetailsRepos = orderDetailsRepos;
        this.orderPaymentRepos = orderPaymentRepos;
    }

    @Transactional
    public void placeOrder(UUID memberId, String username, PlaceOrderRequest request) {

        List<InternalItemRequest> items = request.getItems();
        System.out.println("====== ITEMS ==================");

        List<ItemSnapshotResponse> itemDetails = getItemDetails(items); // 會呼叫 menu-service 8082
        System.out.println("====== GET ITEMS ==================");

        BigDecimal totalPrice = calculateTotalPrice(itemDetails);
        System.out.println("====== TOTAL PRICE ==================");

        Order newOrder = createNewOrder(memberId, username, request, totalPrice, itemDetails);
        System.out.println("====== NEW ORDER ==================");

        createPayment(newOrder, request.getPaymentMethod(), totalPrice);
        System.out.println("====== NEW ORDER PAYMENT ==================");

    }

    private Order createNewOrder(UUID memberId, String username, PlaceOrderRequest request, BigDecimal totalPrice, List<ItemSnapshotResponse> itemDetails) {

        Order newOrder = new Order();
        newOrder.setMemberId(memberId);
        newOrder.setUsername(username);
        newOrder.setBuyerName(request.getBuyerName());
        newOrder.setBuyerPhone(request.getBuyerPhone());
        newOrder.setBuyerMessage(request.getBuyerMessage());
        newOrder.setOrderedDate(LocalDateTime.now());
        newOrder.setReceiveDate(request.getReceiveDate());
        newOrder.setIsEdited(false);
        newOrder.setEditedAt(null);
        newOrder.setOrderStatus("PENDING"); // TODO: 新增店家確認訂單、製作中、已完成
        newOrder.setPaymentStatus("PENDING"); // TODO: 新增未付款、已付款、取餐付款
        newOrder.setTotalPrice(totalPrice);
        orderRepos.save(newOrder);

        for (ItemSnapshotResponse item : itemDetails) {
            OrderDetail newDetail = new OrderDetail();
            newDetail.setOrder(newOrder);
            newDetail.setItemId(item.getItemId());
            newDetail.setItemName(item.getItemName());
            // TODO: 設定 itemSpecs 和 itemAddons，JSON 格式
            newDetail.setItemSpecs(null);
            newDetail.setItemAddons(null);
            newDetail.setItemBasePrice(item.getBasePrice());
            orderDetailsRepos.save(newDetail);
        }

        return newOrder;

    }

    private List<ItemSnapshotResponse> getItemDetails(List<InternalItemRequest> requests) {

        return RestClient.create().post()
                .uri("http://localhost:8082/api/menu/internal/getItemSnapshot") // TODO: Hardcoded
                .contentType(MediaType.APPLICATION_JSON)
                .body(requests)
                .retrieve()
                .body(new ParameterizedTypeReference<>() {
                });

    }

    private BigDecimal calculateTotalPrice(List<ItemSnapshotResponse> responseBody) {

        BigDecimal totalPrice = BigDecimal.valueOf(0.0);
        for (ItemSnapshotResponse item : responseBody) {
            totalPrice = totalPrice.add(item.getBasePrice());
            totalPrice = totalPrice.add(item.getDough().getExtraPrice());
            for (AddonResponse addon : item.getAddons()) {
                totalPrice = totalPrice.add(addon.getExtraPrice());
            }
        }
        return totalPrice;

    }

    private void createPayment(Order order, String paymentMethod, BigDecimal amount) {

        // TODO: 建立訂單 new OrderPayment 的邏輯，而不是實際付款的程序

        if (paymentMethod == null) {
            return;
        }
        OrderPayment newPayment = new OrderPayment();
        newPayment.setOrder(order);
        switch (paymentMethod) {
            case "CASH_PAY" -> {
                newPayment.setPaymentMethod("CASH_PAY");
                newPayment.setProvider("LOCAL");
                newPayment.setPaymentTime(null);
            }
            case "LINE_PAY" -> {
                newPayment.setPaymentMethod("LINE_PAY");
                newPayment.setProvider("LINE");
                newPayment.setPaymentTime(null);
            }
            case "PAYPAL_PAY" -> {
                newPayment.setPaymentMethod("PAYPAL_PAY");
                newPayment.setProvider("PAYPAL");
                newPayment.setPaymentTime(null);
            }
        }
        newPayment.setTransactionId(null);
        newPayment.setTotalPrice(amount);
        orderPaymentRepos.save(newPayment);

    }

}
