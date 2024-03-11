package com.example.A1LibraryManagement.service;

import com.example.A1LibraryManagement.dto.BorrowDTO;
import com.example.A1LibraryManagement.exception.ApiException;
import com.example.A1LibraryManagement.exception.ErrorEnum;
import com.example.A1LibraryManagement.mapper.BorrowMapper;
import com.example.A1LibraryManagement.model.Borrow;
import com.example.A1LibraryManagement.repository.BorrowRepository;
import com.example.A1LibraryManagement.validator.BorrowValidator;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
@RequiredArgsConstructor
public class BorrowService {
    private final BorrowMapper borrowMapper;
    private final BorrowRepository borrowRepository;

    public BorrowDTO getBorrowById(UUID id) {
        Borrow borrow = borrowRepository.findById(id)
                .orElseThrow(() -> new ApiException(ErrorEnum.BORROW_NOT_FOUND));
        return borrowMapper.convertToDto(borrow);
    }

    public BorrowDTO createBorrow(BorrowDTO borrowDTO) {
        if (!BorrowValidator.isValidDate(borrowDTO.getBorrowDate(), borrowDTO.getReturnDate())) {
            throw new ApiException(ErrorEnum.INVALID_RETURN_DATE);
        }
        Borrow borrow = borrowMapper.convertToEntity(borrowDTO);
        borrow = borrowRepository.save(borrow);
        return borrowMapper.convertToDto(borrow);
    }

    public void deleteBorrowByID(UUID ID) {
        final Borrow borrow = borrowRepository.findById(ID)
                .orElseThrow(() -> new ApiException(ErrorEnum.BORROW_NOT_FOUND));
        borrowRepository.deleteById(ID);
    }

    public void updateBorrow(UUID ID, BorrowDTO updatedBorrowDTO){
        if (!BorrowValidator.isValidDate(updatedBorrowDTO.getBorrowDate(), updatedBorrowDTO.getReturnDate())) {
            throw new ApiException(ErrorEnum.INVALID_RETURN_DATE);
        }
        final Borrow borrow = borrowRepository.findById(ID)
                .orElseThrow(()-> new ApiException(ErrorEnum.BORROW_NOT_FOUND));
        borrow.setReturnDate(updatedBorrowDTO.getReturnDate());
        borrowRepository.save(borrow);
    }

}
