package com.fabian.book_searcher_challenge.model;

import com.fasterxml.jackson.annotation.JsonAlias;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import java.util.List;

@JsonIgnoreProperties(ignoreUnknown = true)

public record BookData(
        String title,
        @JsonAlias("authors") List<BookAuthor> author,
        @JsonAlias("languages") List<String> language,
        @JsonAlias("download_count") Integer downloadCount
) {
}
