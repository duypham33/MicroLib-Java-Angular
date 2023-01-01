package com.microlibrary.commonservice.commands;

import org.axonframework.modelling.command.TargetAggregateIdentifier;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;

@Data @Getter @Setter @AllArgsConstructor
public class UpdateStatusBookCommand {
    private String id;  //This is id of the borrowing record
    @TargetAggregateIdentifier
    private String bookId;
    private String employeeId;
    private Boolean active;
}

