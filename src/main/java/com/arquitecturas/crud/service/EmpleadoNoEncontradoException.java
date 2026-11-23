package com.arquitecturas.crud.service;

/**
 * Se lanza cuando se intenta consultar, editar o eliminar un empleado que no
 * existe en la base de datos (por ejemplo, porque otro usuario ya lo borró).
 */
public class EmpleadoNoEncontradoException extends RuntimeException {

    /**
     * @param id identificador del empleado que no se encontró
     */
    public EmpleadoNoEncontradoException(Long id) {
        super("No existe el empleado con id " + id);
    }
}
