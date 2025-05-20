package com.lorenipson.menu_service.service;

import com.lorenipson.menu_service.dto.retrieve.*;
import com.lorenipson.menu_service.entity.*;
import com.lorenipson.menu_service.repository.*;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class InternalMenuService {

    private final PizzaRepository pizzaRepos;
    private final PizzaSizeRepository pizzaSizeRepos;
    private final PizzaDoughRepository pizzaDoughRepos;
    private final PizzaAddonRepository pizzaAddonRepos;
    private final PizzaAddonSizePriceRepository pizzaAddonSizePriceRepos;

    public InternalMenuService(PizzaRepository pizzaRepos,
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

    public List<ItemSnapshotResponse> getItemsDetails(List<InternalItemRequest> requests) {

        List<ItemSnapshotResponse> responses = new ArrayList<>();

        requests.forEach(request -> {
            switch (request.getItemType()) {
                case "PIZZA": // TODO: 改成安全一點的 enum
                    System.out.println("PIZZA");

                    ItemSnapshotResponse snapshotResponse = new ItemSnapshotResponse();

                    Pizza targetPizza = pizzaRepos.findById(request.getItemId())
                            .orElseThrow(EntityNotFoundException::new);
                    snapshotResponse.setItemId(request.getItemId());
                    snapshotResponse.setItemName(targetPizza.getName());
                    snapshotResponse.setBasePrice(targetPizza.getBasePrice());
                    System.out.println("============== NAME");

                    SizeResponse sizeResponse = getSizeResponse(request);
                    snapshotResponse.setSize(sizeResponse);
                    System.out.println("============== SIZE");

                    DoughResponse doughResponse = getDoughResponse(request);
                    snapshotResponse.setDough(doughResponse);
                    System.out.println("============== DOUGH");

                    List<AddonResponse> addonResponses = getAddonResponses(request, sizeResponse.getSize());
                    snapshotResponse.setAddons(addonResponses);
                    System.out.println("============== ADDONS");

                    responses.add(snapshotResponse);

                    break;
                case "DRINK":
                    System.out.println("DRINK");
                    break;
            }

        });

        return responses;

    }

    private SizeResponse getSizeResponse(InternalItemRequest request) {

        PizzaSize targetSize = pizzaSizeRepos.findById(request.getSizeId())
                .orElseThrow(EntityNotFoundException::new);
        SizeResponse sizeResponse = new SizeResponse();
        sizeResponse.setSizeId(request.getSizeId());
        sizeResponse.setSize(targetSize.getSize());
        sizeResponse.setExtraPrice(targetSize.getExtraPrice());
        return sizeResponse;

    }

    private DoughResponse getDoughResponse(InternalItemRequest request) {

        PizzaDough targetDough = pizzaDoughRepos.findById(request.getDoughId())
                .orElseThrow(EntityNotFoundException::new);
        DoughResponse doughResponse = new DoughResponse();
        doughResponse.setDoughId(request.getDoughId());
        doughResponse.setDoughType(targetDough.getDough());
        doughResponse.setExtraPrice(targetDough.getExtraPrice());
        return doughResponse;

    }

    private List<AddonResponse> getAddonResponses(InternalItemRequest request, String size) {

        List<AddonResponse> addonResponses = new ArrayList<>();
        System.out.println("INNER ============== ADDON");
        if (request.getAddons() != null && !request.getAddons().isEmpty()) {
            System.out.println("INNER INNER ============== ADDON");
            request.getAddons().forEach(requestAddon -> {
                AddonResponse addonResponse = new AddonResponse();
                PizzaAddon targetAddon = pizzaAddonRepos.findById(requestAddon.getAddonId())
                        .orElseThrow(EntityNotFoundException::new);
                addonResponse.setId(requestAddon.getAddonId());
                addonResponse.setName(targetAddon.getName());
                System.out.println(targetAddon.getName() + " ADDON NAME ============");

//                PizzaAddonSizePrice targetAddonSizePrice = pizzaAddonSizePriceRepos.findByAddon(targetAddon)
//                        .orElseThrow(EntityNotFoundException::new);
                PizzaAddonSizePrice targetAddonSizePrice = pizzaAddonSizePriceRepos
                        .findByAddonAndSize(targetAddon, size).orElseThrow(EntityNotFoundException::new);
                addonResponse.setExtraPrice(targetAddonSizePrice.getExtraPrice());
                System.out.println(targetAddonSizePrice.getExtraPrice() + " ADDON EXTRA PRICE ============");
                addonResponses.add(addonResponse);
                System.out.println("INNER INNER INNER ============== ADDON");
            });
        }
        return addonResponses;

    }

    /*
     * 假設收到的為：
     * {
     *      "items": [
     *          { "pizzaId": 1, "sizeId": 3, "doughId": 1 , "addons": null },
     *          { "pizzaId": 2, "sizeId": 2, "doughId": 2 , "addons": null },
     *      ]
     * }
     *
     * 假設回傳為：
     * {
     *      "pizzaId": 1,
     *      "pizzaName": "Pepperoni",
     *      "basePrice": 140,
     *      "size": { "id": 3, "size": L, "extraPrice": 20 },
     *      "dough": { "id": 1, "dough": "Fluffy", "extraPrice": 0},
     *      "addons": []
     *      "totalPrice": 160
     * },
     * {
     *      "pizzaId": 2,
     *      "pizzaName": "Hawaii",
     *      "basePrice": 140,
     *      "size": { "id": 3, "size": L, "extraPrice": 20 },
     *      "dough": { "id": 2, "dough": "Crispy", "extraPrice": 20},
     *      "addons": []
     *      "totalPrice": 180
     * }
     * */

}
