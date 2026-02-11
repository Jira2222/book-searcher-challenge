package com.fabian.book_searcher_challenge.main;

import com.fabian.book_searcher_challenge.model.BookList;
import com.fabian.book_searcher_challenge.service.APIConsume;
import com.fabian.book_searcher_challenge.service.ConvertData;

import java.util.*;

public class Main {
    //Variables

    private Scanner key = new Scanner(System.in);

    private APIConsume apiConsume = new APIConsume();
    private ConvertData convertData = new ConvertData();
    private final String URL_BASE = "https://gutendex.com/books/?search=";

    public void test(){
        var json = apiConsume.getData(URL_BASE + "romeo");
        var data = convertData.getData(json, BookList.class);
        data.book().stream()
                .findFirst()
                .ifPresent(System.out::println);
    }
}
