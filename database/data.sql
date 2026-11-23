-- =============================================================
-- Datos de ejemplo para la tabla empleados.
-- Ejecutar después de schema.sql.
-- =============================================================

USE empleados_db;

INSERT INTO empleados (nombre, salario) VALUES
    ('Ana López Martínez',      18500.00),
    ('Carlos Pérez Gómez',      22000.50),
    ('María Fernanda Ruiz',     15750.00),
    ('José Luis Hernández',     30000.00),
    ('Lucía Ramírez Torres',    12800.75),
    ('Roberto Sánchez Díaz',    25400.00);
