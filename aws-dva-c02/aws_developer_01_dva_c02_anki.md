## [AWS-Certified-Developer-Associate_Exam-Guide](https://d1.awsstatic.com/training-and-certification/docs-dev-associate/AWS-Certified-Developer-Associate_Exam-Guide.pdf)
* Development (32%)
  * Lambda
    * Resource allocation
    * Custom Library
    * DB connection string
  * S3
    * Caching
    * Data storage
    * RDBMS
  * Dynamodb
    * Keys and Indexing
    * Querying and Scan operation
* Security
  * Implement authentication and authorization
  * Encryption by using aws services
  * Manage sensitive data
  * IAM, KMS, ACM (Certificate Manager), AWS SM (Secrets Manager), AWS System Manager
* Deployment
  * Prepare application artifacts
  * Test application
  * Automate Deployment
  * Deploy Code using AWS CI/CD
  * CodeCommit, CodeBuild, CodeDeploy, CodePipeline
* Troubleshooting & Optimizing
  * RCA
  * Observability
  * Optimize using AWS services and features
  * Amazon CloudWatch, CloudTrail adn AWS X-Ray

## Exam logistics

* 720/1000 - passing score
* 65 question and 130 minutes

## Why certifications, what they test?
* Minimally Qualified
* services and features
* Not metrics, prices, and limits (these are not tested)
* 1 of 4 (multiple choice) or 2 of 5 (multiple response)



## AWS UI tips

* /Resources


## EC2 Rules 
* Rules with source of 0.0.0.0/0 allow all IP addresses to access your instance. We recommend setting security group rules to allow access from known IP addresses only.
* EC2- UserData (script /commands that needs to be executed)
* Debian: admin/RedHat: ec2-user/Ubuntu: ubuntu

## Ec2 connect
* Permissions 0440 for '/Users/alpha/Downloads/cloud_ec2.pem' are too open. /It is required that your private key files are NOT accessible by others.
* chmod 400 /Users/alpha/Downloads/cloud_ec2.pem
* ssh -i /Users/alpha/Downloads/cloud_ec2.pem ec2-user@18.236.190.196
* On an EBS-backed instance, the default action is for the root EBS volume to be deleted when the instance is terminated. Storage on any local drives will be lost.
* printenv (set -v won't work)
* HOSTNAME=ip-172-31-25-137.us-west-2.compute.internal
* curl -w "\n" http://ip-172-31-25-137.us-west-2.compute.internal/latest/meta-data/
  * Above won't work
* curl -w "\n" http://169.254.169.254/latest/meta-data/