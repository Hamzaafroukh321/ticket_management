package com.example.ticket_management.controller;

import com.example.ticket_management.dto.CommentDTO;
import com.example.ticket_management.entity.Comment;
import com.example.ticket_management.mapper.CommentMapper;
import com.example.ticket_management.service.CommentService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/comments")
public class CommentController {

    private final CommentService commentService;
    private final CommentMapper commentMapper;

    public CommentController(CommentService commentService, CommentMapper commentMapper) {
        this.commentService = commentService;
        this.commentMapper = commentMapper;
    }

    @PostMapping("/{ticketId}")
    public ResponseEntity<CommentDTO> addComment(@PathVariable Long ticketId,
                                                 @RequestBody CommentDTO commentDTO,
                                                 @RequestParam String username) {

        Comment comment = commentMapper.toComment(commentDTO);
        Comment savedComment = commentService.addComment(ticketId, comment, username);
        return new ResponseEntity<>(commentMapper.toCommentDTO(savedComment), HttpStatus.CREATED);
    }
}
