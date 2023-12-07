<%@page import="db.TaskDAO"%>
<%@page import="java.util.List"%>
<%@page import="java.time.LocalDateTime"%>
<%@page import="java.time.format.DateTimeFormatter"%>
<%@page import="java.time.LocalDate"%>
<%@page import="java.util.Iterator"%>
<%@page import="java.util.Map.Entry"%>
<%@page import="controllers.Task"%>
<%@page import="java.util.LinkedHashMap"%>
<%@page import="java.text.SimpleDateFormat"%>
<%@page import="java.util.Date"%>
<%@page import="java.util.ArrayList"%>
<%@page import="business.Tasks"%>
<%@ page import="java.util.logging.Level" %>
<%@ page import="java.util.logging.Logger" %>

<link rel="stylesheet" href="<%= request.getContextPath()%>/styles/style.css">
<%@page contentType="text/html" pageEncoding="UTF-8"%>

<%
    String userEmail = (String) session.getAttribute("userEmail");

    List<Tasks> tasks = (List<Tasks>) request.getAttribute("tasks");
    List<String> errors = (List<String>) request.getAttribute("errors");

    try {
        if (tasks == null || tasks.isEmpty()) {
            tasks = TaskDAO.selectAll(userEmail);
            request.setAttribute("tasks", tasks);
        }
    } catch (ClassNotFoundException e) {
        Logger.getLogger("tasks.jsp").log(Level.SEVERE, "***error in the jsp", e);
        request.setAttribute("NOTIFICATION", "class not found exception.");
    }
%>


<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8"/>
        <title>Your Task Page</title>
    </head>
    <link rel="stylesheet" href="styles/style.css"/>
    <header>
        <h1 class="header-text"><strong>Task List</strong></h1>
        <nav>
            <ul class="navbar-nav navbar-collapse justify-content-end">
                <li><a href="<%= request.getContextPath()%>/home.jsp" class="nav-link">home</a></li>
                <li><a href="<%= request.getContextPath()%>/user/profile.jsp" class="nav-link">profile</a></li>
            </ul>
        </nav>
    </header>
    <div class="alert alert-success center" role="alert">
        <p>${NOTIFICATION}</p>
    </div>
    <center>
        <body>
            <div class="container">
                <h3>List of Tasks</h3>
                <br>
                <% if (tasks != null && !tasks.isEmpty()) { %>
                <% for (Tasks taskItem : tasks) {%>
                <div class="container text-left">
                    <form action="<%= request.getContextPath()%>/user/taskForm.jsp" method="get">
                        <input type="hidden" name="action" value="add task">
                        <input type="hidden" name="taskId" value="<%= taskItem.getId()%>">
                        <input type="submit" value="add task"></form>
                    <form action="<%= request.getContextPath()%>/Tasks" method="post">
                        <input type="hidden" name="action" value="confirmDeleteAll">
                        <input type="hidden" name="taskId" value="<%= taskItem.getId()%>">
                        <input type="submit" value="delete all tasks">
                    </form>
                </div>
                <br>

                <table border="1">
                    <thead>
                        <tr>
                            <th>title</th>
                            <th>description</th>
                            <th>due date</th>
                            <th>timestamp</th>
                            <th>completed</th>
                        </tr>
                    </thead>
                    <tbody>
                        <tr>
                            <td><%= taskItem.getTitle()%></td>
                            <td><%= taskItem.getDescription()%></td>
                            <td><%= taskItem.getDueDate()%></td>
                            <td><%= taskItem.getTimestamp()%></td>
                            <td><form action="<%= request.getContextPath()%>/Tasks" method="post" id="isCompletedForm<%= taskItem.getId()%>">
                                    <input type="hidden" name="action" value="confirmIsCompleted">
                                    <input type="hidden" name="taskId" value="<%= taskItem.getId()%>">
                                    <input type="radio" name="isCompleted" value="false" onclick="document.getElementById('isCompletedForm<%= taskItem.getId()%>').submit();">
                                </form></td>
                            <td><form action="<%= request.getContextPath()%>/Tasks" method="get">
                                    <input type="hidden" name="action" value="edit">
                                    <input type="hidden" name="taskId" value="<%= taskItem.getId()%>">
                                    <input type="submit" value="edit"></form></td>
                            <td> <form action="<%= request.getContextPath()%>/Tasks" method="post">
                                    <input type="hidden" name="action" value="confirmDelete">
                                    <input type="hidden" name="taskId" value="<%= taskItem.getId()%>">
                                    <input type="submit" value="delete">
                                </form></td>
                            </td>
                        </tr>
                        <%}%>
                        <% } else { %>
                        <tr>
                            <td colspan="6"> no tasks yet!</td>
                        </tr>
                        <% }%>
                    </tbody>
                </table>
            </div>
            </div>
        </body>
    </center>
    <footer>
        <div class="footer">© 2023 - TaskBook</div>
    </footer>
</html>
