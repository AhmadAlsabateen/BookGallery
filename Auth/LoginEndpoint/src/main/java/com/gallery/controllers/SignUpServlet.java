package com.gallery.controllers;

import com.gallery.controllers.exception.InvalidCredentials;
import services.RegistrationService;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;

@WebServlet(urlPatterns = "/signup")
public class SignUpServlet extends HttpServlet {
    @Override
    public void doPost(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException {
        if (isValidSignUpRequest(request)) {
            String username = request.getParameter("username");
            String password = request.getParameter("password");
            String password2 = request.getParameter("password2");


            try {
                RegistrationService registrationService = RegistrationService.getInstance();

                registrationService.registerUser(username, password, password2, "user");
            } catch (InvalidCredentials e) {
                e.printStackTrace();
                request.setAttribute("signUpErrorMessage", "password don't mach password2!!");
                request.getRequestDispatcher("/WEB-INF/views/login.jsp").forward(request, response);
            }
            HttpSession session = request.getSession(true);
            session.setAttribute("role", "user");
            response.sendRedirect("/User/userHome");
        } else {
            System.out.println("Invalid Sign up request : " +request.getParameterMap().toString());
            request.setAttribute("signUpErrorMessage", "Invalid Credentials!!");
            request.getRequestDispatcher("/WEB-INF/views/login.jsp").forward(request, response);
        }

    }

    private boolean isValidSignUpRequest(HttpServletRequest request) {

        return (request.getParameterMap().containsKey("username") && request.getParameterMap().containsKey("password") &&
                request.getParameterMap().containsKey("password2") &&
                (request.getParameter("username") != null && request.getParameter("password") != null &&
                        request.getParameter("password2") != null));
    }
}
