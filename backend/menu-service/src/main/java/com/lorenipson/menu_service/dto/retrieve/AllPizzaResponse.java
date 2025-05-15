package com.lorenipson.menu_service.dto.retrieve;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class AllPizzaResponse {

    private String pizzaName;
    private String pizzaDescription;
    private BigDecimal basePrice;
    private boolean isInStock;
    private boolean isAvailable;

}
