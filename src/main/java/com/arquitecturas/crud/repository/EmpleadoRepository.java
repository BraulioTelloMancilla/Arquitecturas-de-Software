package com.arquitecturas.crud.repository;

import com.arquitecturas.crud.model.Empleado;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

/**
 * Acceso a la tabla {@code empleados}.
 *
 * <p>Spring Data JPA genera la implementación en tiempo de ejecución. Las
 * operaciones CRUD se heredan de {@link JpaRepository}; las que usa la
 * aplicación son:</p>
 * <ul>
 *   <li>{@code findAll()}: listar todos los empleados.</li>
 *   <li>{@code findById(id)}: buscar un empleado para editarlo.</li>
 *   <li>{@code save(empleado)}: crear o actualizar un empleado.</li>
 *   <li>{@code deleteById(id)}: eliminar un empleado.</li>
 *   <li>{@code deleteAllById(ids)}: eliminar los empleados seleccionados.</li>
 * </ul>
 */
@Repository
public interface EmpleadoRepository extends JpaRepository<Empleado, Long> {
}
