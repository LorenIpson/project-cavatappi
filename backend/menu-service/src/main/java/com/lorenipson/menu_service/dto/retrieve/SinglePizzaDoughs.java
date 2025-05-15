package com.lorenipson.menu_service.dto.retrieve;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class SinglePizzaDoughs {

    private String doughType;
    private BigDecimal doughExtraPrice;
    private boolean isInStock;
    private boolean isAvailable;

}
