## AWS Organization - Overview

* Multiple account needs to be managed
* Multipl account advantage
  * Cost optimzations
  * Security Groupings
  * Management of controls and workloads
  * Resource groupings
  * Helping to define business units
* Multipl account const
  * Security risk
  * Operation issues
* AWS Orgnization can allow to centrallay manage  

## AWS Organization - Foundations

* Root
  * Organizational Unit (Marketting)
    * Accounts (Marketting - 12 digit number)
      * Service control policies
  * Organizational Unit (sales)
    * Accounts (sales - 12 digit number)
      * Service control policies
  * Organizational Unit (HR)
    * Organizational Unit (HR-APAC)
      * Organizational Unit (HR-India)
  * OU - can be nested at 5 levels.

## AWS SCP - Service Control policies

* Permission that could be applied to root/ou/account
* AWS has master account (that manages other accounts)

## AWS Organization

* provides Single Payer and centralized cost tracking
* Lets you create and invite accounts
* Allows you to apply policy-based controls
* Helps you simplify organization wide management of  AWS Services

## AWS Organization - Management

* Every organization has master account
* Don't use master account to create service such as EC2
* Use it only to manage root,ou and other accounts
* Consider merger & acquisition
  * Master account can create/invite/merge/remove an account from organization


## AWS Organization - How

* AWS Management console > Services
* Management & Governance > AWS Organizations
* Create Organization
  * Master account would be created (by default)  when orgnization is created
* Under Organization > Create Account (or) Invite Account
* Account-Id could be 12 digit number (or Email-ID)
* Invitation would send mail to account-id owner
* Sample organization id - o-6ixk4mv3io
* Under Orgnization Accounts
  * Can create new OU
  * Move accounts to OU


## AWS SCP (Service Control Policies)

* using SCPs, we can specify Conditions, Resources, and NotAction to deny access across accounts in your organization or organizational unit.
* Feature of AWS Organizations rather than IAM
* Specify maximum permissions for affected entities
* SCPs can be specified to roots, organizational units (OUs), or accounts in your organization
* SCPs doesn't affect resource based policies
* SCPs affects principles managed by accounts in your organization

## AWS SCP - Sample

```json
{    
  "Version": "2012-10-17",
  "Statement": [
    {
      "Sid": "DenyChangesToAdminRole",
      "Effect": "Deny",
      "NotAction": [
        "iam:GetContextKeysForPrincipalPolicy",
        "iam:GetRole",
        "iam:GetRolePolicy",
        "iam:ListAttachedRolePolicies",
        "iam:ListInstanceProfilesForRole",
        "iam:ListRolePolicies",
        "iam:ListRoleTags"
      ],
      "Resource": [
        "arn:aws:iam::*:role/AdminRole"
      ],
      "Condition": {
        "StringNotLike": {
          "aws:PrincipalARN":"arn:aws:iam::*:role/AdminRole"
        }
      }
    }
  ]
}
```

## AWS SCP - Policy inheritance

* policies can be attached to organization entities (organization root, organizational unit (OU), or account):
* Attaching a policy to the organization root, all OUs and accounts in the organization inherit that policy.
* Attaching a policy to a specific OU, accounts that are directly under that OU or any child OU inherit the policy.
* Attaching a policy to a specific account, it affects only that account.
* How policies affect the OUs and accounts that inherit them depends on the type of policy
* Eample: If root SCP is governed by 1,2,3 and 4. Child Org is SCP is applied 2,3,5 and6. Effectively Org will have 2 and 3 alone (Intersection of root and Org)


## Reference

* [How to use service control policies to set permission guardrails across accounts in your AWS Organization](https://aws.amazon.com/blogs/security/how-to-use-service-control-policies-to-set-permission-guardrails-across-accounts-in-your-aws-organization/)