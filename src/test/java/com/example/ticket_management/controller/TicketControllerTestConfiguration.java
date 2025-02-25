package com.example.ticket_management.controller;

import com.example.ticket_management.service.TicketService;
import org.mockito.Mockito;
import org.springframework.boot.test.context.TestConfiguration;
import org.springframework.context.annotation.Bean;

@TestConfiguration
public class TicketControllerTestConfiguration {

    @Bean
    public TicketService ticketService() {
        return Mockito.mock(TicketService.class);
    }
}
