package com.example.A1LibraryManagement.dto;

import com.example.A1LibraryManagement.model.Role;
import lombok.*;

import java.util.Set;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class UserDTO {
    private String username;
    private String name;
    private String surname;
    private String email;
    private Set<Role> roles;
}
