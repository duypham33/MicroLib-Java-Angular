package com.microlibrary.employeeservice.command.commands;

import org.axonframework.modelling.command.TargetAggregateIdentifier;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;

@Data @Builder @Getter @Setter @AllArgsConstructor
public class EmployeeDeleteCommand {
    @TargetAggregateIdentifier
    private String id;
}