package com.fabian.book_searcher_challenge;

import com.fabian.book_searcher_challenge.main.Main;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class BookSearcherChallengeApplication implements CommandLineRunner {

	public static void main(String[] args) {
		SpringApplication.run(BookSearcherChallengeApplication.class, args);
	}


    @Override
    public void run(String... args) throws Exception {
        Main main = new Main();
        main.test();
    }
}
