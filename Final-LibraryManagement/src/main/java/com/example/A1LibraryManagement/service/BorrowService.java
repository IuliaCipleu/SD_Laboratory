package com.example.A1LibraryManagement.service;

import com.example.A1LibraryManagement.dto.BorrowDTO;
import com.example.A1LibraryManagement.exception.ApiException;
import com.example.A1LibraryManagement.exception.ErrorEnum;
import com.example.A1LibraryManagement.mapper.BorrowMapper;
import com.example.A1LibraryManagement.model.Book;
import com.example.A1LibraryManagement.model.Borrow;
import com.example.A1LibraryManagement.repository.BorrowRepository;
import com.example.A1LibraryManagement.validator.BorrowValidator;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

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
        if (borrowDTO.getReturnDate() == null) {
            Book book = borrow.getBook();
            book.setCopiesInLibrary(book.getCopiesInLibrary() - 1);
            book.setCopiesBorrowed(book.getCopiesBorrowed() + 1);
        }
        borrow = borrowRepository.save(borrow);
        return borrowMapper.convertToDto(borrow);
    }

    public void deleteBorrowByID(UUID ID) {
        final Borrow borrow = borrowRepository.findById(ID)
                .orElseThrow(() -> new ApiException(ErrorEnum.BORROW_NOT_FOUND));
        borrowRepository.deleteById(ID);
    }

    /*public void updateBorrow(UUID ID, BorrowDTO updatedBorrowDTO){
        if (!BorrowValidator.isValidDate(updatedBorrowDTO.getBorrowDate(), updatedBorrowDTO.getReturnDate())) {
            throw new ApiException(ErrorEnum.INVALID_RETURN_DATE);
        }
        final Borrow borrow = borrowRepository.findById(ID)
                .orElseThrow(()-> new ApiException(ErrorEnum.BORROW_NOT_FOUND));
        if (borrow.getReturnDate() == null && updatedBorrowDTO.getReturnDate() != null){
            borrow.getBook().setCopiesBorrowed(borrow.getBook().getCopiesBorrowed() - 1);
            borrow.getBook().setCopiesInLibrary(borrow.getBook().getCopiesInLibrary() + 1);
        }
        borrow.setReturnDate(updatedBorrowDTO.getReturnDate());
        borrowRepository.save(borrow);
    }*/
    public void updateBorrow(UUID ID, BorrowDTO updatedBorrowDTO) {
        if (!BorrowValidator.isValidDate(updatedBorrowDTO.getBorrowDate(), updatedBorrowDTO.getReturnDate())) {
            throw new ApiException(ErrorEnum.INVALID_RETURN_DATE);
        }

        Borrow borrow = borrowRepository.findById(ID)
                .orElseThrow(() -> new ApiException(ErrorEnum.BORROW_NOT_FOUND));

        // Retrieve the previous return date to determine if the borrow status has changed
        Date previousReturnDate = borrow.getReturnDate();

        // Update the return date
        borrow.setReturnDate(updatedBorrowDTO.getReturnDate());

        // If the previous return date was null and the updated return date is not null
        if (previousReturnDate == null && updatedBorrowDTO.getReturnDate() != null) {
            // Update book copies
            Book book = borrow.getBook();
            book.setCopiesBorrowed(book.getCopiesBorrowed() - 1);
            book.setCopiesInLibrary(book.getCopiesInLibrary() + 1);
        }

        borrowRepository.save(borrow);
    }

    public List<BorrowDTO> getReturnedBorrows() {
        List<Borrow> returnedBorrows = borrowRepository.findByReturnDateIsNotNull();
        return returnedBorrows.stream()
                .map(borrowMapper::convertToDto)
                .collect(Collectors.toList());
    }

    public List<BorrowDTO> getNonReturnedBorrows() {
        List<Borrow> nonReturnedBorrows = borrowRepository.findByReturnDateIsNull();
        return nonReturnedBorrows.stream()
                .map(borrowMapper::convertToDto)
                .collect(Collectors.toList());
    }
}
