package com.microlibrary.employeeservice.command.controller;

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

import com.microlibrary.employeeservice.command.commands.EmployeeCreateCommand;
import com.microlibrary.employeeservice.command.commands.EmployeeDeleteCommand;
import com.microlibrary.employeeservice.command.commands.EmployeeUpdateCommand;
import com.microlibrary.employeeservice.command.dto.EmployeeRequest;


@RestController
@RequestMapping("/api/employees")
public class EmployeeController {
    @Autowired
    private CommandGateway commandGateway;

    @PostMapping
    public ResponseEntity<String> createEmployee(@RequestBody EmployeeRequest request){
        EmployeeCreateCommand command = 
                new EmployeeCreateCommand(UUID.randomUUID().toString(), 
                request.getFirstName(), request.getLastName(), 
                request.getKin(), request.getIsDisciplined());

        commandGateway.sendAndWait(command);

        return ResponseEntity.ok("The employee is added!");
    }

    @PutMapping("/{id}")
    public ResponseEntity<String> updateEmployee(@PathVariable String id, 
                        @RequestBody EmployeeRequest request){
        EmployeeUpdateCommand command = 
                new EmployeeUpdateCommand(id, request.getFirstName(), request.getLastName(),
                request.getKin(), request.getIsDisciplined());

        commandGateway.sendAndWait(command);

        return ResponseEntity.ok("The employee is updated!");
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<String> deleteEmployee(@PathVariable String id){
        EmployeeDeleteCommand command = new EmployeeDeleteCommand(id);

        commandGateway.sendAndWait(command);

        return ResponseEntity.ok("The employee is removed!");
    }
}
