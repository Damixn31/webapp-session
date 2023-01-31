package org.olmedo.apiservlet.webapp.session.service;

import org.olmedo.apiservlet.webapp.session.models.Producto;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

public class ProductoServiceImpl implements ProductoService{

    @Override
    public List<Producto> listar() {
        return Arrays.asList(new Producto(1L, "notebook", "computacion", 17500),
                new Producto(2L, "mesa escritorio", "oficina", 100000),
                new Producto(3L, "teclado mecanico", "computacion", 40000));
    }
    @Override
    public Optional<Producto> buscarProducto(String nombre) {
        return listar().stream().filter(p -> {
            if(nombre == null || nombre.isBlank()) {
                return false;
            }
            return p.getNombre().contains(nombre);
        }).findFirst();
    }

    @Override
    public Optional<Producto> porId(Long id) {
        return listar().stream().filter(p -> p.getId().equals(id)).findAny();
    }


}
