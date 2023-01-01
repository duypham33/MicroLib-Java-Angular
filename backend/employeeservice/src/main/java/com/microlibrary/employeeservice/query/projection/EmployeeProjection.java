package com.microlibrary.employeeservice.query.projection;

import java.util.List;

import org.axonframework.queryhandling.QueryHandler;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.microlibrary.commonservice.dto.EmployeeDto;
import com.microlibrary.commonservice.query.EmployeeDetailQuery;
import com.microlibrary.employeeservice.command.model.Employee;
import com.microlibrary.employeeservice.command.model.EmployeeRepository;

@Component
public class EmployeeProjection {
    @Autowired
    private EmployeeRepository employeeRepository;

    @QueryHandler
    public EmployeeDto handle(EmployeeDetailQuery employeeQuery){
        Employee e = employeeRepository.findById(employeeQuery.getId()).orElse(null);
        EmployeeDto res = new EmployeeDto();
        BeanUtils.copyProperties(e, res);

        return res;
    }

    @QueryHandler
    public List<Employee> handle(Object anything){
        return employeeRepository.findAll();
    }
}
