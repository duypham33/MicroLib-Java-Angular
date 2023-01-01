package com.microlibrary.commonservice.dto;

import java.util.Date;

import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Data @Getter @Setter @NoArgsConstructor
public class BorrowDto {
    private String id;
	private String bookId;
	private String employeeId;
	private Date borrowingDate;
	private Date returnDate;
}
