package dev.crawford.servlets;

import dev.crawford.models.Reimbursement;
import dev.crawford.models.Status;
import dev.crawford.models.User;
import dev.crawford.repositories.ReimbursementDAO;

import javax.servlet.ServletException;
import javax.servlet.http.*;
import java.io.IOException;
import java.io.PrintWriter;


public class UpdateRequestServlet extends HttpServlet {
    
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

        HttpSession session = request.getSession(false);
        
        // Update reimbursement in database
        if(session == null){
            // no session, so we can't get the user object
            PrintWriter out = response.getWriter();
            out.write("<h3>You should access this from the login page only</h3>");
            out.write("<a href='index.html'>Click Here</a>");
        } else {

            Double cost = Double.parseDouble(request.getParameter("cost"));
            String grade = request.getParameter("grade");
            int reimbursementId = Integer.parseInt(request.getParameter("requestId")) ;
            
            User author = (User) session.getAttribute("user");
            
            Status status = Status.PENDING;

            User resolver = new User();
        try {
            Reimbursement newReimbursement = new Reimbursement(reimbursementId, status, author, resolver, cost, grade);
            System.out.println(newReimbursement);
            ReimbursementDAO.update(newReimbursement);
            response.sendRedirect("employee.html"); 
        } catch (Exception e) {
            e.printStackTrace();
        }
    }    
    }
}