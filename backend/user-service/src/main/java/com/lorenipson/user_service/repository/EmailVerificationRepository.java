package com.lorenipson.user_service.repository;

import com.lorenipson.user_service.entity.MemberVerification;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;
import java.util.UUID;

@Repository
public interface EmailVerificationRepository extends JpaRepository<MemberVerification, Long> {
    Optional<MemberVerification> findByToken(UUID token);
}
