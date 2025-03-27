### 📌 ENTITY TYPES
----

## i. USER
 - userID [int]
 - userName [String]
 - userEmail [String]
 - phone [String]
 - userReferralCode [String] : code provided to user once he/she register
 - loginReferralCode [String] : referral-code if used by user while he/she register
 - referralStatus [String] :
             ENUM ----> [PENDING, CLAIMED, NOT_USED]
 - password



## ii. REFERRAL
  - referralID [int]
  - referralCode [String]
  - userID [int] :  MAPPING THE USER-ID N REFERRAL-CODE
