package com.gallery.controllers;

import com.gallery.controllers.exception.InvalidCredentials;
import services.LoginService;

import javax.mail.Session;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;

@WebServlet(urlPatterns = "/login")
public class LoginServlet  extends HttpServlet {
    @Override
    public void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

        request.getRequestDispatcher("/WEB-INF/views/login.jsp").forward(
                request, response);
    }

    @Override
    public void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        LoginService loginService = LoginService.getInstance();
        try {
            String role = loginService.getRole(request.getParameter("username"), request.getParameter("password"));
            HttpSession session= request.getSession(false);
            session.setAttribute("role",role);
            session.setAttribute("logged",true);
            if (role.equals("user")){
                response.sendRedirect("/User/userHome");
            }if (role.equals("admin")){
                response.sendRedirect("/Admin/adminHome");
            }

        }catch (InvalidCredentials e){
            request.setAttribute("errorMessage", "Invalid Credentials!!");
            request.getRequestDispatcher("/WEB-INF/views/login.jsp").forward(
                    request, response);
            e.printStackTrace();
        }catch (IOException e){
            e.printStackTrace();
        }

    }

}
