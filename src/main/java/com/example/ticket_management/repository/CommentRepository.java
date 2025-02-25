package com.example.ticket_management.repository;

import com.example.ticket_management.entity.Comment;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CommentRepository extends JpaRepository<Comment, Long> {
    // Vous pouvez ajouter des méthodes personnalisées si nécessaire
}