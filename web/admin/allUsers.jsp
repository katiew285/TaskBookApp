<%@page import="java.util.LinkedHashMap"%>
<%@page import="business.User"%>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<link rel="stylesheet" href="${pageContext.request.contextPath}/styles/style.css">


<!DOCTYPE html>
<html>
    <head>
        <title>All Users</title>
    </head>
    <body>
        <h1>All Users</h1>
        <table border="1">
            <thead>
                <tr>
                    <th>ID</th>
                    <th>Email</th>
                    <th>Name</th>
                    <th>Date of Birth</th>
                    <th>State</th>
                </tr>
            </thead>
            <tbody>
                <%
                    LinkedHashMap<String, User> users = (LinkedHashMap<String, User>) request.getAttribute("users");
                    if (users != null) {
                        for (User user : users.values()) {
                %>
                <tr>
                    <td><%= user.getId()%></td>
                    <td><%= user.getEmail()%></td>
                    <td><%= user.getName()%></td>
                    <td><%= user.getDob()%></td>
                    <td><%= user.getState()%></td>
                </tr>
                <% } %>
                <% }%>

            </tbody>
        </table>
    </body>
</html>
