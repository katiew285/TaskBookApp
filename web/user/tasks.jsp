<%@page import="java.time.LocalDateTime"%>
<%@page import="java.time.format.DateTimeFormatter"%>
<%@page import="java.time.LocalDate"%>
<%@page import="java.util.Iterator"%>
<%@page import="java.util.Map.Entry"%>
<%@page import="controllers.Task"%>
<%@page import="java.util.LinkedHashMap"%>
<%@page import="java.text.SimpleDateFormat"%>
<%@page import="java.util.Date"%>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@page import="java.util.ArrayList"%>
<%@page import="business.Tasks"%>
<link rel="stylesheet" href="${pageContext.request.contextPath}/styles/style.css">





<!DOCTYPE html>
<html>
    <head>
        <script src="https://code.jquery.com/jquery-3.6.4.min.js"></script>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>Your Task Page</title>
        <script>
            function confirmIsCompleted() {
                return confirm("are you sure you want to mark this task as completed?\nthe task will be deleted.");
            }

            function confirmDelete() {
                return confirm("are you sure you want to delete this task?");
            }

            function confirmDeleteAll() {
                return confirm("are you sure you want to delete all tasks?");
            }


            // Function to format the current date and time in 12-hour format
            function getCurrentDateTime() {
                var currentDate = new Date();
                var formattedDate = currentDate.toISOString().slice(0, 19).replace("T", " ");
                return formattedDate;
            }

            // Set the current date and time when the page loads
            document.addEventListener("DOMContentLoaded", function () {
                var timestampInput = document.getElementById("timestamp");
                timestampInput.value = getCurrentDateTime();
            });

            // Set the current date and time when the page loads
            document.addEventListener("DOMContentLoaded", function () {
                var timestampInput = document.getElementById("timestamp");
                timestampInput.value = getCurrentDateTime();
            });
        </script>
        <%
            Date currentDate = new Date();
            SimpleDateFormat dateFormat = new SimpleDateFormat("MM/dd/yyyy HH:mm");
            String formattedDate = dateFormat.format(currentDate);

            ArrayList<String> errors = (ArrayList<String>) request.getAttribute("errors");
            LinkedHashMap<String, Tasks> tasks = (LinkedHashMap<String, Tasks>) session.getAttribute("tasks");
        %>
        <%  if (errors != null && !errors.isEmpty()) {
                out.println("<div style='color: red;' class='error-message'>");
                Iterator<String> errorIterator = errors.iterator();
                while (errorIterator.hasNext()) {
                    out.println(errorIterator.next() + "<br>");
                }
                out.println("</div>");
            } %>    
    </head>
    <center>
        <body>

            <header><h1><u><i>Your Tasks:</i></u></h1></header>
            <ul>

                <%if (tasks != null) { %>
                <% for (Tasks task : tasks.values()) {%>
                <li>
                    <strong><%=task.getTitle()%></strong> - <%= task.getDueDate() != null ? task.getDueDate() : ""%><br>
                    Description: <%= task.getDescription()%><br>
                    <input type="radio" name="deleteRadio" onclick="return confirmIsCompleted()"> Completed?
                    Timestamp: <%= task.getTimestamp()%>><Br>
                    <form action="Task" method="post" id="deleteTaskForm" onsubmit="return confirmDelete();">
                        <input type="hidden" name="action" value="deleteTask">
                        <input type="hidden" name="taskIndex" value="<%=task.getId()%>">
                        <input type="submit" value="delete">
                    </form>
                    <%}%>
                    <% }%>
                    </form>
            </ul>
            <br>
            <a href="Controller?action=deleteAll" onclick="return confirmDeleteAll();">Delete All</a>
            <br>
            <br>
            <br>
            <br>
            <h1>Add Task</h1>
            <form action="<%=request.getContextPath()%>/Task" method="post" id="addTaskForm">
                <input type="hidden" name="action" value="addTask"><Br>
                <label>Task:</label><input type="text" name="title"><br>
                <label>Description:</label><input type="text" name="description"><br>
                <label>Due Date (not required):</label><input type="date" name="dueDate" id="dueDate" placeholder="yyyy-mm-dd"><br>
                <label>Timestamp:</label><input type="text" name="timestamp" id="timestamp" readonly><br>            
                <input type="submit" value="add task" id="addTask">
            </form>
            <form action="<%= request.getContextPath()%>/Logout" method="get">
            <input type="button" value="profile" onclick="window.location = 'profile.jsp'">
                <input type="submit" value="logout">
            </form>
        </body>
    </center>
</html>
