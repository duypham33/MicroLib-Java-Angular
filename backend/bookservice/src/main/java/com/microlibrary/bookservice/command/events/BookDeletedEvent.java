package com.microlibrary.bookservice.command.events;
import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Data @Getter @Setter @NoArgsConstructor
public class BookDeletedEvent {
    private String id;
}
