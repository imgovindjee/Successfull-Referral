package com.Browine;

import com.DAO.UserDAO;
import com.db.DBConnection;
import com.entity.User;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.FileWriter;
import java.io.IOException;
import java.util.List;



@WebServlet("brownie/reportCSV")
public class ReportCSV extends HttpServlet {

    private final String csvFilePath = "users_referrals.csv";
    
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException {
        try (FileWriter fileWriter = new FileWriter(csvFilePath)) {
            fileWriter.append("UserID | UserName | Email | Phone | Referral Code | Used Referral Code | Referral Status");

//            GETTING THE DATA FROM THE SERVER
            try {
                List<User> userList = new UserDAO(DBConnection.getConnection()).getAllUser();
                for (User u : userList) {
                    fileWriter
                            .append(String.valueOf(u.getUserID()))
                            .append(" | ")
                            .append(u.getUserName())
                            .append(" | ")
                            .append(u.getUserEmail())
                            .append(" | ")
                            .append(u.getPhone())
                            .append(" | ")
                            .append(u.getUserReferralCode())
                            .append(" | ")
                            .append(u.getLoginReferralCode())
                            .append(" | ")
                            .append(u.getReferralStatus());
                }
                System.out.println("CSV FILE CREATED SUCCESSFULLY");
                response.flushBuffer();
            } catch (Exception exception) {
                exception.printStackTrace();
            }
        } catch (IOException ioException) {
            ioException.printStackTrace();
        }
    }
}
