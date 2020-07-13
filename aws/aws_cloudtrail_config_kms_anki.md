## Why we need Cloudtrail? (Auditlog)

* Event history of AWS account activity, 
  * Including actions taken through the AWS Management Console
  * AWS SDKs, command line tools, and other AWS services. 
* Who created new EC2 instances (API/console/AutoScale/User)?
* Enables governance, compliance, operational auditing, and risk auditing of your AWS account
* Audit events for usage of AWS API
* CloudTrail log files to troubleshoot operational or security issues in AWS account.
* CloudWatch is monitoring, whereas CloudTrail is auditing.

## AWS CloudTrail events

* An event in CloudTrail is the record of an activity in an AWS account. This activity can be an action taken by a user, role, or service that is monitorable by CloudTrail. 
* CloudTrail: management events and data events. By default, trails log management events, but not data events.
* CloudTrail events that are sent to CloudWatch Logs can trigger alarms according to the metric filters you define.
* Beginning on April 12, 2019, trails will be viewable only in the AWS Regions where they log events. If you create a trail that logs events in all AWS Regions, it will appear in the console in all AWS Regions. If you create a trail that only logs events in a single AWS Region, you can view and manage it only in that AWS Region.

## AWS CloudTrail integration with S3

* CloudTrail records events in each region and delivers the CloudTrail event log files to an S3 bucket
* CloudTrail must have the required permissions, and it cannot be configured as a Requester Pays bucket. CloudTrail automatically attaches the required permissions to a bucket when you create an Amazon S3 bucket as part of creating or updating a trail in the CloudTrail console.
* Logs are created every 5 mintes, but it takes sometime 15 minutes to deliver to S3 (it is not realtime).
* KMS can be used to encrypt the log files
* SNS can notify the events (with or withou CloudWatch)
* EventSelectors can be used to adjust the CloudTrail

## CloudTrail meta-data

* User-agent can be used to find the source of event
  * "userAgent": "aws-cli/1.3.2 Python/2.7.5 Windows/7"
* CloudTrail can be configured to deliver log files from multiple regions to a single S3 bucket for a single account.
* eventName
* eventSource  - service that generated event
* SourceIPAddress - find the IP address
* "userAgent" : {'Signin.amazonaws.com', 'Console.amazonaws.com', lambda.amazonaws.com'}

## CloudTrail log-file, bucket folder name convention

* AccountID_CloudTrail_RegionName_YYYYMMDDTHHmmZZ_UniqueString.FileNameFormat (.json.gz) is the extension
* BucketName/prefix/AWSLogs/AccountID/CloudTrail/RegionName/YYYY/MM/DD

## AWS CloudTrail logs (sample)

```json
{"Records": [{
    "eventVersion": "1.0",
    "userIdentity": {
        "type": "IAMUser",
        "principalId": "EX_PRINCIPAL_ID",
        "arn": "arn:aws:iam::123456789012:user/Alice",
        "accessKeyId": "EXAMPLE_KEY_ID",
        "accountId": "123456789012",
        "userName": "Alice"
    },
    "eventTime": "2014-03-06T21:22:54Z",
    "eventSource": "ec2.amazonaws.com",
    "eventName": "StartInstances",
    "awsRegion": "us-east-2",
    "sourceIPAddress": "205.251.233.176",
    "userAgent": "ec2-api-tools 1.6.12.2",
    "requestParameters": {"instancesSet": {"items": [{"instanceId": "i-ebeaf9e2"}]}},
    "responseElements": {"instancesSet": {"items": [{
        "instanceId": "i-ebeaf9e2",
        "currentState": {
            "code": 0,
            "name": "pending"
        },
        "previousState": {
            "code": 80,
            "name": "stopped"
        }
    }]}}
}]}
```


## AWS CloudTrail integrity

* We can validate integrity of the log cloudtrail log files
* Used for security and integrity
* SHA-256 is used for CloudTrail
* CloudTrail creates digest every hour
* Digest files are signed by private key of key pair
* ```bash aws cloudtrail validate-logs --trail-arn <trailarn> --start-time <start-time> --endtime <endtime>  --s3-bucket <bucketname> --s3-prefix <s3-prefix> --verbose```

## AWS CloudTrail Secondary account Account-B accessing log of primary account (Account-A)

* Create new role
* Apply policy to only allow access for Account-B's folder in S3
* Establish trus relationship between AccountA and AccountB
* Create new account in AccountB
* Create a policy and apply sts:AssumeRole to this user in AccountB
* Example steps
  * Swith to Account-A (Primary)
  * Create new role named 'Cross-Account-CloudTrail-Log' and assign Role-For cross account access
  * Select secondary account-id for that we need provide access (in Role-For cross account)
  * Attach a policy to this role (allow read-only acccess to bucket)
  * Swith to Account-B (Primary)
  * Create policy for AssumeRoleCloudTrail
  * Create user-xyz in Account-B
  * And attache AssumeRoleCloudTrail policy to user-xyz in Account-B

## AWS CloudTrail S3 policy for readonly account and AssumeRlePolicy

```json
  {
   "Version":"2012-10-17",
   "Statement":[
      {
         "Effect":"Allow",
         "Action":[
            "s3:GetBucket",
            "s3:ListBucket",
         ],
         "Resource":"arn:aws:s3:::awsexamplebucket"
      }
   ]
}
```

```json
  {
   "Version":"2012-10-17",
   "Statement":[
      {
         "Effect":"Allow",
         "Action":["sts:AssumeRole"],
         "Resource":"arn:aws:iam::primary-account-A:role/Cross-account-cloudtrail"
      }
   ]
}
```

## AWS Config

* Why AWS config?
  * What resources we have? can we save some money?
  * Are there any security vulnerabilities?
  * How are resources linked within the environment
  * Is infrastructure are compliant to compliance
* Resource Management (using AWS Config)
  * AWS Config can capture resource changes
  * Act as resource inventory
  * Stores configuration history
  * Provide snapshot of all resources (and configurations)
  * Notification changes
  * Use rules to check compliance
  * Perform security analysis
  * Identify relationship
* AWS Config is region specific  


## AWS Config - Components

* AWS Resource
* AWS Configuration item (json file)
  * Configuration information
  * Relationship information
  * Other metadata
  * Resource changes are automatically recorded as CI and sent to Configuration Stream
  * Configuration history/Configuration streams/Configuration snapshots
* Configuration stream
  * When configuration history files are arrived
  * When configuration snapshot started
  * State of compliance changes for a resource
* Configuration history
  * aws configservice get-resource-config-history --resource-type AWS:EC2::Subnet --resource-id subnet-ef22b32b7
  * Can be delivered every 6 hours to S3 bucket
* Configuration snapshot (for a given time to s3 bucket)
* Configuration recorder
  * Core component that records CI details
  * Can be started/stopped
  * Resource filter can be applied
* Config Rules (help to enforce compliance rules?)
  * Can trigger lambda function
  * AWS Predefined rules (below are few samples)
    * RDS-Storage-Encrypted
    * Encrypted volume
    * Root-Account-MFA-Enabled
    * IAM-User-NOPolicy-Check
* Resource Relationships (dependency matrix?)
* SNS Topic can be used for notification
* S3 bucket can store configuration information
* AWS Config permission
  * Requires read only permission to record all information
  * Requires access to S3 and SNS

## CloudTrail lab notes

* CloudTrail records the last 90 days of events for AWS accounts. 
* The default events do not support triggering alerts, event metrics, and long term storage. We require Trail for that.
* 



## Configuration Item

```json
[
  {
    "version": "1.3",
    "accountId": "777719877733",
    "configurationItemCaptureTime": "2020-07-11T23:57:46.383Z",
    "configurationItemStatus": "ResourceDiscovered",
    "configurationStateId": "1594511866383",
    "configurationItemMD5Hash": "",
    "arn": "arn:aws:ec2:us-west-2:777719877733:security-group/sg-0bf9c8e5ea6375194",
    "resourceType": "AWS::EC2::SecurityGroup",
    "resourceId": "sg-0bf9c8e5ea6375194",
    "resourceName": "default",
    "awsRegion": "us-west-2",
    "availabilityZone": "Not Applicable",
    "tags": {},
    "relatedEvents": [],
    "relationships": [
      {
        "resourceType": "AWS::EC2::VPC",
        "resourceId": "vpc-009882a2845337a77",
        "relationshipName": "Is contained in Vpc"
      }
    ],
    "configuration": {
      "description": "default VPC security group",
      "groupName": "default",
      "ipPermissions": [
        {
          "fromPort": 80,
          "ipProtocol": "tcp",
          "ipv6Ranges": [],
          "prefixListIds": [],
          "toPort": 80,
          "userIdGroupPairs": [],
          "ipv4Ranges": [
            {
              "cidrIp": "0.0.0.0/0"
            }
          ],
          "ipRanges": [
            "0.0.0.0/0"
          ]
        },
        {
          "ipProtocol": "-1",
          "ipv6Ranges": [],
          "prefixListIds": [],
          "userIdGroupPairs": [
            {
              "groupId": "sg-0bf9c8e5ea6375194",
              "userId": "777719877733"
            }
          ],
          "ipv4Ranges": [],
          "ipRanges": []
        },
        {
          "fromPort": 3389,
          "ipProtocol": "tcp",
          "ipv6Ranges": [],
          "prefixListIds": [],
          "toPort": 3389,
          "userIdGroupPairs": [],
          "ipv4Ranges": [
            {
              "cidrIp": "0.0.0.0/0"
            }
          ],
          "ipRanges": [
            "0.0.0.0/0"
          ]
        },
        {
          "fromPort": 443,
          "ipProtocol": "tcp",
          "ipv6Ranges": [],
          "prefixListIds": [],
          "toPort": 443,
          "userIdGroupPairs": [],
          "ipv4Ranges": [
            {
              "cidrIp": "0.0.0.0/0"
            }
          ],
          "ipRanges": [
            "0.0.0.0/0"
          ]
        }
      ],
      "ownerId": "777719877733",
      "groupId": "sg-0bf9c8e5ea6375194",
      "ipPermissionsEgress": [
        {
          "ipProtocol": "-1",
          "ipv6Ranges": [],
          "prefixListIds": [],
          "userIdGroupPairs": [],
          "ipv4Ranges": [
            {
              "cidrIp": "0.0.0.0/0"
            }
          ],
          "ipRanges": [
            "0.0.0.0/0"
          ]
        }
      ],
      "tags": [],
      "vpcId": "vpc-009882a2845337a77"
    },
    "supplementaryConfiguration": {}
  }
]
```


* [Getting Started with AWS CloudTrail Tutorial](https://docs.aws.amazon.com/awscloudtrail/latest/userguide/cloudtrail-tutorial.html)
* [AWS Sample logs](https://docs.aws.amazon.com/awscloudtrail/latest/userguide/cloudtrail-log-file-examples.html#cloudtrail-log-file-examples-section)
* [Cloud Trail documentation](https://docs.aws.amazon.com/cloudtrail/index.html)
* [AWS CloudTrail User guid](https://docs.aws.amazon.com/awscloudtrail/latest/userguide/cloudtrail-user-guide.html)
* [AWS CloudTrail Update – SSE-KMS Encryption & Log File Integrity Verification](https://aws.amazon.com/blogs/aws/aws-cloudtrail-update-sse-kms-encryption-log-file-integrity-verification/)