package dev.crawford.servlets;

import dev.crawford.models.Reimbursement;
import dev.crawford.models.Status;
import dev.crawford.models.User;
import dev.crawford.repositories.ReimbursementDAO;

import javax.servlet.ServletException;
import javax.servlet.http.*;
import java.io.IOException;
import java.io.PrintWriter;


public class RequestFormServlet extends HttpServlet {
    
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        response.setContentType("text/html");
        
        HttpSession session = request.getSession(false);
        
        // Add reimbursement to database
        if(session == null){
            // no session, so we can't get the user object
            PrintWriter out = response.getWriter();
            out.write("<h3>You should access this from the login page only</h3>");
            out.write("<a href='index.html'>Click Here</a>");
        } else {
            String date = request.getParameter("date");
            String time = request.getParameter("time");
            String location = request.getParameter("location");
            String description = request.getParameter("description");
            Double cost = Double.parseDouble(request.getParameter("cost"));
            String justification = request.getParameter("justification");
            Double courseType = Double.parseDouble(request.getParameter("courseType"));

            Double costPercentage = Double.parseDouble(request.getParameter("courseType"));

            User author = (User) session.getAttribute("user");
            
            Status status = Status.PENDING;

            String grade = null;

            User resolver = new User();
        try {
            Reimbursement newReimbursement = new Reimbursement(0, status, author, resolver, cost, date, time, location, description, justification, courseType, grade);
            ReimbursementDAO.create(newReimbursement);
            ReimbursementDAO.updateAllowance(newReimbursement, costPercentage);
            response.sendRedirect("employee.html"); 
        } catch (Exception e) {
            e.printStackTrace();
        }
    }    
    }
}
