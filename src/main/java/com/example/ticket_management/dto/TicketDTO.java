package com.example.ticket_management.dto;

import com.example.ticket_management.entity.TicketCategory;
import com.example.ticket_management.entity.TicketPriority;
import com.example.ticket_management.entity.TicketStatus;
import lombok.*;

import java.time.LocalDateTime;
import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class TicketDTO {
    private Long id;
    private String title;
    private String description;
    private TicketPriority priority;
    private TicketCategory category;
    private LocalDateTime creationDate;
    private TicketStatus status;
    private Long createdById;
    private List<CommentDTO> comments;
    // Vous pouvez ajouter d'autres champs spécifiques ou omettre certains détails
}
