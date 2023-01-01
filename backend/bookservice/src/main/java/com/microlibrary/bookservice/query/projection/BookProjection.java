package com.microlibrary.bookservice.query.projection;

import java.util.List;

import org.axonframework.queryhandling.QueryHandler;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Component;

import com.microlibrary.bookservice.command.model.Book;
import com.microlibrary.bookservice.command.model.BookRepository;
import com.microlibrary.commonservice.dto.BookDto;
import com.microlibrary.commonservice.query.BookDetailQuery;

//import lombok.extern.slf4j.Slf4j;

//@Slf4j
@Component
public class BookProjection {
    @Autowired
    private BookRepository bookRepository;
    
    @Cacheable(value = "BookDto", key = "#bookQuery.id")
    @QueryHandler
    public BookDto handle(BookDetailQuery bookQuery){
        //log.info("Fetch Detail");
        Book book = bookRepository.findById(bookQuery.getId()).orElse(null);
        BookDto res = new BookDto();
        BeanUtils.copyProperties(book, res);

        return res;
    }
    
    @Cacheable(value = "BookList")
    @QueryHandler
    public List<Book> handle(Object anything){
        //log.info("Fetch List");
        return bookRepository.findAll();
    }
}
