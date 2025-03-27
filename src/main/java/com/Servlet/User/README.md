# UserAPI 

## EndPoints
1. **signup**
    - [https://localhost:8080/simplifyMoneyAssignment/signup]() : used for making the user register **with** or **without** **referral Code**
    - Also used for the generating the Referral Code across the User
    - Each ReferralCode generated is Uniquely

2. **signin**
   - [https://localhost:8080/simplifyMoneyAssignment/signin]() : used for making user login to the site or page

3. **completeProfile**
   - [https://localhost:8080/simplifyMoneyAssignment/completeProfile]() : used to complete the profile details
   - Responsible for changeing the **ReferralStatus**, if used while Registering (signup)
