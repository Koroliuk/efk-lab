package com.example.libraryapp.service.impl;

import com.example.libraryapp.persistence.model.Book;
import com.example.libraryapp.persistence.model.Publisher;
import com.example.libraryapp.persistence.repository.BookRepository;
import com.example.libraryapp.service.BookService;
import com.example.libraryapp.service.PublisherService;
import com.example.libraryapp.web.dto.BookDto;
import com.example.libraryapp.web.dto.DtoMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Optional;

@Service
public class BookServiceImpl implements BookService {

    private final BookRepository bookRepository;
    private final PublisherService publisherService;

    @Autowired
    private BookServiceImpl(BookRepository bookRepository, PublisherService publisherService) {
        this.bookRepository = bookRepository;
        this.publisherService = publisherService;
    }


    @Override
    public List<Book> findAll() {
        Iterator<Book> books = bookRepository.findAll().iterator();
        List<Book> bookList = new ArrayList<>();
        while (books.hasNext()) {
            bookList.add(books.next());
        }
        return bookList;
    }

    @Override
    public List<Book> findAll(int page, int size) {
        PageRequest pageRequest = PageRequest.of(page, size);
        Iterator<Book> books = bookRepository.findAll(pageRequest).iterator();
        List<Book> bookList = new ArrayList<>();
        while (books.hasNext()) {
            bookList.add(books.next());
        }
        return bookList;
    }

    @Override
    public List<Book> findByPublisher(Publisher publisher) {
        Iterator<Book> books = bookRepository.findByPublisher(publisher).iterator();
        List<Book> bookList = new ArrayList<>();
        while (books.hasNext()) {
            bookList.add(books.next());
        }
        return bookList;
    }

    @Override
    public Optional<Book> findById(Long id) {
        return bookRepository.findById(id);
    }

    @Override
    public Book create(BookDto bookDto) {
        Long publisherId = bookDto.getPublisherId();
        if (publisherService.findById(publisherId).isEmpty()) {
            throw new IllegalArgumentException(String.format("Publisher with id %s does not exist", publisherId));
        }
        Book book = DtoMapper.convertToEntity(bookDto);
        return bookRepository.save(book);
    }

    @Override
    public Book update(BookDto bookDto) {
        Long publisherId = bookDto.getPublisherId();
        if (publisherService.findById(publisherId).isEmpty()) {
            throw new IllegalArgumentException(String.format("Publisher with id %s does not exist", publisherId));
        }
        Long id = bookDto.getId();
        if (!bookRepository.existsById(id)) {
            throw new IllegalArgumentException(String.format("Book with id %s does not exist", id));
        }
        Book book = DtoMapper.convertToEntity(bookDto);
        return bookRepository.save(book);
    }

    @Override
    public void delete(Long id) {
        bookRepository.deleteById(id);
    }

}
