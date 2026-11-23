package com.arquitecturas.crud;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

/**
 * Punto de entrada de la aplicación CRUD de empleados.
 *
 * <p>Arranca el contexto de Spring Boot, que configura automáticamente el
 * servidor web embebido, la conexión a MySQL y el motor de plantillas
 * Thymeleaf a partir de {@code application.properties}.</p>
 *
 * @author Ivan Alejandro Chi Polanco
 * @author Omar Sebastian Chim Canche
 * @author Braulio Tello Mancilla
 */
@SpringBootApplication
public class CrudApplication {

    /**
     * Inicia la aplicación.
     *
     * @param args argumentos de línea de comandos
     */
    public static void main(String[] args) {
        SpringApplication.run(CrudApplication.class, args);
    }
}
