package com.lorenipson.order_service.dto.request;

import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class SetOrderStatusRequest {

    @NotBlank
    private Long orderId;

    @NotBlank
    private String status; // 收到訂單、收到更新、製作、準備完成、取餐完成、取消訂單。
    // TODO: 考慮使用 Enum

}
