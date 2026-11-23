package com.arquitecturas.crud.controller;

import com.arquitecturas.crud.model.Empleado;
import com.arquitecturas.crud.service.EmpleadoNoEncontradoException;
import com.arquitecturas.crud.service.EmpleadoService;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.validation.Valid;
import org.springframework.beans.propertyeditors.StringTrimmerEditor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.InitBinder;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import org.springframework.web.servlet.support.RequestContextUtils;

import java.util.List;

/**
 * Controlador web del CRUD de empleados.
 *
 * <p>Rutas:</p>
 * <table>
 *   <caption>Rutas del CRUD</caption>
 *   <tr><th>Método</th><th>Ruta</th><th>Acción</th></tr>
 *   <tr><td>GET</td><td>{@code /}</td><td>Listado de empleados</td></tr>
 *   <tr><td>GET</td><td>{@code /empleados/nuevo}</td><td>Formulario de alta</td></tr>
 *   <tr><td>POST</td><td>{@code /empleados}</td><td>Guardar empleado nuevo</td></tr>
 *   <tr><td>GET</td><td>{@code /empleados/{id}/editar}</td><td>Formulario de edición</td></tr>
 *   <tr><td>POST</td><td>{@code /empleados/{id}}</td><td>Guardar cambios</td></tr>
 *   <tr><td>POST</td><td>{@code /empleados/{id}/eliminar}</td><td>Eliminar un empleado</td></tr>
 *   <tr><td>POST</td><td>{@code /empleados/eliminar-multiple}</td><td>Eliminar los seleccionados</td></tr>
 * </table>
 *
 * <p>Después de cada operación que modifica datos se redirige al listado
 * (patrón Post/Redirect/Get) con un mensaje flash en {@code mensaje} o
 * {@code error}.</p>
 */
@Controller
public class EmpleadoController {

    private static final String VISTA_LISTADO = "index";
    private static final String VISTA_FORMULARIO = "form";
    private static final String REDIRECT_LISTADO = "redirect:/";

    private final EmpleadoService service;

    /**
     * @param service servicio de empleados, inyectado por Spring
     */
    public EmpleadoController(EmpleadoService service) {
        this.service = service;
    }

    /**
     * Quita los espacios al inicio y al final de los textos capturados y
     * convierte los vacíos en {@code null}, para que {@code @NotBlank} los rechace.
     *
     * @param binder enlazador de datos del formulario
     */
    @InitBinder
    void initBinder(WebDataBinder binder) {
        binder.registerCustomEditor(String.class, new StringTrimmerEditor(true));
    }

    /**
     * Muestra el listado de empleados; es la página inicial de la aplicación.
     *
     * @param model modelo de la vista; recibe {@code empleados}
     * @return vista del listado
     */
    @GetMapping("/")
    public String listar(Model model) {
        model.addAttribute("empleados", service.listar());
        return VISTA_LISTADO;
    }

    /**
     * Muestra el formulario vacío para registrar un empleado.
     *
     * @param model modelo de la vista; recibe {@code empleado}
     * @return vista del formulario
     */
    @GetMapping("/empleados/nuevo")
    public String nuevo(Model model) {
        model.addAttribute("empleado", new Empleado());
        return VISTA_FORMULARIO;
    }

    /**
     * Guarda un empleado nuevo. Si los datos no son válidos, vuelve a mostrar
     * el formulario con los errores.
     *
     * @param empleado           datos capturados en el formulario
     * @param result             resultado de la validación
     * @param redirectAttributes mensajes flash para el listado
     * @return redirección al listado, o la vista del formulario si hay errores
     */
    @PostMapping("/empleados")
    public String crear(@Valid @ModelAttribute("empleado") Empleado empleado,
                        BindingResult result,
                        RedirectAttributes redirectAttributes) {
        if (result.hasErrors()) {
            return VISTA_FORMULARIO;
        }
        service.crear(empleado);
        redirectAttributes.addFlashAttribute("mensaje", "Empleado registrado correctamente");
        return REDIRECT_LISTADO;
    }

    /**
     * Muestra el formulario con los datos actuales de un empleado.
     *
     * @param id    identificador del empleado
     * @param model modelo de la vista; recibe {@code empleado}
     * @return vista del formulario
     */
    @GetMapping("/empleados/{id}/editar")
    public String editar(@PathVariable Long id, Model model) {
        model.addAttribute("empleado", service.buscarPorId(id));
        return VISTA_FORMULARIO;
    }

    /**
     * Guarda los cambios de un empleado. Si los datos no son válidos, vuelve a
     * mostrar el formulario con los errores.
     *
     * @param id                 identificador del empleado
     * @param empleado           datos capturados en el formulario
     * @param result             resultado de la validación
     * @param redirectAttributes mensajes flash para el listado
     * @return redirección al listado, o la vista del formulario si hay errores
     */
    @PostMapping("/empleados/{id}")
    public String actualizar(@PathVariable Long id,
                             @Valid @ModelAttribute("empleado") Empleado empleado,
                             BindingResult result,
                             RedirectAttributes redirectAttributes) {
        if (result.hasErrors()) {
            empleado.setId(id);
            return VISTA_FORMULARIO;
        }
        service.actualizar(id, empleado);
        redirectAttributes.addFlashAttribute("mensaje", "Empleado actualizado correctamente");
        return REDIRECT_LISTADO;
    }

    /**
     * Elimina un empleado.
     *
     * @param id                 identificador del empleado
     * @param redirectAttributes mensajes flash para el listado
     * @return redirección al listado
     */
    @PostMapping("/empleados/{id}/eliminar")
    public String eliminar(@PathVariable Long id, RedirectAttributes redirectAttributes) {
        service.eliminar(id);
        redirectAttributes.addFlashAttribute("mensaje", "Empleado eliminado correctamente");
        return REDIRECT_LISTADO;
    }

    /**
     * Elimina los empleados marcados con la casilla de verificación.
     *
     * @param ids                valores de las casillas marcadas (parámetro {@code ids});
     *                           {@code null} si no se marcó ninguna
     * @param redirectAttributes mensajes flash para el listado
     * @return redirección al listado
     */
    @PostMapping("/empleados/eliminar-multiple")
    public String eliminarVarios(@RequestParam(name = "ids", required = false) List<Long> ids,
                                 RedirectAttributes redirectAttributes) {
        if (ids == null || ids.isEmpty()) {
            redirectAttributes.addFlashAttribute("error", "Selecciona al menos un empleado para eliminar");
            return REDIRECT_LISTADO;
        }
        service.eliminarVarios(ids);
        redirectAttributes.addFlashAttribute("mensaje", ids.size() + " empleado(s) eliminado(s) correctamente");
        return REDIRECT_LISTADO;
    }

    /**
     * Si el empleado solicitado no existe, regresa al listado con un mensaje de error.
     *
     * @param ex      excepción lanzada por el servicio
     * @param request petición actual, usada para guardar el mensaje flash
     * @return redirección al listado
     */
    @ExceptionHandler(EmpleadoNoEncontradoException.class)
    public String empleadoNoEncontrado(EmpleadoNoEncontradoException ex, HttpServletRequest request) {
        RequestContextUtils.getOutputFlashMap(request).put("error", ex.getMessage());
        return REDIRECT_LISTADO;
    }
}
