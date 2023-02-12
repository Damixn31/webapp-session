package org.olmedo.apiservlet.webapp.session.filters;

import jakarta.inject.Inject;
import jakarta.servlet.*;
import jakarta.servlet.annotation.WebFilter;
import jakarta.servlet.http.HttpServletResponse;
import org.olmedo.apiservlet.webapp.session.configs.MysqlConn;
import org.olmedo.apiservlet.webapp.session.service.ServiceJdbcException;

import java.io.IOException;
import java.sql.Connection;
import java.sql.SQLException;

@WebFilter("/*")
public class ConexionFilter implements Filter {
    /*@Inject
    @MysqlConn
    private Connection conn;*/

    @Override
    public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain) throws IOException, ServletException {

       /* try {
            Connection connRequest = this.conn;//this.com viene del CDI la inyeccion
         if (connRequest.getAutoCommit()) {
             connRequest.setAutoCommit(false);
         } */
         try {
     //        servletRequest.setAttribute("conn", connRequest); // esto ya no va porque la inyeccion la estamos pasando via dependencia con CDI
             filterChain.doFilter(servletRequest, servletResponse);
             //connRequest.commit();
             // ServiceJdbcException es como un puente de comunicacion entre la clase serive y la clase conexionFilter para que realize el rollback
         } catch(ServiceJdbcException e) {
            // connRequest.rollback();
             ((HttpServletResponse)servletResponse).sendError(HttpServletResponse.SC_INTERNAL_SERVER_ERROR, e.getMessage());
             e.printStackTrace();
         }
 /*       } catch (SQLException  throwables) {
            throwables.printStackTrace();
        } */
    }
}
