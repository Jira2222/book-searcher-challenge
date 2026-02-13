package com.fabian.book_searcher_challenge.main;

import com.fabian.book_searcher_challenge.model.Book;
import com.fabian.book_searcher_challenge.model.BookData;
import com.fabian.book_searcher_challenge.model.BookList;
import com.fabian.book_searcher_challenge.repository.BookRepository;
import com.fabian.book_searcher_challenge.service.APIConsume;
import com.fabian.book_searcher_challenge.service.ConvertData;

import java.util.*;

public class Main {
    //Variables

    private Scanner key = new Scanner(System.in);

    private APIConsume apiConsume = new APIConsume();
    private ConvertData convertData = new ConvertData();
    private final String URL_BASE = "https://gutendex.com/books/?search=";
    private BookRepository repository;

    public Main(BookRepository repository) {
        this.repository = repository;
    }


    public void showOptions(){
        var option = -1;

        System.out.println("""
                    ********************************
                    PROJECT GUTENBERG BOOKS SEARCHER
                    ********************************
                    """);

        while (option != 0) {
            System.out.println("""
                    ********************************
                    Please, input a menu number:
                    
                    1 - Search book by title
                    
                    ********************************
                    """);

            option = key.nextInt();
            key.nextLine();

            switch (option){
                case 1:
                    searchBook();
                break;

            }

            option = 0;
        }

    }

    private void searchBook() {
        System.out.println("Please input book title to search:");
        var findBook = key.nextLine();
        var json = apiConsume.getData(URL_BASE + findBook.replace(" ", "%20"));
        var data = convertData.getData(json, BookList.class);
        Book book = data.book().stream()
                .findFirst()
                .map(bookData -> {
                    Book newBook = new Book(bookData);
                    bookData.author().stream()
                            .findFirst()
                            .ifPresent(author -> {
                                newBook.setAuthor(author.name());
                                newBook.setBirthYear(author.birth_year());
                                newBook.setDeathYear(author.death_year());
                            });

                    return newBook;
                })
                .orElse(null);


        if (book != null){
            repository.save(book);
            System.out.println(book);
        }else {
            System.out.println("Book no found!!!");
        }

    }
}
