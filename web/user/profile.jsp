<%@page import="db.UserDAO"%>
<%@ page import="java.util.logging.Level" %>
<%@ page import="java.util.logging.Logger" %>
<%@page import="java.util.List"%>
<%@page import="java.util.ArrayList"%>
<%@page import="business.User"%>
<%@page import="java.util.Set"%>
<%@ page import="javax.servlet.http.HttpSession" %>
<%@ page import="javax.servlet.http.HttpServletRequest" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@page contentType="text/html" pageEncoding="UTF-8"%>

<%
    String userEmail = (String) session.getAttribute("userEmail");
    User user = null;
    
    try{
        user = UserDAO.getUserByEmail(userEmail);
    } catch (ClassNotFoundException e){
    Logger.getLogger("profile.jsp").log(Level.SEVERE, null, e);
    }
    
        List<String> errors = (List<String>) request.getAttribute("errors");

        if (errors == null) {
            errors = new ArrayList<>();
        }
%>

<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>Profile Page</title>
    </head>

    <header>
        <link rel="stylesheet" href="<%= request.getContextPath()%>/styles/style.css">

        <h1 class="header-text"><strong>Profile</strong></h1><br>
        <h3></h3>
        <nav>
            <ul class="navbar-nav navbar-collapse justify-content-end">
                <li><a href="<%=request.getContextPath()%>/home.jsp" class="btn btn-primary">home</a></li><br>
                <li><a href="<%= request.getContextPath()%>/user/tasks.jsp" class="nav-link">tasks</a></li>
            </ul>
        </nav>
    </header>
    <body>
    <center>
        <%if (user != null) {%>
        <h4><%=user.getName()%>'s Profile</h4>
        <div class="container">
            <p><label>email:</label> <%=user.getEmail()%></p>
            <p><label>password:</label> <%=user.getPassword()%></p>
            <p><label>name:</label> <%=user.getName()%></p>
            <p><label>date of birth: </label><%=user.getDob()%></p>
            <p><label>state of residence: </label> <%=user.getState()%></p>
        </div>
        <% } else { %>
        <div class="container">
            <p>user not found</p>
        </div>
        <% }%>
        <input type="button" value="editProfile" onclick="window.location = 'editProfile.jsp'">
    </center>

   
</body><br><Br>
<footer>
    <div class="footer">© 2023 - TaskBook</div>
</footer>
</html>
