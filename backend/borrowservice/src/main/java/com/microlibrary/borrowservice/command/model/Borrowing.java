package com.microlibrary.borrowservice.command.model;

import java.util.Date;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;


@Data @NoArgsConstructor @AllArgsConstructor
@Entity
@Table(name = "borrowings")
public class Borrowing {
    @Id
    private String id;
	private String bookId;

	private String employeeId;
	private Date borrowingDate;
	private Date returnDate;
}
