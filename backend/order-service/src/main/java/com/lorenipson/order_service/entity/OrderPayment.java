package com.lorenipson.order_service.entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Getter
@Setter
@Entity
@Table(name = "order_payment", schema = "order_schema")
public class OrderPayment {

    @Id
    @Column(name = "id", nullable = false)
    private Long id;

    @MapsId
    @OneToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "id", nullable = false)
    private Order order;

    @NotNull
    @Column(name = "payment_method", nullable = false, length = Integer.MAX_VALUE)
    private String paymentMethod;

    @Column(name = "transaction_id", length = Integer.MAX_VALUE)
    private String transactionId;

    @Column(name = "provider", length = Integer.MAX_VALUE)
    private String provider;

    @Column(name = "payment_time")
    private LocalDateTime paymentTime;

    @NotNull
    @Column(name = "total_price", nullable = false, precision = 10, scale = 2)
    private BigDecimal totalPrice;

}