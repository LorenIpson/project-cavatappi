package com.lorenipson.menu_service.entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.ColumnDefault;

import java.math.BigDecimal;

@Getter
@Setter
@Entity
@Table(name = "pizza_addon_size_price", schema = "pizza_schema")
public class PizzaAddonSizePrice {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "addon_id")
    private PizzaAddon addon;

    @NotNull
    @Column(name = "size", nullable = false, length = Integer.MAX_VALUE)
    private String size;

    @NotNull
    @Column(name = "extra_price", nullable = false, precision = 10, scale = 2)
    private BigDecimal extraPrice;

    @NotNull
    @ColumnDefault("true")
    @Column(name = "is_stocked", nullable = false)
    private Boolean isStocked = false;

    @NotNull
    @ColumnDefault("true")
    @Column(name = "is_available", nullable = false)
    private Boolean isAvailable = false;

}