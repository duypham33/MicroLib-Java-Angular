package com.microlibrary.borrowservice.command.controller;

import java.util.Date;
import java.util.UUID;

import org.axonframework.commandhandling.gateway.CommandGateway;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.stream.annotation.EnableBinding;
import org.springframework.cloud.stream.messaging.Source;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.integration.support.MessageBuilder;
import org.springframework.messaging.MessageChannel;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.microlibrary.borrowservice.command.commands.CreateBorrowCommand;
import com.microlibrary.borrowservice.command.commands.ReturnBorrowCommand;
import com.microlibrary.borrowservice.command.dto.BorrowingRequest;
import com.microlibrary.borrowservice.command.dto.MessageRequest;
import com.microlibrary.borrowservice.command.service.IBorrowService;


@RestController
@RequestMapping("/api/borrowing")
@EnableBinding(Source.class)
public class BorrowingController {
    @Autowired
    private CommandGateway commandGateway;

    @Autowired
    private IBorrowService service;

    @Autowired
    private MessageChannel channel;

    @PostMapping
    public ResponseEntity<String> createBorrowing(@RequestBody BorrowingRequest request){
        CreateBorrowCommand command = 
                new CreateBorrowCommand(UUID.randomUUID().toString(), 
                request.getBookId(), request.getEmployeeId(), new Date());

        commandGateway.sendAndWait(command);

        return ResponseEntity.ok("The record is added!");
    }

    @PutMapping
    public ResponseEntity<String> returnBorrowing(@RequestBody BorrowingRequest request){
        try{
            String borrowingId = service.findBorrowingId(request.getEmployeeId(), request.getBookId());
            ReturnBorrowCommand command = 
                new ReturnBorrowCommand(borrowingId, request.getBookId(), 
                                        request.getEmployeeId(), new Date());

            commandGateway.sendAndWait(command);
            return ResponseEntity.ok("The book is returned!");
        }
        
        catch(Exception e){
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(e.getMessage());
        }
    }

    @PostMapping("/sendMsg")
    public void SendMsg(@RequestBody MessageRequest request){
        try{
            ObjectMapper mapper = new ObjectMapper();
            String json = mapper.writeValueAsString(request);
            channel.send(MessageBuilder.withPayload(json).build());
        }
        catch(JsonProcessingException e){
            e.printStackTrace();
        }
    }
}
