package com.example.ticket_management.repository;

import com.example.ticket_management.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface UserRepository extends JpaRepository<User, Long> {
    // Recherche d'un utilisateur par son nom d'utilisateur
    Optional<User> findByUsername(String username);
}
