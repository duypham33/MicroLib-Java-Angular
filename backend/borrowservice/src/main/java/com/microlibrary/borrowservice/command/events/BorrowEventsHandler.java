package com.microlibrary.borrowservice.command.events;

import org.axonframework.eventhandling.EventHandler;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import com.microlibrary.borrowservice.command.dto.MessageRequest;
import com.microlibrary.borrowservice.command.model.Borrowing;
import com.microlibrary.borrowservice.command.model.BorrowingRepository;
import com.microlibrary.borrowservice.command.service.IBorrowService;

@Component
public class BorrowEventsHandler {
    @Autowired
    private BorrowingRepository borrowingRepository;

    @Autowired
    private IBorrowService service;

    @EventHandler
    public void on(BorrowCreatedEvent event){
        Borrowing b = new Borrowing();
        BeanUtils.copyProperties(event, b);

        borrowingRepository.save(b);
    }

    @EventHandler @Transactional
    public void on(BorrowReturnedEvent event){
        Borrowing b = borrowingRepository.findById(event.getId()).orElse(null);
        if(b != null)
            b.setReturnDate(event.getReturnDate());

        borrowingRepository.save(b);
    }

    @EventHandler @Transactional
    public void on(BorrowDeletedEvent event){
        
        borrowingRepository.deleteById(event.getId());
    }

    public void on(BorrowSendMsgEvent event){
        MessageRequest msg = new MessageRequest(event.getEmployeeId(), event.getMessage());
        service.sendMessage(msg);
    }
}
