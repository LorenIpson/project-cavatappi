package com.lorenipson.order_service.dto.response;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class SizeResponse {

    private Long sizeId;
    private String size;
    private BigDecimal extraPrice;

}
