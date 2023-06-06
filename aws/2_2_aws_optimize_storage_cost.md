### Understanding and Optimizing Costs with AWS Storage Services

#### Amazon S3 and Glacier (Glacier deep archive)
* S3 has multiple storage classes
* [Performance across the S3 Storage Classes](https://aws.amazon.com/s3/storage-classes/#Performance across the S3 Storage Classes)
* Lifecycle Transition & Intelligent tiering can be used to move between storage classes and reduce cost


### Free Data transfer
* From internet into S3
* From S3 to EC2
* From S3 to CloudFront

## [EBS Optimized Instances](https://docs.aws.amazon.com/AWSEC2/latest/UserGuide/ebs-optimized.html)
* EBS IO traffic volume generally compete for network along with other network traffic
* EBS-Optimized instance network separated from other network traffic
* Network traffic is exclusively for I/O operations

### Properties that affects the cost
* Profile of your storage
* Access pattern
* Its criticality (things can be destroyed would cost less)
* Availability
* Latency factor of access

### Fee for Data transfer
* Cost 0.02 per GB to trnasfer to other AWS services
* Cost 0.04 per GB to trnasfer to internet from AWS S3 (US, Europe and other region as on 2020-June)
* Cost 0.08 per GB to trnasfer to internet from AWS S3 (other region other region as on 2020-June)
* Inter region replication would be priced upon the source region
* Versioning would cost additional
* S3 Replication time control (RTC)
  * 99% of the objects are replicated within 15 minutes (between region)
  * RTC has $ additional cost of 0.015 per GB
* Lifecycle policies can be used to control cost for S3 and Glacier


### AWS - EFS (NFS version)
* Low-latency for peta-bytes of data
* Can be accessed by 1000s of EC2 instance
* Standard operating system API
* Strong consistency - NFS 4.1 and NFS 4.0
* Regionally accesible (from multiple AZ of the same region)
* EFS Standard vs EFS IA
* EFS Lifecycle management (if file is accessed, moves from IA to standard)
  * Metadata and less than 128 KB files are not moved to IA
* EFS Throughput
  * Bursting vs Provisioned throughput  
  * If you store more data, more bursting throughput is eligible
  * If you store less data but requires frequent and low-latency access, provisioned throughput is requried
  * Here storage IO is priced based on need, and noisy neighbour is forced to pay more


### Amazon FSx
* Amazon FSx - windows file server
  * For windows based file server and based on windows system
  * Sub-milllisecond latency with AD integration
  * Windows NTFS (SSD storage)
  * Priced based on Capacity, Throughput and Backups
  * Multi-AZ is bit more expensive
  * De-duplication feature is free and would reduce actual storage and saves cost
* Amazon FSx for Lustre (for HPC)
  * 100Gbs per second
  * Millions of IPS
  * Integrated with S3
* [Amazon FSx for Lustre Makes High Performance Computing More Accessible](https://cloudacademy.com/blog/amazon-fsx-for-lustre-makes-high-performance-computing-more-accessible/)


#### Lustre Filesystem
* Lustre is a Parallel File System which is similar to NFS4  (Linux kernel supports it)
* Can be compared with GFS or HDFS
* AWS FSx luster can front the S3 buckets
  * We can create a file system to sit in front of part or all of an S3 bucket or buckets. 
  * It takes a few minutes for Lustre to “read” the content and the associated metadata within a bucket using an ImportPath, and then present that as a file structure.


### AWS Backup

* Centralized cloud backup
* Central hub to manage and audit the backup
* First backup of an AWS resource, a full copy of your data is saved. For each incremental backup, only the changed part of your AWS resource data is saved.
* On-premises backup can be automated using AWS storage gateways.
* Need to create backup-policies and backup-plans
  * Backup schedule
  * Lifecycle rules
  * Backup Vault
  * KMS, Tags, Regional copies
* Multiple policies can be created
  * AWS resources can be tagged to match diffent backup plan
  * A resource could be part of different backup policies and plans
* It uses existing backup resoures (EFS, EBS and S3)
* Backup replication can be controled vs Cold-or-Warm storage
* 


### [Amazon Web Services Simple Monthly Calculator](https://calculator.s3.amazonaws.com/index.html)