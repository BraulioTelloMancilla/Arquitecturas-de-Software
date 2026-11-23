package com.arquitecturas.crud.model;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import jakarta.validation.constraints.Digits;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.PositiveOrZero;
import jakarta.validation.constraints.Size;

import java.math.BigDecimal;

/**
 * Empleado registrado en el sistema.
 *
 * <p>Se mapea a la tabla {@code empleados} definida en {@code database/schema.sql}.
 * Las anotaciones de validación se aplican a los datos capturados en los
 * formularios antes de guardarlos.</p>
 */
@Entity
@Table(name = "empleados")
public class Empleado {

    /** Identificador único, generado por la base de datos (AUTO_INCREMENT). */
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    /** Nombre completo del empleado. */
    @NotBlank(message = "El nombre es obligatorio")
    @Size(max = 100, message = "El nombre no puede tener más de 100 caracteres")
    @Column(nullable = false, length = 100)
    private String nombre;

    /** Salario del empleado; admite hasta 8 enteros y 2 decimales. */
    @NotNull(message = "El salario es obligatorio")
    @PositiveOrZero(message = "El salario no puede ser negativo")
    @Digits(integer = 8, fraction = 2, message = "El salario admite hasta 8 enteros y 2 decimales")
    @Column(nullable = false, precision = 10, scale = 2)
    private BigDecimal salario;

    /** Constructor vacío requerido por JPA y por el enlace de formularios de Spring. */
    public Empleado() {
    }

    /**
     * Crea un empleado nuevo, sin identificador.
     *
     * @param nombre  nombre completo del empleado
     * @param salario salario del empleado
     */
    public Empleado(String nombre, BigDecimal salario) {
        this.nombre = nombre;
        this.salario = salario;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public BigDecimal getSalario() {
        return salario;
    }

    public void setSalario(BigDecimal salario) {
        this.salario = salario;
    }

    @Override
    public String toString() {
        return "Empleado{id=" + id + ", nombre='" + nombre + "', salario=" + salario + "}";
    }
}
