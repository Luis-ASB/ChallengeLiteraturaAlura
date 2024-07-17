package com.literatura.challenger_literatura.model;

import jakarta.persistence.*;

import java.util.List;


@Entity
@Table(name = "Autores")
public class Autor {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(unique = true, nullable = false)
    private String nombre;

    private int yearNacimiento;

    private int yearFallecimiento;

    @OneToMany(mappedBy = "autor", cascade = CascadeType.ALL, fetch = FetchType.EAGER)
    private List<Libro> libros;

    //Constructores
    public Autor() {
    }

    public Autor(AuthorData authorData) {
        this.nombre = authorData.name();
        this.yearNacimiento = authorData.yearBirth();
        this.yearFallecimiento = authorData.yearDeath();
    }


    public void setLibros(List<Libro> libros) {
        this.libros = libros;
        libros.forEach(libro -> libro.setAutor(this));
    }

    @Override
    public String toString() {
        return "ID: " + id + " | " + nombre + " (" + yearNacimiento + "-" + yearFallecimiento + ")";
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public int getYearNacimiento() {
        return yearNacimiento;
    }

    public void setYearNacimiento(int yearNacimiento) {
        this.yearNacimiento = yearNacimiento;
    }

    public int getYearFallecimiento() {
        return yearFallecimiento;
    }

    public void setYearFallecimiento(int yearFallecimiento) {
        this.yearFallecimiento = yearFallecimiento;
    }

    public List<Libro> getLibros() {
        return libros;
    }
}
