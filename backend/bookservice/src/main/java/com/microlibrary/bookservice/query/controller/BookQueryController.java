package com.microlibrary.bookservice.query.controller;

import java.util.List;

import org.axonframework.messaging.responsetypes.ResponseTypes;
import org.axonframework.queryhandling.QueryGateway;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.microlibrary.bookservice.command.model.Book;
import com.microlibrary.commonservice.dto.BookDto;
import com.microlibrary.commonservice.query.BookDetailQuery;

@RestController
@RequestMapping("/api/books")
public class BookQueryController {
    @Autowired
    private QueryGateway queryGateway;

    @GetMapping("/{id}")
    public ResponseEntity<BookDto> getBookDetail(@PathVariable String id){
        BookDetailQuery bookQuery = new BookDetailQuery(id);
        BookDto res = queryGateway.query(bookQuery, 
                    ResponseTypes.instanceOf(BookDto.class)).join();
        
        return ResponseEntity.ok(res);
    }

    @GetMapping
    public ResponseEntity<List<Book>> getBooks(){
        Book[] res = queryGateway.query(null, 
                    ResponseTypes.instanceOf(Book[].class)).join();

        return ResponseEntity.ok(List.of(res));
    }
}
