package com.example.ticket_management.controller;

import com.example.ticket_management.dto.TicketDTO;
import com.example.ticket_management.entity.Ticket;
import com.example.ticket_management.entity.TicketStatus;
import com.example.ticket_management.mapper.TicketMapper;
import com.example.ticket_management.service.TicketService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/tickets")
public class TicketController {

    private final TicketService ticketService;
    private final TicketMapper ticketMapper;

    public TicketController(TicketService ticketService, TicketMapper ticketMapper) {
        this.ticketService = ticketService;
        this.ticketMapper = ticketMapper;
    }


    @PostMapping
    public ResponseEntity<TicketDTO> createTicket(@RequestBody TicketDTO ticketDTO) {
        Ticket ticket = ticketMapper.toTicket(ticketDTO);
        Ticket savedTicket = ticketService.createTicket(ticket);
        return new ResponseEntity<>(ticketMapper.toTicketDTO(savedTicket), HttpStatus.CREATED);
    }


    @GetMapping("/{id}")
    public ResponseEntity<TicketDTO> getTicketById(@PathVariable Long id) {
        Ticket ticket = ticketService.getTicketById(id)
                .orElseThrow(() -> new RuntimeException("Ticket not found with id " + id));
        return ResponseEntity.ok(ticketMapper.toTicketDTO(ticket));
    }


    @PutMapping("/{id}/status")
    public ResponseEntity<TicketDTO> updateTicketStatus(@PathVariable Long id,
                                                        @RequestParam TicketStatus status) {
        Ticket updatedTicket = ticketService.updateTicketStatus(id, status);
        return ResponseEntity.ok(ticketMapper.toTicketDTO(updatedTicket));
    }


    @GetMapping
    public ResponseEntity<List<TicketDTO>> getAllTickets() {
        List<TicketDTO> tickets = ticketService.getAllTickets()
                .stream()
                .map(ticketMapper::toTicketDTO)
                .collect(Collectors.toList());
        return ResponseEntity.ok(tickets);
    }


    @GetMapping("/status")
    public ResponseEntity<List<TicketDTO>> getTicketsByStatus(@RequestParam TicketStatus status) {
        List<TicketDTO> tickets = ticketService.getTicketsByStatus(status)
                .stream()
                .map(ticketMapper::toTicketDTO)
                .collect(Collectors.toList());
        return ResponseEntity.ok(tickets);
    }
}
