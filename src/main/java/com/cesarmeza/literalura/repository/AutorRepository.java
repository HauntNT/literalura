package com.cesarmeza.literalura.repository;

import com.cesarmeza.literalura.model.Autor;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;
import java.util.Optional;

public interface AutorRepository extends JpaRepository<Autor,Long> {
    Optional<Autor> findByNombre(String nombre);

    @Query("SELECT a FROM Autor a WHERE :año BETWEEN a.fechaDeNacimiento AND a.fechaDeMuerte")
    List<Autor> obtenerAutoresVivosEnLapso(int año);
}
