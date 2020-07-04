## AWS IAM - Features

* Shared access to AWS account
* Secure access to AWS resources for applications that run on Amazon EC2
* Groups, Users and Roles
* IAM policies
* Multi-factor authentication (MFA)
* Identity federation - (You can allow users who already have passwords elsewhere)
* PCI DSS Compliance - Payment Card Industry (PCI) Data Security Standard (DSS).
* AWS KMS

## AWS IAM - Overview

* Identity - validate {user-id/password/key}
  * User-name/password
  * Multi-factor authentication (MFA)
  * Federated Access
* Authroization
  * Access - Authorization management {role, permission}
  * S3:CreateBucket
  * RDS:ReadOnly
  * EC2:FUllAccess
  * CloudTrail:FullAccess


## AWS IAM - Components

* Root user
* IAM User - person or service who uses the IAM user to interact with AWS
* Roles - identity with permission policies that determine what the identity can and cannot do in AWS. However, a role does not have any credentials
* Groups - is a collection of IAM users (Groups can't be nested)
* Policies - A policy is an object in AWS that, when associated with an identity or resource, defines their permissions.
  * According policy certain resources can or can't be accessed
  * How complex a password policy  
* Permissions = Policy + Identity (or resource like EC2)


## IAM User

* Person or service account
* User can be created either for aws management console or programmatic access (access key id/secret access key)
* When user is created
  * Add user to group
  * Copy permissions from existing user
  * Attach existing policies directly (not good practice)
* Download security credentials via csv (you can't download second time)
* Access key id - 20 random upper case alpha-numeric
* Securet access key - 40 random upper case alpha-numeric
* Sample user ARN: arn:aws:iam::0123456789:user/Admin
* AWS CodeCommit is github equivalent, we can provide access while user is created!

## IAM Groups

* Used in authorization process
* Group = Policies + collections of users
* Groups are based on job role or specific requirement
* Group = GroupName + Set of policies that has access
* Default 100 groups, and user could be associated to 10 groups

## IAM Role

* Users and resources can acquire temporary access to IAM permissions
* By attaching roles to aws-services, they act like users
* Roles can acquire policy
* In AWS, we can't attach policy to aws services
  * Attach role to services, and attach policy to roles
* Nowadays, EC2 roles can be dynamically changed (unlike early days we have to destroy to change roles).
* EC2 instance requiring access to Amazon S3 to Put and Get objects
  * Credentials could be stored on the EC2 instance itself (store access key-id and secret access key)
  * We can dynamically attach roles (instant)
* They can be considered like groups for services (not groups for users)
* Predefined IAM Service Role
  * Amazon EC2
  * Amazon Directory Service
  * Amazon Lambda
  * Amazon Redshift
  * Amazon Batch service role
* Predefined IAM Service Linked Role (can't modify permission)
  * Amazon Lex - Bots
  * Amazon Lex - Channels
* Cross-Account-Access (Truted-Trusting-Account)  
  * When Truted account need to access resources of "Trusting Account"
  * Role is created in the trusting account
  * Trusted account will assume role of trusting account
* Role for Identify Provider Access
  * Grant access to web identiy providers  
  * Grant access to web SSO SAML providers
  * Grant access to SAML Providers


## AWS Policy (Like traditional granular permission)

* Example Policy: AmazonS3FullAccess, AmazonS3ReadOnly, AmazonEC2FullAccess
 

## AWS IAM - User policies based on job functions

1. Administrator -  User has full access and can delegate permissions to every service and resource in AWS.
1. Billing - user needs to view billing information, set up payments, and authorize payments. The user can monitor the costs accumulated for the entire AWS service.
1. Database Administrator
1. Data Scientist - user runs Hadoop jobs and queries. The user also accesses and analyzes information for data analytics and business intelligence.
1. Developer Power User - user performs application development tasks and can create and configure resources and services that support AWS aware application development.
1. Network Administrator
1. Security Auditor
1. Support User - user contacts AWS Support, creates support cases, and views the status of existing cases.
1. System Administrator - user sets up and maintains resources for development operations.
1. View-Only User

## IAM Policies

* Policies are JSON format document
* [IAM JSON Policy Elements Reference](https://docs.aws.amazon.com/IAM/latest/UserGuide/reference_policies_elements.html)
* PolicyTypes
  * AWS Managed policies
  * Customer Managed policies
    * Copy aws manged policy/policy generator/Create your own policy
  * In-line policies
    * They are custom and not-reusable for others to use
    * They are added when resources created (user/ec2)
    * When we need to reduce the risk of permission being reused by any other entity
* Policies resolution conflict
  * By default, all access is denied
  * Explicit allow is required
  * Single Deny would overrule any number of Allow    

```json
{
  "Version": "2012-10-17",
  "Statement": {
    "Effect": "Allow",
    "Action": "s3:ListBucket",
    "Resource": "arn:aws:s3:::example_bucket"
  }
}

{
  "Version": "2012-10-17",
  "Id": "S3-Account-Permissions",
  "Statement": [{
    "Sid": "1",
    "Effect": "Allow",
    "Principal": {"AWS": ["arn:aws:iam::ACCOUNT-ID-WITHOUT-HYPHENS:root"]},
    "Action": "s3:*",
    "Resource": [
      "arn:aws:s3:::mybucket",
      "arn:aws:s3:::mybucket/*"
    ]
  }]
}
```

## IAM MFA

* Governance controle might force MFA
* Additional factor for authentication
* MFA Devices are allowed
  * Virtual MFA device
    * Any mobile application that supports open TOTP standard can be used
  * Hardware Key Fob/ Hardware Display Card
  * SMS MFA device 
* Virtual MFA device
  * Example: Google Authenticator
    * Scan QR code and pass two authentication code for activating virutal MFA

## IAM identify federation

* External identify providers could provide service equivalent of what IAM does
* Id providers allow user to access AWS resource access securely
* OpenId Connection Provider
* Microsoft Active Directory can grant access to your AWS resource
* Trust relationship between AWS account and IdP
  * Two types SAML-V2.0 and OpenID
    * OpenId providers includes Google, Facebook, Amazon
    * SAML (Security assertion markkup language)
      * MS-AD uses SAML
* Active directory authentication
  * Authenticated by MS-AD
  * SAML will be issued to user
  * User would send SAML to AWS STS
  * User would access S3 using STS
  * [Security Token Service](https://docs.aws.amazon.com/STS/latest/APIReference/welcome.html)
* [Steps to create create OpenId provider](https://docs.aws.amazon.com/IAM/latest/UserGuide/id_roles_providers_create_oidc.html)
*[Steps to create - IAM SAML Identity Providers](https://docs.aws.amazon.com/IAM/latest/UserGuide/id_roles_providers_create_saml.html)
 

## AWS IAM account settings

* Password policy (State 8 points)
  * Require atleast one uppercase letter
  * Require atleast one lowercase letter
  * Require atleast one number 
  * Require atleast one non-alphanumeric
  * Allow user to change own password
  * Enable password expiry in 99 days
  * Prevent password reuse (number of password to remember)
  * Prevent password reuse (number of password to remember)
* Security Token service can be selectively activated for certain regions
* Credential report can be generated only once every 4 hours
* [Credential report format](https://docs.aws.amazon.com/IAM/latest/UserGuide/id_credentials_getting-report.html#id_credentials_understanding_the_report_format)
* AWS KMS
  * Managed service
  * Manages encryption keys
  * CMK - Customer master keys can be managed
  * Keys can be used to encrypt data

