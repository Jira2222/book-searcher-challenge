package com.fabian.book_searcher_challenge.repository;

import com.fabian.book_searcher_challenge.model.Book;
import org.springframework.data.jpa.repository.JpaRepository;

public interface BookRepository extends JpaRepository<Book, Long> {
}
