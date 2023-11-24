/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/JSP_Servlet/Servlet.java to edit this template
 */
package controllers;

import db.TaskDAO;
import java.io.IOException;
import java.util.List;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 *
 * @author katie
 */
@WebServlet(name = "Tasks", urlPatterns = {"/Tasks"})
public class Tasks extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        String url = "/user/tasks.jsp";
        String action = request.getParameter("action");
        
        List<String> tasks = TaskDAO.getAllTasks();

        request.setAttribute("tasks", tasks);
        
        if("showConfirmation".equals(action)){
            int taskIndex = Integer.parseInt(request.getParameter("taskIndex"));
            String taskToDelete = tasks.get(taskIndex);
            
            request.setAttribute("taskToDelete", taskToDelete);
        } else if ("confirmDelete".equals(action)){
                int taskIndex = Integer.parseInt(request.getParameter("taskIndex"));
                TaskDAO.deleteTask(String.valueOf(taskIndex));
                
                List<String> updatedTasks = TaskDAO.getAllTasks();
                request.setAttribute("tasks", updatedTasks);
            }
        
        
        getServletContext().getRequestDispatcher(url).forward(request, response);

    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        String url = "/users/tasks.jsp";

        String action = request.getParameter("action");

        switch (action) {
            case "addTask" -> {
                String newTask = request.getParameter("taskItem");
                TaskDAO.addTask(newTask);
            }
            case "deleteTask" -> {
                String taskIndex = request.getParameter("taskIndex");
                TaskDAO.deleteTask(taskIndex);
            }
            case "deleteAll" -> TaskDAO.deleteAllTasks();
            default -> {
            }
        }
        
        List<String> updatedTasks = TaskDAO.getAllTasks();
        request.setAttribute("tasks", updatedTasks);
        
        getServletContext().getRequestDispatcher(url).forward(request, response);

    }

@Override
public String getServletInfo() {
        return "Short description";
    }// </editor-fold>

}
