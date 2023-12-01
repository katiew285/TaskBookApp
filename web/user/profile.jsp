<%@page import="business.User"%>
<%@page import="java.util.Set"%>
<%@page import="java.time.LocalDate"%>
<%@page import="java.time.format.DateTimeFormatter"%>
<%@ page import="javax.servlet.http.HttpSession" %>
<%@ page import="javax.servlet.http.HttpServletRequest" %>
<link rel="stylesheet" href="../styles/style.css">

<%@page contentType="text/html" pageEncoding="UTF-8"%>

<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>Profile Page</title>
    </head>
    <center>
        <body>

            <h1> <%= getUserDisplayName(request)%> Profile Page </h1>
            <div class="profile-container">
            <% User user = (User) session.getAttribute("user");
                if (user != null) {%>
            <p>Email: <%= user.getEmail()%></p>
            <p>Name: <%= user.getName()%></p>
            <p>Date of Birth: <%= user.getDob()%></p>
            <p>State: <%= user.getState()%></p>
            <% } else { %>
            <p>User not found</p>
            <% } %>
            </div>
           
            <form action="<%= request.getContextPath() %>/Logout" method="get">
           <input type="button" value="tasks" onclick="window.location = 'tasks.jsp'">
                <input type="submit" value="logout">
            </form>

        </body>
    </center>
</html>

<%!
    String formatDate(LocalDate date) {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("MMM/dd/yyyy");
        return date.format(formatter);
    }

    String getUserDisplayName(HttpServletRequest request) {
        HttpSession session = request.getSession();
        User user = (User) session.getAttribute("user");
        return (user != null) ? user.getName() : "User";
    }
%>

<%
    String contextPath = pageContext.getServletContext().getContextPath();
    String userDisplayName = getUserDisplayName(request);
%>
