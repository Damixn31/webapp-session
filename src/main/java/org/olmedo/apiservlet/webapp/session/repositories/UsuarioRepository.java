package org.olmedo.apiservlet.webapp.session.repositories;

import org.olmedo.apiservlet.webapp.session.models.Usuario;

import java.sql.SQLException;

public interface UsuarioRepository extends CrudRepository<Usuario> {
    Usuario porUsername(String username) throws SQLException;
}
