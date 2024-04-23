package com.example.libraryapp.service;

import com.example.libraryapp.persistence.model.Book;
import com.example.libraryapp.persistence.model.Publisher;
import com.example.libraryapp.web.dto.BookDto;

import java.util.List;
import java.util.Optional;

public interface BookService {

    List<Book> findAll();

    List<Book> findAll(int page, int size);

    List<Book> findByPublisher(Publisher publisher);

    Optional<Book> findById(Long id);

    Book create(BookDto bookDto);

    Book update(BookDto bookDto);

    void delete(Long id);

}
