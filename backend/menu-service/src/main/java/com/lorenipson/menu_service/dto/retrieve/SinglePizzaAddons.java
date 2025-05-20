package com.lorenipson.menu_service.dto.retrieve;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class SinglePizzaAddons {

    private String addonName;
    private boolean isInStock;
    private boolean isAvailable;

    private List<SinglePizzaAddonSizePrice> addonSizePrice;

}
