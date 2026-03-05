package com.cesarmeza.literalura.model;

import com.fasterxml.jackson.annotation.JsonAlias;


public record DatosAutor(
        @JsonAlias("name") String nombre,
        @JsonAlias("birth_year") int fechaDeNacimiento,
        @JsonAlias("death_year") int fechaDeMuerte
) {
    @Override
    public String toString() {
        return "---------------------------AUTOR---------------------------" +
                "\nNombre: " + nombre +
                "\nAño de Nacimiento: " + fechaDeNacimiento +
                "\nAño de Muerte: " + fechaDeMuerte +
                "\n-----------------------------------------------------------";
    }
}
