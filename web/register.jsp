<%@ page import="java.util.List" %>
<%@ page import="java.util.ArrayList" %>
<link rel="stylesheet" href="styles/style.css">

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>Register</title>
    </head>
    <body>
    <center>
        <% ArrayList<String> errors = (ArrayList<String>) request.getAttribute("errors");
            
            if(errors != null && !errors.isEmpty()){
            for(String error : errors){
            out.println("<div style='color: red;' class='error-message'>" + error + "</div>");
                }
               errors.clear();
            } %>
            <header> <strong><h1>Register</h1></strong><br></header>
        <form action="Register" method="post">
            email: <input type="email" name="email"><br>
            password: <input type="password" name="password"><Br>
            name: <input type="text" name="name"><Br>
            date of birth <input type="date" name="dob"><Br>
            state: <input type="text" name="state" maxlength="2"><Br>
            <br>
            <input type="submit" value="register" onclick="window.location = 'profile.jsp'"><input type="reset" value="reset"><Br>
            <input type="button" value="back" onclick="window.location = 'home.jsp'">

        </form>
    </center>
</body>
</html>
