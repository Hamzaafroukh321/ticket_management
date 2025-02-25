package com.example.ticket_management.ui.controllers;



import com.example.ticket_management.dto.TicketDTO;
import com.example.ticket_management.entity.TicketCategory;
import com.example.ticket_management.entity.TicketPriority;
import com.example.ticket_management.entity.TicketStatus;
import com.example.ticket_management.ui.utils.ApiClient;

import java.io.IOException;
import java.time.LocalDateTime;
import java.util.List;

public class EmployeeController {

    public TicketDTO createTicket(String title,
                                  String description,
                                  TicketPriority priority,
                                  TicketCategory category) {

        TicketDTO ticket = new TicketDTO();
        ticket.setTitle(title);
        ticket.setDescription(description);
        ticket.setPriority(priority);
        ticket.setCategory(category);
        ticket.setStatus(TicketStatus.NEW);
        ticket.setCreationDate(LocalDateTime.now());

        try {
            return ApiClient.createTicket(ticket);
        } catch (IOException | InterruptedException e) {
            // Gérer l’erreur (log, renvoyer un message, etc.)
            throw new RuntimeException("Error while creating ticket", e);
        }
    }



    public List<TicketDTO> getMyTickets(String username) throws IOException, InterruptedException {
        // Pour cet exemple, on récupère tous les tickets
        // Vous pourriez filtrer côté backend avec un endpoint spécifique
        return ApiClient.getAllTickets();
    }

    public List<TicketDTO> searchTickets(String ticketId, String status) throws IOException, InterruptedException {
        if (!ticketId.isEmpty()) {
            TicketDTO ticket = ApiClient.getTicketById(Long.parseLong(ticketId));
            return List.of(ticket);
        } else if (status != null && !status.isEmpty()) {
            return ApiClient.getTicketsByStatus(status);
        } else {
            return ApiClient.getAllTickets();
        }
    }
}
