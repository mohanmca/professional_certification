## AWS Storage offerings

* S3 - Object storage
* EBS - Block level storage (low latency service)
  * Persistent

## AWS EFS - File storage (Kind of SAN or NAS file storage)

* Low latency - file level
* Multiple instance can access the storage
* Acts as network resource
* Mount point
* Can be scaled to peta-bytes in size
* Suppots High level throughput
* NFS 4.1 and 4.0
* Replicated across single region but multiple AZ (Regional boundry)


## AWS EFS - Storage class

* Standard
  * Access - Anytime
  * Cost - Standard Cost
  * Standard Latency
* IA (Infrequent access) - cheaper - higher latency
  * Access - Infrequent
  * Cost - reduced
  * Higher Latency

## AWS EFS LifeCycle Management

* Move to IA after 30/60/90/days
* If it accessed, it would automatically move from IA to standard
  * Time is reset for every read, and moved to IA
* Metadata would not be moved to IA
  * 128K in size (won't move)
* Lifecycle can be switched on/off

## AWS EFS - Performance modes
* AWS EFS - General Purpose
  * Standard Throughput
    * 7k <=
    * Test before choosing between MaxIO or General Purpose
  * Low latency
* AWS EFS - Throughput mode (Max/IO)
  * Named as Max/IO
  * 7K>=
  * Unlimited throughput
    * Bursting throughput mode
      * 100 MiB/s per TiB
      * BurstCreditBalance
    * Provisioned throughput mode
      * We can buy even if we store small amoount of data

## AWS EFS - EC2 instance - EFS - Mounting methods

* Linux NFS
* EFS Mount helper (new and recommended)
  * Log to /var/log/amazon/efs
  * Automatically connect to EFS at startup by editing /etc/fstab
  * ```bash
      sudo yum install -y amazon-efs-utils
      sudo mkdir efs
      sudo mount -t efs fs-0bc8309:/ efs
      sudo mount -t efs -o tls fs-0bc8309:/ efs
      ```
* EC2 instances connect to EFS using mount-target
  * Each mount target has ip-address
* EFS - has dns name


## AWS EFS Overview

* Allow access to 
  * elasticfilesystem:CreateFileSystem
  * elasticfilesystem:CreateMountTarget
* Allow access to mount
  * ec2:DescribeSubnet
  * ec2:CreateNetworkInterface
  * ec2:DescribeNetworkInterfaces

## AWS EFS Security

* Data at rest - AWS KMS
* Data at transit - tls
* Uses stunnel (is an open source multi-platform application)

## AWS EFS - Import existing data

* Aws DataSynch - securely move data from prop network to AWS
* Download dataysync agent and should be installed as VMWare ESXi host to your site
* The AWS DataSync In-cloud Transfer Quick Start and Scheduler can be used for a wide variety of use cases to transfer NFS file data from one file system to another. Below are a few examples.
  * Migrate an NFS file system from Amazon EC2 to Amazon EFS within the same AWS region
  * Replicate an NFS file system from Amazon EC2 in one AWS region to an Amazon EFS file system in a different AWS region for disaster recovery.
  * Migrate an Amazon EFS file system from EFS standard (no lifecycle management) to an EFS file system with lifecycle management enabled. File systems with lifecycle management enabled will automatically move  files to a lower-cost Infrequent Access storage class (EFS IA) based on a predefined lifecycle policy.
  * Migrate an Amazon EFS file system from one performance mode to another performance mode within the same AWS region.
  * Replicate an Amazon EFS file system from one AWS region to another Amazon EFS file system in a different AWS region for disaster recovery.


## AWS EFS Policy

```json
{
  "Version": "2012-10-17",
  "Statement": [
    {
      "Sid" : "AllowFileSystemPermissions",  
      "Effect": "Allow",
      "Action": [
        "elasticfilesystem:CreateFileSystem",
        "elasticfilesystem:CreateMountTarget"
      ],
      "Resource": "arn:aws:elasticfilesystem:us-west-2:account-id:file-system/*"
    },
    {
     "Sid" : "AllowEC2Permissions",
      "Effect": "Allow",
      "Action": [
        "ec2:DescribeSubnets",
        "ec2:CreateNetworkInterface",
        "ec2:DescribeNetworkInterfaces"
      ],
      "Resource": "*"
    }
  ]
}
```

## AWS EFS Demo

* Create EC2 security group (EC2 -> SecurityGroup -> Create)
* Add inbound rule within VPC security-group
* Create EFS
