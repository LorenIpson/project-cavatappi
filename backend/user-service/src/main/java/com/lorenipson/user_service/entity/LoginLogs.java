package com.lorenipson.user_service.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;
import java.util.UUID;

@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "login_logs", schema = "user_schema")
public class LoginLogs {

    // TODO: 建立 memberId 資料表關聯。

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    @Column(name = "member_id", nullable = false)
    private UUID memberId;

    @Column(name = "provider", nullable = false)
    private String provider;

    @Column(name = "attempt_at", nullable = false, updatable = false, insertable = false)
    private LocalDateTime attemptAt;

    @Column(name = "ip_address")
    private String ipAddress;

    @Column(name = "is_success")
    private Boolean isSuccess;

}
