package com.microlibrary.bookservice.command.model;
import org.springframework.data.mongodb.repository.MongoRepository;


public interface BookRepository extends MongoRepository<Book, String> {
    
}
