package com.example.amrat.LearningSpringBootSecurity.repository;

import com.example.amrat.LearningSpringBootSecurity.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface UserRepository extends JpaRepository<User, Long> {

    Optional<User> findByUsername(String username);

}