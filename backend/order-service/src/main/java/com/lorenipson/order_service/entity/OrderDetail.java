package com.lorenipson.order_service.entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.JdbcTypeCode;
import org.hibernate.type.SqlTypes;

import java.math.BigDecimal;
import java.util.Map;

@Getter
@Setter
@Entity
@Table(name = "order_details", schema = "order_schema")
public class OrderDetail {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false)
    private Long id;

    @NotNull
    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "order_id", nullable = false)
    private Order order;

    @NotNull
    @Column(name = "item_id", nullable = false)
    private Long itemId;

    @NotNull
    @Column(name = "item_name", nullable = false, length = Integer.MAX_VALUE)
    private String itemName;

    @Column(name = "item_specs")
    @JdbcTypeCode(SqlTypes.JSON)
    private Map<String, Object> itemSpecs;

    @Column(name = "item_addons")
    @JdbcTypeCode(SqlTypes.JSON)
    private Map<String, Object> itemAddons;

    @NotNull
    @Column(name = "item_base_price", nullable = false, precision = 10, scale = 2)
    private BigDecimal itemBasePrice;

}