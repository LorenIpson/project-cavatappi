package com.lorenipson.order_service.dto.response;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;

/**
 * 餐廳管理者所有訂單頁面的顯示項目。<br>
 * 同時用於餐廳內場的白單。
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class OrderPreviewResponse {

    private Long orderId;
    private String buyerName;
    private String buyerMessage;
    private LocalDateTime orderedDate;
    private LocalDateTime receiveDate;
    private boolean edited;
    private String orderStatus;
    private String paymentStatus;
    private BigDecimal totalPrice;

    private List<OrderPreviewItemResponse> items;

}
