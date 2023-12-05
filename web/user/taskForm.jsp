<%@page import="java.util.ArrayList"%>
<%@page import="db.TaskDAO"%>
<%@page import="java.util.List"%>
<%@page import="business.Tasks"%>
<%@ page import="java.util.logging.Level" %>
<%@ page import="java.util.logging.Logger" %>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<link rel="stylesheet" href="${pageContext.request.contextPath}/styles/style.css">

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

    if (errors == null) {
        errors = new ArrayList<>();
    }
%>

<!DOCTYPE html>
<html>
    <head>

        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>modify or add task</title>
    </head>
    <link rel="stylesheet" href="styles/style.css"/>
    <header>
        <h1><strong>Task Form</strong></h1>
        <nav>
            <ul class="navbar-nav navbar-collapse justify-content-end">
                <li><a href="<%= request.getContextPath()%>/home.jsp" class="nav-link">home</a></li><br>
                <li><a href="<%= request.getContextPath()%>/profile.jsp" class="nav-link">profile</a></li><Br>
                <li><a href="<%= request.getContextPath()%>/tasks.jsp" class="nav-link">task list</a></li>
            </ul>
        </nav>
    </header>
    <center>
        <div class="alert alert-success center" role="alert">
            <p>${NOTIFICATION}</p>
        </div>
        <body>                   
            <i><strong><h3>Task List</h3></strong></i>
            <form action="<%=request.getContextPath()%>/EditTask" method="post">
                <% for (Tasks task : tasks) {%>
                <div class="form-control">
                    <label>title: </label><input type="text" value="<%=task.getTitle()%>" name="title"/><br>
                    <label>description: </label><input type="text" value="<%=task.getDescription()%>" name="description" /><br>
                    <label>timestamp: </label><input type="text" value="<%=task.getTimestamp()%>" name="timestamp" readonly/><br>
                    <label>due date: </label><input type="text" value="<%=task.getDueDate()%>" name="dueDate"/>
                    <input type="submit" value="submit">
                </div>
                <%}%>
            </form>
        </body>
    </center>
    <Br>
    <br>
    <footer>
        <div class="footer">© 2023 - TaskBook</div>
    </footer>
</html>
