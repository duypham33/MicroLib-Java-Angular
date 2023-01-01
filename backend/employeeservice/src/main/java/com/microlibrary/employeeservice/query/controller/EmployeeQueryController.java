package com.microlibrary.employeeservice.query.controller;

import java.util.List;

import org.axonframework.messaging.responsetypes.ResponseTypes;
import org.axonframework.queryhandling.QueryGateway;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.microlibrary.commonservice.dto.BookDto;
import com.microlibrary.commonservice.dto.BorrowDto;
import com.microlibrary.commonservice.dto.EmployeeDto;
import com.microlibrary.commonservice.query.BookDetailQuery;
import com.microlibrary.commonservice.query.EmployeeDetailQuery;
import com.microlibrary.commonservice.query.GetListBorrowingByEmployee;
import com.microlibrary.employeeservice.command.model.Employee;

@RestController
@RequestMapping("/api/employees")
public class EmployeeQueryController {
    @Autowired
    private QueryGateway queryGateway;

    @GetMapping("/{id}")
    public ResponseEntity<EmployeeDto> getEmployeeDetail(@PathVariable String id){
        EmployeeDetailQuery employeeQuery = new EmployeeDetailQuery(id);
        EmployeeDto res = queryGateway.query(employeeQuery, 
                    ResponseTypes.instanceOf(EmployeeDto.class)).join();

        return ResponseEntity.ok(res);
    }

    @GetMapping
    public ResponseEntity<List<Employee>> getEmployees(){
        Employee[] res = queryGateway.query(null, 
                    ResponseTypes.instanceOf(Employee[].class)).join();

        return ResponseEntity.ok(List.of(res));
    }


    @GetMapping("/{employeeId}/books")
    public ResponseEntity<List<BookDto>> getBorrowingBooks(@PathVariable String employeeId){
        //get List Borrowing 
		GetListBorrowingByEmployee getListBorrowingByEmployee = new GetListBorrowingByEmployee();
		getListBorrowingByEmployee.setEmployeeId(employeeId);
		
        List<BorrowDto> listBorrowing = 
		 queryGateway.query(getListBorrowingByEmployee, ResponseTypes.multipleInstancesOf(BorrowDto.class))
		 .join();
	
		//Get list book
        List<BookDto> listBook = listBorrowing.stream()
        .map(b -> queryGateway.query(new BookDetailQuery(b.getBookId()), 
        ResponseTypes.instanceOf(BookDto.class)).join()).toList();
		
		return ResponseEntity.ok(listBook);
    }
}
