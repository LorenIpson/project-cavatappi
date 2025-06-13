package com.lorenipson.order_service.dto.response;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Map;

/**
 * OrderPreviewResponse 的包裝用物件。<br>
 * 輸出為：美式臘腸．鬆厚．M。
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class OrderPreviewItemResponse {

    private String itemName;
    private Map<String, Object> itemSpecs;

}
