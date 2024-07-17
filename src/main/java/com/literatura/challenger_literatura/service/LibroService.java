package com.literatura.challenger_literatura.service;

import com.literatura.challenger_literatura.model.BookData;
import com.literatura.challenger_literatura.model.Data;

import java.util.Optional;

public class LibroService {

    private final APIService apiService;
    private final DataConverter converter;

    public LibroService(APIService apiService, DataConverter converter) {
        this.apiService = apiService;
        this.converter = converter;
    }

    public BookData getBookData(String titulo) {
        var url = "https://gutendex.com/books/?search=" + titulo;
        var json = apiService.getData(url);

        if (!json.isEmpty() && !json.contains("\"count\":0")) {
            var datos = converter.getData(json, Data.class);

            Optional<BookData> libroBuscado = datos.results().stream().findFirst();
            if (libroBuscado.isPresent()) {
                return libroBuscado.get();
            }
        }

        return null;
    }
}
