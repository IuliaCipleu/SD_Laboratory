package com.example.A1LibraryManagement.controller;

import com.example.A1LibraryManagement.config.JwtResponse;
import com.example.A1LibraryManagement.config.JwtUtils;
import com.example.A1LibraryManagement.dto.LoginDTO;
import com.example.A1LibraryManagement.dto.SignUpDTO;
import com.example.A1LibraryManagement.exception.ErrorEnum;
import com.example.A1LibraryManagement.model.Role;
import com.example.A1LibraryManagement.model.User;
import com.example.A1LibraryManagement.repository.RoleRepository;
import com.example.A1LibraryManagement.repository.UserRepository;
import com.example.A1LibraryManagement.validator.UserValidator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Collection;
import java.util.Collections;

@RestController
@RequestMapping("/api/auth")
public class AuthController {

    @Autowired
    private AuthenticationManager authenticationManager;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private RoleRepository roleRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;
    @Autowired
    private JwtUtils jwtUtils;

    @PostMapping("/signin")
    public ResponseEntity<String> authenticateUser(@RequestBody LoginDTO loginDto) {
        Authentication authentication = authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(
                loginDto.getUsernameOrEmail(), loginDto.getPassword()));

        SecurityContextHolder.getContext().setAuthentication(authentication);
        String jwt = jwtUtils.generateJwtToken(authentication);

        Authentication authentication2 = SecurityContextHolder.getContext().getAuthentication();
        Collection<? extends GrantedAuthority> authorities = authentication2.getAuthorities();
        authorities.forEach(authority -> System.out.println("Role request: " + authority.getAuthority()));
        System.out.println(jwt);

        return new ResponseEntity<>("User signed-in successfully: " + new JwtResponse(jwt), HttpStatus.OK);
    }

    @PostMapping("/signup/admin")
    public ResponseEntity<?> registerAdmin(@RequestBody SignUpDTO signUpDto) {

        if (userRepository.existsByUsername(signUpDto.getUsername())) {
            return new ResponseEntity<>("Username is already taken!", HttpStatus.BAD_REQUEST);
        }

        if (userRepository.existsByEmail(signUpDto.getEmail())) {
            return new ResponseEntity<>("Email is already taken!", HttpStatus.BAD_REQUEST);
        }

        if (!UserValidator.isOnlyLetters(signUpDto.getName()) || !UserValidator.isOnlyLetters(signUpDto.getSurname())) {
            return new ResponseEntity<>(ErrorEnum.INVALID_NAME.toString(), HttpStatus.BAD_REQUEST);
        }

        if (!UserValidator.isPasswordValid(signUpDto.getPassword())) {
            return new ResponseEntity<>(ErrorEnum.INVALID_PASSWORD.toString(), HttpStatus.BAD_REQUEST);
        }

        if (!UserValidator.isValidEmail(signUpDto.getEmail())) {
            return new ResponseEntity<>(ErrorEnum.INVALID_EMAIL, HttpStatus.BAD_REQUEST);
        }

        if (signUpDto.getDateOfBirth() != null && !UserValidator.isValidDateOfBirth(signUpDto.getDateOfBirth().toLocalDate())) {
            return new ResponseEntity<>(ErrorEnum.INVALID_DATE_OF_BIRTH.toString(), HttpStatus.BAD_REQUEST);
        }

        // create user object
        User user = new User();
        user.setName(signUpDto.getName());
        user.setSurname(signUpDto.getSurname());
        user.setUsername(signUpDto.getUsername());
        user.setEmail(signUpDto.getEmail());
        user.setPassword(passwordEncoder.encode(signUpDto.getPassword()));

        Role roles = roleRepository.findByName("ADMIN").get();
        user.setRoles(Collections.singleton(roles));

        userRepository.save(user);

        return new ResponseEntity<>("Admin registered successfully", HttpStatus.OK);

    }

    @PostMapping("/signup/simpleUser")
    public ResponseEntity<?> registerSimpleUser(@RequestBody SignUpDTO signUpDto) {

        if (userRepository.existsByUsername(signUpDto.getUsername())) {
            return new ResponseEntity<>("Username is already taken!", HttpStatus.BAD_REQUEST);
        }

        if (userRepository.existsByEmail(signUpDto.getEmail())) {
            return new ResponseEntity<>("Email is already taken!", HttpStatus.BAD_REQUEST);
        }

        if (!UserValidator.isOnlyLetters(signUpDto.getName()) || !UserValidator.isOnlyLetters(signUpDto.getSurname())) {
            return new ResponseEntity<>(ErrorEnum.INVALID_NAME.toString(), HttpStatus.BAD_REQUEST);
        }

        if (!UserValidator.isPasswordValid(signUpDto.getPassword())) {
            return new ResponseEntity<>(ErrorEnum.INVALID_PASSWORD.toString(), HttpStatus.BAD_REQUEST);
        }

        if (!UserValidator.isValidEmail(signUpDto.getEmail())) {
            return new ResponseEntity<>(ErrorEnum.INVALID_EMAIL, HttpStatus.BAD_REQUEST);
        }

        if (signUpDto.getDateOfBirth() != null && !UserValidator.isValidDateOfBirth(signUpDto.getDateOfBirth().toLocalDate())) {
            return new ResponseEntity<>(ErrorEnum.INVALID_DATE_OF_BIRTH.toString(), HttpStatus.BAD_REQUEST);
        }

        User user = new User();
        user.setName(signUpDto.getName());
        user.setSurname(signUpDto.getSurname());
        user.setUsername(signUpDto.getUsername());
        user.setEmail(signUpDto.getEmail());
        user.setPassword(passwordEncoder.encode(signUpDto.getPassword()));

        Role roles = roleRepository.findByName("SIMPLE_USER").get();
        user.setRoles(Collections.singleton(roles));

        userRepository.save(user);

        return new ResponseEntity<>("Simple user registered successfully", HttpStatus.OK);
    }
}