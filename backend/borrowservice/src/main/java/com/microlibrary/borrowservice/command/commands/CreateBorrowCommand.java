package com.microlibrary.borrowservice.command.commands;

import java.util.Date;

import org.axonframework.modelling.command.TargetAggregateIdentifier;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data @NoArgsConstructor @AllArgsConstructor
public class CreateBorrowCommand {
    @TargetAggregateIdentifier
    private String id;
	private String bookId;

	private String employeeId;
	private Date borrowingDate;
}
