package com.microlibrary.borrowservice.command.model;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

public interface BorrowingRepository extends JpaRepository<Borrowing, String> {
    List<Borrowing> findByEmployeeIdAndReturnDataIsNull(String employeeId);
    Borrowing findByEmployeeIdAndBookIdAndReturnDateIsNull(String employeeId, String bookId);
}
