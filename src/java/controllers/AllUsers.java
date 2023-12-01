/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/JSP_Servlet/Servlet.java to edit this template
 */
package controllers;

import business.User;
import db.UserDAO;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 *
 * @author katie
 */
@WebServlet(name = "AllUsers", urlPatterns = {"/AllUsers"})
public class AllUsers extends HttpServlet {

    private static final Logger LOG = Logger.getLogger(Register.class.getName());

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        ArrayList<String> errors = new ArrayList<>();
        LinkedHashMap<String, User> users = null;

        try {
            users = UserDAO.selectAll();
        } catch (Exception e) {
            e.printStackTrace();
            errors.add("error.");
            LOG.log(Level.SEVERE, null, e);
        }

        if (users != null) {
            request.setAttribute("users", users);
        } else {
            errors.add("failed to retrieve data.");
        }

        request.setAttribute("errors", errors);
        RequestDispatcher dispatcher = request.getRequestDispatcher("allUsers.jsp");
        dispatcher.forward(request, response);
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        doGet(request, response);
    }

    @Override
    public String getServletInfo() {
        return "Short description";
    }// </editor-fold>

}
