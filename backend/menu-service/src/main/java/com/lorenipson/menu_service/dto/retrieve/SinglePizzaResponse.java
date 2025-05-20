package com.lorenipson.menu_service.dto.retrieve;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class SinglePizzaResponse {

    private Long pizzaId;
    private String name;
    private String description;
    private byte[] image;
    private BigDecimal basePrice;
    private boolean isInStock;
    private boolean isAvailable;

    private List<SinglePizzaSizes> sizes;
    private List<SinglePizzaDoughs> doughs;
    private List<SinglePizzaAddons> addons;

}
