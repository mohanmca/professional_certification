## RTO - Recovery time objective (8-hrs)

* Defined as the maxium amount of time in which a service can remain unavailable before it's claased as damagin to the business
* How soon system is recovering from failure
* Recoverable service object at point in time
* Disaster recovery process should restore business proess within certain hours

## RPO - Recovery point objective (1-hrs)

* Defined as the maxium amount of time for which data could be lost for a service
* Acceptable amount of data loss measured in time value
* Data loss could be 1 hour (last point in data that we can recover). Should be extreemly low


## Backup and restore

* On disaster - recover data from backup
* Ensure stored archive is secure
* Ensure backup is tested periodically


## Pilot light

* Route 53 - Route requests to other regions
* Bandwidth replication
* Synchronus (Atomic) and Async replication (Not atomic)
* [Rapidly recover mission](https://aws.amazon.com/blogs/publicsector/rapidly-recover-mission-critical-systems-in-a-disaster/)


## Warm Standby

## Multi site (Most prefered and highly available)

## On-Premises backup and story


### Failure for on-premise reasons

* Backup in the same location as production might impact backup storage too
* Tape might be corrupted due to overuse
* Tape archive might lead to manual errors due to "Incorrect tape roation" and "Incorrect labelling"
* Scalability, Costs and Data availability


### Cloud to rescue

* Cost efficient, reliable and scalable
* Available and durable
* Zero maintenance of hardware required
* Off-site storage
* Readily accessible


* Aws direct connect - 10gb speed data transfer between On-Premises and AWS
* AWS VPN, Internet Connection
* AWS Snowball
* AWS Snowmobile (100 PBper snowmobile)

### Durability & Avilability

#### S3 standard

* 99.999-999-999 (11 9s of durability) 
* 99.99 (4 9's of avilability)

#### S3 (IA)
* 99.999-999-999 (11 9s of durability) 
* 99.9 (3 9's of avilability)
* NA (for Glacier availability, but durability remains same)

### Security and Compliance of storage is quite important


### S3 as effective backup solution

* 0 byte to 5 TB
* Standard, IA and Glacier
* 11-9s of available and 4-9's of durablity (for Standard)
* Client or Server side encryption
* 11-9s of availablity and 3-9's of durablity (for IA) (Suitable for DR purpose)
* Glaciers are stored as archive (Different class.. called cold storage)
  * Supports encryption, Vault Lock Seuciryt enabling WORM
  * Take from minutes to hours (not immediate)
* Glacier - Data Retrieval
  * Expedited - 0.03GB and 0.01 (Per request)
  * Standard - 0.01GB and 0.05 (Per 1.000 request)
* https://aws.amazon.com/s3/pricing/
* S3 - not default cross regional replication enabled
* Enable multi-part upload for more than 100MB for through-put and increase speed, can pause and resume


### Security of S3

* IAM policies
* Bucket Policies
* Access control list
* Lifecycle Policies
* MFA Delete
* Versisoning


### AWS Snowball service

* Physical appliance
* 50TB or 80TB
* Dust, Water and Tamper resistant
* Default AWS KMS encrypted
* End-2-End tracking, can be notified using SNS
* Data aggregation using muliple appliance
* 




## [What are the best practices for AWS Disaster Recovery planning?](https://cloudranger.com/best-practices-aws-disaster-recovery-planning/)
## [File transfer calculator](https://thecloudcalculator.com/calculators/file-transfer/)