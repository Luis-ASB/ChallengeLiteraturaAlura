package com.literatura.challenger_literatura.model;

import jakarta.persistence.*;

@Entity
@Table(name = "libros")
public class Libro {
    @Id
    private long id;

    private String titulo;

    @ManyToOne
    private Autor autor;

    @Enumerated(EnumType.STRING)
    private Idioma idioma;

    private int numeroDescargas;


    public Libro(BookData bookData) {
        this.id = bookData.id();
        this.titulo = bookData.title();
        this.numeroDescargas = bookData.numDownloads();
        this.idioma = Idioma.fromString(bookData.languages().getFirst());
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getTitulo() {
        return titulo;
    }

    public void setTitulo(String titulo) {
        this.titulo = titulo;
    }

    public Autor getAutor() {
        return autor;
    }

    public void setAutor(Autor autor) {
        this.autor = autor;
    }

    public Idioma getIdioma() {
        return idioma;
    }

    public void setIdioma(Idioma idioma) {
        this.idioma = idioma;
    }

    public int getNumeroDescargas() {
        return numeroDescargas;
    }

    public void setNumeroDescargas(int numeroDescargas) {
        this.numeroDescargas = numeroDescargas;
    }

    @Override
    public String toString() {
        return "ID: " + id + " | " + titulo + " (" + autor.getNombre() + ") | Lang: " + idioma + " | Downloads: " + numeroDescargas;
    }
}
