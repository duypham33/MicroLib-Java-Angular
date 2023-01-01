package com.microlibrary.borrowservice.query.projection;

import java.util.ArrayList;
import java.util.List;

import org.axonframework.queryhandling.QueryHandler;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.microlibrary.borrowservice.command.model.Borrowing;
import com.microlibrary.borrowservice.command.model.BorrowingRepository;
import com.microlibrary.commonservice.dto.BorrowDto;
import com.microlibrary.commonservice.query.GetListBorrowingByEmployee;

@Component
public class BorrowProjection {
    @Autowired
    private BorrowingRepository repo;

    @QueryHandler
    public List<BorrowDto> handle(GetListBorrowingByEmployee query){
        List<Borrowing> l = repo.findByEmployeeIdAndReturnDataIsNull(query.getEmployeeId());
        List<BorrowDto> l2 = new ArrayList<>();
        for(Borrowing b : l){
            BorrowDto b2 = new BorrowDto();
            BeanUtils.copyProperties(b, b2);
            l2.add(b2);
        }

        return l2;
    }

    @QueryHandler
    public List<Borrowing> handle(Object anything){
        return repo.findAll();
    }
}
