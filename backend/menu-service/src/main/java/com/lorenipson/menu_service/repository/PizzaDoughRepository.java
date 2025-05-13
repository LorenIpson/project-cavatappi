package com.lorenipson.menu_service.repository;

import com.lorenipson.menu_service.entity.PizzaDough;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface PizzaDoughRepository extends JpaRepository<PizzaDough, Long> {
}
