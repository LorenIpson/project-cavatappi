package com.lorenipson.menu_service.controller;

import com.lorenipson.menu_service.dto.NewPizzaRequest;
import com.lorenipson.menu_service.service.PizzaService;
import org.springframework.web.bind.annotation.*;

@RestController
public class PizzaController {

    private final PizzaService pizzaService;

    public PizzaController(PizzaService pizzaService) {
        this.pizzaService = pizzaService;
    }

    @PostMapping("/api/menu/pizza/create")
    public String createNewPizza(@RequestBody NewPizzaRequest request) {
        pizzaService.addNewPizza(request);
        return "Pizza created";
    }

    @GetMapping("/api/menu/helloShibe")
    public String hello() {
        return "Hello World";
    }

}
