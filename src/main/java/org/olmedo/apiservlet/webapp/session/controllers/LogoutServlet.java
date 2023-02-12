package org.olmedo.apiservlet.webapp.session.controllers;

import jakarta.inject.Inject;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.*;
import org.olmedo.apiservlet.webapp.session.service.LoginService;
import org.olmedo.apiservlet.webapp.session.service.LoginServiceCookieImpl;
import org.olmedo.apiservlet.webapp.session.service.LoginServiceSessionImpl;

import java.io.IOException;
import java.util.Optional;

@WebServlet("/logout")
public class LogoutServlet extends HttpServlet {
    @Inject
    private LoginService auth;
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {

        Optional<String> username = auth.getUsername(req);
        if (username.isPresent()) {
            HttpSession session = req.getSession();
            session.invalidate(); // para que borre todo cuando hago el logout
        }
        resp.sendRedirect(req.getContextPath() + "/login.html");
    }
}
