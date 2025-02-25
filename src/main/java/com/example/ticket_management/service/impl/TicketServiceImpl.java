package com.example.ticket_management.service.impl;

import com.example.ticket_management.audit.AuditLogService;
import com.example.ticket_management.entity.Ticket;
import com.example.ticket_management.entity.TicketStatus;
import com.example.ticket_management.entity.User;
import com.example.ticket_management.exception.ResourceNotFoundException;
import com.example.ticket_management.repository.TicketRepository;
import com.example.ticket_management.repository.UserRepository;
import com.example.ticket_management.service.TicketService;
import jakarta.transaction.Transactional;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Service
@Transactional
public class TicketServiceImpl implements TicketService {

    private final TicketRepository ticketRepository;
    private final AuditLogService auditLogService;
    private final UserRepository userRepository;

    public TicketServiceImpl(TicketRepository ticketRepository, AuditLogService auditLogService, UserRepository userRepository) {
        this.ticketRepository = ticketRepository;
        this.auditLogService = auditLogService;
        this.userRepository = userRepository;
    }

    @Override
    public Ticket createTicket(Ticket ticket) {
        try {
            ticket.setCreationDate(LocalDateTime.now());
            if (ticket.getStatus() == null) {
                ticket.setStatus(TicketStatus.NEW);
            }
            if (ticket.getCreatedBy() == null) {
                User defaultEmployee = userRepository.findById(1L)
                        .orElseThrow(() -> new RuntimeException("User with ID=1 not found"));
                ticket.setCreatedBy(defaultEmployee);
            }

            return ticketRepository.save(ticket);
        } catch (Exception e) {
            System.err.println("Erreur lors de la création du ticket : " + e.getMessage());
            throw e;
        }
    }

    @Override
    public Ticket updateTicketStatus(Long ticketId, TicketStatus status) {
        Ticket ticket = ticketRepository.findById(ticketId)
                .orElseThrow(() -> new ResourceNotFoundException("Ticket non trouvé avec l'id " + ticketId));
        ticket.setStatus(status);
        Ticket updatedTicket = ticketRepository.save(ticket);
        // Enregistrement de l'événement d'audit
        auditLogService.logEvent(updatedTicket, "Ticket status updated to " + status, "currentUser");
        return updatedTicket;
    }

    @Override
    public Optional<Ticket> getTicketById(Long id) {
        return ticketRepository.findById(id);
    }

    @Override
    public List<Ticket> getTicketsByStatus(TicketStatus status) {
        return ticketRepository.findByStatus(status);
    }

    @Override
    public List<Ticket> getAllTickets() {
        return ticketRepository.findAll();
    }
}
