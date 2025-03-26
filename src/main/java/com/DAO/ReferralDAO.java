package com.DAO;

import com.entity.Referral;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

public class ReferralDAO {

    private Connection connection;

    public ReferralDAO(Connection connection) {
        this.connection = connection;
    }


    /**]
     *
     * @param referralCode
     * @param userID
     * @return
     */
    public boolean addReferral(String referralCode, int userID) {
        boolean flag = false;
        try {
//            SQL QUERY
            String query = "insert into referral(referralcode, userid) values(?, ?)";

            PreparedStatement preparedStatement = connection.prepareStatement(query);
            preparedStatement.setString(1, referralCode);
            preparedStatement.setInt(2, userID);

//            Execute Query
            preparedStatement.executeUpdate();
            flag = true;
            System.out.println("Referral added to Database.");
        } catch (Exception exception) {
            System.out.println(exception.getMessage());
            exception.printStackTrace();
        }
        return flag;
    }


    /**
     *
     * @param referralCode
     * @return
     */
    public boolean matchReferralUsed(String referralCode) {
        boolean flag = false;
        try {
//            SQL QUERY
            String query = "select * from referral where referralcode=?";

            PreparedStatement preparedStatement = connection.prepareStatement(query);
            preparedStatement.setString(1, referralCode);

//            EXECUTE QUERY
            ResultSet resultSet = preparedStatement.executeQuery();
            boolean res = false;
            while (resultSet.next()) {
                int userID = resultSet.getInt("userid");

//                VALIDATE THE REFERRAL ACROSS THE USER
                res = validate(referralCode, userID);
            }
            flag = res;
            System.out.println("Referral Matched");
        } catch (Exception exception) {
            System.out.println(exception.getMessage());
            exception.printStackTrace();
        }
        return flag;
    }


    /**
     *
     * @param referralCode
     * @param userId
     * @return
     */
    private boolean validate(String referralCode, int userId) {
        boolean flag = false;
        try {
//            SQL QUERY
            String query = "select * from user where userid=?";

            PreparedStatement preparedStatement = connection.prepareStatement(query);
            preparedStatement.setInt(1, userId);
//            EXECUTE QUERY
            ResultSet resultSet = preparedStatement.executeQuery();
            while (resultSet.next()) {
                if (resultSet.getString("userreferralcode").equals(referralCode)){
                    flag = true;
                    break;
//                    BUSINESS LOGIC OVER HERE
                }
            }
            System.out.println("Good to Use");
        } catch (Exception exception) {
            System.out.println(exception.getMessage());
            exception.printStackTrace();
        }
        return flag;
    }


    /**
     *
     * @param userID
     * @param newReferralCode
     * @return
     */
    public boolean changeReferralCode(int userID, String newReferralCode) {
        boolean flag = false;
        try {
//            SQL QUERY
            String query = "update referral set referralcode=? where userid=?";

            PreparedStatement preparedStatement = connection.prepareStatement(query);
            preparedStatement.setString(1, newReferralCode);
            preparedStatement.setInt(2, userID);

//            EXECUTE QUERY
            preparedStatement.executeUpdate();
            flag = true;
            System.out.println("REFERRAL-CODE CHANGED SUCCESSFULLY.");
        } catch (Exception exception) {
            System.out.println(exception.getMessage());
            exception.printStackTrace();
        }
        return flag;
    }


    /**
     *
     * ADMIN TO ACCESS ALL THE REFERRALS ASSOCIATED TO THE USER
     * @return {@Referral} object
     */
    public List<String[]> getAllReferral() {
        List<String[]> referralList = new ArrayList<>();
        try {
//            SQL QUERY
            String query = "select * from referral";

            ResultSet resultSet = connection.prepareStatement(query).executeQuery();
            while (resultSet.next()) {
                referralList.add( new String[]{
                        String.valueOf(resultSet.getInt("referralid")),
                        resultSet.getString("referralcode"),
                        String.valueOf(resultSet.getInt("userid"))
                });
            }
        } catch (Exception exception) {
            System.out.println(exception.getMessage());
            exception.printStackTrace();
        }
        return referralList;
    }


    /**
     * GETTING THE REFERRAL USING userID
     * @param userID to get his all the referrals
     * @return {@List<String[]>} OBJECT
     */
    public List<String[]> getReferrals(int userID) {
        List<String[]> referralList = new ArrayList<>();
        try {
//            SQL QUERY
            String query = "select * from referral where userid=?";

            PreparedStatement preparedStatement = connection.prepareStatement(query);
            preparedStatement.setInt(1, userID);

            ResultSet resultSet = preparedStatement.executeQuery();
            while (resultSet.next()){
                referralList.add(new String[]{
                        String.valueOf(resultSet.getInt("referralid")),
                        resultSet.getString("referralcode")
//                        String.valueOf(resultSet.getInt("userid")) // Not Necessary
                });
            }
        } catch (Exception exception) {
            System.out.println(exception.getMessage());
            exception.printStackTrace();
        }
        return referralList;
    }




    /**
     * GETTING THE REFERRAL USING userID
     * @param referralCode to get his all the referrals
     * @return {@List<String[]>} OBJECT
     */
    public List<String[]> getReferrals(String referralCode) {
        List<String[]> referralList = new ArrayList<>();
        try {
//            SQL QUERY
            String query = "select * from referral where userid=?";

            PreparedStatement preparedStatement = connection.prepareStatement(query);
            preparedStatement.setString(1, referralCode);

            ResultSet resultSet = preparedStatement.executeQuery();
            while (resultSet.next()){
                referralList.add(new String[]{
                        String.valueOf(resultSet.getInt("referralid")),
                        resultSet.getString("referralcode"),
                        String.valueOf(resultSet.getInt("userid")) // Not Necessary
                });
            }
        } catch (Exception exception) {
            System.out.println(exception.getMessage());
            exception.printStackTrace();
        }
        return referralList;
    }


    /**
     *
     * @return
     */
    public List<String[]> getReferralsData() {
        List<String[]> stringList = new ArrayList<>();
        try {
//            SQL QUERY
            String query = "select userid, referralcode, count(distinct referralcode) as referral_count from referral group by userid";

            PreparedStatement preparedStatement = connection.prepareStatement(query);
            ResultSet resultSet = preparedStatement.executeQuery();
            while (resultSet.next()) {
                stringList.add(new String[] {
                        String.valueOf(resultSet.getInt("userid")),
                        resultSet.getString("referralcode"),
                        String.valueOf(resultSet.getInt("referral_count"))
                });
            }
        } catch (Exception exception) {
            System.out.println(exception.getMessage());
            exception.printStackTrace();
        }
        return stringList;
    }
}
