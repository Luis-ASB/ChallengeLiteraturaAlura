package com.literatura.challenger_literatura;

import com.literatura.challenger_literatura.repository.AutorRepository;
import com.literatura.challenger_literatura.repository.LibroRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class ChallengerLiteraturaApplication implements CommandLineRunner {
	@Autowired
	private LibroRepository libroRepository;

	@Autowired
	private AutorRepository autorRepository;

	public static void main(String[] args) {
		SpringApplication.run(ChallengerLiteraturaApplication.class, args);
	}

	@Override
	public void run(String... args) throws Exception {
		Main main = new Main(libroRepository, autorRepository);
		main.displayMenu();
	}

}
