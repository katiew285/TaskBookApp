/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/JSP_Servlet/Servlet.java to edit this template
 */
package controllers;

import business.User;
import com.mysql.jdbc.Connection;
import db.RegisterDAO;
import java.io.IOException;
import java.sql.SQLException;
import java.time.LocalDate;
import java.util.ArrayList;
import java.sql.Date;
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
@WebServlet(name = "Register", urlPatterns = {"/Register"})
public class Register extends HttpServlet {

    private static final Logger LOG = Logger.getLogger(Register.class.getName());
    ArrayList<String> errors = new ArrayList<>();

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        doPost(request, response);
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        String url = "/register.jsp";

        Logger LOG = Logger.getLogger(Register.class.getName());
        String action = request.getParameter("action");
        User user = new User();
        ArrayList<String> errors = new ArrayList<>();

        try {
            String email = request.getParameter("email");
            String password = request.getParameter("password");
            String name = request.getParameter("name");
            Date dob = Date.valueOf(request.getParameter("dob"));
            String state = request.getParameter("state");

            boolean isValid = true;

            if ("".equals(email) || email.isEmpty()) {
                isValid = false;
                errors.add("please enter email.");
            }

            if ("".equals(password) || password.isEmpty()) {
                isValid = false;
                errors.add("please enter password.");
            }

            if ("".equals(name) || name.isEmpty()) {
                isValid = false;
                errors.add("please enter name.");
            }

            LocalDate today = LocalDate.now();
            LocalDate dobLD = dob.toLocalDate();
            try {
                if (dob.equals("")) {
                    isValid = false;
                    errors.add("plese enter date of birth.");
                } else if (dobLD.isAfter(today.minusYears(18))) {
                    isValid = false;
                    errors.add("you must be 18 years or older to register.");
                }
            } catch (Exception e) {
                isValid = false;
                errors.add("dob failed.");
                LOG.log(Level.SEVERE, null, e);
            }

            if ("".equals(state) || state.isEmpty()) {
                isValid = false;
                errors.add("state must be valid and 2 letters.");
            }

            if (isValid) {
                user.setEmail(email);
                user.setPassword(password);
                user.setName(name);
                user.setDob(dob);
                user.setState(state);

                try {
                    RegisterDAO.insert(user);
                    url = "/user/profile.jsp";
                } catch (SQLException e) {
                    LOG.log(Level.SEVERE, "error registering.", e);
                    errors.add("insert register user failed.");
                    url = "/register.jsp";
                }
            } else {
                url = "/register.jsp";
            }
        } catch (Exception e) {
            errors.add("registration failed");
            LOG.log(Level.SEVERE, null, e);
        }

        request.setAttribute("errors", errors);
        request.setAttribute("user", user);

        RequestDispatcher dispatcher = request.getRequestDispatcher("/register.jsp");
        dispatcher.forward(request, response);
        getServletContext().getRequestDispatcher(url).forward(request, response);
    }

    @Override
    public String getServletInfo() {
        return "Short description";
    }// </editor-fold>

}
