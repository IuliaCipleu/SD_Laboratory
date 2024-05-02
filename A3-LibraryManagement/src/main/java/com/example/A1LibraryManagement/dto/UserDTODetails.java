package com.example.A1LibraryManagement.dto;

import com.example.A1LibraryManagement.model.Role;
import lombok.*;

import java.util.Set;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class UserDTODetails {
    private String username;
    private String name;
    private String surname;
    private String email;
    private String password;
    private Set<Role> roles;
}
