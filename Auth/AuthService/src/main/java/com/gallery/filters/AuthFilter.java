package com.gallery.filters;

import javax.servlet.*;
import javax.servlet.annotation.WebFilter;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;

@WebFilter(urlPatterns = "/*")
public class AuthFilter implements Filter {
    private FilterConfig filterConfig;
    @Override
    public void init(FilterConfig filterConfig) throws ServletException {
        this.filterConfig = filterConfig;
    }

    @Override
    public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain) throws IOException, ServletException {

        HttpServletRequest httpRequest = (HttpServletRequest) servletRequest;
        HttpServletResponse httpResponse = (HttpServletResponse) servletResponse;
        HttpSession session = httpRequest.getSession(false);
        boolean isLoggedIn = (session != null && session.getAttribute("logged") != null);
        boolean isLoginRequest = httpRequest.getRequestURI().equals("/Auth/login");


        if (isLoggedIn && isLoginRequest) {
            // the user is already logged in and he's trying to login again
            // then forward to the homepage
            String role = (String) session.getAttribute("role");
            if (role.equals("user")) {

                httpResponse.sendRedirect("/User/userHome");
            }
            if (role.equals("admin")) {
                httpResponse.sendRedirect("/Admin/adminHome");
            }

        } else if (!isLoggedIn && !isLoginRequest) {

            // the user is not logged in, and the requested page requires
            // authentication, then forward to the login page
            httpResponse.sendRedirect("/Auth/login");
        } else {
            // for other requested pages that do not require authentication
            // or the user is already logged in, continue to the destination
            filterChain.doFilter(servletRequest, servletResponse);
        }
    }

    @Override
    public void destroy() {

    }
}