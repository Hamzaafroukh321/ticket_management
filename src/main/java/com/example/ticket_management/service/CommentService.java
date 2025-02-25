package com.example.ticket_management.service;

import com.example.ticket_management.entity.Comment;

public interface CommentService {

    Comment addComment(Long ticketId, Comment comment, String username);
}
