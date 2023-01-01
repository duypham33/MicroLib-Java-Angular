package com.microlibrary.borrowservice.command.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.stream.annotation.EnableBinding;
import org.springframework.cloud.stream.messaging.Source;
import org.springframework.messaging.MessageChannel;
import org.springframework.messaging.support.MessageBuilder;
import org.springframework.stereotype.Service;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.microlibrary.borrowservice.command.dto.MessageRequest;
import com.microlibrary.borrowservice.command.model.Borrowing;
import com.microlibrary.borrowservice.command.model.BorrowingRepository;

@Service
@EnableBinding(Source.class)
public class BorrowService implements IBorrowService {
    @Autowired
    private BorrowingRepository borrowingRepository;

    @Autowired
    private MessageChannel channel;

    @Override
    public void sendMessage(MessageRequest request){
        try{
            ObjectMapper mapper = new ObjectMapper();
            String json = mapper.writeValueAsString(request);
            channel.send(MessageBuilder.withPayload(json).build());
        }
        catch(JsonProcessingException e){
            e.printStackTrace();
        }
    }
    
    @Override
    public String findBorrowingId(String employeeId, String bookId) throws Exception{
        Borrowing b = borrowingRepository.findByEmployeeIdAndBookIdAndReturnDateIsNull(employeeId, bookId);
        if(b == null)
            throw new Exception("No borrowing record is found!");
        else
            return b.getId();
    }
}
