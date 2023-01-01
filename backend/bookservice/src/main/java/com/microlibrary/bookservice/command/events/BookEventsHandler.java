package com.microlibrary.bookservice.command.events;

import org.axonframework.eventhandling.EventHandler;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.CachePut;
import org.springframework.cache.annotation.Caching;
import org.springframework.stereotype.Component;

import com.microlibrary.bookservice.command.model.Book;
import com.microlibrary.bookservice.command.model.BookRepository;
import com.microlibrary.commonservice.dto.BookDto;
import com.microlibrary.commonservice.events.BookRollBackStatusEvent;
import com.microlibrary.commonservice.events.BookUpdateStatusEvent;

@Component
public class BookEventsHandler {
    @Autowired
    private BookRepository bookRepository;

    @EventHandler
    public void on(BookCreatedEvent event){
        Book book = new Book();
        BeanUtils.copyProperties(event, book);

        bookRepository.save(book);
    }
    
    @Caching(put = { @CachePut(value = "BookDto", key = "#event.id") },
            evict = { @CacheEvict(value = "BookList", allEntries = true) }
    )
    @EventHandler
    public BookDto on(BookUpdatedEvent event){
        Book book = bookRepository.findById(event.getId()).orElse(null);
        if(book != null){
            if(event.getName() != null && event.getName().length() > 0)
                book.setName(event.getName());
            if(event.getAuthor() != null && event.getAuthor().length() > 0)
                book.setAuthor(event.getAuthor());
            if(event.getActive() != null)
                book.setActive(event.getActive());
        }
        
        bookRepository.save(book);
        
        BookDto b = new BookDto();
        BeanUtils.copyProperties(book, b);
        return b;
    }
    

    @Caching(evict = { 
        @CacheEvict(value = "BookDto", key = "#event.id"),
        @CacheEvict(value = "BookList", allEntries = true) 
    })
    @EventHandler
    public void on(BookDeletedEvent event){
        bookRepository.deleteById(event.getId());
    }
    


    @Caching(put = { @CachePut(value = "BookDto", key = "#event.id") },
            evict = { @CacheEvict(value = "BookList", allEntries = true) }
    )
    @EventHandler
    public BookDto on(BookUpdateStatusEvent event){
        Book book = bookRepository.findById(event.getBookId()).orElse(null);
        if(book != null && event.getActive() != null)
            book.setActive(event.getActive());

        bookRepository.save(book);

        BookDto b = new BookDto();
        BeanUtils.copyProperties(book, b);
        return b;
    }
    


    @Caching(put = { @CachePut(value = "BookDto", key = "#event.id") },
            evict = { @CacheEvict(value = "BookList", allEntries = true) }
    )
    @EventHandler
    public BookDto on(BookRollBackStatusEvent event){
        Book book = bookRepository.findById(event.getBookId()).orElse(null);
        if(book != null && event.getActive() != null)
            book.setActive(event.getActive());

        bookRepository.save(book);

        BookDto b = new BookDto();
        BeanUtils.copyProperties(book, b);
        return b;
    }
}
