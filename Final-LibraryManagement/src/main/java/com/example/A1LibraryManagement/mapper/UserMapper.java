
package com.example.A1LibraryManagement.mapper;

import com.example.A1LibraryManagement.dto.UserDTO;
import com.example.A1LibraryManagement.dto.UserDTODetails;
import com.example.A1LibraryManagement.model.User;
import org.springframework.stereotype.Component;

@Component
public class UserMapper {
    public UserDTO convertToDTO(User user){
        return UserDTO.builder()
                .username(user.getUsername())
                .name(user.getName())
                .surname(user.getSurname())
                .email(user.getEmail())
                .roles(user.getRoles())
                .build();
    }

    public User convertToEntity(UserDTO userDTO){
        User user = new User();
        user.setEmail(userDTO.getEmail());
        user.setName(userDTO.getName());
        user.setSurname(userDTO.getSurname());
        user.setEmail(userDTO.getEmail());
        user.setUsername(userDTO.getUsername());
        return user;
    }

    public User convertToEntity(UserDTODetails userDTO){
        User user = new User();
        user.setEmail(userDTO.getEmail());
        user.setName(userDTO.getName());
        user.setSurname(userDTO.getSurname());
        user.setEmail(userDTO.getEmail());
        user.setUsername(userDTO.getUsername());
        user.setPassword(userDTO.getPassword());
        return user;
    }
}

