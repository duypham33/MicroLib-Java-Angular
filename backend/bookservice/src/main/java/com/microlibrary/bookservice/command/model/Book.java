package com.microlibrary.bookservice.command.model;

import java.io.Serializable;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data @AllArgsConstructor @NoArgsConstructor @Builder
@Document(value = "books")
public class Book implements Serializable {
    @Id
    private String id;
    private String name;
    private String author;
    private Boolean active;
}
