package com.lorenipson.menu_service.entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.ColumnDefault;

import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
@Entity
@Table(name = "pizza_addon", schema = "pizza_schema")
public class PizzaAddon {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "pizza_id")
    private Pizza pizza;

    @NotNull
    @Column(name = "name", nullable = false, length = Integer.MAX_VALUE)
    private String name;

    @NotNull
    @ColumnDefault("true")
    @Column(name = "is_stocked", nullable = false)
    private Boolean isStocked = false;

    @NotNull
    @ColumnDefault("true")
    @Column(name = "is_available", nullable = false)
    private Boolean isAvailable = false;

    @OneToMany(mappedBy = "addon")
    private List<PizzaAddonSizePrice> pizzaAddonSizePrices = new ArrayList<>();

}