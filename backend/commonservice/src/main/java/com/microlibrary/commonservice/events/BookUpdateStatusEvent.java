package com.microlibrary.commonservice.events;

import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Data @Getter @Setter @NoArgsConstructor
public class BookUpdateStatusEvent {
    private String id;  //This is id of the borrowing record
    private String bookId;
    private String employeeId;
    private Boolean active;
}
