package com.cesarmeza.literalura.repository;

import com.cesarmeza.literalura.model.Libro;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface LibroRepository extends JpaRepository<Libro,Long> {

    @Query(value = "SELECT * FROM libros WHERE :idioma = ANY(idioma)", nativeQuery = true)
    List<Libro> obtenerLibrosPorIdioma(String idioma);
}
