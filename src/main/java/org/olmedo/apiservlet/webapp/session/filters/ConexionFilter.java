package org.olmedo.apiservlet.webapp.session.filters;

import jakarta.servlet.*;
import jakarta.servlet.annotation.WebFilter;
import jakarta.servlet.http.HttpServletResponse;
import org.olmedo.apiservlet.webapp.session.util.ConexionBaseDatos;

import java.io.IOException;
import java.sql.Connection;
import java.sql.SQLException;

@WebFilter("/*")
public class ConexionFilter implements Filter {
    @Override
    public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain) throws IOException, ServletException {

        try(Connection conn = ConexionBaseDatos.getConnection()) {
         if (conn.getAutoCommit()) {
             conn.setAutoCommit(false);
         }
         try {
             servletRequest.setAttribute("conn", conn);
             filterChain.doFilter(servletRequest, servletResponse);
             conn.commit();
         } catch(SQLException e) {
             conn.rollback();
             ((HttpServletResponse)servletRequest).sendError(HttpServletResponse.SC_UNAUTHORIZED, e.getMessage());
             e.printStackTrace();
         }
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
    }
}
