package com.example.ticket_management.mapper;

import com.example.ticket_management.dto.AuditLogDTO;
import com.example.ticket_management.entity.AuditLog;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface AuditLogMapper {

    @Mapping(source = "ticket.id", target = "ticketId")
    AuditLogDTO toAuditLogDTO(AuditLog auditLog);

    @Mapping(source = "ticketId", target = "ticket.id")
    AuditLog toAuditLog(AuditLogDTO auditLogDTO);
}
