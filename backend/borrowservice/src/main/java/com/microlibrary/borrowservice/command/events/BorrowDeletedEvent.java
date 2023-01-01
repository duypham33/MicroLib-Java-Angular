package com.microlibrary.borrowservice.command.events;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data @NoArgsConstructor @AllArgsConstructor
public class BorrowDeletedEvent {
    private String id;
}
