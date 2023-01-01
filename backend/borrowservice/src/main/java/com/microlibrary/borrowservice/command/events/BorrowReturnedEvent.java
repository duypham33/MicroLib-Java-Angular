package com.microlibrary.borrowservice.command.events;

import java.util.Date;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data @NoArgsConstructor @AllArgsConstructor
public class BorrowReturnedEvent {
    private String id;
	private String bookId;

	private String employeeId;
	private Date returnDate;
}
