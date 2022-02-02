package dev.crawford.servlets;

import dev.crawford.models.User;
import dev.crawford.repositories.UserDAO;
import dev.crawford.services.AuthService;

import javax.servlet.ServletException;
import javax.servlet.http.*;
import java.io.IOException;


public class RegisterServlet extends HttpServlet {
    
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

        String firstName = request.getParameter("firstName");
        String lastName = request.getParameter("lastName");
        String username = request.getParameter("username");
        String password = request.getParameter("password");

        // Add user to database
        User user = UserDAO.getByUsername(username);
        if(user == null) {
            try {
                User newUser = new User(0, firstName, lastName, username, password, "EMPLOYEE");
                AuthService.register(newUser);
            } catch (Exception e) {
                System.out.println("User creation unsuccessful");
            }   
        }
        System.out.println("Username already exists");
        // Redirect to login page
        response.sendRedirect("index.html");

    }
}