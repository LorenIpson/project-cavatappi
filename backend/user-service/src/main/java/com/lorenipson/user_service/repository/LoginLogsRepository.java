package com.lorenipson.user_service.repository;

import com.lorenipson.user_service.entity.LoginLogs;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface LoginLogsRepository extends JpaRepository<LoginLogs, Long> {
}
