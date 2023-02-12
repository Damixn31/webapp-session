package org.olmedo.apiservlet.webapp.session.service;

import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import org.olmedo.apiservlet.webapp.session.configs.ProductoServicePrincipal;
import org.olmedo.apiservlet.webapp.session.configs.Service;
import org.olmedo.apiservlet.webapp.session.interceptors.Logging;
import org.olmedo.apiservlet.webapp.session.models.Categoria;
import org.olmedo.apiservlet.webapp.session.models.Producto;
import org.olmedo.apiservlet.webapp.session.repositories.CrudRepository;

import java.sql.SQLException;
import java.util.List;
import java.util.Optional;

//cada cliente que se conecta va a tener su propia coneccion y toda las transacciones no se van a ver afectadas
@Service
@ProductoServicePrincipal // el dia de mania que cambie la implementacion siemplemente movemos esta implementacion en la otra clase que vamos a usar
public class ProductoServiceJdbcImpl implements ProductoService{
    @Inject
    private CrudRepository<Producto> repositoryJdbc; //le pasamos Repository<Producto> para que sea del generico
    @Inject
    private CrudRepository<Categoria> repositoryCategoriaJdbc; //le pasamos Repository<Categoria> para que sea del generico

    @Override
    public List<Producto> listar() {
        try {
            return repositoryJdbc.listar();
        } catch (SQLException throwables) {
            throw new ServiceJdbcException(throwables.getMessage(), throwables.getCause()); // esto viene de serviceJdbcException
        }
    }

    @Override
    public Optional<Producto> buscarProducto(String nombre) {
        return Optional.empty();
    }

    @Override
    public Optional<Producto> porId(Long id) {
        try {
            return Optional.ofNullable(repositoryJdbc.porId(id));
        } catch (SQLException throwables) {
            throw new ServiceJdbcException(throwables.getMessage(), throwables.getCause()); // esto viene de serviceJdbcException
        }
    }

    @Override
    public void guardar(Producto producto) {
        try {
            repositoryJdbc.guardar(producto);
        } catch (SQLException throwables) {
            throw new ServiceJdbcException(throwables.getMessage(), throwables.getCause());
        }

    }

    @Override
    public void eliminar(Long id) {
        try {
            repositoryJdbc.eliminar(id);
        } catch (SQLException throwables) {
            throw new ServiceJdbcException(throwables.getMessage(), throwables.getCause());
        }
    }

    @Override
    public List<Categoria> listarCategoria() {
        try {
            return repositoryCategoriaJdbc.listar();
        } catch (SQLException throwables) {
            throw new ServiceJdbcException(throwables.getMessage(), throwables.getCause());
        }
    }

    @Override
    public Optional<Categoria> porIdCategoria(Long id) {
        try {
            return Optional.ofNullable(repositoryCategoriaJdbc.porId(id));
        } catch (SQLException throwables) {
            throw new ServiceJdbcException(throwables.getMessage(), throwables.getCause());
        }
    }
}
