package com.cesarmeza.literalura.principal;

import com.cesarmeza.literalura.model.*;
import com.cesarmeza.literalura.repository.AutorRepository;
import com.cesarmeza.literalura.repository.LibroRepository;
import com.cesarmeza.literalura.service.ConsumoApi;
import com.cesarmeza.literalura.service.ConvierteDatos;

import java.util.List;
import java.util.Scanner;

public class Principal {
    private Scanner sc = new Scanner(System.in);
    private ConsumoApi consumoApi = new ConsumoApi();
    private ConvierteDatos conversor = new ConvierteDatos();
    private final String URL_BASE = "https://gutendex.com//books/?search=";
    private LibroRepository repositorioLibros;
    private AutorRepository repositorioAutores;
    private List<Libro> libros;
    private List<Autor> autores;

    public Principal(LibroRepository repositorioLibros, AutorRepository repositorioAutores) {
        this.repositorioLibros = repositorioLibros;
        this.repositorioAutores = repositorioAutores;
    }

    public void muestraElMenu() {
        var opcion = -1;
        while (opcion != 0) {
            var menu = """
                    -----------------------------------------Menú Principal de Operaciones-----------------------------------------
                    1 - Buscar libros por titulo 
                    2 - Listar todos los libros registrados
                    3 - Listar todos los autores registrados
                    4 - Listar autores vivos en un determinado año
                    5 - Listar libros por idioma
                    0 - Salir
                    """;
            System.out.println(menu);
            opcion = sc.nextInt();
            sc.nextLine();

            switch (opcion) {
                case 1:
                    buscarLibroWeb();
                    break;
                case 2:
                    mostrarLibrosBuscados();
                    break;
                case 3:
                    mostrarTodosLosAutores();
                    break;
                case 4:
                    autorVivoEnAño();
                    break;
                case 5:
                    clasificarPorIdioma();
                    break;
                case 0:
                    System.out.println("Cerrando la aplicación...");
                    break;
                default:
                    System.out.println("Opción inválida");
            }
        }

    }

    private DatosLibro getDatosLibro() {
        System.out.println("Escribe el nombre del libro para agregar: ");
        var nombreLibro = sc.nextLine();
        var json = consumoApi.obtenerDatos(URL_BASE + nombreLibro.replace(" ", "+"));
        DatosRespuesta respuesta = conversor.obtenerDatos(json, DatosRespuesta.class);
        if (respuesta.results().isEmpty()) {
            System.out.println("Libro no encontrado.");
            return null;
        }
        DatosLibro libro = respuesta.results().get(0);
        DatosAutor autor = libro.autores().get(0);
        System.out.println("LIBRO ENCONTRADO!");
        System.out.println(libro);
        return libro;
    }

    /*Al momento de buscar el libro, primero agrego al autor a su tabla, a partir de aqui asigno al libro el autor. Si ya existe el autor lo extraigo por nombre, si no creo uno nuevo*/
    private void buscarLibroWeb() {
        DatosLibro datos = getDatosLibro();
        if (datos == null) return;
        DatosAutor datosAutor = datos.autores().get(0);
        Autor autor = repositorioAutores
                .findByNombre(datosAutor.nombre())
                .orElseGet(() -> {
                    Autor nuevoAutor = new Autor(datosAutor);
                    return repositorioAutores.save(nuevoAutor);
                });

        Libro libro = new Libro(datos);
        libro.setAutor(autor);

        repositorioLibros.save(libro);
    }

    private void mostrarLibrosBuscados() {
        libros = repositorioLibros.findAll();
        libros.stream()
                .forEach(System.out::println);
    }

    private void mostrarTodosLosAutores() {
        autores = repositorioAutores.findAll();
        autores.stream()
                .forEach(System.out::println);
    }

    private void autorVivoEnAño() {
        System.out.println("Ingrese el año vivo del autor que desea buscar: ");
        var año = sc.nextInt();
        sc.nextLine();
        List<Autor> autoresVivos = repositorioAutores.obtenerAutoresVivosEnLapso(año);
        autoresVivos.stream()
                .forEach(System.out::println);
    }

    public void clasificarPorIdioma() {
        int opcion;
        String idioma = "";
        System.out.println("""
                -----------------------------------------Seleccione el idioma-----------------------------------------
                1 - es
                2 - en
                3 - fr
                4 - pt
                0 - Cancelar
                """);
        opcion = sc.nextInt();
        sc.nextLine();
        switch (opcion) {
            case 1:
                idioma = "es";
                break;
            case 2:
                idioma = "en";
                break;
            case 3:
                idioma = "fr";
                break;
            case 4:
                idioma = "pt";
                break;
            case 0:
                System.out.println("Cancelando operación...");
                return;
            default:
                System.out.println("Opción inválida");
                return;
        }
        List<Libro> librosPorIdioma = repositorioLibros.obtenerLibrosPorIdioma(idioma);
        librosPorIdioma.forEach(System.out::println);
    }
}
