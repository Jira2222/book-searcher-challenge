package com.fabian.book_searcher_challenge.repository;

import com.fabian.book_searcher_challenge.model.Book;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;
import java.util.Optional;

public interface BookRepository extends JpaRepository<Book, Long> {
    Optional<Book> findByTitleContainsIgnoreCase(String title);

    @Query(value = """
    SELECT 
        author,
        birth_year,
        death_year,
        array_agg(title) AS titles
    FROM books
    GROUP BY author, birth_year, death_year
    """, nativeQuery = true)
    List<Object[]> findAuthorsGrouped();
}
