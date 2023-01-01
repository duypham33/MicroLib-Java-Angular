package com.microlibrary.bookservice.command.commands;

import org.axonframework.modelling.command.TargetAggregateIdentifier;

public class DeleteBookCommand {
    @TargetAggregateIdentifier
    private String id;

    public DeleteBookCommand(String id){
        this.id = id;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    
}
