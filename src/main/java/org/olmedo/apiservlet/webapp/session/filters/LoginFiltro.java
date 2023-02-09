package org.olmedo.apiservlet.webapp.session.filters;

import jakarta.servlet.*;
import jakarta.servlet.annotation.WebFilter;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.olmedo.apiservlet.webapp.session.service.LoginService;
import org.olmedo.apiservlet.webapp.session.service.LoginServiceSessionImpl;

import java.io.IOException;
import java.util.Optional;

// los filtras http son 100% orientado al request a manejar el siclo de vida: cuando se incia un request y cuando finaliza
// y para implementar una tarea transbersal  a nuestra aplicacion: validar la session de usario de lo contrario lanzar una pagina de error
// tambien podemos decidir que ruta utl seleccionar cuando tenemos paginas privadas
@WebFilter({"/carro/*", "/productos/form/*", "/productos/eliminar/*", "/usuarios/form/*", "/usuarios/eliminar/*"}) // para mantener las rutas privadas para que solo entren cuanto estas con session iniciada
public class LoginFiltro implements Filter {
    @Override
    public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain) throws IOException, ServletException {
        LoginService service = new LoginServiceSessionImpl();
        Optional<String> username = service.getUsername((HttpServletRequest) servletRequest);
        if (username.isPresent()) {
            filterChain.doFilter(servletRequest, servletResponse);
        } else {
            ((HttpServletResponse)servletResponse).sendError(HttpServletResponse.SC_UNAUTHORIZED,
                    "Lo sentimos no estas autorizado para ingresar a esta pagina!");
        }
    }
}
