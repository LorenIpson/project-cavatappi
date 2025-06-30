package com.lorenipson.menu_service.dto.retrieve;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class LatestPizzaResponse {

    private String name;
    private String description;
    private byte[] image;
    private BigDecimal basePrice;

}
