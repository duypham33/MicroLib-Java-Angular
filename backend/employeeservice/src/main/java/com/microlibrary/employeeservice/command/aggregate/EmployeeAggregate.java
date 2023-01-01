package com.microlibrary.employeeservice.command.aggregate;

import org.axonframework.commandhandling.CommandHandler;
import org.axonframework.eventsourcing.EventSourcingHandler;
import org.axonframework.modelling.command.AggregateIdentifier;
import org.axonframework.modelling.command.AggregateLifecycle;
import org.axonframework.spring.stereotype.Aggregate;
import org.springframework.beans.BeanUtils;

import com.microlibrary.employeeservice.command.commands.EmployeeCreateCommand;
import com.microlibrary.employeeservice.command.commands.EmployeeDeleteCommand;
import com.microlibrary.employeeservice.command.commands.EmployeeUpdateCommand;
import com.microlibrary.employeeservice.command.events.EmployeeCreatedEvent;
import com.microlibrary.employeeservice.command.events.EmployeeDeletedEvent;
import com.microlibrary.employeeservice.command.events.EmployeeUpdatedEvent;

import lombok.Data;

@Aggregate @Data
public class EmployeeAggregate {
    @AggregateIdentifier
    private String id;
    private String firstName;
    private String lastName;
    private String kin;
    private Boolean isDisciplined;

    public EmployeeAggregate(){}

    //Create Employee
    @CommandHandler
    public EmployeeAggregate(EmployeeCreateCommand command){
        EmployeeCreatedEvent event = new EmployeeCreatedEvent();
        BeanUtils.copyProperties(command, event);

        AggregateLifecycle.apply(event);
    }

    //Update Employee
    @CommandHandler
    public void on(EmployeeUpdateCommand command){
        EmployeeUpdatedEvent event = new EmployeeUpdatedEvent();
        BeanUtils.copyProperties(command, event);

        AggregateLifecycle.apply(event);
    }

    //Delete Employee
    @CommandHandler
    public void handle(EmployeeDeleteCommand command){
        EmployeeDeletedEvent event = new EmployeeDeletedEvent(command.getId());

        AggregateLifecycle.apply(event);
    }

    @EventSourcingHandler
    public void on(EmployeeCreatedEvent event){
        BeanUtils.copyProperties(event, this);
    }

    @EventSourcingHandler
    public void on(EmployeeUpdatedEvent event){
        BeanUtils.copyProperties(event, this);
    }

    @EventSourcingHandler
    public void on(EmployeeDeletedEvent event){
        this.id = event.getId();
    }
}
