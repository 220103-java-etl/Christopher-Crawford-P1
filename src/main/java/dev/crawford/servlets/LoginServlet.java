package dev.crawford.servlets;

import dev.crawford.models.User;
import dev.crawford.repositories.UserDAO;
import dev.crawford.services.AuthService;

import javax.servlet.ServletException;
import javax.servlet.http.*;
import java.io.IOException;


public class LoginServlet extends HttpServlet {

    private static AuthService authService = new AuthService();
    
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

        String username = request.getParameter("username");
        String password = request.getParameter("password");
        
        User u = new User();
        u.setUsername(username);
        u.setPassword(password);

        try {
            // check if user is valid
            User userAuth = authService.login(username, password);
            if(userAuth != null) {
                // Create an http session with that user info
                HttpSession session = request.getSession();

                // Set the session object
                User user = UserDAO.getByUsername(username);
                session.setAttribute("user", user);

                User userRole = UserDAO.getByUsername(username);
                String role = userRole.getRole();
                if(role.equals("EMPLOYEE")) { 
                    response.sendRedirect("employee.html");
                } else {
                    response.sendRedirect("manager.html");
                }
            } else {
                response.sendRedirect("index.html");
            }
        } catch (Exception e) {
            System.out.println("Invalid username or password");
        }
    }
}