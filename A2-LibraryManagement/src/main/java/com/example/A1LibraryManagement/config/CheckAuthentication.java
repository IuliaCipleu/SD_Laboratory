package com.example.A1LibraryManagement.config;

import com.example.A1LibraryManagement.exception.ApiException;
import com.example.A1LibraryManagement.exception.ErrorEnum;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;

import java.util.Collection;

public class CheckAuthentication {

    public CheckAuthentication() {
    }

    public void checkAuthenticationAll() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        Collection<? extends GrantedAuthority> authorities = authentication.getAuthorities();
        boolean hasPermission = authorities.stream()
                .anyMatch(authority -> authority.getAuthority().equals("ADMIN") || authority.getAuthority().equals("SIMPLE_USER"));
        if (!hasPermission) {
            throw new ApiException(ErrorEnum.ACCESS_DENIED);
        }
    }

    public void checkAuthenticationRole(String role) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        Collection<? extends GrantedAuthority> authorities = authentication.getAuthorities();
        boolean hasPermission = authorities.stream()
                .anyMatch(authority -> authority.getAuthority().equals(role));
        if (!hasPermission) {
            throw new ApiException(ErrorEnum.ROLE_DENIED);
        }
    }
}
