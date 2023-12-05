<%@page contentType="text/html" pageEncoding="UTF-8"%>

<%@ page import="java.util.List" %>
<%@ page import="java.util.ArrayList" %>
<%@ page import="controllers.Login" %>
<%@ page import="db.LoginDAO" %>


<!DOCTYPE html>
<html>
    <head>
        <link rel="stylesheet" href="styles/style.css"/>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>Login Page</title>
    </head>

    <header>
        <h1 class="header-text"><strong>Login</strong></h1>
        <nav>
            <ul class="navbar-nav navbar-collapse justify-content-end">
                <li><a href="<%= request.getContextPath()%>/home.jsp" class="nav-link">home</a></li><br>
                <li><a href="<%= request.getContextPath()%>/register.jsp" class="nav-link">register</a></li>
            </ul>
        </nav>
    </header>
    <center>
        <body>
            <div class="alert alert-success center" role="alert">
                <p>${NOTIFICATION}</p>
            </div>
            <strong><i><h3>please log in</h3></i></strong>
            <form action="<%=request.getContextPath()%>/Login" method="post">
                <div class="form-group">
                    <label>email:</label> <input type="text" class="form-control" id="email" name="email">
                </div>
                <div class="form-group">
                    <label>Password:</label> <input type="password" class="form-control" id="password" name="password">
                </div>
                <button type="submit" class="btn btn-primary">Submit</button>
            </form>
        </body>
    </center>
    <footer>
        <div class="footer">© 2023 - TaskBook</div>
    </footer>
</html>
