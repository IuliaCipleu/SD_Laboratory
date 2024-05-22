package com.example.A1LibraryManagement.repository;

import com.example.A1LibraryManagement.model.Borrower;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;
import java.util.UUID;

public interface BorrowerRepository extends JpaRepository<Borrower, UUID> {
    Optional<Borrower> findBySurname(String surname);

    Optional<Borrower> findByName(String name);

    Optional<Borrower> findById(UUID id);

    //Optional<Borrower> findByEmailAndPassword(String email, String password);

    void deleteById(UUID id);

    Optional<Borrower> findByEmail(String email);
}
