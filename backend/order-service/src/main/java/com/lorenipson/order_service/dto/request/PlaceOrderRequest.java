package com.lorenipson.order_service.dto.request;

import com.lorenipson.order_service.dto.internal.InternalItemRequest;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class PlaceOrderRequest {

    private String buyerName;
    private String buyerPhone;
    private String buyerMessage;
    private LocalDateTime receiveDate;
    private List<InternalItemRequest> items;
    private String paymentMethod;

}
