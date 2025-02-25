package com.example.ticket_management.audit;

import com.example.ticket_management.entity.AuditLog;
import com.example.ticket_management.entity.Ticket;
import com.example.ticket_management.repository.AuditLogRepository;
import org.springframework.stereotype.Service;
import java.time.LocalDateTime;

@Service
public class AuditLogServiceImpl implements AuditLogService {

    private final AuditLogRepository auditLogRepository;

    public AuditLogServiceImpl(AuditLogRepository auditLogRepository) {
        this.auditLogRepository = auditLogRepository;
    }

    @Override
    public AuditLog logEvent(Ticket ticket, String eventDescription, String username) {
        AuditLog log = AuditLog.builder()
                .ticket(ticket)
                .eventDate(LocalDateTime.now())
                .eventDescription(eventDescription)
                .username(username)
                .build();
        return auditLogRepository.save(log);
    }
}
