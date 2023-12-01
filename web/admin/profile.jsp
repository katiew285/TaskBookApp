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
<link rel="stylesheet" href="${pageContext.request.contextPath}/styles/style.css">



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
                
            <form action="<%= request.getContextPath()%>/Logout" method="get">
            <input type="button" value="all users" onclick="window.location = 'allUsers.jsp'">
                <input type="submit" value="logout">
            </form>

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
