package com.fabian.book_searcher_challenge.model;

import com.fasterxml.jackson.annotation.JsonAlias;

public record BookAuthor(
        String name,
        Integer birth_year,
        Integer death_year
) {
}
