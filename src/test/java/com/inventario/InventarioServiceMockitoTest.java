package com.inventario;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import java.util.Optional;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.List;
import java.util.NoSuchElementException;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.argThat;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class InventarioServiceMockitoTest {

    @Mock
    private InventarioRepository repositorio;

    @InjectMocks
    private InventarioService servicio;

    private Producto teclado;

    @BeforeEach
    void preparar() {
        teclado = new Producto("P01", "Teclado", 10, 75.0);
    }
@Test
void registrarProductoNuevoGuarda() {

    // Simular que el producto no existe
    when(repositorio.buscarPorCodigo("P01"))
        .thenReturn(Optional.empty());

    // Registrar el producto
    servicio.registrarProducto(teclado);

    // Verificar que se buscó y guardó
    verify(repositorio).buscarPorCodigo("P01");
    verify(repositorio).guardar(teclado);
    verifyNoMoreInteractions(repositorio);
}

@Test
void registrarDuplicadoNoGuarda() {

    // Simular que el producto ya existe
    when(repositorio.buscarPorCodigo("P01"))
        .thenReturn(Optional.of(teclado));

    // Verificar que se rechaza el duplicado
    IllegalArgumentException error = assertThrows(
        IllegalArgumentException.class,
        () -> servicio.registrarProducto(teclado)
    );

    assertEquals("El producto ya existe", error.getMessage());

    // Comprobar que no se guardó
    verify(repositorio).buscarPorCodigo("P01");
    verify(repositorio, never()).guardar(any(Producto.class));
}

@Test
void registrarNuloNoConsultaRepositorio() {

    // Intentar registrar un producto nulo
    IllegalArgumentException error = assertThrows(
        IllegalArgumentException.class,
        () -> servicio.registrarProducto(null)
    );

    // Verificar el mensaje de error
    assertEquals("El producto es obligatorio", error.getMessage());

    // Comprobar que no se utilizó el repositorio
    verifyNoInteractions(repositorio);
}

@Test
void buscarRetornaDatoSimulado() {

    // Simular que el producto existe
    when(repositorio.buscarPorCodigo("P01"))
        .thenReturn(Optional.of(teclado));

    // Buscar el producto
    Optional<Producto> resultado = servicio.buscarProducto("P01");

    // Verificar que se encontró el producto
    assertTrue(resultado.isPresent());
    assertSame(teclado, resultado.get());

    // Comprobar que se consultó una sola vez
    verify(repositorio, times(1)).buscarPorCodigo("P01");
}

@Test
void disponibilidadDeProductoConExistencias() {

    // Simular un producto con 10 unidades
    when(repositorio.buscarPorCodigo("P01"))
        .thenReturn(Optional.of(teclado));

    // Consultar el producto
    Optional<Producto> resultado = servicio.buscarProducto("P01");

    // Verificar que tiene existencias
    assertTrue(resultado.isPresent());
    assertTrue(resultado.get().existencias() > 0);

    // Verificar la consulta al repositorio
    verify(repositorio).buscarPorCodigo("P01");
}

@Test
void buscarProductoInexistente() {

    // Simular que el producto no existe
    when(repositorio.buscarPorCodigo("P99"))
        .thenReturn(Optional.empty());

    // Buscar el producto
    Optional<Producto> resultado = servicio.buscarProducto("P99");

    // Verificar que el resultado esté vacío
    assertTrue(resultado.isEmpty());

    // Comprobar que se realizó la consulta
    verify(repositorio, times(1)).buscarPorCodigo("P99");
}

@Test
void listarProductosRetornaListaSimulada() {

    // Simular una lista de productos
    Producto mouse = new Producto("P02", "Mouse", 5, 50.0);

    when(repositorio.listarTodos())
        .thenReturn(List.of(teclado, mouse));

    // Consultar los productos
    List<Producto> resultado = servicio.listarProductos();

    // Verificar los resultados
    assertEquals(2, resultado.size());
    assertEquals("P01", resultado.get(0).codigo());
    assertEquals("P02", resultado.get(1).codigo());

    // Verificar la consulta
    verify(repositorio, times(1)).listarTodos();
}
@Test
void eliminarProductoExistente() {

    // Simular que el producto existe
    when(repositorio.buscarPorCodigo("P01"))
        .thenReturn(Optional.of(teclado));

    // Eliminar el producto
    servicio.eliminarProducto("P01");

    // Verificar que se buscó y eliminó
    verify(repositorio).buscarPorCodigo("P01");
    verify(repositorio).eliminar("P01");

    // Comprobar que no hubo otras operaciones
    verifyNoMoreInteractions(repositorio);
}

@Test
void eliminarProductoInexistente() {

    // Simular que el producto no existe
    when(repositorio.buscarPorCodigo("P99"))
        .thenReturn(Optional.empty());

    // Verificar que se produzca una excepción
    IllegalArgumentException error = assertThrows(
        IllegalArgumentException.class,
        () -> servicio.eliminarProducto("P99")
    );

    // Comprobar el mensaje
    assertEquals("El producto no existe", error.getMessage());

    // Verificar que no se eliminó ningún producto
    verify(repositorio).buscarPorCodigo("P99");
    verify(repositorio, never()).eliminar(anyString());
}

@Test
void calcularValorTotalConMockito() {

    // Crear un segundo producto
    Producto mouse = new Producto("P02", "Mouse", 5, 50.0);

    // Simular los productos del inventario
    when(repositorio.listarTodos())
        .thenReturn(List.of(teclado, mouse));

    // Calcular el valor total
    double total = servicio.calcularValorTotal();

    // Teclado: 10 * 75 = 750
    // Mouse: 5 * 50 = 250
    assertEquals(1000.0, total, 0.001);

    // Verificar que se consultó el repositorio
    verify(repositorio, times(1)).listarTodos();
}

@Test
void venderProductoConExistenciasSuficientes() {

    // Simular un producto con 10 unidades
    when(repositorio.buscarPorCodigo("P01"))
        .thenReturn(Optional.of(teclado));

    // Vender 3 unidades
    servicio.venderProducto("P01", 3);

    // Verificar que se guardaron 7 unidades
    verify(repositorio).guardar(argThat(producto ->
        producto.codigo().equals("P01") &&
        producto.existencias() == 7 &&
        producto.precio() == 75.0
    ));

    // Verificar que se consultó el producto
    verify(repositorio).buscarPorCodigo("P01");
}

@Test
void venderProductoSinExistenciasSuficientes() {

    // Simular un producto con 10 unidades
    when(repositorio.buscarPorCodigo("P01"))
        .thenReturn(Optional.of(teclado));

    // Intentar vender 15 unidades
    IllegalArgumentException error = assertThrows(
        IllegalArgumentException.class,
        () -> servicio.venderProducto("P01", 15)
    );

    // Verificar el mensaje de error
    assertEquals("Existencias insuficientes", error.getMessage());

    // Comprobar que se consultó el producto
    verify(repositorio).buscarPorCodigo("P01");

    // Verificar que no se guardaron cambios
    verify(repositorio, never()).guardar(any(Producto.class));
}

@Test
void venderProductoConCantidadNegativa() {

    // Intentar vender una cantidad negativa
    IllegalArgumentException error = assertThrows(
        IllegalArgumentException.class,
        () -> servicio.venderProducto("P01", -5)
    );

    // Verificar el mensaje
    assertEquals(
        "La cantidad debe ser mayor que cero",
        error.getMessage()
    );

    // Comprobar que no se utilizó el repositorio
    verifyNoInteractions(repositorio);
}

@Test
void venderProductoConCantidadCero() {

    // Intentar vender cero unidades
    IllegalArgumentException error = assertThrows(
        IllegalArgumentException.class,
        () -> servicio.venderProducto("P01", 0)
    );

    // Comprobar el mensaje de error
    assertEquals(
        "La cantidad debe ser mayor que cero",
        error.getMessage()
    );

    // Verificar que no se utilizó el repositorio
    verifyNoInteractions(repositorio);
}

@Test
void buscarProductoSinModificarInventario() {

    // Simular que el producto existe
    when(repositorio.buscarPorCodigo("P01"))
        .thenReturn(Optional.of(teclado));

    // Buscar el producto
    Optional<Producto> resultado = servicio.buscarProducto("P01");

    // Verificar que se encontró correctamente
    assertTrue(resultado.isPresent());
    assertEquals("P01", resultado.get().codigo());

    // Comprobar que solo se realizó la búsqueda
    verify(repositorio, times(1)).buscarPorCodigo("P01");

    // Verificar que no se guardó ni eliminó nada
    verify(repositorio, never()).guardar(any(Producto.class));
    verify(repositorio, never()).eliminar(anyString());
}

@Test
void venderUnaUnidadDejaNueveExistencias() {
    // Simular un producto con 10 unidades
    when(repositorio.buscarPorCodigo("P01"))
        .thenReturn(Optional.of(teclado));

    // Vender una unidad
    servicio.venderProducto("P01", 1);

    // Verificar que quedaron 9 unidades
    verify(repositorio).guardar(argThat(producto ->
        producto.codigo().equals("P01") &&
        producto.existencias() == 9
    ));

    // Comprobar que se consultó el producto
    verify(repositorio).buscarPorCodigo("P01");
}


@Test
void listarProductosDevuelveListaVacia() {
    // Simular un inventario sin productos
    when(repositorio.listarTodos())
        .thenReturn(List.of());

    // Consultar los productos
    List<Producto> resultado = servicio.listarProductos();

    // Verificar que la lista esté vacía
    assertNotNull(resultado);
    assertTrue(resultado.isEmpty());
    assertEquals(0, resultado.size());

    // Verificar que se consultó el repositorio
    verify(repositorio, times(1)).listarTodos();

    // Comprobar que no hubo otras operaciones
    verifyNoMoreInteractions(repositorio);
}

@Test
void calcularValorInventarioVacioEsCero() {
    // Simular un inventario sin productos
    when(repositorio.listarTodos())
        .thenReturn(List.of());

    // Calcular el valor total
    double total = servicio.calcularValorTotal();

    // Verificar que el valor sea cero
    assertEquals(0.0, total, 0.001);

    // Comprobar que se consultó el repositorio
    verify(repositorio, times(1)).listarTodos();

    // Verificar que no hubo otras operaciones
    verifyNoMoreInteractions(repositorio);
}


}