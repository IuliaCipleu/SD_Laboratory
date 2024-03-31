
package com.example.A1LibraryManagement.controller;

import com.example.A1LibraryManagement.config.CheckAuthentication;
import com.example.A1LibraryManagement.dto.UserDTO;
import com.example.A1LibraryManagement.dto.UserDTODetails;
import com.example.A1LibraryManagement.exception.ApiException;
import com.example.A1LibraryManagement.exception.ErrorEnum;
import com.example.A1LibraryManagement.mapper.UserMapper;
import com.example.A1LibraryManagement.repository.UserRepository;
import com.example.A1LibraryManagement.service.UserService;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;

import java.util.Collection;
import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/api/users")
@RequiredArgsConstructor
public class UserController {
    @Autowired
    private final UserService userService;
    @Autowired
    private final UserMapper userMapper;
    @Autowired
    private final UserRepository userRepository;
    @Autowired
    private PasswordEncoder passwordEncoder;
    @Autowired
    private AuthenticationManager authenticationManager;

    @GetMapping("/")
    public List<UserDTO> getAll() {
        CheckAuthentication checkAuthentication = new CheckAuthentication();
        checkAuthentication.checkAuthenticationAll();
        List<UserDTO> users = userService.getAll();
       /* users.forEach(user -> {
            System.out.println("User: " + user.getUsername());
            user.getRoles().forEach(role -> System.out.println("Role: " + role.getName()));
        });*/
        return users;
    }


    @GetMapping("/admins")
    //@PreAuthorize("hasRole('SIMPLE_USER') or hasRole('ADMIN')")
    public List<UserDTO> getAllAdmins(){
        CheckAuthentication checkAuthentication = new CheckAuthentication();
        checkAuthentication.checkAuthenticationAll();
        return userService.getAllAdmins();
    }

/*    private void checkAuthentication() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        Collection<? extends GrantedAuthority> authorities = authentication.getAuthorities();
        boolean hasPermission = authorities.stream()
                .anyMatch(authority -> authority.getAuthority().equals("ADMIN") || authority.getAuthority().equals("SIMPLE_USER"));
        if (!hasPermission) {
            throw new ApiException(ErrorEnum.ACCESS_DENIED);
        }
    }*/

    @GetMapping("/simple")
    //@PreAuthorize("hasRole('SIMPLE_USER') or hasRole('ADMIN')")
    public List<UserDTO> getAllSimpleUsers(HttpServletRequest request){
        /*String token = request.getHeader("Authorization");
        System.out.println("Token: " + token);*/
        //checkAuthentication();
        CheckAuthentication checkAuthentication1 = new CheckAuthentication();
        checkAuthentication1.checkAuthenticationAll();
        return userService.getAllSimpleUsers();
    }

    @GetMapping("/email/{email}")
    //@PreAuthorize("hasRole('SIMPLE_USER') or hasRole('ADMIN')")
    public ResponseEntity<UserDTO> getUserByEmail(@PathVariable String email){
        CheckAuthentication checkAuthentication = new CheckAuthentication();
        checkAuthentication.checkAuthenticationAll();
        return ResponseEntity.ok(userService.getUserByEmail(email));
    }

    @PostMapping("/create")
    //@PreAuthorize("hasRole('SIMPLE_USER') or hasRole('ADMIN')")
    public UserDTO createUser(@RequestBody UserDTODetails userDTODetails){
        CheckAuthentication checkAuthentication = new CheckAuthentication();
        checkAuthentication.checkAuthenticationRole("ADMIN");
        return userService.createUser(userDTODetails);
    }

    @DeleteMapping("/{email}")
    //@PreAuthorize("hasRole('SIMPLE_USER') or hasRole('ADMIN')")
    public ResponseEntity<String> deleteUser(@PathVariable String email){
        //checkAuthentication();
        CheckAuthentication checkAuthentication = new CheckAuthentication();
        checkAuthentication.checkAuthenticationRole("ADMIN");
        try{
            userService.deleteUserByEmail(email);
            return ResponseEntity.ok("User deleted successfully!");
        } catch (Exception e){
            return ResponseEntity.badRequest().body("Error deleting user: " + e.getMessage());
        }
    }

    @PutMapping("/{email}")
    //@PreAuthorize("hasRole('SIMPLE_USER') or hasRole('ADMIN')")
    public ResponseEntity<String> updateUser(@PathVariable String email, @RequestBody UserDTODetails updatedUser){
        CheckAuthentication checkAuthentication = new CheckAuthentication();
        checkAuthentication.checkAuthenticationRole("ADMIN");
        try{
            userService.updateUser(email, updatedUser);
            return ResponseEntity.ok("User updated successfully!");
        } catch (Exception e){
            return ResponseEntity.badRequest().body("error updating user: " + e.getMessage());
        }
    }

    /*@PostMapping("/signin")
    @PreAuthorize("hasRole('SIMPLE_USER') or hasRole('ADMIN')")
    public ResponseEntity<String> authenticateUser(@RequestBody LoginDTO loginDTO){
        Authentication authentication = authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(
                loginDTO.getUsernameOrEmail(), loginDTO.getPassword()));

        SecurityContextHolder.getContext().setAuthentication(authentication);
        return new ResponseEntity<>("User signed-in successfully!.", HttpStatus.OK);
    }*/
}

