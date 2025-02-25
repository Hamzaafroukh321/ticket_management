package com.example.ticket_management.dto;

import lombok.*;

import java.time.LocalDateTime;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class AuditLogDTO {
    private Long id;
    private LocalDateTime eventDate;
    private String eventDescription;
    private String username;
    private Long ticketId;
}
