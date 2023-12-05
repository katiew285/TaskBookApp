/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/JSP_Servlet/Servlet.java to edit this template
 */
package controllers;

import business.Tasks;
import db.TaskDAO;
import db.UserDAO;
import java.io.IOException;
import java.io.PrintWriter;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
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
@WebServlet(name = "UserTasks", urlPatterns = {"/UserTasks"})
public class UserTasks extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        int id = (int) request.getSession().getAttribute("id");
        List<Tasks> tasks = null;
        try {
            if(tasks == null || tasks.isEmpty()){
            tasks = TaskDAO.selectTasksByUserId(id);
            request.setAttribute("tasks", tasks);
            }
        } catch (SQLException | ClassNotFoundException e) {
            request.setAttribute("NOTIFICATION", "ailed to retrieve tasks.");
        }

        RequestDispatcher dispatcher = request.getRequestDispatcher("admin/userTasks.jsp");
        dispatcher.forward(request, response);
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        int taskId = Integer.parseInt(request.getParameter("taskId"));

        try {
            TaskDAO.deleteTask(taskId);
            request.setAttribute("NOTIFICATION", "task deleted.");
        } catch (SQLException | ClassNotFoundException e) {
            request.setAttribute("NOTIFICATION", "failed to delete task.");
        }
        
        doGet(request, response); 
    }

    /**
     * Returns a short description of the servlet.
     *
     * @return a String containing servlet description
     */
    @Override
    public String getServletInfo() {
        return "Short description";
    }// </editor-fold>

}
