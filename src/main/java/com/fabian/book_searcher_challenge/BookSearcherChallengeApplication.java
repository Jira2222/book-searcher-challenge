package com.fabian.book_searcher_challenge;

import com.fabian.book_searcher_challenge.main.Main;
import com.fabian.book_searcher_challenge.repository.BookRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class BookSearcherChallengeApplication implements CommandLineRunner {
    @Autowired
    private BookRepository repository;
	public static void main(String[] args) {
		SpringApplication.run(BookSearcherChallengeApplication.class, args);
	}


    @Override
    public void run(String... args) throws Exception {
        Main main = new Main(repository);
        main.showOptions();
    }
}
