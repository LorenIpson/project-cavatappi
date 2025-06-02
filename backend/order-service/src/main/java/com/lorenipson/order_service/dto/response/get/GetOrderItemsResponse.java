package com.lorenipson.order_service.dto.response.get;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.util.List;
import java.util.Map;

/**
 * GetOrderResponse 的包裝用物件。<br>
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class GetOrderItemsResponse {

    private Long itemId;
    private String itemName;
    private BigDecimal itemBasePrice;
    private Map<String, Object> itemSpecs; // 包裝 dough、size。
    private List<Map<String, Object>> itemAddons;

}
