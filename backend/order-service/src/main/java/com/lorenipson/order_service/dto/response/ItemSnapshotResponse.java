package com.lorenipson.order_service.dto.response;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ItemSnapshotResponse {

    private Long itemId;
    private String itemName;
    private BigDecimal basePrice;
    private SizeResponse size;
    private DoughResponse dough;
    private List<AddonResponse> addons;

}
