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
import org.olmedo.apiservlet.webapp.session.service.ProductoService;
import org.olmedo.apiservlet.webapp.session.service.ProductoServiceJdbcImpl;

import java.io.IOException;
import java.sql.Connection;
import java.util.Optional;

@WebServlet("/productos/eliminar")
public class ProductoEliminarServlet extends HttpServlet {
    @Inject
    @ProductoServicePrincipal
    private ProductoService service;
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {

        long id;
        try {
            id = Long.parseLong(req.getParameter("id"));
        } catch (NumberFormatException e) {
            id = 0L;
        }
        if (id > 0) {
            Optional<Producto> o = service.porId(id);
            if (o.isPresent()) { // si el id que queremos elimnar existe lo eliminamos
                service.eliminar(id);
                resp.sendRedirect(req.getContextPath() + "/productos");
            } else { // si no existe el id que queremos eliminar lazamos un error 404
                resp.sendError(HttpServletResponse.SC_NOT_FOUND, "No existe el producto en la base de datos!");
            }
        } else {
            resp.sendError(HttpServletResponse.SC_NOT_FOUND, "Error el id es null, se debe enviar como parametro en la url!");
        }

    }
}
