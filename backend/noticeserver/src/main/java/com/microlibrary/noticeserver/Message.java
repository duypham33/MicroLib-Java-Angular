package com.microlibrary.noticeserver;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data @AllArgsConstructor
public class Message {
	private String employeeId;
	private String message;
	
	@Override
	public String toString() {
		return "Message [employeeId=" + employeeId + ", message=" + message + "]";
	}
}
