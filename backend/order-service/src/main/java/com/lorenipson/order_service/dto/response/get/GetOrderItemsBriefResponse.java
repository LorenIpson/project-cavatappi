package com.lorenipson.order_service.dto.response.get;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Map;

/**
 * GetOrderBriefResponse 的包裝用物件。<br>
 * 輸出前端為：美式臘腸．鬆厚．M。
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class GetOrderItemsBriefResponse {

    private String itemName;
    private Map<String, Object> itemSpecs;

}
