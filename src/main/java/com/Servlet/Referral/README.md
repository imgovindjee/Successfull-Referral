# Referral EndPoints

## Comman-Seperated Values (CSV)

### Introduction
A **Comma-Separated Values (CSV)** file is just a normal plain-text file, store data in column by column, and split it by a separator.

---

## EndPoints

This all extraction is only possible if the user is login

1.  **getAllReferral**
     - [https://localhost:8080/simplifyMoneyAssignment/admin/getAllReferrals]() : Extracts all the Referrals from the Server
     - This is the ADMIN-API
     - CUSTOMIZE ---> **Email: admin@gmail.com** and **Password: password**
     - **HARDCODED**, Present Secenarios
2.  **getReferralsUsingReferralCode**
     - [https://localhost:8080/simplifyMoneyAssignment/findReferralCodeUsage]() : Extracts all the Referrals from the Server **based on the ReferralCode**
3.  **getReferralUsingUser** :
     - [https://localhost:8080/simplifyMoneyAssignment/getReferral]() : Extracts all the Referrals from the Server **based on the userid** from current login user (Specifically from the HTTP-HEADER)


---

**NOTE: TRYING
     - **getAllReferral** : Based on the unique userID and  
     - [https://localhost:8080/simplifyMoneyAssignment/admin/referralsData]() : Creates a **.csv file** for all the Register user with or without referral and the status of the referrals used
     - Referral Status is based on the **Profile Details Completation****
