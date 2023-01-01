package com.microlibrary.borrowservice.command.dto;

import java.util.Date;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data @NoArgsConstructor @AllArgsConstructor
public class BorrowingRequest {
    private String id;
	private String bookId;

	private String employeeId;
	private Date borrowingDate;
	private Date returnDate;
}
