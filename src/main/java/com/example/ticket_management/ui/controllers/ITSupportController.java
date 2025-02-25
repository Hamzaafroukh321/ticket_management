package com.example.ticket_management.ui.controllers;


import com.example.ticket_management.dto.TicketDTO;
import com.example.ticket_management.ui.utils.ApiClient;

import java.io.IOException;
import java.util.List;

public class ITSupportController {

    public List<TicketDTO> getAllTickets() throws IOException, InterruptedException {
        return ApiClient.getAllTickets();
    }

    public TicketDTO updateTicketStatus(Long ticketId, String newStatus) throws IOException, InterruptedException {
        return ApiClient.updateTicketStatus(ticketId, newStatus);
    }

    public void addComment(Long ticketId, String username, String content) throws IOException, InterruptedException {
        ApiClient.addComment(ticketId, username, content);
    }

    public TicketDTO getTicketById(Long ticketId) throws IOException, InterruptedException {
        return ApiClient.getTicketById(ticketId);
    }

}
