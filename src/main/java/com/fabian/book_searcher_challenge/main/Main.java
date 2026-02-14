package com.fabian.book_searcher_challenge.main;

import com.fabian.book_searcher_challenge.model.Book;
import com.fabian.book_searcher_challenge.model.BookData;
import com.fabian.book_searcher_challenge.model.BookList;
import com.fabian.book_searcher_challenge.repository.BookRepository;
import com.fabian.book_searcher_challenge.service.APIConsume;
import com.fabian.book_searcher_challenge.service.ConvertData;

import java.util.*;
import java.util.stream.Collectors;

public class Main {
    //Variables

    private Scanner key = new Scanner(System.in);

    private APIConsume apiConsume = new APIConsume();
    private ConvertData convertData = new ConvertData();
    private final String URL_BASE = "https://gutendex.com/books/?search=";
    private BookRepository repository;

    private String booksStructure = """
                        ------- BOOK -------
                        Title: %s
                        Author: %s
                        Language: %s
                        Download Count: %s
                        --------------------
                        
                        """;

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
                    2 - Show all DB register books
                    3 - Show all DB register authors
                    4 - Show DB register books by year authors alive
                    5 - Show all DB register book by language
                    ********************************
                    """);

            try {
                option = key.nextInt();
                key.nextLine();
            }catch (NumberFormatException e){
                System.out.println("No valid option!!");
            }

            switch (option){
                case 1:
                    searchBook();
                break;
                case 2:
                    showRegisterBooks();
                break;
                case 3:
                    showRegisterAuthors();
                break;
                case 4:
                    ShowByYearAuthorsAlive();
                break;
                case 5:
                    showByLanguage();
                break;

                case 0:
                    System.out.println("Closing app!!");
                break;

                default:
                    System.out.println("Please input a valid option!");

            }

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


        Optional<Book> repeatBook;
        repeatBook = repository.findByTitleContainsIgnoreCase(findBook);

        if (book != null){
            if (repeatBook.isPresent()){
                System.out.println("Repeat book in our Data Base!");
            }else {
                repository.save(book);
                System.out.println(String.format(booksStructure,
                        book.getTitle(), book.getAuthor(), book.getLanguage(), book.getDownloadCount()));
            }
        }else {
            System.out.println("Book no found!!!");
        }
    }

    private void showRegisterBooks() {
        List<Book> books = repository.findAll();

        books.forEach(b ->
                        System.out.printf(booksStructure,
                                b.getTitle(), b.getAuthor(), b.getLanguage(),b.getDownloadCount())
                );
    }


    private void showRegisterAuthors() {
       List<Object[]> booksByAuthor = repository.findAuthorsGrouped();

       authorsTemplate(booksByAuthor);
    }

    private void ShowByYearAuthorsAlive() {
        System.out.println("Input a year to search alive authors and their books:");
        var year = key.nextInt();
        key.nextLine();

        List<Object[]> booksByAliveYearAuthor = repository.findByAliveYear(year);

        if(booksByAliveYearAuthor.isEmpty()){
            System.out.println("Books no found with alive authors in " + year + "!!!");
        }else{
            authorsTemplate(booksByAliveYearAuthor);
        }

    }

    private void showByLanguage() {
        System.out.println("""
                Input language to find books (input the two first letter):
                en - English
                es - Spanish
                fr - French
                pt - Portuguese
                de - German
                """);
        var lang = key.nextLine();

        List<Book> booksByLanguage = repository.findByLanguage(lang);

        if (booksByLanguage.isEmpty()){
            System.out.println("Books no found with that language!");
        }else{
            booksByLanguage.forEach(b ->
                    System.out.printf(booksStructure,
                            b.getTitle(), b.getAuthor(), b.getLanguage(),b.getDownloadCount())
            );
        }
    }

    private void authorsTemplate(List<Object[]> authorBook){
        for (Object[] row: authorBook) {
            String author = (String) row[0];
            Integer birth_day = (Integer) row[1];
            Integer death_day = (Integer) row[2];

            String[] titlesArray = (String[]) row[3];
            List<String> titles = List.of(titlesArray);

            System.out.println(String.format("""
                   Author: %s
                   Birth Day: %s
                   Death Day: %s
                   Titles: %s
                   """, author, birth_day, death_day, titles));
        }
    }

}
