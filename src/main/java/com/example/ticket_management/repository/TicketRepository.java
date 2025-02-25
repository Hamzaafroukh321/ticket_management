package com.example.ticket_management.repository;

import com.example.ticket_management.entity.Ticket;
import com.example.ticket_management.entity.TicketStatus;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.List;

public interface TicketRepository extends JpaRepository<Ticket, Long> {
    List<Ticket> findByStatus(TicketStatus status);
}