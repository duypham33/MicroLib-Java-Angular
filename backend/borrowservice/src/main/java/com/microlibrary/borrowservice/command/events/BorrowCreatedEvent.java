package com.microlibrary.borrowservice.command.events;

import java.util.Date;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data @NoArgsConstructor @AllArgsConstructor
public class BorrowCreatedEvent {
    private String id;
	private String bookId;

	private String employeeId;
	private Date borrowingDate;
	private Date returnDate;
}
