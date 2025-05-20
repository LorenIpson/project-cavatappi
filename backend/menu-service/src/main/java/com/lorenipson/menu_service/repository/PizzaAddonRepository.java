package com.lorenipson.menu_service.repository;

import com.lorenipson.menu_service.entity.PizzaAddon;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface PizzaAddonRepository extends JpaRepository<PizzaAddon, Long> {
}
