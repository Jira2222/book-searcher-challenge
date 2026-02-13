package com.fabian.book_searcher_challenge.model;

import jakarta.persistence.*;

import java.util.List;

@Entity
@Table(name = "books")
public class Book {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(unique = true)
    private String title;

    private String author;
    private Integer birthYear;
    private Integer deathYear;

    private String language;

    private Integer downloadCount;

    public Book(){}

    public Book(BookData bookData) {
        this.title =bookData.title();
        this.language = bookData.language().stream()
                .findFirst()
                .orElse("N/A");
        this.downloadCount = bookData.downloadCount();
    }

    public Book(BookAuthor bookAuthor){
        this.author = bookAuthor.name();
        this.birthYear = bookAuthor.birth_year();
        this.deathYear = bookAuthor.death_year();
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getAuthor() {
        return author;
    }

    public void setAuthor(String author) {
        this.author = author;
    }

    public Integer getBirthYear() {
        return birthYear;
    }

    public void setBirthYear(Integer birthYear) {
        this.birthYear = birthYear;
    }

    public Integer getDeathYear() {
        return deathYear;
    }

    public void setDeathYear(Integer deathYear) {
        this.deathYear = deathYear;
    }

    public Integer getDownloadCount() {
        return downloadCount;
    }

    public void setDownloadCount(Integer downloadCount) {
        this.downloadCount = downloadCount;
    }

    public String getLanguage() {
        return language;
    }

    public void setLanguage(String language) {
        this.language = language;
    }

    @Override
    public String toString() {
        return "Book{" +
                "title='" + title + '\'' +
                ", author='" + author + '\'' +
                ", birthYear=" + birthYear +
                ", deathYear=" + deathYear +
                ", language='" + language + '\'' +
                ", downloadCount=" + downloadCount +
                '}';
    }
}


