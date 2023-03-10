package org.olmedo.apiservlet.webapp.session.controllers;

import jakarta.inject.Inject;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.*;
import org.olmedo.apiservlet.webapp.session.models.Usuario;
import org.olmedo.apiservlet.webapp.session.service.*;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.Optional;

@WebServlet({"/login", "/login.html"})
public class LoginServlet extends HttpServlet {
    @Inject
    private UsuarioService service;

    @Inject
    private LoginService auth;

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {

        Optional<String> usernameOptional = auth.getUsername(req);

        if(usernameOptional.isPresent()) {
            resp.setContentType("text/html");
            PrintWriter out = resp.getWriter();
            out.println("<!DOCTYPE html>");
            out.println("<html>");
            out.println("      <head>");
            out.println("            <meta charset=\"UTF-8\">");
            out.println("            <title>Hola " + usernameOptional.get() + " </title>");
            out.println("     </head>");
            out.println("     <body>");
            out.println("           <h1>Hola " + usernameOptional.get() + " has iniciado sesion con exito!</h1>");
            out.println("<p><a href='" + req.getContextPath() + "/index.jsp'>Volver</a></p>");
            out.println("<p><a href='" + req.getContextPath() + "/logout'>Cerrar sesion</a></p>");
            out.println("      </body>");
            out.println("</html>");
            out.close();
        } else {
            req.setAttribute("title", req.getAttribute("title") + ": Login"); // para modificar cada titulo que lo pasamos por el Aplicacionlistener
            getServletContext().getRequestDispatcher("/login.jsp").forward(req, resp);
        }

    }
    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String username = req.getParameter("username");
        String password = req.getParameter("password");

        Optional<Usuario> usuarioOptional = service.login(username, password);

        if (usuarioOptional.isPresent()) { // si el usuario existe se inicia session corectamente

            HttpSession session = req.getSession(); //obtenemos la sessiony se genera de manera automatica
            session.setAttribute("username", username);

            resp.sendRedirect(req.getContextPath() + "/login.html"); // redirige la ruta
        } else {
            resp.sendError(HttpServletResponse.SC_UNAUTHORIZED, "Lo sentimos no esta autorizado para ingresar a esta pagina!");
        }
    }
}
