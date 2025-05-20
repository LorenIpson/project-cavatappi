package com.lorenipson.menu_service.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.PositiveOrZero;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class NewPizzaAddons {

    @NotBlank
    private String addonName;

    @NotNull
    @PositiveOrZero
    private List<NewPizzaAddonSizePrice> addonSizePrice;

}
