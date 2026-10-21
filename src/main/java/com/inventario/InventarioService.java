
package com.inventario;

import java.util.List;
import java.util.Optional;

public class InventarioService {

    private final InventarioRepository repositorio;

    public InventarioService(InventarioRepository repositorio) {
        if (repositorio == null) {
            throw new IllegalArgumentException(
                "El repositorio es obligatorio"
            );
        }
        this.repositorio = repositorio;
    }

    public void registrarProducto(Producto producto) {
        if (producto == null) {
            throw new IllegalArgumentException(
                "El producto es obligatorio"
            );
        }

        if (repositorio.buscarPorCodigo(producto.codigo()).isPresent()) {
            throw new IllegalArgumentException(
                "El producto ya existe"
            );
        }

        repositorio.guardar(producto);
    }

    public Optional<Producto> buscarProducto(String codigo) {
        return repositorio.buscarPorCodigo(codigo);
    }

    public List<Producto> listarProductos() {
        return repositorio.listarTodos();
    }

    public void eliminarProducto(String codigo) {
        if (repositorio.buscarPorCodigo(codigo).isEmpty()) {
            throw new IllegalArgumentException(
                "El producto no existe"
            );
        }

        repositorio.eliminar(codigo);
    }

    public double calcularValorTotal() {
        return repositorio.listarTodos()
                .stream()
                .mapToDouble(p -> p.existencias() * p.precio())
                .sum();
    }
    public void venderProducto(String codigo, int cantidad) {
    if (cantidad <= 0) {
        throw new IllegalArgumentException(
            "La cantidad debe ser mayor que cero"
        );
    }

    Producto producto = buscarProducto(codigo)
        .orElseThrow(() -> new IllegalArgumentException(
            "Producto no encontrado"
        ));

    if (cantidad > producto.existencias()) {
        throw new IllegalArgumentException(
            "Existencias insuficientes"
        );
    }

    Producto actualizado = new Producto(
        producto.codigo(),
        producto.nombre(),
        producto.existencias() - cantidad,
        producto.precio()
    );

    repositorio.guardar(actualizado);
}
}
