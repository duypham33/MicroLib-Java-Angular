package com.microlibrary.employeeservice.command.events;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;

@Data @Getter @Setter @AllArgsConstructor
public class EmployeeDeletedEvent {
    private String id;
}
