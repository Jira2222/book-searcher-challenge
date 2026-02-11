package com.fabian.book_searcher_challenge.service;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

public class ConvertData implements IConvertData {
    private ObjectMapper objectMapper = new ObjectMapper();

    @Override
    public <T> T getData(String json, Class<T> tclass) {
        try {
            return objectMapper.readValue(json, tclass);
        } catch (JsonProcessingException e) {
            throw new RuntimeException(e);
        }
    }


}
