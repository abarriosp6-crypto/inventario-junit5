
package com.inventario;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.RepeatedTest;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;
import org.junit.jupiter.api.Nested;

public class ProductoTest {

    @Test
    void testCrearProducto() {
        Producto producto = new Producto("P001", "Laptop", 10, 5000);

        assertEquals("P001", producto.codigo());
        assertEquals("Laptop", producto.nombre());
        assertEquals(10, producto.existencias());
        assertEquals(5000, producto.precio());
    }

    @Test
    void testProductoSinCodigo() {
        assertThrows(IllegalArgumentException.class, () -> {
            new Producto("", "Laptop", 10, 5000);
        });
    }

    @Test
    void testProductoSinNombre() {
        assertThrows(IllegalArgumentException.class, () -> {
            new Producto("P001", "", 10, 5000);
        });
    }

    @Test
    void testExistenciasNegativas() {
        assertThrows(IllegalArgumentException.class, () -> {
            new Producto("P001", "Laptop", -5, 5000);
        });
    }



    @Test
    void testCalcularValorInventario() {
        Producto producto = new Producto("P001", "Laptop", 4, 5000);

        double valor = producto.existencias() * producto.precio();

        assertEquals(20000, valor);
    }

@Test
void testCodigoVacio() {
    IllegalArgumentException error = assertThrows(
        IllegalArgumentException.class,
        () -> new Producto("", "Laptop", 10, 5000)
    );

    assertEquals("Código obligatorio", error.getMessage());
}

@Test
void testPrecioNegativo() {
    IllegalArgumentException error = assertThrows(
        IllegalArgumentException.class,
        () -> new Producto("P003", "Teclado", 5, -100)
    );

    assertEquals("Precio inválido", error.getMessage());
}
@RepeatedTest(3)
void testProductoRepetido() {
    Producto producto = new Producto(
        "P001", "Laptop", 10, 5000
    );

    assertEquals("P001", producto.codigo());
    assertEquals(10, producto.existencias());
}

@ParameterizedTest
@ValueSource(ints = {0, 5, 10, 20})
void testExistenciasValidas(int cantidad) {
    Producto producto = new Producto(
        "P001", "Laptop", cantidad, 5000
    );

    assertEquals(cantidad, producto.existencias());
}

@Nested
class ValidacionesProducto {

    @Test
    void testNombreVacio() {
        assertThrows(
            IllegalArgumentException.class,
            () -> new Producto("P001", "", 10, 5000)
        );
    }

    @Test
    void testExistenciasNegativas() {
        assertThrows(
            IllegalArgumentException.class,
            () -> new Producto("P002", "Mouse", -5, 100)
        );
    }

    @Test
    void testProductoValido() {
        Producto producto =
            new Producto("P003", "Teclado", 10, 250);

        assertEquals("Teclado", producto.nombre());
        assertEquals(10, producto.existencias());
    }
}
}
