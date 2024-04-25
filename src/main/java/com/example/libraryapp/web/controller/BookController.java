package com.example.libraryapp.web.controller;

import com.example.libraryapp.client.FluentdClient;
import com.example.libraryapp.persistence.model.Book;
import com.example.libraryapp.service.BookService;
import com.example.libraryapp.web.dto.BookDto;
import com.example.libraryapp.web.dto.DtoMapper;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;

import static com.example.libraryapp.client.LogMapper.createIdMap;
import static com.example.libraryapp.client.LogMapper.createPage;

@RestController
@RequestMapping("books")
public class BookController {

    private static final String BOOK_TAG = "book";

    private final BookService bookService;
    private final FluentdClient fluentdClient;

    @Autowired
    private BookController(BookService bookService, FluentdClient fluentdClient) {
        this.bookService = bookService;
        this.fluentdClient = fluentdClient;
    }

    @GetMapping
    public ResponseEntity<List<BookDto>> getAllBooks(@RequestParam(required = false) Integer page,
                                                     @RequestParam(required = false) Integer size) {
        List<BookDto> bookDtos;
        if (page != null && size != null) {
            if (page <= 0 || size <= 0) {
                return ResponseEntity.badRequest().build();
            }
            bookDtos = bookService.findAll(page, size).stream()
                    .map(DtoMapper::convertToDto)
                    .toList();
        } else {
            bookDtos = bookService.findAll().stream()
                    .map(DtoMapper::convertToDto)
                    .toList();
        }
        fluentdClient.send(BOOK_TAG, createPage("Request list of books", page, size));
        return ResponseEntity.ok(bookDtos);
    }

    @GetMapping("/{id}")
    public ResponseEntity<BookDto> getBook(@PathVariable Long id) {
        ResponseEntity<BookDto> response = bookService.findById(id)
                .map(book -> ResponseEntity.ok(DtoMapper.convertToDto(book)))
                .orElseGet(() -> ResponseEntity.notFound().build());
        fluentdClient.send(BOOK_TAG, createIdMap(id, String.format("Book with id '%s' was create", id)));
        return response;
    }

    @PostMapping
    public ResponseEntity<BookDto> createPublisher(@Valid @RequestBody BookDto bookDto) {
        Book book = bookService.create(bookDto);
        Long id = book.getId();
        fluentdClient.send(BOOK_TAG, createIdMap(id, String.format("Book with id '%s' was create", id)));
        return ResponseEntity.status(HttpStatus.CREATED).body(DtoMapper.convertToDto(book));
    }

    @PutMapping("/{id}")
    public ResponseEntity<BookDto> updatePublisher(@PathVariable Long id, @Valid @RequestBody BookDto bookDto) {
        bookService.findById(id).orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND));
        bookDto.setId(id);
        Book updatedBook = bookService.update(bookDto);
        fluentdClient.send(BOOK_TAG, createIdMap(id, String.format("Book with id '%s' was updated", id)));
        return ResponseEntity.ok(DtoMapper.convertToDto(updatedBook));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteBook(@PathVariable Long id) {
        bookService.findById(id).orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND));
        bookService.delete(id);
        fluentdClient.send(BOOK_TAG, createIdMap(id, String.format("Book with id '%s' deleted", id)));
        return ResponseEntity.noContent().build();
    }

}
