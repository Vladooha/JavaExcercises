package com.vladooha.servlet;

import com.vladooha.data.dao.ProfileDAO;
import com.vladooha.data.entity.Profile;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

public class EndProcessServlet extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        Profile profile = new Profile();
        ProfileDAO profileDAO = ProfileDAO.getInstance();
        if (profileDAO != null) {
            profile = profileDAO.getLastProfile();
            profileDAO.removeProfile(profile);

            req.setAttribute("profile", profile);
        }

        RequestDispatcher rd = req.getRequestDispatcher("end.jsp");
        rd.forward(req, resp);
    }
}
