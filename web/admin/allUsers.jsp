<%@page import="db.UserDAO"%>
<%@page import="java.util.List"%>
<%@page import="java.util.LinkedHashMap"%>
<%@page import="business.User"%>
<%@ page import="java.util.logging.Level" %>
<%@ page import="java.util.logging.Logger" %>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<link rel="stylesheet" href="${pageContext.request.contextPath}/styles/style.css">


<%
    List<User> users = (List<User>) request.getAttribute("users");

    try {
        if (users == null || users.isEmpty()) {
            users = UserDAO.selectAll();

            if (users != null && !users.isEmpty()) {
                request.setAttribute("users", users);
            } else {
                request.setAttribute("NOTIFICATION", "no users");
            }
        }
    } catch (ClassNotFoundException e) {
        Logger.getLogger("tasks.jsp").log(Level.SEVERE, "***error in the jsp", e);
        request.setAttribute("NOTIFICATION", "class not found exception.");
    }


%>

<!DOCTYPE html>
<html>
    <head>
        <title>All Users</title>
    </head>
    <header>
        <h3 class="header-text"><strong><i>All Users</i></strong></h3>
        <nav>
            <ul class="navbar-nav navbar-collapse justify-content-end">
                <li><a href="<%= request.getContextPath()%>/home.jsp" class="nav-link">home</a></li>
                <li><a href="<%= request.getContextPath()%>/admin/profile.jsp" class="nav-link">profile</a></li>
            </ul>
        </nav>
    </header>
    <body>
        <strong><i><h3>All Users</h3></i></strong><Br>
        <div class="alert alert-success center" role="alert">
            <p>${NOTIFICATION}</p>
        </div>
        <table border="1">
            <thead>
                <tr>
                    <th>id</th>
                    <th>email</th>
                    <th>name</th>
                    <th>date of Birth</th>
                    <th>state</th>
                </tr>
            </thead>
            <tbody>
                <%
                    if (users != null) {
                        for (User user : users) {%>
                <tr>
                    <td><%= user.getId()%></td>
                    <td><%= user.getEmail()%></td>
                    <td><%= user.getName()%></td>
                    <td><%= user.getDob()%></td>
                    <td><%= user.getState()%></td>
                    <td>
                        <form action="<%= request.getContextPath()%>/admin/userTasks.jsp" method="get">
                            <input type="hidden" name="userId" value="<%= user.getId()%>">
                            <input type="submit" value="view tasks" onclick="<%=request.getContextPath()%>/admin/userTasks.jsp">
                        </form>
                        <form action="<%= request.getContextPath()%>/admin/deleteUser" method="post">
                            <input type="hidden" name="userId" value="<%= user.getId()%>">
                            <input type="submit" value="delete">
                        </form>
                    </td>
                </tr>
                <% }%>
                <%}%>
            </tbody>
        </table>
    </body>
    <footer>
        <div class="footer">© 2023 - TaskBook</div>
    </footer>
</html>
