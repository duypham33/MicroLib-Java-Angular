package com.microlibrary.bookservice.command.dto;

import lombok.Builder;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;

@Data @Builder @Getter @Setter
public class BookRequest {
    private String id;
    private String name;
    private String author;
    private Boolean active;
}
