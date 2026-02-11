package com.fabian.book_searcher_challenge.service;

public interface IConvertData {
    <T> T getData(String json, Class<T> tclass);
}
