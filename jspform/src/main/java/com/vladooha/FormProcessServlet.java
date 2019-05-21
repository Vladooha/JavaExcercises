package com.vladooha;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

public class FormProcessServlet extends HttpServlet {
    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        req.setCharacterEncoding("UTF-8");

        String name = req.getParameter("name");
        String surname = req.getParameter("surname");

        if (name == null || surname == null) {
            resp.sendRedirect("/form.jsp");
        } else {
            resp.setContentType("text/html");
            resp.setCharacterEncoding("UTF-8");

            resp.getWriter().println("Ваше имя и фамилия: " + name + ", " + surname);
        }
    }
}
