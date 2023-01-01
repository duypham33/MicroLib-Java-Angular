package com.microlibrary.borrowservice.command.aggregate;

import java.util.Date;

import org.axonframework.commandhandling.CommandHandler;
import org.axonframework.eventsourcing.EventSourcingHandler;
import org.axonframework.modelling.command.AggregateIdentifier;
import org.axonframework.modelling.command.AggregateLifecycle;
import org.axonframework.spring.stereotype.Aggregate;
import org.springframework.beans.BeanUtils;

import com.microlibrary.borrowservice.command.commands.DeleteBorrowCommand;
import com.microlibrary.borrowservice.command.commands.CreateBorrowCommand;
import com.microlibrary.borrowservice.command.commands.ReturnBorrowCommand;
import com.microlibrary.borrowservice.command.commands.SendMessageCommand;
import com.microlibrary.borrowservice.command.events.BorrowCreatedEvent;
import com.microlibrary.borrowservice.command.events.BorrowDeletedEvent;
import com.microlibrary.borrowservice.command.events.BorrowReturnedEvent;
import com.microlibrary.borrowservice.command.events.BorrowSendMsgEvent;

import lombok.Data;

@Aggregate @Data
public class BorrowingAggregate {
    @AggregateIdentifier
    private String id;
    private String bookId;
	private String employeeId;
	private Date borrowingDate;
	private Date returnDate;

    private String message;

    public BorrowingAggregate(){}

    //Borrow book
    @CommandHandler
    public BorrowingAggregate(CreateBorrowCommand command){
        BorrowCreatedEvent event = new BorrowCreatedEvent();
        BeanUtils.copyProperties(command, event);

        AggregateLifecycle.apply(event);
    }

    //Return book
    @CommandHandler
    public void handle(ReturnBorrowCommand command){
        BorrowReturnedEvent event = new BorrowReturnedEvent();
        BeanUtils.copyProperties(command, event);

        AggregateLifecycle.apply(event);
    }

    //Roll back
    public void handle(DeleteBorrowCommand command){
        BorrowDeletedEvent event = new BorrowDeletedEvent();
        event.setId(event.getId());

        AggregateLifecycle.apply(event);
    }

    //Send Message
    public void handle(SendMessageCommand command){
        BorrowSendMsgEvent event = new BorrowSendMsgEvent();
        BeanUtils.copyProperties(command, event);

        AggregateLifecycle.apply(event);
    }


    @EventSourcingHandler
    public void on(BorrowCreatedEvent event){
        this.id = event.getId();
        this.bookId = event.getBookId();
        this.employeeId = event.getEmployeeId();
        this.borrowingDate = event.getBorrowingDate();
    }

    @EventSourcingHandler
    public void on(BorrowReturnedEvent event){
        this.id = event.getId();
        this.bookId = event.getBookId();
        this.employeeId = event.getEmployeeId();
        this.returnDate = event.getReturnDate();
    }

    @EventSourcingHandler
    public void on(BorrowDeletedEvent event){
        this.id = event.getId();
    }
    
    @EventSourcingHandler
    public void on(BorrowSendMsgEvent event){
        this.id = event.getId();
        this.employeeId = event.getEmployeeId();
        this.message = event.getMessage();
    }
}
