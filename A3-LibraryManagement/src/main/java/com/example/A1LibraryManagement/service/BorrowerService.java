package com.example.A1LibraryManagement.service;

import com.example.A1LibraryManagement.dto.BorrowerDTO;
import com.example.A1LibraryManagement.dto.BorrowerDTODetails;
import com.example.A1LibraryManagement.exception.ApiException;
import com.example.A1LibraryManagement.exception.ErrorEnum;
import com.example.A1LibraryManagement.mapper.BorrowerMapper;
import com.example.A1LibraryManagement.model.Borrower;
import com.example.A1LibraryManagement.repository.BorrowerRepository;
import com.example.A1LibraryManagement.validator.UserValidator;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
@RequiredArgsConstructor
public class BorrowerService {
    private final BorrowerMapper borrowerMapper;
    private final BorrowerRepository borrowerRepository;
    //private final JWTUtil jwtUtil;
    //private final PasswordEncoder passwordEncoder;
    public BorrowerDTO createBorrower(BorrowerDTO borrowerDTO) {
        if (!UserValidator.isValidEmail(borrowerDTO.getEmail())) {
            throw new ApiException(ErrorEnum.INVALID_EMAIL);
        }
        if (!UserValidator.isValidDateOfBirth(borrowerDTO.getDateOfBirth().toLocalDate())) {
            throw new ApiException(ErrorEnum.INVALID_DATE_OF_BIRTH);
        }
        if( !UserValidator.isOnlyLetters(borrowerDTO.getName()) || !UserValidator.isOnlyLetters(borrowerDTO.getSurname())){
            throw new ApiException(ErrorEnum.INVALID_NAME);
        }
        Borrower borrower = borrowerMapper.convertToEntity(borrowerDTO);
        borrower = borrowerRepository.save(borrower);
        return borrowerMapper.convertToDto(borrower);
    }

    public BorrowerDTODetails createBorrower(BorrowerDTODetails borrowerDTODetails) {
        if (!UserValidator.isValidEmail(borrowerDTODetails.getEmail())) {
            throw new ApiException(ErrorEnum.INVALID_EMAIL);
        }
        if (!UserValidator.isValidDateOfBirth(borrowerDTODetails.getDateOfBirth().toLocalDate())) {
            throw new ApiException(ErrorEnum.INVALID_DATE_OF_BIRTH);
        }
        if( !UserValidator.isOnlyLetters(borrowerDTODetails.getName()) || !UserValidator.isOnlyLetters(borrowerDTODetails.getSurname())){
            throw new ApiException(ErrorEnum.INVALID_NAME);
        }
        Borrower borrower = borrowerMapper.convertToEntity(borrowerDTODetails);
        borrower = borrowerRepository.save(borrower);
        return borrowerMapper.convertToDtoDetails(borrower);
    }

    public BorrowerDTO getBorrowerById(UUID id) {
       Borrower borrower = borrowerRepository.findById(id)
                .orElseThrow(() -> new ApiException(ErrorEnum.BORROWER_NOT_FOUND));
        return borrowerMapper.convertToDto(borrower);
    }

    public BorrowerDTO getBorrowerByName(String name) {
        Borrower borrower = borrowerRepository.findByName(name)
                .orElseThrow(() -> new ApiException(ErrorEnum.BORROWER_NOT_FOUND));
        return borrowerMapper.convertToDto(borrower);
    }

    public BorrowerDTO getBorrowerBySurname(String surname) {
        Borrower borrower = borrowerRepository.findByName(surname)
                .orElseThrow(() -> new ApiException(ErrorEnum.BORROWER_NOT_FOUND));
        return borrowerMapper.convertToDto(borrower);
    }

    public void deleteBorrowerByID(UUID ID) {
        final Borrower borrower = borrowerRepository.findById(ID)
                .orElseThrow(() -> new ApiException(ErrorEnum.BORROWER_NOT_FOUND));
        borrowerRepository.deleteById(ID);
    }

    public void updateBorrower(UUID ID, BorrowerDTO updatedBorrower){
        if(!UserValidator.isValidDateOfBirth(updatedBorrower.getDateOfBirth().toLocalDate())){
            throw new ApiException(ErrorEnum.INVALID_DATE_OF_BIRTH);
        }
        if(!UserValidator.isValidEmail(updatedBorrower.getEmail())){
            throw new ApiException(ErrorEnum.INVALID_EMAIL);
        }
        final Borrower borrower = borrowerRepository.findById(ID)
                .orElseThrow(() -> new ApiException(ErrorEnum.AUTHOR_NOT_FOUND));
        borrower.setName(updatedBorrower.getName());
        borrower.setSurname(updatedBorrower.getSurname());
        borrower.setEmail(updatedBorrower.getEmail());
        borrower.setDateOfBirth(updatedBorrower.getDateOfBirth());
        borrowerRepository.save(borrower);
    }

/*    public BorrowerDTO findByEmailAndPassword(String email, String password){
        Borrower borrower = borrowerRepository.findByEmailAndPassword(email, password)
                .orElseThrow(() -> new ApiException(ErrorEnum.WRONG_PASSWORD));
        return borrowerMapper.convertToDto(borrower);
    }*/

/*    public String login(String email, String password) {
        Borrower borrower = borrowerRepository.findByEmailAndPassword(email, password)
                .orElseThrow(() -> new ApiException(ErrorEnum.WRONG_PASSWORD));

        String token = jwtUtil.generateToken(email);
        borrower.setJwtToken(token);
        borrowerRepository.save(borrower);

        return token;
    }*/

//    public BorrowerDTO createBorrowerForSignIn(BorrowerDTO borrowerDTO) {
//        // Check if user with the same email already exists
//        if (borrowerRepository.findByEmail(borrowerDTO.getEmail()).isPresent()) {
//            throw new ApiException(ErrorEnum.USER_ALREADY_EXISTS);
//        }
//
//        // Validate other user details if needed
//
//        // Map DTO to entity
//        Borrower borrower = borrowerMapper.convertToEntity(borrowerDTO);
//
//        // Save user to database
//        borrower = borrowerRepository.save(borrower);
//
//        // Return DTO
//        return borrowerMapper.convertToDto(borrower);
//    }



}
