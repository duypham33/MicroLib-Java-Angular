package com.microlibrary.bookservice.command.commands;
import org.axonframework.modelling.command.TargetAggregateIdentifier;

import lombok.Builder;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;

@Data @Builder @Getter @Setter
public class UpdateBookCommand {
    @TargetAggregateIdentifier
    private String id;
    private String name;
    private String author;
    private Boolean active;

    public UpdateBookCommand(String id, String name, String author, Boolean active){
        this.id = id;
        this.name = name;
        this.author = author;
        this.active = active;
    }
}
