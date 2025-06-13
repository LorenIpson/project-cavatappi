package com.lorenipson.order_service.dto.internal;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class AddOnDetailResponse {

    private Long id;
    private String name;
    private BigDecimal extraPrice;

}
