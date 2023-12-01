<%@page contentType="text/html" pageEncoding="UTF-8"%>

<%@ page import="java.util.List" %>
<%@ page import="java.util.ArrayList" %>
<%@ page import="controllers.Login" %>


<!DOCTYPE html>
<html>
    <head>
        <link rel="stylesheet" href="styles/style.css"/>

        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>Login or Register</title>
    </head>
    <center>
    <header><h1><strong>Login</strong></h1></header>
        <body>
            <% ArrayList<String> errors = (ArrayList<String>) request.getAttribute("errors");

                if (errors != null && !errors.isEmpty()) {
                    for (String error : errors) {
                        out.println("<div style='color: red;' class='error-message'>" + error + "</div>");
                    }
            }%>

            <form action="Login" method="post" >
                <input type="hidden" name="login" id="login">
                email: <input type="email" name="email"><br>
                password: <input type="password" name="password"><Br>
                <input type="submit" value="login"><input type="reset" value="reset"><br>
                Don't have an account?<br>
                <input type="button" value="register" onclick="window.location = 'register.jsp'">
            </form>
        </body>
    </center>
</html>
