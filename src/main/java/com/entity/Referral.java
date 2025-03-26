package com.entity;

public class Referral {

    private int referralID;
    private String referralCode;
    private int userID; // MAPPING THE USER-ID N REFERRAL-CODE

    /**
     *
     * {@AllArgsConstructor}
     *  diff-Type
     */
    public Referral() {
        super();
    }

    public Referral(String referralCode, int userID){
        this.referralCode = referralCode;
        this.userID = userID;
    }


    /**
     * GETTER's AND SETTER's
     * @return OBJECT's
     */
    public int getReferralID() {
        return referralID;
    }
    public void setReferralID(int referralID) {
        this.referralID = referralID;
    }
    public String getReferralCode() {
        return referralCode;
    }
    public void setReferralCode(String referralCode) {
        this.referralCode = referralCode;
    }
    public int getUserID() {
        return userID;
    }
    public void setUserID(int userID) {
        this.userID = userID;
    }
}
