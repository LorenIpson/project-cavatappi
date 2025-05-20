package com.lorenipson.menu_service.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.PositiveOrZero;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.math.BigDecimal;
import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class NewPizzaRequest {

    @NotBlank
    private String name;

    private String description;

    private String imageBase64;

    @NotNull
    @PositiveOrZero
    private BigDecimal basePrice;

    @NotEmpty
    private List<NewPizzaSizes> sizes;

    @NotEmpty
    private List<NewPizzaDough> doughs;

    @NotEmpty
    private List<NewPizzaAddons> addons;

}
