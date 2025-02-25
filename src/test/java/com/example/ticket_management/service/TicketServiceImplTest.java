package com.example.ticket_management.service;

import com.example.ticket_management.audit.AuditLogService;
import com.example.ticket_management.entity.Ticket;
import com.example.ticket_management.entity.TicketStatus;
import com.example.ticket_management.exception.ResourceNotFoundException;
import com.example.ticket_management.repository.TicketRepository;
import com.example.ticket_management.service.impl.TicketServiceImpl;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class TicketServiceImplTest {

    @Mock
    private TicketRepository ticketRepository;

    @Mock
    private AuditLogService auditLogService;

    @InjectMocks
    private TicketServiceImpl ticketService;

    private Ticket ticket;

    @BeforeEach
    void setup() {
        ticket = Ticket.builder()
                .id(1L)
                .title("Test Ticket")
                .description("Test Description")
                .creationDate(null)
                .status(null)
                .build();
    }

    @Test
    void testCreateTicketShouldSetCreationDateAndDefaultStatus() {
        when(ticketRepository.save(any(Ticket.class))).thenAnswer(invocation -> {
            Ticket t = invocation.getArgument(0);
            t.setId(1L);
            return t;
        });

        Ticket createdTicket = ticketService.createTicket(ticket);

        assertThat(createdTicket.getCreationDate()).isNotNull();
        assertThat(createdTicket.getStatus()).isEqualTo(TicketStatus.NEW);
        verify(ticketRepository, times(1)).save(ticket);
    }

    @Test
    void testUpdateTicketStatusTicketNotFound() {
        when(ticketRepository.findById(1L)).thenReturn(Optional.empty());

        assertThatThrownBy(() -> ticketService.updateTicketStatus(1L, TicketStatus.IN_PROGRESS))
                .isInstanceOf(ResourceNotFoundException.class)
                .hasMessageContaining("Ticket non trouvé avec l'id 1");
    }

    @Test
    void testUpdateTicketStatusSuccess() {
        Ticket existingTicket = Ticket.builder()
                .id(2L)
                .title("Another Ticket")
                .status(TicketStatus.NEW)
                .build();

        when(ticketRepository.findById(2L)).thenReturn(Optional.of(existingTicket));
        when(ticketRepository.save(any(Ticket.class))).thenReturn(existingTicket);

        Ticket updated = ticketService.updateTicketStatus(2L, TicketStatus.RESOLVED);

        assertThat(updated.getStatus()).isEqualTo(TicketStatus.RESOLVED);
        verify(auditLogService).logEvent(existingTicket, "Ticket status updated to RESOLVED", "currentUser");
    }

    @Test
    void testGetTicketByIdFound() {
        when(ticketRepository.findById(1L)).thenReturn(Optional.of(ticket));

        Optional<Ticket> found = ticketService.getTicketById(1L);
        assertThat(found).isPresent();
        assertThat(found.get().getTitle()).isEqualTo("Test Ticket");
    }

    @Test
    void testGetTicketByIdNotFound() {
        when(ticketRepository.findById(999L)).thenReturn(Optional.empty());

        Optional<Ticket> found = ticketService.getTicketById(999L);
        assertThat(found).isEmpty();
    }

    @Test
    void testGetAllTickets() {
        when(ticketRepository.findAll()).thenReturn(List.of(ticket));

        List<Ticket> all = ticketService.getAllTickets();
        assertThat(all).hasSize(1);
        assertThat(all.get(0).getTitle()).isEqualTo("Test Ticket");
    }
}
