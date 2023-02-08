package org.olmedo.apiservlet.webapp.session.service;

import org.olmedo.apiservlet.webapp.session.models.Usuario;
import org.olmedo.apiservlet.webapp.session.repositories.UsuarioRepository;
import org.olmedo.apiservlet.webapp.session.repositories.UsuarioRepositoryImpl;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.Optional;

public class UsuarioServiceImpl implements UsuarioService{
    private UsuarioRepository usuarioRepository;

    public UsuarioServiceImpl(Connection connection) {
        this.usuarioRepository = new UsuarioRepositoryImpl(connection); // estamos inicializando con la coneccion de la base de datos
    }

    @Override
    public Optional<Usuario> login(String username, String password) {
        try {
            return Optional.ofNullable(usuarioRepository.porUsername(username)).filter(u -> u.getPassword().equals(password)); // el filter compara si el password es igual al de la base de datos
        } catch (SQLException throwables) {
            throw  new ServiceJdbcException(throwables.getMessage(), throwables.getCause());
        }
    }
}
