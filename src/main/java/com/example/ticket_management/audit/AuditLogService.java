package com.example.ticket_management.audit;

import com.example.ticket_management.entity.AuditLog;
import com.example.ticket_management.entity.Ticket;

public interface AuditLogService {
    AuditLog logEvent(Ticket ticket, String eventDescription, String username);
}
