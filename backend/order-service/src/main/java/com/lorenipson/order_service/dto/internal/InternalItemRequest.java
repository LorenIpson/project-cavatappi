package com.lorenipson.order_service.dto.internal;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

/**
 * 使用商品與克制化選項的 ID 向 <code>menu-service</code> 下單或請求取得對應資訊。
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class InternalItemRequest {

    private String itemType;
    private Long itemId;
    private Long sizeId;
    private Long doughId;
    private List<SelectedAddOnDTO> addons;

}
