<%@page import="java.text.SimpleDateFormat"%>
<%@page import="java.util.Date"%>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@page import="java.util.ArrayList"%>

<%
    HttpSession sess = request.getSession();
    ArrayList<String> task = (ArrayList) sess.getAttribute("task");
    String message = (String) request.getAttribute("message");
    if(message == null) {
        message = "";
    }

%>

<%
    Date currentDate = new Date();
    SimpleDateFormat dateFormat = new SimpleDateFormat("MM/dd/yyyy HH:mm");
    String formattedDate = dateFormat.format(currentDate);
    %>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>Your Task Page</title>
    </head>
    <body>
        <h1><u><i>Your Tasks:</i></u></h1>
        
        <h2><%= message %></h2>
        
        <ol>
            <% for (int i = 0; i < task.size(); i++) {%>
            <li>
                <%= task.get(i) %>
                
                <input type="radio" name="deleteRadio" onclick="confirmDelete(<%= i%>)"> Completed
                
                <form action="Controller" method="post" id="deleteTaskForm">
                    <input type="hidden" name="action" id="taskToDelete" value="deleteTask">
                    <input type="hidden" name="taskIndex" value="taskIndex" id="taskIndex">
                    <input type="submit" value="delete" id="taskToDelete">
                </form>
            </li> 
            <% }%>
        </ol>
        
        <a href="Controller?action=deleteAll">Delete All</a>
        <a href="Controller?action=storeCookie">Store list to cookie</a>
        <a href="Controller?action=recallCookie">Recall list from cookie</a>

        <h1>Add Task</h1>
        <form action="Controller" method="post">
            <input type="hidden" name="action" value="addTask">
            <input type="text" name="title" value="title">
            <input type="text" name="description" value="description">
            <input type="text" name="timestamp" value="<%= formattedDate %>" readonly/>
            <input type="date" name="dueDate" value="dueDate">
            
            <input type="submit" value="add task">
        </form>
    </body>
</html>
