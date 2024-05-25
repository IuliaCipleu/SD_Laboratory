package com.example.A1LibraryManagement.repository;

import com.example.A1LibraryManagement.model.Borrow;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.UUID;

public interface BorrowRepository extends JpaRepository<Borrow, UUID> {
    List<Borrow> findByReturnDateIsNull();
    List<Borrow> findByReturnDateIsNotNull();
}