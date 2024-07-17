package com.literatura.challenger_literatura.service;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.stereotype.Service;

@Service
public class DataConverter {
    private final ObjectMapper objectMapper = new ObjectMapper();


    public <T> T getData(String json, Class<T> valueType) {
        try {
            return objectMapper.readValue(json, valueType);
        } catch (Exception e) {
            throw new RuntimeException("Error al convertir datos", e);
        }
    }
}
