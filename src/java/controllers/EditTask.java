/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/JSP_Servlet/Servlet.java to edit this template
 */
package controllers;

import business.Tasks;
import db.TaskDAO;
import java.io.IOException;
import java.io.PrintWriter;
import java.sql.Timestamp;
import java.sql.SQLException;
import java.sql.Date;
import java.util.logging.Logger;
import java.util.logging.Level;
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
@WebServlet(name = "EditTask", urlPatterns = {"/EditTask"})
public class EditTask extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        String id = request.getParameter("id");
        if (id != null && !id.isEmpty()) {
            int taskId = Integer.parseInt(id);
            Tasks task = null;

            try {
                task = TaskDAO.getTaskById(taskId);
            } catch (SQLException ex) {
                Logger.getLogger(EditTask.class.getName()).log(Level.SEVERE, null, ex);
            } catch (ClassNotFoundException ex) {
                Logger.getLogger(EditTask.class.getName()).log(Level.SEVERE, null, ex);
            }

            request.setAttribute("task", task);
        }

        RequestDispatcher dispatcher = request.getRequestDispatcher("user/taskForm.jsp");
        dispatcher.forward(request, response);
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        String action = request.getParameter("action");
        String title = request.getParameter("title");
        String description = request.getParameter("description");
        Timestamp timestamp = Timestamp.valueOf(request.getParameter("timestamp"));
        Date dueDate = Date.valueOf(request.getParameter("dueDate"));

        HttpSession session = request.getSession();
        String userEmail = (String) session.getAttribute("userEmail");

        if ("add".equals(action)) {
            Tasks task = new Tasks();
            task.setEmail(userEmail);
            task.setTitle(title);
            task.setDescription(description);
            task.setTimestamp(timestamp);
            task.setDueDate(dueDate);

            try {
                TaskDAO.addTask(task);
                request.setAttribute("NOTIFICATION", "task added successfully.");
            } catch (SQLException | ClassNotFoundException e) {
                Logger.getLogger("EditTask").log(Level.INFO, "error adding task", e);
            }
        } else if ("update".equals(action)) {
            String id = request.getParameter("id");

            if (id != null && !id.isEmpty()) {
                int taskId = Integer.parseInt(id);
                Tasks existingTask = null;

                try {
                    existingTask = TaskDAO.getTaskById(taskId);
                } catch (SQLException | ClassNotFoundException e) {
                    Logger.getLogger(EditTask.class.getName()).log(Level.SEVERE, "error getting task by id (doPost)", e);

                    if (existingTask != null && existingTask.getEmail().equals(userEmail)) {
                        existingTask.setTitle(title);
                        existingTask.setDescription(description);
                        existingTask.setTimestamp(timestamp);
                        existingTask.setDueDate(dueDate);

                        try {
                            TaskDAO.updateTask(existingTask);
                            request.setAttribute("NOTIFICATION", "task updated successfully.");
                        } catch (SQLException | ClassNotFoundException ex) {
                            Logger.getLogger(EditTask.class.getName()).log(Level.SEVERE, "error updating task by id", ex);
                            request.setAttribute("NOTIFICATION", "error updating task");
                        }
                    } else {
                        request.setAttribute("NOTIFICATION", "task not found");
                    }
                }
            } else {
                request.setAttribute("NOTIFICATION", "invalid task id.");
            }
        } else {
            request.setAttribute("NOTIFICATION", "invalid action");
        }
         RequestDispatcher dispatcher = request.getRequestDispatcher("/taskForm.jsp");
        dispatcher.forward(request, response);
    }

    @Override
    public String getServletInfo() {
        return "Short description";
    }// </editor-fold>

}
