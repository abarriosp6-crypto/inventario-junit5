
package com.inventario;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.HashMap;
import java.util.Map;

public class RepositorioMemoria implements InventarioRepository {

    private final Map<String, Producto> productos = new HashMap<>();

    @Override
    public void guardar(Producto producto) {
        productos.put(producto.codigo(), producto);
    }

    @Override
    public Optional<Producto> buscarPorCodigo(String codigo) {
        return Optional.ofNullable(productos.get(codigo));
    }

    @Override
    public List<Producto> listarTodos() {
        return new ArrayList<>(productos.values());
    }

    @Override
    public void eliminar(String codigo) {
        productos.remove(codigo);
    }
}
