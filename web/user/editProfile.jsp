<%@page import="db.UserDAO"%>
<%@ page import="java.util.List" %>
<%@ page import="java.util.ArrayList" %>
<%@ page import="business.User" %>
<%@ page import="java.util.logging.Logger" %>
<%@ page import="java.util.logging.Level" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>

<% String NOTIFICATION = (String) request.getAttribute("NOTIFICATION");
    String userEmail = (String) session.getAttribute("userEmail");

    List<String> errors = new ArrayList<>();
    User user = null;

    if (userEmail != null && !userEmail.isEmpty()) {
        try {
            user = UserDAO.getUserByEmail(userEmail);
        } catch (Exception e) {
            Logger.getLogger("editProfile").log(Level.SEVERE, null, e);
        }

        if (user == null) {
            errors.add("user not found");
        }
    } else {
        errors.add("user email is null or empty");
    }
%>


<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>Edit User Profile</title>
    </head><header>
        <link rel="stylesheet" href="<%= request.getContextPath()%>/styles/style.css">

        <h1 class="header-text"><strong>Task Form</strong></h1>
        <nav>
            <ul>
                <li><a href="<%= request.getContextPath()%>/home.jsp" class="nav-link">home</a></li><br>
                <li><a href="<%= request.getContextPath()%>/user/profile.jsp" class="nav-link">profile</a></li><br>
                <li><a href="<%= request.getContextPath()%>/user/tasks.jsp" class="nav-link">task list</a></li>
            </ul>
        </nav>
    </header>
    <body>
    <center>
        <div class="alert alert-success center" role="alert">
            <p>${NOTIFICATION}</p>
        </div>
        <div class="container">
            <% if (!errors.isEmpty()) {
                    for (String error : errors) {%>
            <p><%=error%></p>
            <% }
            } else {%>
            <h4>Edit profile</h4><br>
            <form action="<%=request.getContextPath()%>/EditProfile" method="post">
                <label>email:</label>
                <input type="text" id="email" name="email" value="<%=user.getEmail()%>" readonly><br>
                <label>password:</label>
                <input type="text" id="password" name="password" value="<%=maskedPassword(user.getPassword())%>"><br>
                <label>name:</label>
                <input type="text" id="name" name="name" value="<%=user.getName()%>"><br>
                <label>date of birth:</label>
                <input type="date" id="dob" name="dob" value="<%=user.getDob()%>"><br>
                <label>state of residence:</label>
                <input type="text" id="state" name="state" value="<%=user.getState()%>"><br>
                <br>
                <input type="submit" value="update">
            </form>
            <% }%>
        </div>
    </center>
</body>

    <%! String maskedPassword(String password) {
            if (password != null) {
                int length = password.length();
                StringBuilder masked = new StringBuilder();
                for (int i = 0; i < length; i++) {
                    masked.append("*");
                }
                return masked.toString();
            } else {
                return "";
            }
        }%>
        
<footer>
    <div class="footer">© 2023 - TaskBook</div>
</footer>
</html>
