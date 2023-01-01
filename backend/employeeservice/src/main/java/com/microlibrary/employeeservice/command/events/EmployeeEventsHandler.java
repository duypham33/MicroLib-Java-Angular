package com.microlibrary.employeeservice.command.events;

import org.axonframework.eventhandling.EventHandler;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.microlibrary.employeeservice.command.model.Employee;
import com.microlibrary.employeeservice.command.model.EmployeeRepository;

@Component
public class EmployeeEventsHandler {
    @Autowired
    private EmployeeRepository employeeRepository;

    @EventHandler
    public void on(EmployeeCreatedEvent event){
        Employee e = new Employee();
        BeanUtils.copyProperties(event, e);

        employeeRepository.save(e);
    }

    @EventHandler
    public void on(EmployeeUpdatedEvent event){
        Employee e = employeeRepository.findById(event.getId()).orElse(null);
        if(e != null){
            if(event.getFirstName() != null && event.getFirstName().length() > 0)
                e.setFirstName(event.getFirstName());
            if(event.getLastName() != null && event.getLastName().length() > 0)
                e.setLastName(event.getLastName());
            if(event.getKin() != null && event.getKin().length() > 0)
                e.setKin(event.getKin());
            if(event.getIsDisciplined() != null)
                e.setIsDisciplined(event.getIsDisciplined());
        }
        
        employeeRepository.save(e);
    }

    @EventHandler
    public void on(EmployeeDeletedEvent event){
        employeeRepository.deleteById(event.getId());
    }
}
