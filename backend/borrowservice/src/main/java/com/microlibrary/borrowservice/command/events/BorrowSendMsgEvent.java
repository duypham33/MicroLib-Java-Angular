package com.microlibrary.borrowservice.command.events;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data @NoArgsConstructor @AllArgsConstructor
public class BorrowSendMsgEvent {
    private String id;
	private String employeeId;
    private String message;
}
