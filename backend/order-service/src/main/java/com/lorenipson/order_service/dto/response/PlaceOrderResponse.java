package com.lorenipson.order_service.dto.response;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class PlaceOrderResponse {

    private boolean success;
    private boolean external;
    private String redirectURL;

}
