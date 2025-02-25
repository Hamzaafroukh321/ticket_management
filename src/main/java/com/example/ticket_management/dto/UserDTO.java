package com.example.ticket_management.dto;

import com.example.ticket_management.entity.UserRole;
import lombok.*;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class UserDTO {
    private Long id;
    private String username;
    private UserRole role;

}
