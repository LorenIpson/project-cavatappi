package com.lorenipson.menu_service.controller;

import com.lorenipson.menu_service.dto.create.NewPizzaRequest;
import com.lorenipson.menu_service.dto.retrieve.AllPizzaResponse;
import com.lorenipson.menu_service.dto.retrieve.SinglePizzaResponse;
import com.lorenipson.menu_service.service.PizzaService;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.ResponseEntity;
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

    @GetMapping("/api/menu/pizza/get/all")
    public ResponseEntity<Page<AllPizzaResponse>> getAllPizza(@PageableDefault Pageable pageable) {
        Page<AllPizzaResponse> allPizzas = pizzaService.getAllPizzas(pageable);
        return ResponseEntity.ok(allPizzas);
    }

    @GetMapping("/api/menu/pizza/get/{pizzaId}")
    public ResponseEntity<SinglePizzaResponse> getSinglePizza(@PathVariable Long pizzaId) {
        SinglePizzaResponse singlePizza = pizzaService.getSinglePizza(pizzaId);
        return ResponseEntity.ok(singlePizza);
    }

}
