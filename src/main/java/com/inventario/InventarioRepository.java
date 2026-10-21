
package com.inventario;

import java.util.List;
import java.util.Optional;

public interface InventarioRepository {

    void guardar(Producto producto);

    Optional<Producto> buscarPorCodigo(String codigo);

    List<Producto> listarTodos();

    void eliminar(String codigo);
}
