
<%@page import="business.Tasks"%>
<%@page import="java.util.List"%>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<link rel="stylesheet" href="<%= request.getContextPath()%>/styles/style.css">

<% List<Tasks> tasks = (List<Tasks>) request.getAttribute("tasks");%>

<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>user tasks</title>
    </head>
    <header>
        <h1 class="header-text"><strong>user task list</strong></h1>
        <nav>
            <ul class="navbar-nav navbar-collapse justify-content-end">
                <li><a href="<%= request.getContextPath()%>/home.jsp" class="nav-link">home</a></li><Br>
                <li><a href="<%= request.getContextPath()%>/admin/profile.jsp" class="nav-link">profile</a></li><br>
                <li><a href="<%= request.getContextPath()%>/admin/allUsers.jsp" class="nav-link">all users</a></li>
            </ul>
        </nav>
    </header>
    <center>
        <div class="alert alert-success center" role="alert">
            <p>${NOTIFICATION}</p>
        </div>
        <body>
            <div class="container">
                <strong><i><h3>user's tasks</h3></i></strong>
                <table class="table table-bordered">
                    <thead>
                        <tr>
                            <th>Title</th>
                            <th>Description</th>
                            <th>Due Date</th>
                            <th>Timestamp</th>
                            <th>Completed</th>
                        </tr>
                    </thead>
                    <tbody>
                        <% if (tasks != null && !tasks.isEmpty()) { %>
                        <% for (Tasks taskItem : tasks) {%>
                        <tr>
                            <td><%= taskItem.getTitle()%></td>
                            <td><%= taskItem.getDescription()%></td>
                            <td><%= taskItem.getDueDate()%></td>
                            <td><%= taskItem.getTimestamp()%></td>
                            <td><form action="<%= request.getContextPath()%>/admin/userTasks.jsp" method="post">
                                    <input type="hidden" name="taskId" value="<%= taskItem.getId()%>">
                                    <input type="submit" value="delete">
                                </form></td>
                        </tr>
                        <% } %>
                        <% } else { %>
                        <tr>
                            <td colspan="5">No tasks yet!</td>
                        </tr>
                        <% }%>
                    </tbody>
                </table>
            </div>
        </body>
    </center>
    <footer>
        <div class="footer">© 2023 - TaskBook</div>
    </footer>
</html>
