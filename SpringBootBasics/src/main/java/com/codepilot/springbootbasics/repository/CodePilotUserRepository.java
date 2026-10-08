package com.codepilot.springbootbasics.repository;

import com.codepilot.springbootbasics.entity.CodePilotUser;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface CodePilotUserRepository extends JpaRepository<CodePilotUser, Long> {

    boolean existsByEmail(String email);

    Optional<CodePilotUser> findByEmail(String email);
}