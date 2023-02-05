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

import javax.swing.text.html.Option;
import java.io.IOException;
import java.sql.Connection;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

@WebServlet("/productos/form")
public class ProductoFormServlet extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        Connection conn = (Connection) req.getAttribute("conn");
        ProductoService service = new ProductoServiceJdbcImpl(conn);
        req.setAttribute("categorias", service.listarCategoria());
        Long id;
        try {
          id = Long.valueOf(req.getParameter("id")); // obtengo el id para editar
        } catch (NumberFormatException e) {
            id = 0L; // si es null asignamos el id a 0
        }
        // buscamos el producto porId
        Producto producto = new Producto(); // un nuevo  en caso que el id no exista
        producto.setCategoria(new Categoria()); // creamos una categoria vacia para el producto por defecto cuando queremos crear un nuevo producto
        if (id > 0) {
            Optional<Producto> o = service.porId(id);
            if (o.isPresent()) {
                producto = o.get();
            }
        }
        req.setAttribute("producto", producto);
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

        // validacion del formulario
        Map<String, String> errores = new HashMap<>(); // mapeamos por cada elemento del formulario
        if (nombre == null || nombre.isBlank()) {
            errores.put("nombre", "El nombre es requerido!");
        }
        if (sku == null || sku.isBlank()) {
            errores.put("sku", "El sku es requerido!");
        } else if (sku.length() > 10) {
            errores.put("sku", "El sku debe tener max 10 caracteres!");
        }

        if (fechaStr == null || fechaStr.isBlank()) {
            errores.put("fecha_registro", "La fecha es requerida!");
        }
        if (precio.equals(0)) {
            errores.put("precio", "El precio es requerido!");
        }
        if (categoriaId.equals(0)) {
            errores.put("categoria", "La categoria es requerida!");
        }
        if (errores.isEmpty()) {

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
        } else {
            req.setAttribute("errores", errores);
            doGet(req, resp); // volvemos a invocar el doGet para volver a mostrar el formulario
        }
    }
}
