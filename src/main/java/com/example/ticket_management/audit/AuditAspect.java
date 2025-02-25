package com.example.ticket_management.audit;

import com.example.ticket_management.entity.Ticket;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.AfterReturning;
import org.aspectj.lang.annotation.Aspect;
import org.springframework.stereotype.Component;

@Aspect
@Component
public class AuditAspect {

    private final AuditLogService auditLogService;

    public AuditAspect(AuditLogService auditLogService) {
        this.auditLogService = auditLogService;
    }

    @AfterReturning(
            pointcut = "execution(* com.example.ticket_management.service.TicketService.updateTicketStatus(..))",
            returning = "result"
    )
    public void logTicketStatusChange(JoinPoint joinPoint, Object result) {
        if (result instanceof Ticket) {
            Ticket updatedTicket = (Ticket) result;
            String eventDescription = "Ticket status updated to " + updatedTicket.getStatus();
            // Pour cet exemple, nous utilisons "system" comme utilisateur (dans un contexte réel, récupérez-le depuis le SecurityContext)
            String username = "system";
            auditLogService.logEvent(updatedTicket, eventDescription, username);
        }
    }
}
