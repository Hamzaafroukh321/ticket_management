package com.example.ticket_management.repository;

import com.example.ticket_management.entity.AuditLog;
import org.springframework.data.jpa.repository.JpaRepository;

public interface AuditLogRepository extends JpaRepository<AuditLog, Long> {
    // Méthodes personnalisées pour les logs d'audit peuvent être ajoutées ici
}
