package com.lorenipson.user_service.repository;

import com.lorenipson.user_service.entity.MemberAuths;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface MemberAuthsRepository extends JpaRepository<MemberAuths, Long> {
    Optional<MemberAuths> findByProviderAndProviderUserId(String provider, String providerId);
}
