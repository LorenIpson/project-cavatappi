package com.lorenipson.order_service.dto.response;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

/**
 * 回傳詳細細節用的包裝。<br>
 * 前端點擊單一 Order ID 該要得到的資訊。
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class OrderDetailResponse {

    private Long orderId;
    private UUID userId;
    private String buyerName;
    private String buyerPhone;
    private String buyerMessage;
    private LocalDateTime orderedDate;
    private LocalDateTime receiveDate;
    private boolean edited;
    private LocalDateTime editedAt;
    private boolean completed;
    private String orderStatus;
    private boolean paid;
    private String paymentStatus;
    private BigDecimal totalPrice;
    private String paymentMethod;

    private List<OrderDetailItemResponse> items;

}
