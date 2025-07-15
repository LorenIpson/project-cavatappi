package com.lorenipson.menu_service.service;

import com.lorenipson.menu_service.dto.retrieve.LatestPizzaResponse;
import com.lorenipson.menu_service.entity.Pizza;
import com.lorenipson.menu_service.repository.PizzaRepository;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.stereotype.Service;

@Service
public class AdPanelService {

    private final PizzaRepository pizzaRepos;

    public AdPanelService(PizzaRepository pizzaRepos) {
        this.pizzaRepos = pizzaRepos;
    }

    public LatestPizzaResponse newestPizza() {
        Pizza newestPizza = pizzaRepos.findTopByOrderByIdDesc().orElseThrow(EntityNotFoundException::new);
        LatestPizzaResponse response = new LatestPizzaResponse();
        response.setName(newestPizza.getName());
        response.setDescription(newestPizza.getDescription());
        response.setImage(newestPizza.getImage());
        response.setBasePrice(newestPizza.getBasePrice());
        return  response;
    }

}
