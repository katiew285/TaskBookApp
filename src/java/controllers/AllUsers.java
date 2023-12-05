/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/JSP_Servlet/Servlet.java to edit this template
 */
package controllers;

import business.User;
import db.UserDAO;
import java.io.IOException;
import java.io.PrintWriter;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
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

        List<User> users = null;

        try {
            users = UserDAO.selectAll();
        } catch (ClassNotFoundException e) {
            LOG.log(Level.SEVERE, "error fetching users", e);
            request.setAttribute("NOTIFICATION", "no users");
        } catch (SQLException ex) {
            Logger.getLogger(AllUsers.class.getName()).log(Level.SEVERE, null, ex);
        }

        if (users != null) {
            request.setAttribute("users", users);
        } else {
            LOG.log(Level.SEVERE, "error fetching users");
            request.setAttribute("NOTIFICATION", "no users");
        }
        String userToDelete = request.getParameter("deleteUserId");
        if (userToDelete != null && !userToDelete.isEmpty()) {
            // Perform delete user action
            try {
                int userId = Integer.parseInt(userToDelete);
                boolean isDeleted = UserDAO.deleteUserById(userId);
                if (isDeleted) {
                    request.setAttribute("NOTIFICATION", "user deleted successfully.");
                } else {
                    request.setAttribute("NOTIFICATION", "failed to delete user.");
                }
            } catch (NumberFormatException | SQLException | ClassNotFoundException e) {
                LOG.log(Level.SEVERE, "error deleting user", e);
                request.setAttribute("NOTIFICATION", "error deleting user.");
            }
        } else {
            RequestDispatcher dispatcher = request.getRequestDispatcher("admin/allUsers.jsp");
            dispatcher.forward(request, response);
        }
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
