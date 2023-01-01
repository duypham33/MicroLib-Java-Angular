package com.microlibrary.userservice.dto;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data @AllArgsConstructor
public class AddRole {
    private String username;
	private String rolename;
}
