package com.lorenipson.order_service.dto.response;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.util.List;
import java.util.Map;

/**
 * OrderDetailResponse 的包裝用物件。<br>
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class OrderDetailItemResponse {

    private Long itemId;
    private String itemName;
    private BigDecimal itemBasePrice;
    private Map<String, Object> itemSpecs; // 包裝 dough、size。
    private List<Map<String, Object>> itemAddons;

}
