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


### Getting data in/out of AWS
* Direct connect
* VPN
* Internet connections
* AWS Snowball
* AWS Snowmobile
* AWS Storage gateway


### AWS Snowball service

* Physical appliance
* 50TB or 80TB
* SFP+ Coper or SFP+ Optical, RJ45 (Cat6)
* Dust, Water and Tamper resistant
* Default AWS KMS encrypted
* End-2-End tracking using E-INK, can be notified using SNS
* AWS would ensure data removed after transfering to S3
* Data aggregation using muliple appliance
* If your data transfer would take more than a week, prefer AWS snowball
* E-INK has return address, and snowball devices are owned by AWS

### Aws storage gateway

* 3 types - File gatway, volume gateway and tape gateway
* Software appliance - Integrates between On-Premise storage with AWS
* File Gatways - stores in S3
  * HTTPS to S3
  * Provides Local Cache
  * Mounts as drive using NFS v4.1 or V3

#### AWS Stored volume gateways
* Present locally and remotely in S3
* Provides latency (using iScsi volume) backed local disks and mounted using SAN/NAS
* Represented as iSCSI volumes
* Volume can be 1GiB - 16TiB
* Each storage gateway can hold 32 volume
* 16 * 32 = 512TiB maximum storage gatway per AWS stored valume gateway
* SSL, Snapshot support
* Incremental volume snapshopt are stored on S3 as EBS snapshot

#### AWS Cached volume gateways
* Provides as iScsi mounted using SAN/NAS
* Local buffer/cache
* 32 * 32 = 1024iB maximum storage gatway per AWS cached valume gateway
* Incremental volume snapshopt are stored on S3 as EBS snapshot
* Snapshots can be easily mounted to any EC2 as EBS volume

### AWS VTL (Virtual tape library)
* Existing tape backup applications are supported
* Leverage AWS glacier for data archive
* Storage gateway 1500 virtual tapes
* Virtual Tapes - 100Gib-2.5TiB. Data stored in VT are backed by S3 and visible in virtual tape library
* 10 virtual Tape Drives and Media Changer as iScsi device
* Archive 3to5 hrs to retrieve


### One of the main benefit - Easy to retrieve and test the DR plans from the backup using AWS infrastructure.



 



## [What are the best practices for AWS Disaster Recovery planning?](https://cloudranger.com/best-practices-aws-disaster-recovery-planning/)
## [File transfer calculator](https://thecloudcalculator.com/calculators/file-transfer/)