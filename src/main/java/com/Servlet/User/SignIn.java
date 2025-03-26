package com.Servlet.User;

import com.DAO.UserDAO;
import com.db.DBConnection;
import com.entity.User;

import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;


@WebServlet("signin")
public class SignIn extends HttpServlet {

    public SignIn(){
        super();
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws IOException {
        try {
            String email = request.getParameter("Email");
            String password = request.getParameter("Password");

//            DB Connection
            UserDAO userDAO = new UserDAO(DBConnection.getConnection());
            User user = userDAO.loginUser(email, password);
            if (user == null) {
                response.sendRedirect("signin.jsp");
            } else {
//                CREATING A NEW SESSION
//                AND SETTING THE userid and referralStatus
                HttpSession httpSession = request.getSession(true);
                httpSession.setAttribute("userID", user.getUserID());
                httpSession.setAttribute("email", user.getUserEmail());
                httpSession.setAttribute("password", user.getPassword());
                httpSession.setAttribute("referralStatus", user.getReferralStatus());

                response.sendRedirect("home.jsp");
            }
        } catch (Exception exception) {
            System.out.println("\n\n\n"+exception.getMessage());
            response.getWriter().print("\n\n\n"+exception.getMessage());
        }
    }
}
