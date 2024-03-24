package com.example.A1LibraryManagement.mapper;

import com.example.A1LibraryManagement.dto.BorrowerDTO;
import com.example.A1LibraryManagement.model.Borrower;
import org.springframework.stereotype.Component;

@Component
public class BorrowerMapper {
    public BorrowerDTO convertToDto(Borrower borrower) {
        return BorrowerDTO.builder()
                //.ID(borrower.getID())
                .name(borrower.getName())
                .surname(borrower.getSurname())
                .dateOfBirth(borrower.getDateOfBirth())
                .email(borrower.getEmail())
                .build();
    }

    public Borrower convertToEntity(BorrowerDTO borrowerDTO) {
        Borrower borrower = new Borrower();
        borrower.setName(borrowerDTO.getName());
        borrower.setSurname(borrowerDTO.getSurname());
        borrower.setEmail(borrowerDTO.getEmail());
        borrower.setDateOfBirth(borrowerDTO.getDateOfBirth());
        return borrower;
    }
}
