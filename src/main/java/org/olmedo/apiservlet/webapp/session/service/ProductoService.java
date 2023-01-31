package org.olmedo.apiservlet.webapp.session.service;

import org.olmedo.apiservlet.webapp.session.models.Producto;

import java.util.List;
import java.util.Optional;

public interface ProductoService {
    List<Producto> listar();
    Optional<Producto> buscarProducto(String nombre);
    Optional<Producto> porId(Long id);
}
