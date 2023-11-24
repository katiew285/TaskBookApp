package controllers;

import Utils.ConnectionPool;
import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import business.User;
import com.mysql.jdbc.Connection;
import db.LoginDAO;
import db.UserDAO;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import javax.servlet.RequestDispatcher;
import javax.servlet.annotation.WebServlet;

@WebServlet(name = "Login", urlPatterns = {"/Login"})

public class Login extends HttpServlet {

    private static final Logger LOG = Logger.getLogger(Login.class.getName());
    ArrayList<String> errors = new ArrayList<>();
    Connection conn;

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        doPost(request, response);
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        String url = "/home.jsp";

        Logger LOG = Logger.getLogger(Controller.class.getName());

        if (conn != null) {
            ConnectionPool.getInstance().freeConnection(conn);
        }

        String email = request.getParameter("email");
        String password = request.getParameter("password");

        try {

            LoginDAO loginDAO = new LoginDAO();
            UserDAO userDAO = new UserDAO();

            if (!loginDAO.select(email, password)) {
                if(!userDAO.doesEmailExist(email)){
                    errors.add("email not registered: " + email);
                } else {
                        errors.add("Incorrect password for email: " + email);
                        }
                url = "/home.jsp"; 
                errors.add("invalid email or password for email: " + email);
                url = "/home.jsp";
            } else {
                LinkedHashMap<String, User> users = userDAO.getUserByEmail(email);

                if (users != null) {
                    HttpSession sess = request.getSession();
                    sess.setAttribute("user", users);

                    if (users.containsKey("admin")) {
                        url = "/admin/profile.jsp";
                    } else {
                        url = "/user/profile.jsp";
                    }
                } else {
                    errors.add("user info not found.");
                    url = "/home.jsp";
                }
            }
    
        } catch (SQLException e) {
            Logger.getLogger(Login.class.getName()).log(Level.SEVERE, null, e);
            errors.add("error during login.");
            url = "/home.jsp";
        }

        request.setAttribute("errors", errors);

        RequestDispatcher dispatcher = request.getRequestDispatcher("/home.jsp");
        dispatcher.forward(request, response);
        getServletContext().getRequestDispatcher(url).forward(request, response);
    }

    @Override
    public String getServletInfo() {
        return "Short description";
    }// </editor-fold>

}
