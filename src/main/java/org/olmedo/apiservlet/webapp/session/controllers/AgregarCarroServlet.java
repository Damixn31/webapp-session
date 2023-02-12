package org.olmedo.apiservlet.webapp.session.controllers;

import jakarta.inject.Inject;
import jakarta.inject.Named;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.olmedo.apiservlet.webapp.session.configs.ProductoServicePrincipal;
import org.olmedo.apiservlet.webapp.session.models.Carro;
import org.olmedo.apiservlet.webapp.session.models.ItemCarro;
import org.olmedo.apiservlet.webapp.session.models.Producto;
import org.olmedo.apiservlet.webapp.session.service.ProductoService;

import java.io.IOException;
import java.util.Optional;

@WebServlet("/carro/agregar")
public class AgregarCarroServlet extends HttpServlet {
    @Inject
    @ProductoServicePrincipal
    private ProductoService service;
    @Inject
    private Carro carro;
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {

        Long id = Long.parseLong(req.getParameter("id"));

        Optional<Producto> producto = service.porId(id);
        if (producto.isPresent()){
            ItemCarro item = new ItemCarro(1, producto.get());
            // como tenemos ya la inyeccion de dependencia ya el codigo de abajo no va, porque ahora carro lo vamos a inyectar y va a ser un atributo del servlet
            //HttpSession session = req.getSession();
            //Carro carro = (Carro) session.getAttribute("carro");
            carro.addItemCarro(item);
        }
        resp.sendRedirect(req.getContextPath() + "/carro/ver");
    }
}
