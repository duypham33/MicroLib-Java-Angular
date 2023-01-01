package com.microlibrary.bookservice.command.controller;

import java.util.UUID;

import org.axonframework.commandhandling.gateway.CommandGateway;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.microlibrary.bookservice.command.commands.CreateBookCommand;
import com.microlibrary.bookservice.command.commands.DeleteBookCommand;
import com.microlibrary.bookservice.command.commands.UpdateBookCommand;
import com.microlibrary.bookservice.command.dto.BookRequest;

@RestController
@RequestMapping("/api/books")
public class BookCommandController {
    @Autowired
    private CommandGateway commandGateway;

    @PostMapping
    public ResponseEntity<String> createBook(@RequestBody BookRequest request){
        CreateBookCommand createBookCommand = 
                new CreateBookCommand(UUID.randomUUID().toString(), 
                request.getName(), request.getAuthor(), true);

        commandGateway.sendAndWait(createBookCommand);

        return ResponseEntity.ok("The book is added!");
    }
    
    @PutMapping("/{id}")
    public ResponseEntity<String> updateBook(@PathVariable String id, @RequestBody BookRequest request){
        UpdateBookCommand updateBookCommand = 
                new UpdateBookCommand(id, request.getName(), request.getAuthor(), request.getActive());

        commandGateway.sendAndWait(updateBookCommand);

        return ResponseEntity.ok("The book is updated!");
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<String> deleteBook(@PathVariable String id){
        DeleteBookCommand deleteBookCommand = new DeleteBookCommand(id);

        commandGateway.sendAndWait(deleteBookCommand);

        return ResponseEntity.ok("The book is removed!");
    }
}
