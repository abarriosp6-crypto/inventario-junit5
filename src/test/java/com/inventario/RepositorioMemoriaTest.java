
package com.inventario;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

public class RepositorioMemoriaTest {

    @Test
    void testGuardarProducto() {
        RepositorioMemoria repo = new RepositorioMemoria();
        Producto producto = new Producto("P001", "Laptop", 10, 5000);

        repo.guardar(producto);

        assertTrue(repo.buscarPorCodigo("P001").isPresent());
    }

    @Test
    void testBuscarProducto() {
        RepositorioMemoria repo = new RepositorioMemoria();
        Producto producto = new Producto("P002", "Mouse", 20, 100);

        repo.guardar(producto);

        assertEquals("Mouse",
            repo.buscarPorCodigo("P002").orElseThrow().nombre());
    }

    @Test
    void testListarProductos() {
        RepositorioMemoria repo = new RepositorioMemoria();

        repo.guardar(new Producto("P001", "Laptop", 10, 5000));
        repo.guardar(new Producto("P002", "Mouse", 20, 100));

        assertEquals(2, repo.listarTodos().size());
    }

    @Test
    void testEliminarProducto() {
        RepositorioMemoria repo = new RepositorioMemoria();
        repo.guardar(new Producto("P003", "Teclado", 5, 250));

        repo.eliminar("P003");

        assertTrue(repo.buscarPorCodigo("P003").isEmpty());
    }
}
