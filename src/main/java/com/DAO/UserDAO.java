package com.DAO;

import com.entity.User;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

public class UserDAO {

    private Connection connection;

    public UserDAO(Connection connection){
        this.connection = connection;
    }




    /**
     * REGISTER A USER
     * PROVIDING THE REFERRAL CODE WHILE USER REGISTER
     * @param user data from frontend who is registering
     * @return {@Boolean} TYPE OF ACKNOWLEDGEMENT
     */
    public boolean userRegister(User user){
        boolean flag = false;
        try {
//            Data from the FRONTEND form
            String userName = user.getUserName();
            String userEmail = user.getUserEmail();
            String password = user.getPassword();
            String userReferralCode = generateReferralCode(userName);
            String loginReferralCode = user.getLoginReferralCode();

            if (loginReferralCode.length() != 0){
    //            SQL-QUERY
                String query = "insert into user(username, useremail, userreferralcode, loginreferralcode, password, referralstatus) values(?,?,?,?,?,?)";

                PreparedStatement preparedStatement = connection.prepareStatement(query);
                preparedStatement.setString(1, userName);
                preparedStatement.setString(2, userEmail);
                preparedStatement.setString(3, userReferralCode);
                preparedStatement.setString(4, loginReferralCode);
                preparedStatement.setString(5, password);
                preparedStatement.setString(6, String.valueOf(ReferralStatus.PENDING)); // REGISTER USING SOME REFERRAL

    //            EXECUTE QUERY
                preparedStatement.executeUpdate();
                System.out.println("User Register Successfully Using ReferralCode ("+loginReferralCode+")");
                flag = true;
            } else {
//                SQL-QUERY
                String query = "insert into user(username, useremail, userreferralcode, loginreferralcode, password, referralstatus) values(?,?,?,?,?,?)";

                PreparedStatement preparedStatement = connection.prepareStatement(query);
                preparedStatement.setString(1, userName);
                preparedStatement.setString(2, userEmail);
                preparedStatement.setString(3, userReferralCode);
                preparedStatement.setString(4, null); // setting the loginReferralCode as NULL.
                preparedStatement.setString(5, password);
                preparedStatement.setString(6, String.valueOf(ReferralStatus.NOT_USED)); // REGISTER UNIQUELY - WITHOUT USING ANY REFERRAL

//                EXECUTE QUERY
                preparedStatement.executeUpdate();
                System.out.println("User Register Successfully.");
                flag = true;
            }
        } catch (Exception exception) {
            System.out.println(exception.getMessage());
            exception.printStackTrace();
        }
        return flag;
    }





    /**
     *
     * Login User {ONCE REGISTER}
     * @param userEmail of user who is trying to log-in
     * @param password of user who is trying to log-in
     * @return {@USER}
     */
    public User loginUser(String userEmail, String password) {
        User user = null; // DEFAULT USER
        try {
//            SQL-QUERY
            String query = "select * from user where useremail=? and password=?";

            PreparedStatement preparedStatement = connection.prepareStatement(query);
            preparedStatement.setString(1, userEmail);
            preparedStatement.setString(2, password);

//            EXECUTE QUERY
            ResultSet resultSet = preparedStatement.executeQuery();
            System.out.println(resultSet); // TESTING
            while (resultSet.next()) {
                user = new User();
                user.setUserID(resultSet.getInt("userID"));
                user.setUserName(resultSet.getString("userName"));
                user.setUserEmail(resultSet.getString("userEmail"));
                user.setPassword(resultSet.getString("password"));
            }
        } catch (Exception exception) {
            System.out.println(exception.getMessage());
            exception.printStackTrace();
        }
        return user;
    }




    /**
     *  CHANGING-INFO TO Database of the user
     *  Update the Profile or complete the detail-info section
     *  Here one can update the {@userName}, {@phone}, {@userReferralCode}
     * @param user data from the frontend
     * @return {@Boolean} TYPE OF ACKNOWLEDGEMENT
     */
    public boolean completeProfile(User user) {
        boolean flag = false;
        try {
//            DATA FROM FRONTEND form
            String userName = user.getUserName();
            String userEmail = user.getUserEmail();
            String password = user.getPassword();
            String phone = user.getPhone();
            String userReferralCode = user.getUserReferralCode(); // User can CUSTOMIZE the referral code
            String loginReferralCode = user.getLoginReferralCode();

//            SQL QUERY
            String query = "update user set userName=?, phone=?, userreferralcode=? where useremail=? and password=?";

            PreparedStatement preparedStatement = connection.prepareStatement(query);
            preparedStatement.setString(1, userName);
            preparedStatement.setString(2, phone);
            preparedStatement.setString(3, userReferralCode);
            preparedStatement.setString(4, userEmail);
            preparedStatement.setString(5, password);

//            EXECUTE QUERY
            preparedStatement.executeUpdate();
//            UPDATE THE REFERRAL STATUS IF USED
            boolean update = false;
            if (loginReferralCode.length() != 0 && loginReferralCode.equals(String.valueOf(ReferralStatus.PENDING))) {
                update = updateReferralStatus(user);
            }
            flag = true;
            System.out.println("PROFILE UPDATING COMPLETED.");
        } catch (Exception exception) {
            System.out.println(exception.getMessage());
            exception.printStackTrace();
        }
        return flag;
    }




    /**
     *  Changing the password of the user
     * @param userID
     * @param newPassword
     * @return
     */
    public boolean changePassword(int userID, String newPassword) {
        boolean flag = false;
        try {
//            SQL QUERY
            String query = "update user set password=? where userid=?";

            PreparedStatement preparedStatement = connection.prepareStatement(query);
            preparedStatement.setString(1, newPassword);
            preparedStatement.setInt(2, userID);

//            EXECUTE QUERY
            preparedStatement.executeUpdate();
            flag = true;
            System.out.println("User Password changed successfully");
        } catch (Exception exception) {
            System.out.println(exception.getMessage());
            exception.printStackTrace();
        }
        return flag;
    }



    /**
     *
     * Generating the User Referral Code
     * @param userName who is trying the register themselves
     * @return String
     */
    private String generateReferralCode(String userName){
        String specialSymbol = "!@#$%&-";
        String code = UUID.randomUUID().toString().substring(1, 3);
        String referralCode = userName.substring(0, 3).toUpperCase() +
                specialSymbol.charAt((int)(Math.random()%specialSymbol.length())) +
                code;
        System.out.println(referralCode);
        return referralCode;
//        BUSINESS LOGIC IN ORDER TO GENERATE THE REFERRAL CODE
    }


    /**
     * UPDATING THE REFERRAL STATUS ONCE THE PROFILE DETAILS IS COMPLETED
     * @param user Where to update
     * @return {@Boolean} type ACKNOWLEDGEMENT
     */
    private boolean updateReferralStatus(User user) {
        boolean flag = false;
        try {
//            SQL QUERY
            String query = "update user set referralstatus=? where id=?";

            PreparedStatement preparedStatement = connection.prepareStatement(query);
            preparedStatement.setString(1, String.valueOf(ReferralStatus.CLAIMED));
            preparedStatement.setInt(2, user.getUserID());

//            Execute Query
            preparedStatement.executeUpdate();
            flag = true;
            System.out.println("REFERRAL STATUS UPDATED SUCCESSFULLY.");
        } catch (Exception exception) {
            System.out.println(exception.getMessage());
            exception.printStackTrace();
        }
        return flag;
    }


    /**
     * ]
     * @param email
     * @param password
     * @return
     */
    public User getUser(String email, String password) {
        User user = null;
        try {
            String query = "select * from user where email=? and password=?";

            PreparedStatement preparedStatement = connection.prepareStatement(query);
            preparedStatement.setString(1, email);
            preparedStatement.setString(2, password);

            ResultSet resultSet = preparedStatement.executeQuery();
            while (resultSet.next()) {
                user = new User();
                user.setUserID(resultSet.getInt("userid"));
                user.setUserName(resultSet.getString("username"));
                user.setUserEmail(resultSet.getString("useremail"));
                user.setReferralStatus(resultSet.getString("referralstatus"));
                user.setUserReferralCode(resultSet.getString("userreferralcode"));
                user.setLoginReferralCode(resultSet.getString("loginreferralcode"));
                user.setPassword(resultSet.getString("password"));
                user.setPhone(resultSet.getString("phone"));
            }
        } catch (Exception exception) {
            System.out.println(exception.getMessage());
            exception.printStackTrace();
        }
        return  user;
    }


    /**
     *
     * @return
     */
    public List<User> getAllUser() {
        List<User> userList = new ArrayList<>();
        try {
            String query = "select * from user";

            PreparedStatement preparedStatement = connection.prepareStatement(query);

            ResultSet resultSet = preparedStatement.executeQuery();
            while (resultSet.next()) {
                userList.add(new User(
                        resultSet.getInt("userid"),
                        resultSet.getString("username"),
                        resultSet.getString("useremail"),
                        resultSet.getString("phone"),
                        resultSet.getString("referralstatus"),
                        resultSet.getString("userreferralcode"),
                        resultSet.getString("loginreferralcode")
                ));
            }
        } catch (Exception exception) {
            System.out.println(exception.getMessage());
            exception.printStackTrace();
        }
        return userList;
    }
}
