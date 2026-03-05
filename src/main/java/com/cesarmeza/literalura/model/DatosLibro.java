package com.cesarmeza.literalura.model;

import com.fasterxml.jackson.annotation.JsonAlias;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import java.util.List;

@JsonIgnoreProperties(ignoreUnknown = true)
public record DatosLibro(
        @JsonAlias("title") String titulo,
        @JsonAlias("authors") List<DatosAutor>autores,
        @JsonAlias("languages") List<String> idioma,
        @JsonAlias("download_count") int numeroDeDescargas
) {
    @Override
    public String toString() {
        return "---------------------------LIBRO---------------------------"+
                "\nTitulo: "+titulo+
                "\nAutor: "+autores.get(0).nombre()+
                "\nIdioma: "+idioma.get(0)+
                "\nDescargas: "+numeroDeDescargas+
                "\n-----------------------------------------------------------";
    }
}
