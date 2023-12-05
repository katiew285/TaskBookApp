/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/JSP_Servlet/Servlet.java to edit this template
 */
package controllers;

import business.User;
import db.UserDAO;
import java.io.IOException;
import java.lang.System.Logger.Level;
import java.sql.SQLException;
import java.util.ArrayList;
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
@WebServlet(name = "Profile", urlPatterns = {"/Profile"})
public class Profile extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        HttpSession session = request.getSession();
        String userEmail = (String) session.getAttribute("userEmail");
        ArrayList<String> errors = new ArrayList<>();
        
        if (userEmail != null && !userEmail.isEmpty()) {
            errors.add("error: user email is null or empty.");
        }
        
        User user = null;
        try {
            user = UserDAO.getUserByEmail(userEmail);
        } catch (SQLException | ClassNotFoundException ex) {
            Logger.getLogger(Profile.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        
        if(user != null){
            session.setAttribute("userEmail", userEmail);
            //request.setAttribute("userEmail", user);
            response.sendRedirect(request.getContextPath() + "/user/profile.jsp");
            return;
        } else {
            errors.add("user not found with email: " + userEmail);
        }
        RequestDispatcher dispatcher = request.getRequestDispatcher("/login.jsp");
        dispatcher.forward(request, response);
    }

            @Override
            protected void doPost
            (HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
                doGet(request, response);
            }

            @Override
            public String getServletInfo
            
            
                () {
        return "Short description";
            }// </editor-fold>

        }
