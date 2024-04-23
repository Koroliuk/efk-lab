package com.example.libraryapp.persistence.repository;

import com.example.libraryapp.persistence.model.Book;
import com.example.libraryapp.persistence.model.Publisher;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface BookRepository extends JpaRepository<Book, Long> {

    List<Book> findByPublisher(Publisher publisher);

}
