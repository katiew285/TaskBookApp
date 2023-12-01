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
import java.util.LinkedHashMap;
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
    Connection conn;
    ArrayList<String> errors = new ArrayList<>();

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        
        String action = request.getParameter("action");
        HttpSession session = request.getSession();
        LinkedHashMap<String, Tasks> tasks = null;
        Logger LOG = Logger.getLogger(Task.class.getName());

        try {
            tasks = TaskDAO.selectAll();
            if (tasks == null) {
                errors.add("task list is null.");
            } else if (tasks.isEmpty()) {
                errors.add("task list is empty.");
            }
        } catch (SQLException e) {
            errors.add("error showing all tasks." + e.getMessage());
            LOG.log(Level.SEVERE, null, e);
        }

        session.setAttribute("tasks", tasks);
        request.setAttribute("errors", errors);
        RequestDispatcher dispatcher = request.getRequestDispatcher("user/tasks.jsp");
        dispatcher.forward(request, response);
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        String action = request.getParameter("action");
        HttpSession session = request.getSession();
        String email = (String) session.getAttribute("email");
        String url = "";

        switch (action) {
            case "confirmIsCompleted" -> {
                try {
                    int taskId = Integer.parseInt(request.getParameter("taskId"));
                    TaskDAO.markTaskCompleted(taskId);
                } catch (SQLException ex) {
                    errors.add("error completing task.");
                    Logger.getLogger(Tasks.class.getName()).log(Level.SEVERE, null, ex);
                }
                String jScript = "alert('task completed!')";
                request.setAttribute("javascript", jScript);
                break;
            }
            case "confirmDelete" -> {
                try {
                    int taskIndex = Integer.parseInt(request.getParameter("taskIndex"));
                    TaskDAO.deleteTask(taskIndex);
                } catch (SQLException ex) {
                    errors.add("error deleting task.");
                    LOG.log(Level.SEVERE, null, ex);
                }
                String jScript = "alert('task deleted.')";
                request.setAttribute("javascript", jScript);
                break;
            }
            case "confirmDeleteAll" -> {
                try {
                    TaskDAO.deleteAllTasks();
                } catch (Exception ex) {
                    errors.add("error deleting all tasks.");
                    LOG.log(Level.SEVERE, null, ex);
                }
                String jScript = "alert('All tasks deleted.')";
                request.setAttribute("javascript", jScript);
                break;
            }
            case "addTask" -> {
                try {

                    String title = request.getParameter("title");
                    String description = request.getParameter("description");
                    String timestampString = request.getParameter("timestamp");
                    String dueDateString = request.getParameter("dueDate");
                    
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

                    LocalDate dueDate = null;
                    DateTimeFormatter output = DateTimeFormatter.ofPattern("MM/dd/yyyy");

                    if (dueDateString != null && !dueDateString.isEmpty()) {
                        try {
                            dueDate = LocalDate.parse(dueDateString);

                            if (dueDate.isBefore(LocalDate.now())) {
                                isValid = false;
                                errors.add("Due date should not be before today.");
                            }
                        } catch (DateTimeParseException e) {
                            isValid = false;
                            errors.add("Invalid due date format.");
                            LOG.log(Level.SEVERE, null, e);
                        }
                    } else {
                        isValid = false;
                    }

                    String formattedDueDate = (dueDate != null) ? dueDate.format(output) : null;

                    if (isValid) {
                        Tasks task = new Tasks();

                        task.setEmail(email);
                        task.setTitle(title);
                        task.setDescription(description);
                        task.setTimestamp(timestamp);
                        task.setDueDate(dueDate);
                        try {
                            TaskDAO.addTask(task);
                            request.setAttribute("task", task);
                            url = "/user/tasks.jsp";
                        } catch (SQLException e) {
                            errors.add("error with sql.");
                            url = "/user/tasks.jsp";
                            LOG.log(Level.SEVERE, null, e);
                            e.printStackTrace();
                            System.out.println(e);
                                    }
                    }
                } catch (DateTimeParseException e) {
                    errors.add("error parsing.");
                    LOG.log(Level.SEVERE, null, e);
                }
                break;
            }
            default -> {
                errors.add("invalid action.");
            }
        }

//        LinkedHashMap<String, Tasks> updatedTasks = null;
//        try {
//            updatedTasks = TaskDAO.selectAll();
//        } catch (SQLException ex) {
//            errors.add("error retrieving tasks.");
//            Logger.getLogger(Tasks.class
//                    .getName()).log(Level.SEVERE, null, ex);
//        }
        // request.setAttribute("updatedTasks", updatedTasks);
        request.setAttribute("errors", errors);
        RequestDispatcher dispatcher = request.getRequestDispatcher("user/tasks.jsp");
        dispatcher.forward(request, response);

    }

    @Override
    public String getServletInfo() {
        return "Short description";
    }// </editor-fold>

}
