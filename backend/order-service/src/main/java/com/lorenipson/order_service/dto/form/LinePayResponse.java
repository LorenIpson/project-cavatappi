package com.lorenipson.order_service.dto.form;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class LinePayResponse {

    private String transactionId;
    private String webUrl;
    private String appUrl;

}
