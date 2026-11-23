package com.arquitecturas.crud.service;

import com.arquitecturas.crud.model.Empleado;
import com.arquitecturas.crud.repository.EmpleadoRepository;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * Operaciones CRUD sobre los empleados.
 *
 * <p>Los controladores usan esta clase en lugar del repositorio para que las
 * reglas (por ejemplo, verificar que un empleado exista antes de modificarlo)
 * estén en un solo lugar.</p>
 */
@Service
@Transactional
public class EmpleadoService {

    private final EmpleadoRepository repository;

    /**
     * @param repository repositorio de empleados, inyectado por Spring
     */
    public EmpleadoService(EmpleadoRepository repository) {
        this.repository = repository;
    }

    /**
     * Obtiene todos los empleados ordenados por id.
     *
     * @return lista de empleados; vacía si no hay registros
     */
    @Transactional(readOnly = true)
    public List<Empleado> listar() {
        return repository.findAll(Sort.by("id"));
    }

    /**
     * Busca un empleado por su identificador.
     *
     * @param id identificador del empleado
     * @return el empleado encontrado
     * @throws EmpleadoNoEncontradoException si no existe
     */
    @Transactional(readOnly = true)
    public Empleado buscarPorId(Long id) {
        return repository.findById(id)
                .orElseThrow(() -> new EmpleadoNoEncontradoException(id));
    }

    /**
     * Registra un empleado nuevo.
     *
     * @param empleado datos capturados; se ignora cualquier id que traiga
     * @return el empleado guardado, con el id asignado por la base de datos
     */
    public Empleado crear(Empleado empleado) {
        empleado.setId(null);
        return repository.save(empleado);
    }

    /**
     * Actualiza el nombre y el salario de un empleado existente.
     *
     * @param id    identificador del empleado a modificar
     * @param datos nuevos valores
     * @return el empleado actualizado
     * @throws EmpleadoNoEncontradoException si no existe
     */
    public Empleado actualizar(Long id, Empleado datos) {
        Empleado empleado = buscarPorId(id);
        empleado.setNombre(datos.getNombre());
        empleado.setSalario(datos.getSalario());
        return repository.save(empleado);
    }

    /**
     * Elimina un empleado.
     *
     * @param id identificador del empleado
     * @throws EmpleadoNoEncontradoException si no existe
     */
    public void eliminar(Long id) {
        if (!repository.existsById(id)) {
            throw new EmpleadoNoEncontradoException(id);
        }
        repository.deleteById(id);
    }

    /**
     * Elimina varios empleados a la vez. Los ids que no existan se ignoran.
     *
     * @param ids identificadores de los empleados seleccionados
     */
    public void eliminarVarios(List<Long> ids) {
        repository.deleteAllById(ids);
    }
}
