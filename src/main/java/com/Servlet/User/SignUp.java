package com.Servlet.User;


import com.DAO.UserDAO;
import com.db.DBConnection;
import com.entity.User;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;



@WebServlet("signup")
public class SignUp extends HttpServlet {
    public SignUp() {
        super();
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException {
        try {
            String userName = request.getParameter("Name");
            String userEmail = request.getParameter("Email");
            String password = request.getParameter("Password");
            String referralCode = request.getParameter("Referral Code");

//            User Object Creation
            User user = new User(userName, userEmail, password, referralCode);

//            DB CONNECTION
            UserDAO userDAO = new UserDAO(DBConnection.getConnection());
            boolean statusFlag = userDAO.userRegister(user);
            if (statusFlag) {
                response.getWriter().print("USER REGISTER SUCCESSFULLY");
//                REDIRECT TO ANOTHER PAGE IF REGISTER
//                WHERE USER CAN LOG-IN THEMSELVES
                response.sendRedirect("signin.jsp");
            } else {
                response.getWriter().print("SOMETHING WENT WRONG, PLEASE TRY AGAIN");
//                REGISTRATION FAILS N USER HAVE TO RE-REGISTER THEMSELVES
                response.sendRedirect("signup.jsp");
            }
        } catch (Exception exception) {
            System.out.println("\n\n\n\n\n"+exception.getMessage());
            response.getWriter().print("\n\n\n\n\n"+exception.getMessage());
        }
    }
}

