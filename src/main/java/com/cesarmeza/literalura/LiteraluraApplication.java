package com.cesarmeza.literalura;

import com.cesarmeza.literalura.principal.Principal;
import com.cesarmeza.literalura.repository.AutorRepository;
import com.cesarmeza.literalura.repository.LibroRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class LiteraluraApplication implements CommandLineRunner {

	@Autowired
	private LibroRepository repositorioLibros;
	@Autowired
	private AutorRepository repositorioAutores;
	public static void main(String[] args) {
		SpringApplication.run(LiteraluraApplication.class, args);
	}

	@Override
	public void run(String... args) throws Exception {
		Principal principal = new Principal(repositorioLibros, repositorioAutores);
		principal.muestraElMenu();
	}
}
