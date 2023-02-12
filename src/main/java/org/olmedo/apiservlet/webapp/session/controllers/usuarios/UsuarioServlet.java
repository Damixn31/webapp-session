package org.olmedo.apiservlet.webapp.session.controllers.usuarios;

import jakarta.inject.Inject;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.olmedo.apiservlet.webapp.session.models.Usuario;
import org.olmedo.apiservlet.webapp.session.service.LoginService;
import org.olmedo.apiservlet.webapp.session.service.LoginServiceSessionImpl;
import org.olmedo.apiservlet.webapp.session.service.UsuarioService;
import org.olmedo.apiservlet.webapp.session.service.UsuarioServiceImpl;

import java.io.IOException;
import java.sql.Connection;
import java.util.List;
import java.util.Optional;

@WebServlet("/usuarios")
public class UsuarioServlet extends HttpServlet {

    @Inject
    private UsuarioService service;
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {

        List<Usuario> usuarios = service.listar();

        LoginService auth = new LoginServiceSessionImpl();
        Optional<String> usernameOptional = auth.getUsername(req);

        req.setAttribute("usuarios", usuarios);
        req.setAttribute("username", usernameOptional);

        req.setAttribute("title", req.getAttribute("title") + ": Listado de usuarios");
        getServletContext().getRequestDispatcher("/usuarios/listar.jsp").forward(req, resp);
    }
}
