package org.olmedo.apiservlet.webapp.session.service;

import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import org.olmedo.apiservlet.webapp.session.configs.Service;
import org.olmedo.apiservlet.webapp.session.models.Usuario;
import org.olmedo.apiservlet.webapp.session.repositories.UsuarioRepository;

import java.sql.SQLException;
import java.util.List;
import java.util.Optional;

//cada cliente que se conecta va a tener su propia coneccion y toda las transacciones no se van a ver afectadas
@Service
public class UsuarioServiceImpl implements UsuarioService{
    private UsuarioRepository usuarioRepository;

    @Inject
    public UsuarioServiceImpl(UsuarioRepository usuarioRepository) { //@named no va porque tenemos una sola implementacion UsuarioRepository va a inyectar la primera que encuentre en este caso UsuarioServiceImpl
        this.usuarioRepository = usuarioRepository;
    }

    @Override
    public Optional<Usuario> login(String username, String password) {
        try {
            return Optional.ofNullable(usuarioRepository.porUsername(username)).filter(u -> u.getPassword().equals(password)); // el filter compara si el password es igual al de la base de datos
        } catch (SQLException throwables) {
            throw  new ServiceJdbcException(throwables.getMessage(), throwables.getCause());
        }
    }

    @Override
    public List<Usuario> listar() {
        try {
            return usuarioRepository.listar();
        } catch (SQLException e) {
            throw new ServiceJdbcException(e.getMessage(), e.getCause());
        }
    }

    @Override
    public Optional<Usuario> porId(Long id) {
        try {
          return Optional.ofNullable(usuarioRepository.porId(id));
        } catch (SQLException e) {
            throw new ServiceJdbcException(e.getMessage(), e.getCause());
        }
    }

    @Override
    public void guardar(Usuario usuario) {
        try {
            usuarioRepository.guardar(usuario);
        } catch (SQLException e ) {
            throw new ServiceJdbcException(e.getMessage(), e.getCause());
        }

    }

    @Override
    public void eliminar(Long id) {
        try {
            usuarioRepository.eliminar(id);
        } catch (SQLException e) {
            throw new ServiceJdbcException(e.getMessage(), e.getCause());
        }
    }
}
