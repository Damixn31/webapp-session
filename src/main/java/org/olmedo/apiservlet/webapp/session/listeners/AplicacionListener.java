package org.olmedo.apiservlet.webapp.session.listeners;

import jakarta.servlet.*;
import jakarta.servlet.annotation.WebListener;
import jakarta.servlet.http.HttpSession;
import jakarta.servlet.http.HttpSessionEvent;
import jakarta.servlet.http.HttpSessionListener;
import org.olmedo.apiservlet.webapp.session.models.Carro;

// Listerner significa un evento un escuchcador va a estar escuchando el siclo de vida por ejemplo de request o del objeto session o de nuestra aplicacion
@WebListener
public class AplicacionListener implements ServletContextListener, ServletRequestListener, HttpSessionListener {

    private ServletContext servletContext;

    @Override
    public void contextInitialized(ServletContextEvent sce) {
        sce.getServletContext().log("Inicializando la aplicacion!");
        servletContext = sce.getServletContext(); // lo inicializamos
        servletContext.setAttribute("mensaje", "Algun valor global de la app!");
    }

    @Override
    public void contextDestroyed(ServletContextEvent sce) {
        servletContext.log("Destruyendo la aplicacion!");
    }
    @Override
    public void requestInitialized(ServletRequestEvent sre) {
        servletContext.log("Inicializando el request!");
        sre.getServletRequest().setAttribute("mensaje", "guardando algun valor para el request");
        sre.getServletRequest().setAttribute("title", "Catalogo Servlet"); //modifica el titulo en cada pagina
    }

    @Override
    public void requestDestroyed(ServletRequestEvent sre) {
        servletContext.log("Destruyendo el request!");
    }

    @Override
    public void sessionCreated(HttpSessionEvent se) {
        servletContext.log("Inicianlizando la session http!");
        //ya no es necesario controlarlo con codigo java ya que estamos pasanle anotaciones de inyeccion de dependencia
        //Carro carro = new Carro();
        //HttpSession session = se.getSession();
        //session.setAttribute("carro", carro);
    }

    @Override
    public void sessionDestroyed(HttpSessionEvent se) {
        servletContext.log("Destruyendo la session http!");
    }
}
