
package com.example.A1LibraryManagement.service;

import com.example.A1LibraryManagement.dto.UserDTO;
import com.example.A1LibraryManagement.dto.UserDTODetails;
import com.example.A1LibraryManagement.exception.ApiException;
import com.example.A1LibraryManagement.exception.ErrorEnum;
import com.example.A1LibraryManagement.mapper.UserMapper;
import com.example.A1LibraryManagement.model.User;
import com.example.A1LibraryManagement.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class UserService{
    private final UserMapper userMapper;
    private final UserRepository userRepository;
    public List<UserDTO> getAll() {
        List<User> users = userRepository.findAll();
        if (users.isEmpty()) {
            throw new ApiException(ErrorEnum.USER_NOT_FOUND);
        }
        return users.stream()
                .map(userMapper::convertToDTO)
                .collect(Collectors.toList());
    }

    public List<UserDTO> getAllAdmins() {
        List<User> users = userRepository.findAll();
        if (users.isEmpty()) {
            throw new ApiException(ErrorEnum.USER_NOT_FOUND);
        }
        return users.stream()
                .filter(user -> user.getRoles().stream().anyMatch(role -> role.getName().equals("ADMIN")))
                .map(userMapper::convertToDTO)
                .collect(Collectors.toList());
    }

    public List<UserDTO> getAllSimpleUsers() {
        List<User> users = userRepository.findAll();
        if (users.isEmpty()) {
            throw new ApiException(ErrorEnum.USER_NOT_FOUND);
        }
        return users.stream()
                .filter(user -> user.getRoles().stream().anyMatch(role -> role.getName().equals("SIMPLE_USER")))
                .map(userMapper::convertToDTO)
                .collect(Collectors.toList());
    }

    public UserDTO getUserById(UUID id) {
        User user = userRepository.findById(id)
                .orElseThrow(() -> new ApiException(ErrorEnum.USER_NOT_FOUND));
        return userMapper.convertToDTO(user);
    }

    public UserDTO getUserByEmail(String email) {
        User user = userRepository.findByEmail(email)
                .orElseThrow(() -> new ApiException(ErrorEnum.USER_NOT_FOUND));
        return userMapper.convertToDTO(user);
    }
    public UserDTO getUserByEmailAndPassword(String email, String password) {
        User user = userRepository.findByEmailAndPassword(email, password)
                .orElseThrow(() -> new ApiException(ErrorEnum.WRONG_PASSWORD));
        return userMapper.convertToDTO(user);
    }

    public UserDTO createUser(UserDTODetails userDTODetails) {
        User user = userMapper.convertToEntity(userDTODetails);
        user = userRepository.save(user);
        return userMapper.convertToDTO(user);
    }

    public void deleteUserById(UUID id) {
        final User user = userRepository.findById(id)
                .orElseThrow(() -> new ApiException(ErrorEnum.USER_NOT_FOUND));
        userRepository.deleteById(id);
    }

    public void updateUser(String email, UserDTODetails updatedUser) {
        final User user = userRepository.findByEmail(email)
                .orElseThrow(() -> new ApiException(ErrorEnum.USER_NOT_FOUND));
        user.setUsername(updatedUser.getUsername());
        user.setName(updatedUser.getName());
        user.setEmail(updatedUser.getEmail());
        user.setSurname(updatedUser.getSurname());
    }

}

