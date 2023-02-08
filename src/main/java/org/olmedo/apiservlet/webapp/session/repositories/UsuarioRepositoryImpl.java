package org.olmedo.apiservlet.webapp.session.repositories;

import org.olmedo.apiservlet.webapp.session.models.Usuario;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

public class UsuarioRepositoryImpl implements UsuarioRepository{
    private Connection conn;

    //esta coneccion es importante para poder conectar los metodos de abajo
    public UsuarioRepositoryImpl(Connection conn) {
        this.conn = conn;
    }

    //metodos
    @Override
    public List<Usuario> listar() throws SQLException {
        return null;
    }

    @Override
    public Usuario porId(Long id) throws SQLException {
        return null;
    }

    @Override
    public void guardar(Usuario usuario) throws SQLException {

    }

    @Override
    public void eliminar(Long id) throws SQLException {

    }

    @Override
    public Usuario porUsername(String username) throws SQLException {
        Usuario usuario = null;
        try(PreparedStatement stmt = conn.prepareStatement("select * from usuarios where username=?")) {
            stmt.setString(1,  username);
            try(ResultSet rs = stmt.executeQuery()) {
                if(rs.next()) {
                    usuario = new Usuario();
                    usuario.setId(rs.getLong("id"));
                    usuario.setUsername(rs.getString("username"));
                    usuario.setPassword(rs.getString("password"));
                    usuario.setEmail(rs.getString("email"));

                }
            }
        }
        return usuario;
    }
}
