<%@ page import="java.util.List" %>
<%@ page import="java.util.ArrayList" %>
<link rel="stylesheet" href="styles/style.css">

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>Register Page</title>
   <header>
            <h1 class="header-text"><strong>Register</strong></h1>
       <nav>
            <ul class="navbar-nav navbar-collapse justify-content-end">
                <li><a href="<%= request.getContextPath()%>/home.jsp" class="nav-link">home</a></li><br>
                <li><a href="<%= request.getContextPath()%>/login.jsp" class="nav-link">login</a></li>
            </ul>
        </nav>
    </header>
    <body>
    <center>
        
                <div class="alert alert-success center" role="alert">
                    <p>${NOTIFICATION}</p>
                </div>
                <strong><i><h3>user registration</h3></i></strong>
                <form action="<%=request.getContextPath()%>/Register" method="post">
                    <div class="form-group">
                        <label>email:</label> <input type="text" class="form-control" id="email" name="email">
                    </div>
                    <div class="form-group">
                        <label>password:</label> <input type="password" class="form-control" id="password" name="password" >
                    </div>
                    <div class="form-control">
                        <label>name:</label> <input type="text" class="form-control" id="name" name="name">
                    </div>
                    <div class="form-control">
                        <label>date of birth</label> <input type="date" class="form-control" id="dob" name="dob">
                    </div>
                    <div class="form-control">
                        <label>state</label> <input type="text" class="form-control" id="state" name="state">
                    </div>
                    
                    <button type="submit" class="btn btn-primary">register</button>

                </form>
            </div>
        </div>
        
    </center>
</body>
<footer>
    <div class="footer">© 2023 - TaskBook</div>
</footer>
</html>
