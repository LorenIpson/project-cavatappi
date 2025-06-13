package com.lorenipson.order_service.dto.internal;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * 使用 Add-on ID 向 <code>menu-service</code> 請求對應詳細資訊。<br>
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class SelectedAddOnDTO {

    private Long addonId;

}
