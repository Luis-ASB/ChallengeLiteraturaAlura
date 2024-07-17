package com.literatura.challenger_literatura;

import com.literatura.challenger_literatura.model.*;
import com.literatura.challenger_literatura.repository.AutorRepository;
import com.literatura.challenger_literatura.repository.LibroRepository;
import com.literatura.challenger_literatura.service.APIService;
import com.literatura.challenger_literatura.service.DataConverter;
import com.literatura.challenger_literatura.service.LibroService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.autoconfigure.SpringBootApplication;


import java.util.Optional;
import java.util.Scanner;

@SpringBootApplication
public class Main{

    @Autowired
    private LibroRepository libroRepository;

    @Autowired
    private AutorRepository autorRepository;

    private final Scanner userInput = new Scanner(System.in);
    private final APIService apiService = new APIService();
    private final DataConverter converter = new DataConverter();
    private final LibroService libroService = new LibroService(apiService, converter);

    public Main(LibroRepository libroRepository, AutorRepository autorRepository) {
        this.libroRepository = libroRepository;
        this.autorRepository = autorRepository;
    }


    public void displayMenu() {
        var option = -1;
        var text = """
                Literatura Alura
                ---------------------------------------------------
                1 – Buscar libro por título                          
                2 – Buscar autor por nombre
                3 – Lista de libros registrados
                4 – Lista de autores registrados
                5 – Lista de autores vivos en cierto año
                0 - SALIR
                ---------------------------------------------------
                """;

        while (option != 0) {
            System.out.print(text);
            option = userInput.nextInt();
            userInput.nextLine();

            switch (option) {
                case 1:
                    searchBookByTitle();
                    break;
                case 2:
                    searchAuthorByName();
                    break;
                case 3:
                    listBooks();
                    break;
                case 4:
                    listAuthors();
                    break;
                case 5:
                    listLivingAuthors();
                    break;
                case 0:
                    break;
                default:
                    System.out.println("Opción inválida");
                    break;
            }
        }
    }

    private void searchBookByTitle() {
        System.out.print("Escriba el título del libro a buscar: ");
        var titulo = userInput.nextLine().trim().toLowerCase().replace(" ", "+");

        BookData datos = libroService.getBookData(titulo);

        if (datos != null) {
            Libro libro = libroRepository.findById(datos.id()).orElse(null);

            if (libro == null) {
                Autor author = autorRepository.findByNombreContainingIgnoreCase(datos.authors().get(0).name())
                        .stream().findFirst().orElse(null);

                if (author == null) {
                    author = new Autor();
                    author.setNombre(datos.authors().get(0).name());
                    // Aquí puedes establecer el año de nacimiento y muerte si los tienes
                    autorRepository.save(author);
                }

                libro = new Libro(datos);
                libro.setId(datos.id());
                libro.setTitulo(datos.title());
                libro.setIdioma(Idioma.valueOf(datos.languages().get(0)));
                libro.setNumeroDescargas(datos.numDownloads());
                libro.setAutor(author);
                libroRepository.save(libro);
            }

            System.out.println(libro);
        } else {
            System.out.println("Libro no encontrado");
        }

    }

    private void searchAuthorByName() {
        System.out.print("Escriba el nombre del autor que busca: ");
        var nombre = userInput.nextLine();

        var autores = autorRepository.findByNombreContainingIgnoreCase(nombre);
        if (autores.isEmpty()) {
            System.out.println("No se encontraron autores");
        } else {
            autores.forEach(System.out::println);
        }

    }

    private void listBooks() {
        var books = libroRepository.findAll();
        if (books.isEmpty()) {
            System.out.println("No hay libros registrados");
        } else {
            books.forEach(System.out::println);
        }

    }

    private void listAuthors() {
        var autores = autorRepository.findAll();
        if (autores.isEmpty()) {
            System.out.println("No hay autores registrados");
        } else {
            autores.forEach(System.out::println);
        }

    }

    private void listLivingAuthors() {
        System.out.print("Escriba el año que busca para autores vivos: ");
        var year = userInput.nextInt();
        userInput.nextLine();

        var autores = autorRepository.findByFechaNacimientoBeforeAndFechaMuerteAfter(year, year);
        if (autores.isEmpty()) {
            System.out.println("No se encontraron autores vivos en el año " + year);
        } else {
            autores.forEach(System.out::println);
        }

    }

    private void listBooksByLang() {
        System.out.println("Seleccione el idioma:");
        System.out.println("1 - Inglés");
        System.out.println("2 - Español");
        System.out.println("3 - Francés");
        System.out.println("4 - Portugués");

        var option = userInput.nextInt();
        userInput.nextLine();
        String language = switch (option) {
            case 1 -> "en";
            case 2 -> "es";
            case 3 -> "fr";
            case 4 -> "pt";
            default -> {
                System.out.println("Opción inválida, seleccionando inglés por defecto.");
                yield "en";
            }
        };

        var books = libroRepository.findByIdioma(language);
        if (books.isEmpty()) {
            System.out.println("No se encontraron libros en " + language);
        } else {
            books.forEach(System.out::println);
        }
    }
}
