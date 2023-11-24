package controllers;

import java.io.IOException;
import java.io.PrintWriter;
import java.net.URLDecoder;
import java.net.URLEncoder;

import java.util.ArrayList;
import javax.servlet.ServletException;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;


public class Controller extends HttpServlet {

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        String url = "/user/tasks.jsp";
        
        String action = request.getParameter("action");
        if (action == null) {
            action = "listTask";
        }
        
        switch (action) {
            case "listTask":
                ArrayList<String> task = setupTasks(request);
                request.getSession().setAttribute("task", task);
                break;
            case "addTask":
                url = addTask(request, response);
                break;
            case "deleteTask":
                url = deleteTask(request, response);
                break;
            case "deleteAll":
                url = deleteAll(request, response);
                break;
            case "storeCookie":
                url = storeCookie(request, response);
                break;
            case "recallCookie":
                url = recallCookie(request, response);
                break;
        }

        getServletContext().getRequestDispatcher(url).forward(request, response);
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        doPost(request, response);
    }

    protected ArrayList<String> setupTasks(HttpServletRequest request) {
        HttpSession sess = request.getSession();
        ArrayList<String> task = (ArrayList) sess.getAttribute("task");
        if (task == null) {
            task = new ArrayList();
            task.add("This is where your tasks go!");
        }
        return task;
    }

    protected String addTask(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        ArrayList<String> task = setupTasks(request);

        String taskItem = request.getParameter("taskItem");
        if ("".equals(taskItem)) {
            request.setAttribute("message", "Task must not be empty");
        } else {
            task.add(taskItem);
        }

        request.getSession().setAttribute("task", task);

        return "/user/tasks.jsp";
    }

    protected String deleteTask(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        ArrayList<String> task = setupTasks(request);
        String indexString = request.getParameter("taskIndex");
        String message = "";
        try {
            int index = Integer.parseInt(indexString);
            task.remove(index);
        } catch (NumberFormatException e) {
            message = "Invalid index number";
        }
        request.setAttribute("message", message);

        request.getSession().setAttribute("task", task);
        return "/user/tasks.jsp";

    }

    protected String deleteAll(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        ArrayList<String> task = setupTasks(request);

        task.clear();

        request.getSession().setAttribute("task", task);
        return "/user/tasks.jsp";

    }

    protected String storeCookie(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        ArrayList<String> task = setupTasks(request);

        String delim = "|";
        String all = "";
        for (String temp : task) {
            all += temp + delim;
        }
        Cookie cookie = new Cookie("taskList", URLEncoder.encode(all, "UTF-8"));
        cookie.setMaxAge(60 * 60 * 24 * 365 * 2);
        response.addCookie(cookie);

        request.getSession().setAttribute("task", task);
        return "/user/tasks.jsp";

    }

    protected String recallCookie(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        ArrayList<String> task = setupTasks(request);

        Cookie[] cookies = request.getCookies();
        for (Cookie cookie : cookies) {
            if (cookie.getName().equals("taskList")) {
                String all = URLDecoder.decode(cookie.getValue(), "UTF-8");
                String[] values = all.split("\\|");
                task.clear();
                for (String temp : values) {
                    task.add(temp);
                }
            }
        }
        request.getSession().setAttribute("task", task);
        return "/user/tasks.jsp";

    }

    @Override
    public String getServletInfo() {
        return "Short description";
    }

}