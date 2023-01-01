package com.microlibrary.borrowservice.query.controller;

import java.util.List;

import org.axonframework.messaging.responsetypes.ResponseTypes;
import org.axonframework.queryhandling.QueryGateway;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import org.springframework.http.ResponseEntity;

import com.microlibrary.borrowservice.command.model.Borrowing;
import com.microlibrary.commonservice.dto.BorrowDto;
import com.microlibrary.commonservice.query.GetListBorrowingByEmployee;

@RestController
@RequestMapping("/api/borrowing")
public class BorrowingController {
    @Autowired
    private QueryGateway queryGateway;

    @GetMapping("/{employeeId}")
    public ResponseEntity<List<BorrowDto>> getBorrowingByEmployee(@PathVariable String employeeId){
		GetListBorrowingByEmployee getBorrowingQuery = new GetListBorrowingByEmployee();
		getBorrowingQuery.setEmployeeId(employeeId);
		
		List<BorrowDto> list = 
		queryGateway.query(getBorrowingQuery, ResponseTypes.multipleInstancesOf(BorrowDto.class))
			.join();
		
		return ResponseEntity.ok(list);
	}

	@GetMapping
	public ResponseEntity<List<Borrowing>> getAllBorrowing(){
		List<Borrowing> list = queryGateway.query(null, 
        ResponseTypes.multipleInstancesOf(Borrowing.class))
				.join();
		return ResponseEntity.ok(list);
	}
}
