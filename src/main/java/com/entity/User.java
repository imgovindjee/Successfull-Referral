package com.entity;

public class User {

    private int userID;
    private String userName;
    private String userEmail;
    private String phone;
    private String userReferralCode; // code provided to user once he/she register
    private String loginReferralCode; // referral-code if used by user while he/she register
    private String referralStatus; // SOME CASES [PENDING, CLAIMED, NOT_USED]
    private String password;

    /**
     * {@AllArgsConstructor} diff-types
     */
    public User(){
        super();
    }

    public User(int userID, String userName, String userEmail, String phone, String userReferralCode, String loginReferralCode, String password, String referralStatus) {
        super();
        this.userID = userID;
        this.userName = userName;
        this.userEmail = userEmail;
        this.phone = phone;
        this.userReferralCode = userReferralCode;
        this.loginReferralCode = loginReferralCode;
        this.password = password;
        this.referralStatus = referralStatus;
    }

    public User(String userName, String userEmail, String phone, String password) {
        this.userName = userName;
        this.userEmail = userEmail;
        this.phone = phone;
        this.password = password;
    }


    public User(String  userName, String userEmail, String password, String userReferralCode, String loginReferralCode){
        this.userName = userName;
        this.userEmail = userEmail;
        this.password = password;
        this.userReferralCode = userReferralCode;
        this.loginReferralCode = loginReferralCode;
        this.referralStatus = "PENDING";
    }

    public User(int userid, String username, String useremail, String phone, String referralstatus, String userreferralcode, String loginreferralcode) {
        super();
        this.userID = userid;
        this.userEmail = useremail;
        this.userName = username;
        this.phone = phone;
        this.loginReferralCode = loginreferralcode;
        this.referralStatus = referralstatus;
        this.userReferralCode = userreferralcode;
    }


    /**
     *
     * GETTER's AND SETTER's
     * @return OBJECT's
     */
    public int getUserID() {
        return userID;
    }
    public void setUserID(int userID) {this.userID = userID;}
    public String getUserName() {
        return userName;
    }
    public void setUserName(String userName) {
        this.userName = userName;
    }
    public String getUserEmail() {
        return userEmail;
    }
    public void setUserEmail(String userEmail) {
        this.userEmail = userEmail;
    }
    public String getPhone() {
        return phone;
    }
    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getUserReferralCode() {
        return userReferralCode;
    }
    public void setUserReferralCode(String userReferralCode) {
        this.userReferralCode = userReferralCode;
    }

    public String getLoginReferralCode() {
        return loginReferralCode;
    }
    public void setLoginReferralCode(String loginReferralCode) {
        this.loginReferralCode = loginReferralCode;
    }

    public String getPassword() {
        return password;
    }
    public void setPassword(String password) {
        this.password = password;
    }

    public String getReferralStatus() {
        return referralStatus;
    }
    public void setReferralStatus(String referralStatus) {
        this.referralStatus = referralStatus;
    }

    /**
     * DEFAULT LAYOUT DISPLAY
     * @return String
     */
    @Override
    public String toString(){
        return "User [userID:"+userID+ ", userName:"+userName+", userEmail:"+userEmail+", Phone:"+phone+"]";
    }
}
