package com.fabian.book_searcher_challenge.model;

import com.fasterxml.jackson.annotation.JsonAlias;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import java.util.List;

@JsonIgnoreProperties(ignoreUnknown = true)

public record BookList(
        @JsonAlias("results") List<BookData> book
) {
}
