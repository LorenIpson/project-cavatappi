package com.lorenipson.menu_service.entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;

@Getter
@Setter
@Entity
@Table(name = "drink_size", schema = "drink_schema")
public class DrinkSize {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "drink_id")
    private Drink drink;

    @NotNull
    @Column(name = "size", nullable = false, length = Integer.MAX_VALUE)
    private String size;

    @NotNull
    @Column(name = "extra_price", nullable = false, precision = 10, scale = 2)
    private BigDecimal extraPrice;

}