
package com.inventario;

/**
 * Producto del catálogo de inventario.
 * Record inmutable de Java 21.
 */
public record Producto(
        String codigo,
        String nombre,
        int existencias,
        double precio) {

    public Producto {
        if (codigo == null || codigo.isBlank()) {
            throw new IllegalArgumentException("Código obligatorio");
        }

        if (nombre == null || nombre.isBlank()) {
            throw new IllegalArgumentException("Nombre obligatorio");
        }

        if (existencias < 0) {
            throw new IllegalArgumentException("Existencias negativas");
        }

        if (!Double.isFinite(precio) || precio < 0) {
            throw new IllegalArgumentException("Precio inválido");
        }
    }

}
