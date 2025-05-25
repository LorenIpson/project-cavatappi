package com.lorenipson.user_service.repository;

import com.lorenipson.user_service.entity.Member;
import com.lorenipson.user_service.entity.MemberRoles;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface MemberRolesRepository extends JpaRepository<com.lorenipson.user_service.entity.MemberRoles, Long> {
    List<MemberRoles> findByMemberId(Member memberId);
}
