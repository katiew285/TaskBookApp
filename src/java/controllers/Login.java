package controllers;

import java.io.IOException;
import java.util.logging.Logger;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import business.User;
import db.LoginDAO;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.logging.Level;
import javax.servlet.RequestDispatcher;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpSession;

@WebServlet(name = "Login", urlPatterns = {"/Login"})
public class Login extends HttpServlet {

    private static final Logger LOG = Logger.getLogger(Login.class.getName());

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        RequestDispatcher dispatcher = request.getRequestDispatcher("login.jsp");
        dispatcher.forward(request, response);
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        
        HttpSession session = request.getSession();
        String email = request.getParameter("email");
        String password = request.getParameter("password");
        ArrayList<String> errors = new ArrayList<>();
        User user = null;
        
        if(email != null && !email.isEmpty() && password != null && !password.isEmpty()){
         try {
                user = LoginDAO.select(email, password);

            if (user != null) {
                session.setAttribute("userEmail", email);
                //request.setAttribute("userEmail", user);
                if (user.isAdmin()) {
                    response.sendRedirect("admin/profile.jsp");
                } else {
                    response.sendRedirect("user/profile.jsp");
                }
                return;
            } else {
                // Handle invalid login
                errors.add("incorrect email or password");
            }
        } catch (SQLException | ClassNotFoundException e){
            Logger.getLogger(Login.class.getName()).log(Level.SEVERE, "Error on login servlet.");
            errors.add("error.");
        }
        } else {
            errors.add("please enter email and password to log in.");
        }
        

        request.setAttribute("errors", errors);
        RequestDispatcher dispatcher = request.getRequestDispatcher("login.jsp");
        dispatcher.forward(request, response);

//        String url = "/home.jsp";
//
//        Logger LOG = Logger.getLogger(Controller.class.getName());
//
//        String email = request.getParameter("email");
//        String password = request.getParameter("password");
//
//        try {
//            ConnectionPool pool = ConnectionPool.getInstance();
//            Connection conn = (Connection) pool.getConnection();
//
//            if (conn != null) {
//                ConnectionPool.getInstance().freeConnection(conn);
//            }
//
//            if (email.isEmpty() || password.isEmpty()) {
//                errors.add("please login.");
//                url = "/home.jsp";
//            } else {
//               
//                User user = LoginDAO.select(email, password);
//                
//                if (user != null) {
//                    System.out.println("user found.");
//
//                    if (user.isAdmin()) {
//                        url = "/admin/profile.jsp";
//                    } else {
//                        url = "/user/profile.jsp";
//                    }
//                } else {
//                    errors.add("user not found.");
//                    errors.add("incorrect email or password.");
//                    url = "/home.jsp";
//                }
//            }
////
////            LoginDAO loginDAO = new LoginDAO();
////            UserDAO userDAO = new UserDAO();
////
////            if (email.isEmpty() || password.isEmpty()) {
////                errors.add("please login.");
////            } else {
////
////                if (!userDAO.doesEmailExist(email)) {
////                    errors.add("email not registered: " + email);
////                    url = "/home.jsp";
////                } else {
////                    
////                    User user = loginDAO.select(email, password);
////                   
////                    if (user == null) {
////                        errors.add("incorrect email or password.");
////                        url = "/home.jsp";
////                    } else {
////                        LinkedHashMap<String, User> users = userDAO.getUserByEmail(email);
////
////                        if (users != null) {
////                            HttpSession sess = request.getSession();
////                            sess.setAttribute("user", user);
////
////                            if (user.isAdmin()) {
////                                url = "/admin/profile.jsp";
////                            } else {
////                                url = "/user/profile.jsp";
////                            }
////                        } else {
////                            errors.add("user information not found.");
////                            url = "/home.jsp";
////                        }
////                    }
////                }
////            }
//
//            pool.freeConnection(conn);
//        } catch (Exception e) {
//            Logger.getLogger(Login.class.getName()).log(Level.SEVERE, null, e);
//            errors.add("error during login." + e.getMessage());
//            url = "/home.jsp";
//        }
//
//        request.setAttribute("errors", errors);
//
//        RequestDispatcher dispatcher = request.getRequestDispatcher("/home.jsp");
//        dispatcher.forward(request, response);
//
//        getServletContext().getRequestDispatcher(url).forward(request, response);
    }

    @Override
    public String getServletInfo() {
        return "Short description";
    }// </editor-fold>

}
