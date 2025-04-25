package com.lorenipson.user_service.repository;

import com.lorenipson.user_service.entity.MemberAuths;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface MemberAuthsRepository extends JpaRepository<MemberAuths, Long> {
}
