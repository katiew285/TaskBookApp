/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/JSP_Servlet/Servlet.java to edit this template
 */
package controllers;

import business.User;
import db.UserDAO;
import java.io.IOException;
import java.io.PrintWriter;
import java.sql.Date;
import java.sql.SQLException;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

/**
 *
 * @author katie
 */
@WebServlet(name = "EditProfile", urlPatterns = {"/EditProfile"})
public class EditProfile extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        doPost(request, response);
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        HttpSession session = request.getSession();
        String userEmail = (String) session.getAttribute("userEmail");
        User user = (User) session.getAttribute("user");

        if (userEmail == null || userEmail.isEmpty()) {
            request.setAttribute("NOTIFICATION", "USER IS NULL");
            String editProfile = "/user/editProfile.jsp";

            if (user != null && user.isAdmin()) {
                editProfile = "/admin/editProfile.jsp";
            }

            RequestDispatcher dispatcher = request.getRequestDispatcher(editProfile);
            dispatcher.forward(request, response);
            return;
        }

        System.out.println("userEmail from session: " + userEmail);
        System.out.println("User object from session: " + user);

        String password = request.getParameter("password");
        String name = request.getParameter("name");
        Date dob = Date.valueOf(request.getParameter("dob"));
        String state = request.getParameter("state");

        try {
            user = UserDAO.getUserByEmail(userEmail);

            if (user != null) {
                user.setPassword(password);
                user.setName(name);
                user.setDob(dob);
                user.setState(state);
                User updatedUser = UserDAO.update(user);

                if (updatedUser != null) {
                    session.setAttribute("user", updatedUser);

                    String profile = user.isAdmin() ? "/admin/profile.jsp" : "/user/profile.jsp";
                    response.sendRedirect(request.getContextPath() + profile);

                    request.setAttribute("NOTIFICATION", "Retrieved user: " + user);
                    return;
                } else {
                    request.setAttribute("NOTIFICATION", "Failed to update profile: user null");
                    String profile = user.isAdmin() ? "/admin/profile.jsp" : "/user/profile.jsp";
                    response.sendRedirect(request.getContextPath() + profile);
                    return;
                }
            } else {
                request.setAttribute("NOTIFICATION", "User not found for email: " + userEmail);
            }
        } catch (SQLException | ClassNotFoundException ex) {
            Logger.getLogger(EditProfile.class.getName()).log(Level.SEVERE, null, ex);
            request.setAttribute("NOTIFICATION", "Failed to update profile");

        }
        String editProfile = (user != null && user.isAdmin()) ? "/admin/editProfile.jsp" : "/user/editProfile.jsp";
        RequestDispatcher dispatcher = request.getRequestDispatcher(editProfile);
        dispatcher.forward(request, response);

    }

    @Override
    public String getServletInfo() {
        return "Short description";
    }// </editor-fold>

}
