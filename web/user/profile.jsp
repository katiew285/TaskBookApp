<%-- 
    Document   : profile
    Created on : Nov 8, 2023, 8:15:16 PM
    Author     : owner
--%>

<%@page import="business.User"%>
<%@page import="java.util.Set"%>
<%@page import="java.time.LocalDate"%>
<%@page import="java.time.format.DateTimeFormatter"%>
<%@ page import="javax.servlet.http.HttpSession" %>
<%@ page import="javax.servlet.http.HttpServletRequest" %>



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

            <% User user = (User) session.getAttribute("user");
                if(user != null) { %>
                <p>Email: <%= user.getEmail()%></p>
                <p>Name: <%= user.getName()%></p>
                <p>Date of Birth: <%= user.getDob()%></p>
                <p>State: <%= user.getState()%></p>
                <% } else { %>
                <p>User not found</p>
                <% } %>
                
            <p>Roles:</p>
            <ul>
                <% Set<String> roles = user.getRoles(); %>
                <% if (roles != null && !roles.isEmpty()) { %>
                <% for (String role : roles) {%>
                <li><%= role%></li>
                    <% } %>
                    <% } else { %>
                <li>No roles assigned</li>
                    <% } %>
            </ul>
            <a href="tasks.jsp">Tasks</a>
            <a href="logout.jsp">Logout</a>

        </body>
    </center>
</html>

<%!
    String formatDate(LocalDate date) {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("MMM dd, yyyy");
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
