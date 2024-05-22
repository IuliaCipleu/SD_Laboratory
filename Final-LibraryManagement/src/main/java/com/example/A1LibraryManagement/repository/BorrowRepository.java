package com.example.A1LibraryManagement.repository;

import com.example.A1LibraryManagement.model.Borrow;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

public interface BorrowRepository extends JpaRepository<Borrow, UUID> {

}