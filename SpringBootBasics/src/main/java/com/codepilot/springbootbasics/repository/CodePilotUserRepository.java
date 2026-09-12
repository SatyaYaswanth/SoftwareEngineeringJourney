package com.codepilot.springbootbasics.repository;

import com.codepilot.springbootbasics.entity.CodePilotUser;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CodePilotUserRepository extends JpaRepository<CodePilotUser, Long> {
}