
package com.inventario;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

public class InventarioServiceTest {

    @Test
    void testRegistrarProducto() {
        InventarioService servicio =
            new InventarioService(new RepositorioMemoria());

        Producto producto =
            new Producto("P001", "Laptop", 10, 5000);

        servicio.registrarProducto(producto);

        assertTrue(servicio.buscarProducto("P001").isPresent());
    }

    @Test
    void testProductoDuplicado() {
        InventarioService servicio =
            new InventarioService(new RepositorioMemoria());

        Producto producto =
            new Producto("P001", "Laptop", 10, 5000);

        servicio.registrarProducto(producto);

        assertThrows(IllegalArgumentException.class, () -> {
            servicio.registrarProducto(producto);
        });
    }

    @Test
    void testListarProductos() {
        InventarioService servicio =
            new InventarioService(new RepositorioMemoria());

        servicio.registrarProducto(
            new Producto("P001", "Laptop", 10, 5000)
        );

        servicio.registrarProducto(
            new Producto("P002", "Mouse", 20, 100)
        );

        assertEquals(2, servicio.listarProductos().size());
    }

    @Test
    void testEliminarProducto() {
        InventarioService servicio =
            new InventarioService(new RepositorioMemoria());

        servicio.registrarProducto(
            new Producto("P001", "Laptop", 10, 5000)
        );

        servicio.eliminarProducto("P001");

        assertTrue(servicio.buscarProducto("P001").isEmpty());
    }

    @Test
    void testCalcularValorTotal() {
        InventarioService servicio =
            new InventarioService(new RepositorioMemoria());

        servicio.registrarProducto(
            new Producto("P001", "Laptop", 2, 5000)
        );

        servicio.registrarProducto(
            new Producto("P002", "Mouse", 5, 100)
        );

        assertEquals(10500, servicio.calcularValorTotal());
    }
    
@Test
void testRepositorioNulo() {
    assertThrows(IllegalArgumentException.class, () -> {
        new InventarioService(null);
    });
}
@Test
void testFalloControlado() {
    assertEquals(2, 2);
}
@Test
void testVentaExactaDiezUnidades() {
    InventarioService servicio =
        new InventarioService(new RepositorioMemoria());

    servicio.registrarProducto(
        new Producto("P010", "Teclado", 10, 150)
    );

    servicio.venderProducto("P010", 10);

    Producto producto = servicio.buscarProducto("P010")
        .orElseThrow();

    assertEquals(0, producto.existencias());
}

@Test
void testRegistroTercerProducto() {
    InventarioService servicio =
        new InventarioService(new RepositorioMemoria());

    servicio.registrarProducto(
        new Producto("P001", "Laptop", 10, 5000)
    );

    servicio.registrarProducto(
        new Producto("P002", "Mouse", 20, 100)
    );

    servicio.registrarProducto(
        new Producto("P003", "Teclado", 15, 250)
    );

    assertEquals(3, servicio.listarProductos().size());
    assertTrue(servicio.buscarProducto("P003").isPresent());
}

@Test
void testVentaNegativaSinCambios() {
    InventarioService servicio =
        new InventarioService(new RepositorioMemoria());

    servicio.registrarProducto(
        new Producto("P020", "Teclado", 10, 150)
    );

    IllegalArgumentException error = assertThrows(
        IllegalArgumentException.class,
        () -> servicio.venderProducto("P020", -5)
    );

    assertEquals(
        "La cantidad debe ser mayor que cero",
        error.getMessage()
    );

    Producto producto = servicio.buscarProducto("P020")
        .orElseThrow();

    assertEquals(10, producto.existencias());
}


}

