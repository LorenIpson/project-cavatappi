package com.lorenipson.order_service.dto.response.get;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;

/**
 * 顯示於管理者的所有訂單頁面用回傳內容。
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class GetOrderBriefResponse {

    private Long orderId;
    private String buyerName;
    private String buyerMessage;
    private LocalDateTime orderedDate;
    private LocalDateTime receiveDate;
    private boolean edited;
    private String orderStatus;
    private String paymentStatus;
    private BigDecimal totalPrice;

    private List<GetOrderItemsBriefResponse> items;

}
