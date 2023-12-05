/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/JSP_Servlet/Servlet.java to edit this template
 */
package controllers;

import business.Tasks;
import com.mysql.jdbc.Connection;
import db.TaskDAO;
import java.io.IOException;
import java.sql.Timestamp;
import java.sql.SQLException;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.util.ArrayList;
import java.sql.Date;
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
import javax.servlet.http.HttpSession;

/**
 *
 * @author katie
 */
@WebServlet(name = "Task", urlPatterns = {"/Task"})
public class Task extends HttpServlet {

    private static final Logger LOG = Logger.getLogger(Register.class.getName());
    ArrayList<String> errors = new ArrayList<>();

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        String action = request.getParameter("action");
        HttpSession session = request.getSession();
        List<Tasks> tasks = null;

        String userEmail = (String) session.getAttribute("userEmail");
        try {
            tasks = TaskDAO.selectAll(userEmail);
            if (tasks == null) {
                errors.add("task list is null.");
            } else if (tasks.isEmpty()) {
                errors.add("task list is empty.");
            } else {
                request.setAttribute("NOTIFICATION", "tasks loaded successfully.");
            }
        } catch (Exception e) {
            errors.add("error showing all tasks." + e.getMessage());
            LOG.log(Level.SEVERE, "***error showing all tasks.", e);
        }

        session.setAttribute("userEmail", userEmail);
        request.setAttribute("tasks", tasks);
        request.setAttribute("errors", errors);

        if ("edit".equals(action)) {
            RequestDispatcher dispatcher = request.getRequestDispatcher("user/taskForm.jsp");
            dispatcher.forward(request, response);
        } else {
            RequestDispatcher dispatcher = request.getRequestDispatcher("user/tasks.jsp");
            dispatcher.forward(request, response);
        }
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        String action = request.getParameter("action");
        HttpSession session = request.getSession();
        String email = (String) session.getAttribute("email");

        switch (action) {
            case "confirmIsCompleted" -> {
                try {
                    int taskId = Integer.parseInt(request.getParameter("taskId"));
                    TaskDAO.markTaskCompleted(taskId);
                    request.setAttribute("NOTIFICATION", "task completed!!!");
                } catch (SQLException ex) {
                    errors.add("error completing task.");
                    LOG.log(Level.SEVERE, "error with sql, confirm is completed", ex);
                } catch (ClassNotFoundException ex) {
                    LOG.log(Level.SEVERE, "class not found, confirm is completed", ex);
                }
            }
            case "confirmDelete" -> {
                try {
                    int taskIndex = Integer.parseInt(request.getParameter("taskIndex"));
                    TaskDAO.deleteTask(taskIndex);
                    request.setAttribute("NOTIFICATION", "task deleted");
                } catch (SQLException ex) {
                    errors.add("error deleting task.");
                    LOG.log(Level.SEVERE, "error with sql, confirm delete", ex);
                } catch (ClassNotFoundException ex) {
                    LOG.log(Level.SEVERE, "class not found, confirm delete", ex);
                }
            }
            case "confirmDeleteAll" -> {
                try {
                    TaskDAO.deleteAllTasks();
                    request.setAttribute("NOTIFICATION", "all tasks deleted.");
                } catch (Exception ex) {
                    errors.add("error deleting all tasks.");
                    LOG.log(Level.SEVERE, "error deleting all tasks", ex);
                }
            }
            case "addTask" -> {
                try {
                    String title = request.getParameter("title");
                    String description = request.getParameter("description");
                    String timestampString = request.getParameter("timestamp");
                    Date dueDate = Date.valueOf(request.getParameter("dueDate"));

                    boolean isValid = true;

                    if ("".equals(title) || title.isEmpty()) {
                        isValid = false;
                        errors.add("please enter title.");
                    }

                    if ("".equals(description) || description.isEmpty()) {
                        isValid = false;
                        errors.add("please enter description.");
                    }

                    Timestamp timestamp = null;
                    try {
                        timestamp = Timestamp.valueOf(timestampString);
                    } catch (IllegalArgumentException e) {
                        isValid = false;
                        errors.add("invalid timestamp format.");
                        LOG.log(Level.SEVERE, null, e);
                    }

                    if (dueDate != null) {
                        try {
                            LocalDate dueDateLD = dueDate.toLocalDate();
                            if (dueDateLD.isBefore(LocalDate.now())) {
                                isValid = false;
                                errors.add("due date should not be before today.");
                            }
                        } catch (DateTimeParseException e) {
                            isValid = false;
                            errors.add("invalid due date format.");
                            LOG.log(Level.SEVERE, null, e);
                        }
                    } else {
                        isValid = false;
                    }

                    if (isValid) {
                        Tasks task = new Tasks();

                        task.setEmail(email);
                        task.setTitle(title);
                        task.setDescription(description);
                        task.setTimestamp(timestamp);
                        task.setDueDate(dueDate);

                        try {
                            TaskDAO.addTask(task);
                            request.setAttribute("NOTIFICATION", "task added!");
                            request.setAttribute("task", task);
                        } catch (SQLException e) {
                            errors.add("error with sql.");
                            LOG.log(Level.SEVERE, null, e);
                        } catch (ClassNotFoundException ex) {
                            LOG.log(Level.SEVERE, null, ex);
                        }
                    }
                } catch (DateTimeParseException e) {
                    errors.add("error parsing.");
                    LOG.log(Level.SEVERE, null, e);
                }
                
                    RequestDispatcher dispatcher = request.getRequestDispatcher("user/taskForm.jsp");
                    dispatcher.forward(request, response);

                break;
            }
            default -> {
                errors.add("invalid action.");
            }
        }

        request.setAttribute("errors", errors);
        RequestDispatcher dispatcher = request.getRequestDispatcher("user/tasks.jsp");
        dispatcher.forward(request, response);

    }

    @Override
    public String getServletInfo() {
        return "Short description";
    }// </editor-fold>

}
