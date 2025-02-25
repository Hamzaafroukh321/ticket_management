package com.example.ticket_management.service;

import com.example.ticket_management.entity.Ticket;
import com.example.ticket_management.entity.TicketStatus;
import java.util.List;
import java.util.Optional;

public interface TicketService {
    Ticket createTicket(Ticket ticket);
    Ticket updateTicketStatus(Long ticketId, TicketStatus status);
    Optional<Ticket> getTicketById(Long id);
    List<Ticket> getTicketsByStatus(TicketStatus status);
    List<Ticket> getAllTickets();
}
