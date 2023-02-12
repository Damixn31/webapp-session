package org.olmedo.apiservlet.webapp.session.controllers;

import jakarta.inject.Inject;
import jakarta.inject.Named;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.olmedo.apiservlet.webapp.session.configs.ProductoServicePrincipal;
import org.olmedo.apiservlet.webapp.session.models.Producto;
import org.olmedo.apiservlet.webapp.session.service.*;

import java.io.IOException;
import java.util.List;
import java.util.Optional;

@WebServlet({"/productos.html", "/productos"}) //url enlasadas
public class ProductoServlet extends HttpServlet {
    @Inject
    @ProductoServicePrincipal
    private ProductoService service;
    @Inject
    private LoginService auth;
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {

        List<Producto> productos = service.listar();


        Optional<String> usernameOptional = auth.getUsername(req);


        req.setAttribute("productos", productos); // productos le pasamos el mismo nombre que lo estamos recibiendo en la lista.jsp
        req.setAttribute("username", usernameOptional);
        req.setAttribute("title", req.getAttribute("title") + ": Listado de productos"); // para modificar cada titulo que lo pasamos por el Aplicacionlistener
        getServletContext().getRequestDispatcher("/listar.jsp").forward(req,resp);
    }
}
