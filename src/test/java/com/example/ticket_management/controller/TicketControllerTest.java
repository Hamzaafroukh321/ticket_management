package com.example.ticket_management.controller;

import com.example.ticket_management.dto.TicketDTO;
import com.example.ticket_management.entity.TicketCategory;
import com.example.ticket_management.entity.TicketPriority;
import com.example.ticket_management.entity.TicketStatus;
import com.example.ticket_management.mapper.TicketMapper;
import com.example.ticket_management.service.TicketService;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.context.annotation.Import;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import java.time.LocalDateTime;
import java.util.Collections;
import java.util.Optional;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.BDDMockito.given;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(TicketController.class)
@Import(TicketControllerTestConfiguration.class)
class TicketControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private TicketService ticketService;

    @Mock
    private TicketMapper ticketMapper;

    @Autowired
    private ObjectMapper objectMapper;

    @Test
    @DisplayName("GET /api/tickets - should return 200 and empty list")
    void testGetAllTicketsEmpty() throws Exception {
        given(ticketService.getAllTickets()).willReturn(Collections.emptyList());
        mockMvc.perform(get("/api/tickets"))
                .andExpect(status().isOk())
                .andExpect(content().json("[]"));
    }

    @Test
    @DisplayName("GET /api/tickets/{id} - found")
    void testGetTicketByIdFound() throws Exception {
        TicketDTO dto = TicketDTO.builder()
                .id(1L)
                .title("Test Ticket")
                .description("Desc")
                .priority(TicketPriority.LOW)
                .category(TicketCategory.NETWORK)
                .status(TicketStatus.NEW)
                .creationDate(LocalDateTime.now())
                .build();

        // Simule un ticket existant
        given(ticketService.getTicketById(1L)).willReturn(Optional.ofNullable(null));
        given(ticketService.getTicketById(1L)).willReturn(Optional.ofNullable(null));
        given(ticketService.getTicketById(1L)).willReturn(Optional.of(new com.example.ticket_management.entity.Ticket()));
        given(ticketMapper.toTicketDTO(any())).willReturn(dto);

        mockMvc.perform(get("/api/tickets/1"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(1L))
                .andExpect(jsonPath("$.title").value("Test Ticket"));
    }

    @Test
    @DisplayName("GET /api/tickets/{id} - not found")
    void testGetTicketByIdNotFound() throws Exception {
        given(ticketService.getTicketById(999L)).willReturn(Optional.empty());

        mockMvc.perform(get("/api/tickets/999"))
                .andExpect(status().is4xxClientError());
    }

    @Test
    @DisplayName("POST /api/tickets - create ticket")
    void testCreateTicket() throws Exception {
        TicketDTO inputDto = TicketDTO.builder()
                .title("New Ticket")
                .description("Desc")
                .priority(TicketPriority.LOW)
                .category(TicketCategory.HARDWARE)
                .status(TicketStatus.NEW)
                .build();

        TicketDTO savedDto = TicketDTO.builder()
                .id(10L)
                .title("New Ticket")
                .description("Desc")
                .priority(TicketPriority.LOW)
                .category(TicketCategory.HARDWARE)
                .status(TicketStatus.NEW)
                .creationDate(LocalDateTime.now())
                .build();

        given(ticketService.createTicket(any())).willReturn(new com.example.ticket_management.entity.Ticket());
        given(ticketMapper.toTicket(any())).willReturn(new com.example.ticket_management.entity.Ticket());
        given(ticketMapper.toTicketDTO(any())).willReturn(savedDto);

        mockMvc.perform(post("/api/tickets")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(inputDto)))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.id").value(10L))
                .andExpect(jsonPath("$.title").value("New Ticket"));
    }

    @Test
    @DisplayName("PUT /api/tickets/{id}/status?status=RESOLVED - ok")
    void testUpdateTicketStatusOk() throws Exception {
        TicketDTO updatedDto = TicketDTO.builder()
                .id(1L)
                .title("Test Ticket")
                .status(TicketStatus.RESOLVED)
                .build();

        given(ticketService.updateTicketStatus(eq(1L), eq(TicketStatus.RESOLVED)))
                .willReturn(new com.example.ticket_management.entity.Ticket());
        given(ticketMapper.toTicketDTO(any())).willReturn(updatedDto);

        mockMvc.perform(put("/api/tickets/1/status")
                        .param("status", "RESOLVED"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.status").value("RESOLVED"));
    }

    @Test
    @DisplayName("PUT /api/tickets/{id}/status - not found")
    void testUpdateTicketStatusNotFound() throws Exception {
        given(ticketService.updateTicketStatus(eq(999L), eq(TicketStatus.RESOLVED)))
                .willThrow(new RuntimeException("Ticket not found"));

        mockMvc.perform(put("/api/tickets/999/status")
                        .param("status", "RESOLVED"))
                .andExpect(status().is5xxServerError());

    }
}
