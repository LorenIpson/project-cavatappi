package com.lorenipson.menu_service.service;

import com.lorenipson.menu_service.dto.NewPizzaRequest;
import com.lorenipson.menu_service.entity.*;
import com.lorenipson.menu_service.repository.*;
import jakarta.transaction.Transactional;
import org.springframework.stereotype.Service;

import java.util.Base64;

@Service
public class PizzaService {

    private final PizzaRepository pizzaRepos;
    private final PizzaSizeRepository pizzaSizeRepos;
    private final PizzaDoughRepository pizzaDoughRepos;
    private final PizzaAddonRepository pizzaAddonRepos;
    private final PizzaAddonSizePriceRepository pizzaAddonSizePriceRepos;

    public PizzaService(PizzaRepository pizzaRepos,
                        PizzaSizeRepository pizzaSizeRepos,
                        PizzaDoughRepository pizzaDoughRepos,
                        PizzaAddonRepository pizzaAddonRepos,
                        PizzaAddonSizePriceRepository pizzaAddonSizePriceRepos) {
        this.pizzaRepos = pizzaRepos;
        this.pizzaSizeRepos = pizzaSizeRepos;
        this.pizzaDoughRepos = pizzaDoughRepos;
        this.pizzaAddonRepos = pizzaAddonRepos;
        this.pizzaAddonSizePriceRepos = pizzaAddonSizePriceRepos;
    }

    @Transactional
    public void addNewPizza(NewPizzaRequest request) {

        Pizza pizza = new Pizza();

        pizza.setName(request.getName());
        if (request.getDescription() != null) {
            pizza.setDescription(request.getDescription());
        }
        if (request.getImageBase64() != null) {
            byte[] decoded = Base64.getDecoder().decode(request.getImageBase64());
            pizza.setImage(decoded);
        }
        pizza.setBasePrice(request.getBasePrice());
        pizza.setStocked(true);
        pizza.setAvailable(true);
        pizzaRepos.save(pizza);

        request.getSizes().forEach(size -> {
            PizzaSize pizzaSize = new PizzaSize();
            pizzaSize.setPizza(pizza);
            pizzaSize.setSize(size.getSize());
            pizzaSize.setExtraPrice(size.getExtraPrice());
            pizzaSizeRepos.save(pizzaSize);
        });

        request.getDoughs().forEach(dough -> {
            PizzaDough pizzaDough = new PizzaDough();
            pizzaDough.setPizza(pizza);
            pizzaDough.setDough(dough.getDoughType());
            pizzaDough.setExtraPrice(dough.getDoughExtraPrice());
            pizzaDoughRepos.save(pizzaDough);
        });

        request.getAddons().forEach(addon -> {
            PizzaAddon pizzaAddon = new PizzaAddon();
            pizzaAddon.setPizza(pizza);
            pizzaAddon.setName(addon.getAddonName());
            pizzaAddonRepos.save(pizzaAddon); // TODO: 試試看 pizzaAddon = ....
            addon.getAddonSizePrice().forEach(addonSize -> {
                PizzaAddonSizePrice pizzaAddonSizePrice = new PizzaAddonSizePrice();
                pizzaAddonSizePrice.setAddon(pizzaAddon);
                pizzaAddonSizePrice.setSize(addonSize.getSize());
                pizzaAddonSizePrice.setExtraPrice(addonSize.getAddonExtraPrice());
                pizzaAddonSizePriceRepos.save(pizzaAddonSizePrice);
            });
        });

    }

}
