package com.microlibrary.borrowservice.command.saga;

import org.axonframework.commandhandling.gateway.CommandGateway;
import org.axonframework.messaging.responsetypes.ResponseTypes;
import org.axonframework.modelling.saga.EndSaga;
import org.axonframework.modelling.saga.SagaEventHandler;
import org.axonframework.modelling.saga.SagaLifecycle;
import org.axonframework.modelling.saga.StartSaga;
import org.axonframework.queryhandling.QueryGateway;
import org.axonframework.spring.stereotype.Saga;
import org.springframework.beans.factory.annotation.Autowired;
import com.microlibrary.borrowservice.command.commands.DeleteBorrowCommand;
import com.microlibrary.borrowservice.command.commands.SendMessageCommand;
import com.microlibrary.borrowservice.command.events.BorrowCreatedEvent;
import com.microlibrary.borrowservice.command.events.BorrowDeletedEvent;
import com.microlibrary.borrowservice.command.events.BorrowReturnedEvent;
import com.microlibrary.commonservice.commands.RollBackBookStatusCommand;
import com.microlibrary.commonservice.commands.UpdateStatusBookCommand;
import com.microlibrary.commonservice.dto.BookDto;
import com.microlibrary.commonservice.dto.EmployeeDto;
import com.microlibrary.commonservice.events.BookRollBackStatusEvent;
import com.microlibrary.commonservice.events.BookUpdateStatusEvent;
import com.microlibrary.commonservice.query.BookDetailQuery;
import com.microlibrary.commonservice.query.EmployeeDetailQuery;

@Saga
public class BorrowingSaga {
    @Autowired
    private transient CommandGateway commandGateway;

    @Autowired
    private transient QueryGateway queryGateway;

    @StartSaga
    @SagaEventHandler(associationProperty = "id")
    private void handle(BorrowCreatedEvent event){
        try{
            SagaLifecycle.associateWith("bookId", event.getBookId());

            BookDetailQuery bookQuery = new BookDetailQuery(event.getId());
            BookDto book = queryGateway.query(bookQuery, 
                    ResponseTypes.instanceOf(BookDto.class)).join();
            
            if(book.getActive()){
                UpdateStatusBookCommand command = new UpdateStatusBookCommand(
                    event.getId(), book.getId(), event.getEmployeeId() , false
                );

                commandGateway.sendAndWait(command);
            }
            else
                throw new Exception("The book is not available!");
            
        }
        catch(Exception e){
            rollBackBorrowRecord(event.getId());
        }
    }

    private void rollBackBorrowRecord(String id){
        commandGateway.send(new DeleteBorrowCommand(id));
    }

    @SagaEventHandler(associationProperty = "bookId")
    private void handle(BookUpdateStatusEvent event){
        try{
            //This case is returing book, not need to check discipline
            if(event.getActive()){
                //   Never happen, because we did handle end cycle in BorrowReturnedEvent below
            }


            EmployeeDetailQuery employeeQuery = new EmployeeDetailQuery(event.getEmployeeId());
            EmployeeDto employee = queryGateway.query(employeeQuery, 
                        ResponseTypes.instanceOf(EmployeeDto.class)).join();

            if(employee != null && employee.getIsDisciplined()){
                throw new Exception("The employee is in disciplined! Cannot borrow book!");
            }
            else{
                //   send Msg
                SendMessageCommand sendMessageCommand = new SendMessageCommand(
                    event.getId(), event.getEmployeeId(), "Borrowed book successfully!"
                );
                commandGateway.sendAndWait(sendMessageCommand);
               
                SagaLifecycle.end();
            }
        }
        catch(Exception e){
            rollBackBookStatus(event.getBookId(), event.getEmployeeId(), event.getId());
        }
    }

    private void rollBackBookStatus(String bookId, String employeeId, String borrowId){
        SagaLifecycle.associateWith("bookId", bookId);
        RollBackBookStatusCommand command = new RollBackBookStatusCommand(borrowId, bookId, employeeId, true);
        commandGateway.send(command);
    }


    @SagaEventHandler(associationProperty = "bookId")
    private void handle(BookRollBackStatusEvent event){
        rollBackBorrowRecord(event.getId());
    }
    
    @EndSaga
    @SagaEventHandler(associationProperty = "id")
    private void handle(BorrowDeletedEvent event){
        SagaLifecycle.end();
    }




    @StartSaga
    @SagaEventHandler(associationProperty = "id")
    private void handle(BorrowReturnedEvent event){
        try{
            SagaLifecycle.associateWith("bookId", event.getBookId());

            UpdateStatusBookCommand command = new UpdateStatusBookCommand(
                event.getId(), event.getId(), event.getEmployeeId() , true
            );

            commandGateway.sendAndWait(command);
            //Send msg
            SendMessageCommand sendMessageCommand = new SendMessageCommand(
                event.getId(), event.getEmployeeId(), "Returned book successfully!"
            );
            commandGateway.sendAndWait(sendMessageCommand);

            SagaLifecycle.end();
        }
        catch(Exception e){
            
        }
    }

}
