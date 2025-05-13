package com.lorenipson.menu_service.service;

import com.lorenipson.menu_service.dto.NewPizzaRequest;
import com.lorenipson.menu_service.dto.retrieve.*;
import com.lorenipson.menu_service.entity.*;
import com.lorenipson.menu_service.repository.*;
import jakarta.persistence.EntityNotFoundException;
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
        pizza.setIsStocked(true);
        pizza.setIsAvailable(true);
        pizzaRepos.save(pizza);

        request.getSizes().forEach(size -> {
            PizzaSize pizzaSize = new PizzaSize();
            pizzaSize.setPizza(pizza);
            pizzaSize.setSize(size.getSize());
            pizzaSize.setExtraPrice(size.getExtraPrice());
            pizzaSize.setIsStocked(true);
            pizzaSize.setIsAvailable(true);
            pizzaSizeRepos.save(pizzaSize);
        });

        request.getDoughs().forEach(dough -> {
            PizzaDough pizzaDough = new PizzaDough();
            pizzaDough.setPizza(pizza);
            pizzaDough.setDough(dough.getDoughType());
            pizzaDough.setExtraPrice(dough.getDoughExtraPrice());
            pizzaDough.setIsStocked(true);
            pizzaDough.setIsAvailable(true);
            pizzaDoughRepos.save(pizzaDough);
        });

        request.getAddons().forEach(addon -> {
            PizzaAddon pizzaAddon = new PizzaAddon();
            pizzaAddon.setPizza(pizza);
            pizzaAddon.setIsStocked(true);
            pizzaAddon.setIsAvailable(true);
            pizzaAddon.setName(addon.getAddonName());
            pizzaAddonRepos.save(pizzaAddon);
            addon.getAddonSizePrice().forEach(addonSize -> {
                PizzaAddonSizePrice pizzaAddonSizePrice = new PizzaAddonSizePrice();
                pizzaAddonSizePrice.setAddon(pizzaAddon);
                pizzaAddonSizePrice.setSize(addonSize.getSize());
                pizzaAddonSizePrice.setExtraPrice(addonSize.getAddonExtraPrice());
                pizzaAddonSizePrice.setIsStocked(true);
                pizzaAddonSizePrice.setIsAvailable(true);
                pizzaAddonSizePriceRepos.save(pizzaAddonSizePrice);
            });
        });

    }

    public SinglePizzaResponse getSinglePizza(Long pizzaId) {

        /* TODO:
         *  1. 增加判斷 null 的邏輯
         *  2. 考慮使用 DTO Mapper
         * */

        SinglePizzaResponse response = new SinglePizzaResponse();

        Pizza targetPizza = pizzaRepos.findById(pizzaId).orElseThrow(EntityNotFoundException::new);

        response.setPizzaId(targetPizza.getId());
        response.setName(targetPizza.getName());
        response.setDescription(targetPizza.getDescription());
        response.setImage(targetPizza.getImage());
        response.setBasePrice(targetPizza.getBasePrice());
        response.setInStock(targetPizza.getIsStocked());
        response.setAvailable(targetPizza.getIsAvailable());
        response.setDoughs(targetPizza.getPizzaDoughs().stream().map(dough -> {
            SinglePizzaDoughs singlePizzaDoughs = new SinglePizzaDoughs();
            singlePizzaDoughs.setDoughType(dough.getDough());
            singlePizzaDoughs.setDoughExtraPrice(dough.getExtraPrice());
            singlePizzaDoughs.setInStock(dough.getIsStocked());
            singlePizzaDoughs.setAvailable(dough.getIsAvailable());
            return singlePizzaDoughs;
        }).toList());
        response.setSizes(targetPizza.getPizzaSizes().stream().map(size -> {
            SinglePizzaSizes singlePizzaSizes = new SinglePizzaSizes();
            singlePizzaSizes.setSize(size.getSize());
            singlePizzaSizes.setExtraPrice(size.getExtraPrice());
            singlePizzaSizes.setInStock(size.getIsStocked());
            singlePizzaSizes.setAvailable(size.getIsAvailable());
            return singlePizzaSizes;
        }).toList());
        response.setAddons(targetPizza.getPizzaAddons().stream().map(addon -> {
            SinglePizzaAddons singlePizzaAddons = new SinglePizzaAddons();
            singlePizzaAddons.setAddonName(addon.getName());
            singlePizzaAddons.setAddonSizePrice(
                    addon.getPizzaAddonSizePrices().stream().map(addonSize -> {
                        SinglePizzaAddonSizePrice addonSizePrice = new SinglePizzaAddonSizePrice();
                        addonSizePrice.setSize(addonSize.getSize());
                        addonSizePrice.setAddonExtraPrice(addonSize.getExtraPrice());
                        addonSizePrice.setInStock(addonSize.getIsStocked());
                        addonSizePrice.setAvailable(addonSize.getIsAvailable());
                        return addonSizePrice;
                    }).toList()
            );
            singlePizzaAddons.setInStock(addon.getIsStocked());
            singlePizzaAddons.setAvailable(addon.getIsAvailable());
            return singlePizzaAddons;
        }).toList());

        return response;

    }

}
