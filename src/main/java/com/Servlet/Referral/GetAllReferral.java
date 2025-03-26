package com.Servlet.Referral;

import com.DAO.ReferralDAO;
import com.db.DBConnection;
import com.entity.Referral;

import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.FileWriter;
import java.io.IOException;
import java.rmi.ServerException;
import java.util.List;


@WebServlet("admin/getAllReferrals")
public class GetAllReferral extends HttpServlet {

    private final String csvFilePath = "allReferrals.csv";

    public GetAllReferral(){
        super();
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws IOException, ServerException {
        try {
            HttpSession session = request.getSession(false); // don't create a new session
            if (session != null) {
                if (session.getAttribute("email").equals("admin@gmail.com") && session.getAttribute("password").equals("password")){
                    ReferralDAO referralDAO = new ReferralDAO(DBConnection.getConnection());

                    List<String[]> referralList = referralDAO.getAllReferral();
                    if (!referralList.isEmpty()){
//                        BUSINESS LOGIC

//                        TEMPORARY STORING THE DATA IN THE CSV FILE FORMAT
                        try (FileWriter fileWriter = new FileWriter(csvFilePath)) {
                            fileWriter.append("referralID | referralCode | userID");
                            for (String[] rl:referralList) {
                                fileWriter.append(rl[0])
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
                        response.getWriter().print("Access not allowed");
                        response.sendRedirect("signin.jsp");
                    }
                }
            } else {
                response.getWriter().print("Access Denied");
                response.sendRedirect("signin.jsp");
            }
        } catch (Exception exception) {
            System.out.println("\n\n\n"+exception.getMessage());
            response.getWriter().print("\n\n\n"+exception.getMessage());
        }
    }
}
