package com.microlibrary.commonservice.dto;
import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Data @Getter @Setter @NoArgsConstructor
public class BookDto {
    private String id;
    private String name;
    private String author;
    private Boolean active;
}
