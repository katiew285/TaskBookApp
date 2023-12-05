<%@page contentType="text/html" pageEncoding="UTF-8"%>

<%@ page import="java.util.List" %>
<%@ page import="java.util.ArrayList" %>
<%@ page import="controllers.Login" %>


<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>TaskBook Welcome Page</title>
    </head>
    <link rel="stylesheet" href="<%= request.getContextPath()%>/styles/style.css">
    <header>
        <h1 class="header-text"><strong>Welcome to TaskBook!</strong></h1>
        <nav>
            <ul class="navbar-nav navbar-collapse justify-content-end">
                <li><a href="<%= request.getContextPath()%>/login.jsp" class="nav-link">login</a></li><br>
                <li><a href="<%= request.getContextPath()%>/register.jsp" class="nav-link">register</a></li>
            </ul>
        </nav>
    </header>
    <body>
        <div class="container">
            <i><p>please register or log in to access content.</p></i>
        </div>
    </body>
    <footer>
        <div class="footer">© 2023 - TaskBook</div>
    </footer>
</html>
