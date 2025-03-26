package com.Servlet.Referral;

import com.DAO.ReferralDAO;
import com.db.DBConnection;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.FileWriter;
import java.io.IOException;
import java.util.List;



@WebServlet("findReferralCodeUsage")
public class GetReferralsUsingReferralCode extends HttpServlet {

    private final String csvFilePath = "getReferralsUsingReferral.csv";

    public GetReferralsUsingReferralCode() {
        super();
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        try {
            HttpSession session = request.getSession(false); // don't create a new session
            if (session != null) {
                ReferralDAO referralDAO = new ReferralDAO(DBConnection.getConnection());

                List<String[]> referralList = referralDAO.getReferrals(request.getParameter("referralCode"));
                if (!referralList.isEmpty()) {
                    // Business Logic

//                    TEMPORARY STORING THE DATA IN THE CSV FILE FORMAT
                    try (FileWriter fileWriter = new FileWriter(csvFilePath)) {
                        fileWriter.append("referralID | referralCode | userID");
                        for (String[] rl : referralList) {
                            fileWriter
                                    .append(rl[0])
                                    .append(" | ")
                                    .append(rl[1])
                                    .append(" | ")
                                    .append(rl[2]);
                        }
                        System.out.println("CSV FILE CREATED SUCCESSFULLY");
                    } catch (IOException ioException) {
                        ioException.printStackTrace();
                    }
                } else {
                    response.getWriter().print("Something went wrong, Please try again");
                }
            } else {
                response.getWriter().print("Sign-in to Access this Region");
                response.sendRedirect("signin.jsp");
            }
        } catch (Exception exception) {
            System.out.println("\n\n\n" + exception.getMessage());
            response.getWriter().print("\n\n\n" + exception.getMessage());
        }
    }
}
