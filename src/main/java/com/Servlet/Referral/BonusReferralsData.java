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


@WebServlet("admin/referralsData")
public class BonusReferralsData extends HttpServlet {

    private final String csvFilePath = "bonusReferralData.csv";

    public BonusReferralsData(){
        super();
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        try {
            HttpSession session = request.getSession(false); // don't create a new session
            if (session != null) {
                if (session.getAttribute("email").equals("admin@gmail.com") && session.getAttribute("password").equals("password")) {
                    ReferralDAO referralDAO = new ReferralDAO(DBConnection.getConnection());

                    List<String[]> referralsData= referralDAO.getReferralsData();
                    if (!referralsData.isEmpty()) {
//                        BUSINESS LOGIC

//                        TEMPORARY STORING THE DATA IN THE CSV FILE FORMAT
                        try (FileWriter fileWriter = new FileWriter(csvFilePath)) {
                            fileWriter.append("userID | referralCode | referral_Count");
                            for (String[] rd:referralsData) {
                                fileWriter.append(rd[0])
                                        .append(" | ")
                                        .append(rd[1])
                                        .append(" | ")
                                        .append(rd[2]);
                            }
                            System.out.println("CSV FILE CREATED SUCCESSFULLY");
                        } catch (IOException e) {
                            e.printStackTrace();
                        }
                    } else {
                        response.getWriter().print("Something went wrong, Please try again");
                    }
                }
            } else {
                response.getWriter().print("Access Denied");
                response.sendRedirect("signin.jsp"); // go to signin page
            }
        } catch (Exception exception) {
            System.out.println("\n\n\n"+exception.getMessage());
            response.getWriter().print("\n\n\n"+exception.getMessage());
        }
    }
}
