package com.example.ticket_management.service.impl;

import com.example.ticket_management.entity.Comment;
import com.example.ticket_management.entity.Ticket;
import com.example.ticket_management.entity.User;
import com.example.ticket_management.exception.ResourceNotFoundException;
import com.example.ticket_management.repository.CommentRepository;
import com.example.ticket_management.repository.TicketRepository;
import com.example.ticket_management.repository.UserRepository;
import com.example.ticket_management.service.CommentService;
import com.example.ticket_management.audit.AuditLogService;
import jakarta.transaction.Transactional;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;

@Service
@Transactional
public class CommentServiceImpl implements CommentService {

    private final TicketRepository ticketRepository;
    private final CommentRepository commentRepository;
    private final AuditLogService auditLogService;
    private final UserRepository userRepository;

    public CommentServiceImpl(TicketRepository ticketRepository, CommentRepository commentRepository, AuditLogService auditLogService, UserRepository userRepository) {
        this.ticketRepository = ticketRepository;
        this.commentRepository = commentRepository;
        this.auditLogService = auditLogService;
        this.userRepository = userRepository;
    }


    @Override
    public Comment addComment(Long ticketId, Comment comment, String username) {
        Ticket ticket = ticketRepository.findById(ticketId)
                .orElseThrow(() -> new ResourceNotFoundException("Ticket non trouvé avec l'id " + ticketId));
        comment.setTicket(ticket);
        comment.setCreatedDate(LocalDateTime.now());
        User user = userRepository.findById(2L)
                .orElseThrow(() -> new ResourceNotFoundException("User not found with id 2"));
        comment.setAuthor(user);
        Comment savedComment = commentRepository.save(comment);
        auditLogService.logEvent(ticket, "Comment added by IT Support", "IT_SUPPORT");
        return savedComment;
    }

}
