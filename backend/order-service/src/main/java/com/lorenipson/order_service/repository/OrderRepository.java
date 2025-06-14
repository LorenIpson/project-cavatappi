package com.lorenipson.order_service.repository;

import com.lorenipson.order_service.entity.Order;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.Optional;
import java.util.UUID;

@Repository
public interface OrderRepository extends JpaRepository<Order, Long> {
    Page<Order> findAllByReceiveDateBetween(LocalDateTime receiveDateAfter, LocalDateTime receiveDateBefore, Pageable pageable);

    Page<Order> findByMemberId(UUID memberId, Pageable pageable);

    Page<Order> findByMemberIdAndIsCompleted(UUID memberId, Boolean isCompleted, Pageable pageable);

    Page<Order> findByMemberIdAndIsPaid(UUID memberId, Boolean isPaid, Pageable pageable);

    Optional<Order> findByMemberIdAndId(UUID memberId, Long id);
}
