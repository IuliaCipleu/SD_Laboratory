package com.example.A1LibraryManagement.controller;

import com.example.A1LibraryManagement.dto.AuthorDTO;
import com.example.A1LibraryManagement.dto.BorrowerDTO;
import com.example.A1LibraryManagement.mapper.BorrowerMapper;
import com.example.A1LibraryManagement.model.Borrower;
import com.example.A1LibraryManagement.repository.BorrowerRepository;
import com.example.A1LibraryManagement.service.BorrowerService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("api/borrowers")
@RequiredArgsConstructor
public class BorrowerController {
    private final BorrowerService borrowerService;
    @Autowired
    private BorrowerRepository borrowerRepository;
    @Autowired
    private BorrowerMapper borrowerMapper;

    @GetMapping("/")
    public List<Borrower> getAllUsers() {
        return borrowerRepository.findAll();
    }

    @GetMapping("/ID/{id}")
    public ResponseEntity<BorrowerDTO> getBorrowerById(@PathVariable UUID id) {
        return ResponseEntity.ok(borrowerService.getBorrowerById(id));
    }

    @PostMapping("/")
    public BorrowerDTO createBorrower(@RequestBody BorrowerDTO borrowerDTO) {
        return borrowerService.createBorrower(borrowerDTO);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<String> deleteBorrower(@PathVariable UUID id) {
        try {
            borrowerService.deleteBorrowerByID(id);
            return ResponseEntity.ok("Borrower deleted successfully");
        } catch (Exception e) {
            return ResponseEntity.badRequest().body("Error deleting borrower: " + e.getMessage());
        }
    }

    @PutMapping("/{id}")
    public ResponseEntity<String> updateBorrower(@PathVariable UUID id, @RequestBody BorrowerDTO updatedBorrower) {
        try {
            borrowerService.updateBorrower(id, updatedBorrower);
            return ResponseEntity.ok("Borrower updated successfully");
        } catch (Exception e) {
            return ResponseEntity.badRequest().body("Error updating borrower: " + e.getMessage());
        }
    }
}