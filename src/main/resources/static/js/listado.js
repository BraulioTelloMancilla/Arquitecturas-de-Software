const seleccionarTodos = document.getElementById("seleccionar-todos");
const casillasEmpleados = document.querySelectorAll('input[name="ids"][form="form-eliminar"]');

// Actualiza la casilla del encabezado según la selección de los empleados.
function actualizarSeleccionTodos() {
    const seleccionadas = Array.from(casillasEmpleados).filter((casilla) => casilla.checked).length;
    seleccionarTodos.checked = casillasEmpleados.length > 0 && seleccionadas === casillasEmpleados.length;
    seleccionarTodos.indeterminate = seleccionadas > 0 && seleccionadas < casillasEmpleados.length;
}

// Propaga la selección del encabezado a todas las filas.
function actualizarCasillasEmpleados() {
    casillasEmpleados.forEach((casilla) => {
        casilla.checked = seleccionarTodos.checked;
    });
    actualizarSeleccionTodos();
}

seleccionarTodos.addEventListener("change", actualizarCasillasEmpleados);
casillasEmpleados.forEach((casilla) => {
    casilla.addEventListener("change", actualizarSeleccionTodos);
});
