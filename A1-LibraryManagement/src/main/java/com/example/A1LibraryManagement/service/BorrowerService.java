package com.example.A1LibraryManagement.service;

import com.example.A1LibraryManagement.dto.AuthorDTO;
import com.example.A1LibraryManagement.dto.BorrowerDTO;
import com.example.A1LibraryManagement.exception.ApiException;
import com.example.A1LibraryManagement.exception.ErrorEnum;
import com.example.A1LibraryManagement.mapper.BorrowerMapper;
import com.example.A1LibraryManagement.model.Author;
import com.example.A1LibraryManagement.model.Borrower;
import com.example.A1LibraryManagement.repository.BorrowerRepository;
import com.example.A1LibraryManagement.validator.BorrowerValidator;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
@RequiredArgsConstructor
public class BorrowerService {
    private final BorrowerMapper borrowerMapper;
    private final BorrowerRepository borrowerRepository;
    public BorrowerDTO createBorrower(BorrowerDTO borrowerDTO) {
        if (!BorrowerValidator.isValidEmail(borrowerDTO.getEmail())) {
            throw new ApiException(ErrorEnum.INVALID_EMAIL);
        }
        if (!BorrowerValidator.isValidDateOfBirth(borrowerDTO.getDateOfBirth().toLocalDate())) {
            throw new ApiException(ErrorEnum.INVALID_DATE_OF_BIRTH);
        }
        Borrower borrower = borrowerMapper.convertToEntity(borrowerDTO);
        borrower = borrowerRepository.save(borrower);
        return borrowerMapper.convertToDto(borrower);
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
        if(!BorrowerValidator.isValidDateOfBirth(updatedBorrower.getDateOfBirth().toLocalDate())){
            throw new ApiException(ErrorEnum.INVALID_DATE_OF_BIRTH);
        }
        if(!BorrowerValidator.isValidEmail(updatedBorrower.getEmail())){
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
}
