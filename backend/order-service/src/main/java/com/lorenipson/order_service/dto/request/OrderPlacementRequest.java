package com.lorenipson.order_service.dto.request;

import com.lorenipson.order_service.dto.internal.InternalItemRequest;
import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class OrderPlacementRequest {

    @NotBlank
    private String buyerName;

    @NotBlank
    private String buyerPhone;

    private String buyerMessage;

    @NotBlank
    private LocalDateTime receiveDate;

    @NotBlank
    private List<InternalItemRequest> items;

    @NotBlank
    private String paymentMethod;

}
