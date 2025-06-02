package com.lorenipson.order_service.dto.request;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class SetOrderStatusRequest {

    private String status; // 訂單確認中、訂單更新中、已確認訂單、訂單製作中、訂單就緒、取餐完成、取消訂單。

}
