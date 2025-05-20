package com.lorenipson.order_service.entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.ColumnDefault;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.UUID;

@Getter
@Setter
@Entity
@Table(name = "\"order\"", schema = "order_schema")
public class Order {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false)
    private Long id;

    @NotNull
    @Column(name = "member_id", nullable = false)
    private UUID memberId;

    @NotNull
    @Column(name = "username", nullable = false, length = Integer.MAX_VALUE)
    private String username;

    @NotNull
    @Column(name = "buyer_name", nullable = false, length = Integer.MAX_VALUE)
    private String buyerName;

    @NotNull
    @Column(name = "buyer_phone", nullable = false, length = Integer.MAX_VALUE)
    private String buyerPhone;

    @Column(name = "buyer_message", length = Integer.MAX_VALUE)
    private String buyerMessage;

    @NotNull
    @Column(name = "ordered_date", nullable = false)
    private LocalDateTime orderedDate;

    @NotNull
    @Column(name = "receive_date", nullable = false)
    private LocalDateTime receiveDate;

    @NotNull
    @ColumnDefault("false")
    @Column(name = "is_edited", nullable = false)
    private Boolean isEdited = false;

    @Column(name = "edited_at")
    private LocalDateTime editedAt;

    @NotNull
    @Column(name = "order_status", nullable = false, length = Integer.MAX_VALUE)
    private String orderStatus;

    @NotNull
    @Column(name = "payment_status", nullable = false, length = Integer.MAX_VALUE)
    private String paymentStatus;

    @NotNull
    @Column(name = "total_price", nullable = false, precision = 10, scale = 2)
    private BigDecimal totalPrice;

}