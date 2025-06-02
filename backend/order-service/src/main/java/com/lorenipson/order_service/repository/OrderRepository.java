package com.lorenipson.order_service.repository;

import com.lorenipson.order_service.entity.Order;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;

@Repository
public interface OrderRepository extends JpaRepository<Order, Long> {
    Page<Order> findAllByReceiveDateBetween(LocalDateTime receiveDateAfter, LocalDateTime receiveDateBefore, Pageable pageable);
}
