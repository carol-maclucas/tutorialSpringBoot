package com.ccsw.tutorial.category.model;

import jakarta.persistence.*;

/**
 * @author ccsw
 *
 */
//Crear nuestra -> ENTITY -> dentro del pacakage-model de CADA funcionalidad
// Entity: le digo "esta clase representa una base de datos" ^^^^ "TABLE Category", sino no podremos hacer QUERIES
// Lo más fácil posible
@Entity

@Table(name = "category")
public class Category {
    // PRIMERY KEY - Debe tener esta estructura para evitar COMPUESTOS y DUPLICADOS
    @Id
    // Los genera solos
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    // El nombre la columna en la tabla
    @Column(name = "id", nullable = false)
    private Long id;

    @Column(name = "name", nullable = false)
    private String name;

    /**
     * @return id
     */
    public Long getId() {
        return this.id;
    }

    /**
     * @param id new value of {@link #getId}.
     */
    public void setId(Long id) {
        this.id = id;
    }

    /**
     * @return name
     */
    public String getName() {
        return this.name;
    }

    /**
     * @param name new value of {@link #getName}.
     */
    public void setName(String name) {
        this.name = name;
    }

}
