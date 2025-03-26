package com.Servlet.User;


import com.DAO.UserDAO;
import com.db.DBConnection;
import com.entity.User;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;


@WebServlet("completeProfile")
public class CompleteProfile extends HttpServlet {

    public CompleteProfile() {
        super();
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        try {
            String name = request.getParameter("Name");
            String email = request.getParameter("Email");
            String password = request.getParameter("Password");
            String phone = request.getParameter("Phone");
            String changeReferralCode = request.getParameter("Your Referral Code");

            HttpSession session = request.getSession(false); // don't create a new session
            if (session != null) {
                if (email.equals(session.getAttribute("email")) && password.equals(session.getAttribute("password"))) {
//                    DB CONNECTION
                    UserDAO userDAO = new UserDAO(DBConnection.getConnection());
//                    GET USER
                    User user = userDAO.getUser(email, password);
                    if (user != null) {
                        if (userDAO.completeProfile(new User(user.getUserID(), name, email, phone, changeReferralCode, user.getLoginReferralCode(), password, user.getReferralStatus()))){
                            response.getWriter().print("Profile updated successfully");
                            response.sendRedirect("home.jsp");
//                            business logic
                        } else {
                            response.getWriter().print("Something went wrong, please try again");
                            response.sendRedirect("completeProfile.jsp");
                        }
                    } else {
                        response.getWriter().print("Invalid Data provided");
                    }
                } else {
                    response.getWriter().print("Invalid email or password.");
                    response.sendRedirect("completeProfile.jsp");
                }
            } else {
                response.getWriter().print("Access not allowed, please login first to user this section");
                response.sendRedirect("signin.jsp");
            }
        } catch (Exception exception) {
            System.out.println("\n\n\n"+exception.getMessage());
            response.getWriter().print("\n\n\n"+exception.getMessage());
        }
    }
}
