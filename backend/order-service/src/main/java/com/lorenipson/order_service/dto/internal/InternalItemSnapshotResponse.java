package com.lorenipson.order_service.dto.internal;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.util.List;

/**
 * 接收 <code>menu-service</code> 回傳的餐點詳細資訊。
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class InternalItemSnapshotResponse {

    private Long itemId;
    private String itemName;
    private BigDecimal basePrice;
    private SizeDetailResponse size;
    private DoughDetailResponse dough;
    private List<AddOnDetailResponse> addons;

}
