package com.example.ticket_management.mapper;

import com.example.ticket_management.dto.CommentDTO;
import com.example.ticket_management.entity.Comment;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface CommentMapper {

    @Mapping(source = "ticket.id", target = "ticketId")
    @Mapping(source = "author.id", target = "authorId")
    CommentDTO toCommentDTO(Comment comment);

    @Mapping(source = "ticketId", target = "ticket.id")
    @Mapping(source = "authorId", target = "author.id")
    Comment toComment(CommentDTO commentDTO);
}
