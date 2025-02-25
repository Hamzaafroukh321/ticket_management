package com.example.ticket_management.mapper;

import com.example.ticket_management.dto.TicketDTO;
import com.example.ticket_management.entity.Ticket;
import com.example.ticket_management.entity.User;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface TicketMapper {

    @Mapping(source = "createdBy.id", target = "createdById")
    TicketDTO toTicketDTO(Ticket ticket);

    @Mapping(source = "createdById", target = "createdBy")
    Ticket toTicket(TicketDTO ticketDTO);

    // Méthode de mapping personnalisée pour convertir un Long en User
    default User map(Long id) {
        if (id == null) {
            return null;
        }
        User user = new User();
        user.setId(id);
        return user;
    }
}
