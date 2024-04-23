package com.example.libraryapp.web.dto;

import com.example.libraryapp.persistence.model.Book;
import com.example.libraryapp.persistence.model.Publisher;
import lombok.experimental.UtilityClass;

@UtilityClass
public class DtoMapper {

    public static Publisher convertToEntity(PublisherDto publisherDto) {
        return Publisher.builder()
                .id(publisherDto.getId())
                .name(publisherDto.getName())
                .build();
    }

    public static PublisherDto convertToDto(Publisher publisher) {
        return PublisherDto.builder()
                .id(publisher.getId())
                .name(publisher.getName())
                .build();
    }

    public static Book convertToEntity(BookDto bookDto) {
        return Book.builder()
                .id(bookDto.getId())
                .title(bookDto.getTitle())
                .author(bookDto.getAuthor())
                .isbn(bookDto.getIsbn())
                .year(bookDto.getYear())
                .publisher(Publisher.builder()
                        .id(bookDto.getPublisherId())
                        .build())
                .build();
    }

    public static BookDto convertToDto(Book book) {
        return BookDto.builder()
                .id(book.getId())
                .title(book.getTitle())
                .author(book.getAuthor())
                .isbn(book.getIsbn())
                .year(book.getYear())
                .publisherId(book.getPublisher() == null ? null : book.getPublisher().getId())
                .build();
    }

}
