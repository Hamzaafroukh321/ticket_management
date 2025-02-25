package com.example.ticket_management.entity;

import lombok.*;
import jakarta.persistence.*;
import java.time.LocalDateTime;

@Entity
@Table(name = "audit_logs")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class AuditLog {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private LocalDateTime eventDate;


    private String eventDescription;


    private String username;


    @ManyToOne
    @JoinColumn(name = "ticket_id")
    private Ticket ticket;
}
