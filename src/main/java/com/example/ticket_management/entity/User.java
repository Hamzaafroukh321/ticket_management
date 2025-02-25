package com.example.ticket_management.entity;


import jakarta.persistence.*;
import lombok.*;
import java.util.List;

@Entity
@Table(name = "users")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String username;

    private String password;

    @Enumerated(EnumType.STRING)
    private UserRole role;

    // Tickets créés par cet utilisateur
    @OneToMany(mappedBy = "createdBy", cascade = CascadeType.ALL)
    private List<Ticket> tickets;

    // Commentaires postés par cet utilisateur
    @OneToMany(mappedBy = "author", cascade = CascadeType.ALL)
    private List<Comment> comments;
}
