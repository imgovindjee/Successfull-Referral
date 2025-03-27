# DATA ACCESS OBJECT (DAO)

## Introduction

The **Data Access Object** Pattern, sometimes known as the **DAO** pattern, is a method of structuring code to manage the interaction between a computer and a database

In this Project, I Have used two DAO model:
1. UserDAO
2. ReferralDAO
   
**NOTE: ReferralStatus is Enum-type to update the CURRENT-REFERRAL STATUS.**

----

### Interaction Points

----

#### UserDAO
1. **userRegister**: Used to register a User
2. **loginUser**: used to login user once register
3. **completeProfile**: Used to fill the Data required to update the Profile and helps to use the referrals
4. **changePassword**: used to change the password
5. **updateReferralStatus**: Applied once the Profile Details is Completely filled and User have register using ReferralCode
6. **getUser**: fetching data from the server based on the **useremail** and **password**
7. **getAllUser**: fetches all the user from the server register
     - EndPoint for the **ADMIN**
  

#### ReferralDAO
1. **addReferral**: Used to addReferrals to Database once user is Register
2. **matchReferralUsed**: Used for validating the ReferralCode that a newUser is using while Register themselves
   - Here we get the userID from the **Server (form Referral Table)**
   - **validate(referralCode, userID)**: used to validate the referralCode across the **User Table form the server** -- THIS IS INTERNAL IMPLEMENTATION
3. **changeReferralCode**: Used to coustomize the userReferralCode if needed
4. **getAllReferral**: fetches all the Referrals from the Server
5. **getAllReferral**: fetches all the Referrals **based on the userID**
6. **getReferrals**: fetches all the Referrals **based on the ReferralCode**
7. **getReferralData**: fetches all the Referrals form the **Server Based on Unique userID** 
