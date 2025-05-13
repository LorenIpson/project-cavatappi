package com.lorenipson.menu_service.repository;

import com.lorenipson.menu_service.entity.PizzaAddonSizePrice;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface PizzaAddonSizePriceRepository extends JpaRepository<PizzaAddonSizePrice, Long> {
}
