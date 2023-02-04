package org.olmedo.apiservlet.webapp.session.controllers;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.olmedo.apiservlet.webapp.session.models.Categoria;
import org.olmedo.apiservlet.webapp.session.models.Producto;
import org.olmedo.apiservlet.webapp.session.service.ProductoService;
import org.olmedo.apiservlet.webapp.session.service.ProductoServiceImpl;
import org.olmedo.apiservlet.webapp.session.service.ProductoServiceJdbcImpl;

import java.io.IOException;
import java.sql.Connection;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

@WebServlet("/productos/form")
public class ProductoFormServlet extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        Connection conn = (Connection) req.getAttribute("conn");
        ProductoService service = new ProductoServiceJdbcImpl(conn);
        req.setAttribute("categorias", service.listarCategoria());
        getServletContext().getRequestDispatcher("/form.jsp").forward(req, resp);

    }

    // doPost --> para resivir la informacion para la base de datos
    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {

        Connection conn = (Connection) req.getAttribute("conn");
        ProductoService service = new ProductoServiceJdbcImpl(conn);
        //cara campturar cada dato de formulario
        String nombre = req.getParameter("nombre");

        Integer precio;
        try {
            precio = Integer.valueOf(req.getParameter("precio"));
        } catch (NumberFormatException e) {
            precio = 0;
        }

        String sku = req.getParameter("sku");
        String fechaStr = req.getParameter("fecha_registro");
        Long categoriaId;
        try {
            categoriaId = Long.parseLong(req.getParameter("categoria"));
        } catch (NumberFormatException e) {
            categoriaId = 0L;
        }

        LocalDate fecha = LocalDate.parse(fechaStr, DateTimeFormatter.ofPattern("yyyy-MM-dd")); // convertimos la fecha en un LocalDate para poder usarla
        Producto producto = new Producto();
        producto.setNombre(nombre);
        producto.setSku(sku);
        producto.setPrecio(precio);
        producto.setFechaRegistro(fecha);

        Categoria categoria = new Categoria();
        categoria.setId(categoriaId);
        // ahora pasamos la catetoria al producto
        producto.setCategoria(categoria);

        //guadamos el producto creado mediante el service
        service.guardar(producto);
        //para que no vuelva a dupicar los datos con un refresh de la pagina usamos el sendRedirect para que nos vuelva a redirigir a otra pagina
        resp.sendRedirect(req.getContextPath() + "/productos");
    }
}
