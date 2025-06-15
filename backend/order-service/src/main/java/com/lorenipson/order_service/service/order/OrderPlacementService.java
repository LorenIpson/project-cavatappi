package com.lorenipson.order_service.service.order;

import com.lorenipson.order_service.dto.form.linepay.LinePayReqForm;
import com.lorenipson.order_service.dto.form.linepay.LinePayPackage;
import com.lorenipson.order_service.dto.form.linepay.LinePayProduct;
import com.lorenipson.order_service.dto.form.linepay.LinePayReqResponse;
import com.lorenipson.order_service.dto.internal.InternalItemRequest;
import com.lorenipson.order_service.dto.request.OrderPlacementRequest;
import com.lorenipson.order_service.dto.internal.AddOnDetailResponse;
import com.lorenipson.order_service.dto.internal.InternalItemSnapshotResponse;
import com.lorenipson.order_service.dto.response.OrderPlacementResponse;
import com.lorenipson.order_service.entity.Order;
import com.lorenipson.order_service.entity.OrderDetail;
import com.lorenipson.order_service.entity.OrderPayment;
import com.lorenipson.order_service.repository.OrderDetailsRepository;
import com.lorenipson.order_service.repository.OrderPaymentRepository;
import com.lorenipson.order_service.repository.OrderRepository;
import com.lorenipson.order_service.service.payment.impl.LinePayService;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestClient;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.*;
import java.util.stream.Collectors;

/**
 * 使用者下單功能的主要邏輯。
 */
@Service
public class OrderPlacementService {

    @Value("${frontend.url}")
    private String frontendURL;

    @Value("${backend.menu.service.url}")
    private String backendMenuServiceURL;

    private final OrderRepository orderRepos;
    private final OrderDetailsRepository orderDetailsRepos;
    private final OrderPaymentRepository orderPaymentRepos;

    private final LinePayService linePayService;

    public OrderPlacementService(OrderRepository orderRepos,
                                 OrderDetailsRepository orderDetailsRepos,
                                 OrderPaymentRepository orderPaymentRepos,
                                 LinePayService linePayService) {
        this.orderRepos = orderRepos;
        this.orderDetailsRepos = orderDetailsRepos;
        this.orderPaymentRepos = orderPaymentRepos;
        this.linePayService = linePayService;
    }

    @Transactional
    public OrderPlacementResponse placeOrder(UUID memberId, String username, OrderPlacementRequest request) {

        List<InternalItemRequest> items = request.getItems();
        System.out.println("====== ITEMS ==================");

        List<InternalItemSnapshotResponse> itemDetails = getItemDetails(items);
        System.out.println("====== GET ITEMS ==================");

        BigDecimal totalPrice = calculateTotalPrice(itemDetails);
        System.out.println("====== TOTAL PRICE ==================");

        Order newOrder = createNewOrder(memberId, username, request, totalPrice, itemDetails);
        System.out.println("====== NEW ORDER ==================");

        LinePayReqForm linePayForm = createLinePayForm(newOrder, itemDetails); // TODO: 移動到 createPayment 判斷後再執行
        System.out.println("====== NEW ORDER FORM ==================");

        OrderPlacementResponse response = createPayment(newOrder, request.getPaymentMethod(), totalPrice, linePayForm);
        System.out.println("====== NEW ORDER PAYMENT ==================");

        return response;

    }

    /**
     * 呼叫 menu-service，取得餐點資訊 snapshots。
     */
    private List<InternalItemSnapshotResponse> getItemDetails(List<InternalItemRequest> requests) {
        return RestClient.create().post()
                .uri(backendMenuServiceURL + "/api/menu/internal/getItemSnapshot")
                .contentType(MediaType.APPLICATION_JSON)
                .body(requests)
                .retrieve()
                .body(new ParameterizedTypeReference<>() {
                });
    }

    /**
     * 計算總價。
     */
    private BigDecimal calculateTotalPrice(List<InternalItemSnapshotResponse> items) {
        return items.stream().map(this::calculateItemPrice).reduce(BigDecimal.ZERO, BigDecimal::add);
    }

    private BigDecimal calculateItemPrice(InternalItemSnapshotResponse item) {

        BigDecimal basePrice = item.getBasePrice();
        BigDecimal doughPrice = item.getDough().getExtraPrice();
        BigDecimal sizePrice = item.getSize().getExtraPrice();
        BigDecimal addOnsPrice = item.getAddons() != null
                ? item.getAddons()
                .stream()
                .map(AddOnDetailResponse::getExtraPrice)
                .reduce(BigDecimal.ZERO, BigDecimal::add)
                : BigDecimal.ZERO;

        return basePrice.add(doughPrice).add(sizePrice).add(addOnsPrice);

    }

    /**
     * 建立訂單。<br>
     */
    private Order createNewOrder(UUID memberId,
                                 String username,
                                 OrderPlacementRequest request,
                                 BigDecimal totalPrice,
                                 List<InternalItemSnapshotResponse> itemDetails) {

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
        newOrder.setIsCompleted(false);
        newOrder.setOrderStatus("訂單待確認");
        newOrder.setIsPaid(false);
        newOrder.setPaymentStatus("付款待確認");
        newOrder.setTotalPrice(totalPrice);
        orderRepos.save(newOrder);

        // 以下是將從 menu-service 拿到的 snapshot 寫入資料庫的步驟。
        for (InternalItemSnapshotResponse item : itemDetails) {
            OrderDetail newDetail = new OrderDetail();
            newDetail.setOrder(newOrder);
            newDetail.setItemId(item.getItemId());
            newDetail.setItemName(item.getItemName());
            newDetail.setItemBasePrice(item.getBasePrice());

            // 存豆和尺寸
            Map<String, Object> newItemSpecs = new HashMap<>();
            newItemSpecs.put("sizeId", item.getSize().getSizeId());
            newItemSpecs.put("size", item.getSize().getSize());
            newItemSpecs.put("sizeExtraPrice", item.getSize().getExtraPrice());
            newItemSpecs.put("doughId", item.getDough().getDoughId());
            newItemSpecs.put("doughType", item.getDough().getDoughType());
            newItemSpecs.put("doughExtraPrice", item.getDough().getExtraPrice());
            newDetail.setItemSpecs(newItemSpecs);

            List<Map<String, Object>> newItemAddonList = new ArrayList<>();
            item.getAddons().forEach(addon -> {
                Map<String, Object> addonSpecs = new HashMap<>();
                addonSpecs.put("addonId", addon.getId());
                addonSpecs.put("name", addon.getName());
                addonSpecs.put("addonExtraPrice", addon.getExtraPrice());
                newItemAddonList.add(addonSpecs);
            });
            newDetail.setItemAddons(newItemAddonList);

            orderDetailsRepos.save(newDetail);
        }

        return newOrder;

    }

    /**
     * 包裝 LINE Pay Request Body。
     */
    private LinePayReqForm createLinePayForm(Order order, List<InternalItemSnapshotResponse> itemDetails) {

        String confirmRedirectURL = frontendURL + "/cart/payment/line-pay/confirm?orderId=" + order.getId();
        String cancelRedirectURL = frontendURL + "/cart/payment/line-pay/cancel";

        LinePayReqForm form = new LinePayReqForm();

        form.setOrderId(order.getId());
        form.setAmount(order.getTotalPrice().intValue());
        form.setCurrency("TWD");
        Map<String, String> redirectUrls = new HashMap<>();
        redirectUrls.put("confirmUrl", confirmRedirectURL);
        redirectUrls.put("cancelUrl", cancelRedirectURL);
        form.setRedirectUrls(redirectUrls);

        // products
        List<LinePayProduct> products = new ArrayList<>();
        itemDetails.forEach(item -> {
            LinePayProduct product = new LinePayProduct();

            // Prices
            BigDecimal addOnsPrice = item.getAddons() != null
                    ? item.getAddons()
                    .stream()
                    .map(AddOnDetailResponse::getExtraPrice)
                    .reduce(BigDecimal.ZERO, BigDecimal::add)
                    : BigDecimal.ZERO;
            BigDecimal doughPrice = item.getDough().getExtraPrice();
            BigDecimal sizePrice = item.getSize().getExtraPrice();

            // Names
            String addOns = item.getAddons() != null
                    ? item.getAddons()
                    .stream()
                    .map(AddOnDetailResponse::getName)
                    .collect(Collectors.joining(", "))
                    : "";
            product.setName(item.getItemName()
                            + " / "
                            + item.getSize().getSize()
                            + " / "
                            + item.getDough().getDoughType()
                            + " / "
                            + addOns
            );

            product.setQuantity(1);
            product.setPrice(item.getBasePrice().add(addOnsPrice).add(doughPrice).add(sizePrice).intValue());
            products.add(product);

        });

        // packages
        List<LinePayPackage> packages = new ArrayList<>();
        LinePayPackage aPackage = new LinePayPackage();
        aPackage.setId("order-" + order.getId());
        aPackage.setName("Package");
        aPackage.setAmount(order.getTotalPrice().intValue());
        aPackage.setProducts(products);
        packages.add(aPackage);

        form.setPackages(packages);

        return form;

    }

    /**
     * 建立訂單 new OrderPayment 的邏輯，而不是實際付款的程序。<br>
     * 回傳為 Redirect URL。
     */
    private OrderPlacementResponse createPayment(Order order, String paymentMethod, BigDecimal amount, LinePayReqForm form) {

        OrderPayment newPayment = new OrderPayment();
        OrderPlacementResponse response = new OrderPlacementResponse();
        newPayment.setOrder(order);

        switch (paymentMethod) {
            case "CASH_PAY" -> {
                newPayment.setPaymentMethod("CASH_PAY");
                newPayment.setProvider("LOCAL");
                newPayment.setPaymentTime(null);
                newPayment.setTransactionId(null);
                order.setPaymentStatus("待取餐付款");
                response.setSuccess(true);
                response.setExternal(false);
                response.setRedirectURL("/cart/payment/success");
            }
            case "LINE_PAY" -> {
                newPayment.setPaymentMethod("LINE_PAY");
                newPayment.setProvider("LINE");
                LinePayReqResponse linePayReqResponse = linePayService.requestOnlinePay(form);
                newPayment.setTransactionId(linePayReqResponse.getTransactionId());
                newPayment.setRedirectUrl(linePayReqResponse.getWebUrl());
                newPayment.setPaymentTime(null);
                order.setPaymentStatus("Line Pay 未完成");
                response.setSuccess(true);
                response.setExternal(true);
                response.setRedirectURL(linePayReqResponse.getWebUrl());
            }
            case "PAYPAL_PAY" -> {
                newPayment.setPaymentMethod("PAYPAL_PAY");
                newPayment.setProvider("PAYPAL");
                newPayment.setTransactionId(null);
                newPayment.setPaymentTime(null);
                order.setPaymentStatus("Paypal 未完成");
                response.setSuccess(true);
                response.setExternal(true);
                response.setRedirectURL("/cart/payment/error");
            }
            default -> {
                response.setSuccess(false);
                response.setExternal(false);
                response.setRedirectURL("/cart/payment/error");
            }
        }

        newPayment.setTotalPrice(amount);
        orderPaymentRepos.save(newPayment);
        orderRepos.save(order);

        return response;

    }

}
