package com.lorenipson.user_service.entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "member_auths", schema = "user_schema")
public class MemberAuths {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    @NotNull
    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "member_id", nullable = false)
    private Member memberId;

    @NotNull
    @Column(name = "provider", nullable = false)
    private String provider;

    @Column(name = "provider_id", unique = true)
    private String providerId;

    @Column(name = "password")
    private String password;

}
