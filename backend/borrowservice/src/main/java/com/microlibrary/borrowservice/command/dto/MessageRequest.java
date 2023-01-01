package com.microlibrary.borrowservice.command.dto;


import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data @NoArgsConstructor @AllArgsConstructor
public class MessageRequest {
	private String employeeId;
    private String message;
}
