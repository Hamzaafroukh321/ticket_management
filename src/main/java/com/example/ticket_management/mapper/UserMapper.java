package com.example.ticket_management.mapper;

import com.example.ticket_management.dto.UserDTO;
import com.example.ticket_management.entity.User;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface UserMapper {

    UserDTO toUserDTO(User user);

    User toUser(UserDTO userDTO);
}
