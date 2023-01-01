package com.microlibrary.borrowservice.command.service;

import com.microlibrary.borrowservice.command.dto.MessageRequest;

public interface IBorrowService {
    void sendMessage(MessageRequest request);
    String findBorrowingId(String employeeId, String bookId) throws Exception;
}
