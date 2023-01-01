package com.microlibrary.bookservice.command.aggregate;

import org.axonframework.commandhandling.CommandHandler;
import org.axonframework.eventsourcing.EventSourcingHandler;
import org.axonframework.modelling.command.AggregateIdentifier;
import org.axonframework.modelling.command.AggregateLifecycle;
import org.axonframework.spring.stereotype.Aggregate;
import org.springframework.beans.BeanUtils;

import com.microlibrary.bookservice.command.commands.CreateBookCommand;
import com.microlibrary.bookservice.command.commands.DeleteBookCommand;
import com.microlibrary.bookservice.command.commands.UpdateBookCommand;
import com.microlibrary.bookservice.command.events.BookCreatedEvent;
import com.microlibrary.bookservice.command.events.BookDeletedEvent;
import com.microlibrary.bookservice.command.events.BookUpdatedEvent;
import com.microlibrary.commonservice.commands.RollBackBookStatusCommand;
import com.microlibrary.commonservice.commands.UpdateStatusBookCommand;
import com.microlibrary.commonservice.events.BookRollBackStatusEvent;
import com.microlibrary.commonservice.events.BookUpdateStatusEvent;

import lombok.Data;

@Aggregate @Data
public class BookAggregate {
    @AggregateIdentifier
    private String id;
    private String name;
    private String author;
    private Boolean active;

    public BookAggregate(){}

    //Create Book
    @CommandHandler
    public BookAggregate(CreateBookCommand createBookCommand){
        BookCreatedEvent bookCreatedEvent = new BookCreatedEvent();
        BeanUtils.copyProperties(createBookCommand, bookCreatedEvent);

        AggregateLifecycle.apply(bookCreatedEvent);
    }

    //Update Book
    @CommandHandler
    public void handle(UpdateBookCommand updateBookCommand){
        BookUpdatedEvent bookUpdatedEvent = new BookUpdatedEvent();
        BeanUtils.copyProperties(updateBookCommand, bookUpdatedEvent);

        AggregateLifecycle.apply(bookUpdatedEvent);
    }

    //Delete Book
    @CommandHandler
    public void handle(DeleteBookCommand deleteBookCommand){
        BookDeletedEvent bookDeletedEvent = new BookDeletedEvent();
        bookDeletedEvent.setId(deleteBookCommand.getId());

        AggregateLifecycle.apply(bookDeletedEvent);
    }

    //Update status
    public void handle(UpdateStatusBookCommand updateStatusBookCommand){
        BookUpdateStatusEvent bookUpdateStatusEvent = new BookUpdateStatusEvent();
        BeanUtils.copyProperties(updateStatusBookCommand, bookUpdateStatusEvent);
        
        AggregateLifecycle.apply(bookUpdateStatusEvent);
    }

    public void handle(RollBackBookStatusCommand rollBackBookStatusCommand){
        BookRollBackStatusEvent bookUpdateStatusEvent = new BookRollBackStatusEvent();
        BeanUtils.copyProperties(rollBackBookStatusCommand, bookUpdateStatusEvent);
        
        AggregateLifecycle.apply(bookUpdateStatusEvent);
    }

    @EventSourcingHandler
    public void on(BookCreatedEvent event){
        BeanUtils.copyProperties(event, this);
    }

    @EventSourcingHandler
    public void on(BookUpdatedEvent event){
        BeanUtils.copyProperties(event, this);
    }

    @EventSourcingHandler
    public void on(BookDeletedEvent event){
        this.id = event.getId();
    }


    @EventSourcingHandler
    public void on(BookUpdateStatusEvent event){
        this.id = event.getBookId();
        this.active = event.getActive();
    }

    @EventSourcingHandler
    public void on(BookRollBackStatusEvent event){
        this.id = event.getBookId();
        this.active = event.getActive();
    }
}
