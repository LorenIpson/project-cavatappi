package com.lorenipson.user_service.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface MemberRolesRepository extends JpaRepository<com.lorenipson.user_service.entity.MemberRoles, Long> {
}
