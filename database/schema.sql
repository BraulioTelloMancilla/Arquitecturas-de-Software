-- =============================================================
-- Esquema de la base de datos de la aplicación CRUD de empleados.
-- Ejecutar una sola vez antes de iniciar la aplicación.
-- Advertencia: borra la tabla si ya existe.
-- =============================================================

CREATE DATABASE IF NOT EXISTS empleados_db
    CHARACTER SET utf8mb4
    COLLATE utf8mb4_unicode_ci;

USE empleados_db;

DROP TABLE IF EXISTS empleados;

CREATE TABLE empleados (
    id      BIGINT        NOT NULL AUTO_INCREMENT,  -- identificador único
    nombre  VARCHAR(100)  NOT NULL,                 -- campo de texto
    salario DECIMAL(10,2) NOT NULL,                 -- campo numérico
    PRIMARY KEY (id),
    CONSTRAINT chk_salario_no_negativo CHECK (salario >= 0)
);
