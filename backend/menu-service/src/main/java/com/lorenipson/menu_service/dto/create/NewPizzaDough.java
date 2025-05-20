package com.lorenipson.menu_service.dto.create;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.PositiveOrZero;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class NewPizzaDough {

    @NotBlank
    private String doughType;

    @NotNull
    @PositiveOrZero
    private BigDecimal doughExtraPrice;

}
