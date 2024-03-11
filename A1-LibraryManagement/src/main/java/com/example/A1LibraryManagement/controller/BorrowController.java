package com.example.A1LibraryManagement.controller;

import com.example.A1LibraryManagement.dto.BorrowDTO;
import com.example.A1LibraryManagement.model.Borrow;
import com.example.A1LibraryManagement.repository.BorrowRepository;
import com.example.A1LibraryManagement.service.BorrowService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/api/borrows")
@RequiredArgsConstructor
public class BorrowController {
    private final BorrowService borrowService;
    @Autowired
    private BorrowRepository borrowRepository;

    @GetMapping("/")
    public List<Borrow> getAllUsers() {
        return borrowRepository.findAll();
    }

    @GetMapping("/ID/{id}")
    public ResponseEntity<BorrowDTO> getBorrowByID(@PathVariable UUID id) {
        return ResponseEntity.ok(borrowService.getBorrowById(id));
    }

    @PostMapping("/")
    public BorrowDTO createBorrow(@RequestBody BorrowDTO borrowDTO) {
        return borrowService.createBorrow(borrowDTO);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<String> deleteBorrow(@PathVariable UUID id) {
        try {
            borrowService.deleteBorrowByID(id);
            return ResponseEntity.ok("Borrow deleted successfully");
        } catch (Exception e) {
            return ResponseEntity.badRequest().body("Error deleting borrow: " + e.getMessage());
        }
    }

    @PutMapping("/{id}")
    public ResponseEntity<String> updateBook(@PathVariable UUID id, @RequestBody BorrowDTO borrowDTO){
        try{
            borrowService.updateBorrow(id, borrowDTO);
            return ResponseEntity.ok("Borrow updated successfully");
        }catch (Exception e){
            return ResponseEntity.badRequest().body("Error updating book: " + e.getMessage());
        }
    }
}
