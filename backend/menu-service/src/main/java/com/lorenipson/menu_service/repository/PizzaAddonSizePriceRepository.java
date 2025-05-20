package com.lorenipson.menu_service.repository;

import com.lorenipson.menu_service.entity.PizzaAddon;
import com.lorenipson.menu_service.entity.PizzaAddonSizePrice;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface PizzaAddonSizePriceRepository extends JpaRepository<PizzaAddonSizePrice, Long> {
    // Optional<PizzaAddonSizePrice> findByAddon(PizzaAddon addon);

    Optional<PizzaAddonSizePrice> findByAddonAndSize(PizzaAddon addon, String size);
}
