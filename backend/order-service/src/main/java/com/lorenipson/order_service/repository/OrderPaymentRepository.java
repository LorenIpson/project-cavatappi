package com.lorenipson.order_service.repository;

import com.lorenipson.order_service.entity.Order;
import com.lorenipson.order_service.entity.OrderPayment;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface OrderPaymentRepository extends JpaRepository<OrderPayment, Long> {
    Optional<OrderPayment> findByOrder(Order order);
}
