## AWS Database

* AWS Non Relational database
  * Amazon DynamoDB
  * Amazon ElasticCache
  * Amazon Neptune
* AWS Relational database
  * Amazon RDS (Relational Database service)
    * Commercial
      * Microsoft SQL Server for RDS  
      * Oracle for RDS  
    * Amazon Aurora for RDS
      * Amazon Aurora MySQL fork of AWS (No.1 usage)
      * PostgreSQL
    * Amazon Community DB
      * PostgreSQL
      * MySQL
      * MariaDB
  * Amazon RedShift

## AWS Non-Relational database

* Amazon DynamoDB
  * Key-Value
  * ~Document~
* Amazon ElasticCache
  * Redis
  * Memcache
* Amazon Neptune
  * BlazeGraph
* Features
  * No table schema
  * Fast and secure data store
  * Lack of processing engine

## AWS Cloud Database

* Same as what we use, but renting instead of owning
* Operated by cloud, no upfront license cost
* Infrastructure and everything is provisioned on demand
* Majority of the patch is owned by AWS
* Autoscaling
* Backup managed
* AWS KMS

## AWS DynamoDB

* It is web-service based database and can massively scale for OLTP applications
* Fully managed service
* Document and key-store object
* Local version for testing is accessible
* Supports encryption at rest
* Speed and Performance 

## AWS ElastiCache

* Memcached vs Redis
* Redis
  * Lots of features
  * Supports multiple data-types (string, hashes, lists, sets and sorted-sets, bitmaps)
* Memcached
  * Simpler
  * Need to run on multicore and multi-threads
  * Scale-in/scale-out


## AWS Neptune

* Graph Database based on blazegraph
* Knowledge graphs
  * W3C RDF
  * Property Graph
  * OpenCypher, Sparql, Gremlin
* Stores data across 6 Nodes in 3 AZ and supports upto 15 read replicas

## AWS Timestream
1. Timeseries database
2. Separates
   3. ingestion, storage and query layers, enable them to scale independently
4. Amazon Quantum Ledger Database
   5. Amazon QLDB
  
## AWS RDS Subnet

* Subnets are segments of a VPC's IP address range that allow you to group your resources based on security and operational needs.
* A DB Subnet Group is a collection of subnets (typically private) that you create in a VPC and designate for your DB instances.
* Each DB subnet group should have subnets in at least two Availability Zones in a given region. 
* Note that SQL Server Mirroring with a SQL Server DB instance requires at least 3 subnets in distinct Availability Zones.
* When creating a DB instance in a VPC, 
  1. You must select a DB subnet group. 
  2. Amazon RDS uses that DB subnet group and your preferred Availability Zone to select a subnet and an IP address within that subnet to associate with your DB instance.
* When Amazon RDS creates a DB instance in a VPC, it assigns a network interface to your DB instance by using an IP address selected from your DB Subnet Group.
* If the primary DB instance of a Multi-AZ deployment fails, Amazon RDS can promote the corresponding standby and subsequently create a new standby using an IP address from an assigned subnet in one of the other Availability Zones.

## Amazon RDS for MySQL

* Supports 5.5, 5.6, 5.7 and 8.0
* Four instance types - micro-instances, general purpose, memory optimized, and the burstable
* Can create read replicas of database and deploy those across more than one availability zone, using the multi-AZ feature of MySQL and RDS.
* Increase the size of your database storage on the fly, with zero downtime.
* Point-in-time restore and snapshot restore features for InnoDB storage engine.
* MariaDB also supported

## Amazon RDS for Microsoft SQL Server

* Default database size is four terabytes
* Create a database up to 16 terabytes, and as a managed service you can select a multi-AZ deployment
* provision from 1000 IOPS to 32,000 IOPS for new SQL Server DB
* The transaction log is backed up at five-minute intervals enabling point-in-time recovery to any one given second. 
* Automatic backups can be stored for up to 35 days.


## Amazon RDS for Oracle

* Supports multiple edition - SE, SE1, SE2 and EE
* Amazon Redshift is a supportive data-source for Oracle Business Intelligence versions 12.2.1.0 and 12.2.1.1
* BYOL - Only oracle uses this license

## Amazon RDS for Postgres 

* Supports from 9.3 to 12.3 on Amazon RDS


## Amazon Aurora, the cloud native database from Amazon

* Amazon's own fork of MySQL and PostgreSQL
* Designed and built from the ground up to be cloud native
* Amazon Aurora replicates data across three availability zones by default
* Aurora service also deploys a cloud native database cluster and this database cluster as the underlying data store
* Each cluster has one primary instance which performs all the data modifications to the cluster volume and supports read and write operations.
* Each cluster also has at least one Aurora replica which supports only read operations
* Aurora DB cluster can have up to 15 Aurora replicas of the primary instance
* Multiple Aurora replicas distribute the read workload


## Creating DB service

* Choose DB type and version
* Choose instance type
* Choose storage and provisioned IOPS
* Choose DB instance name and password 
* Create VPC
* Select AZ
* Encrypt database at rest and AWS KMS details
* Backup retention and backup window
* Maintenance window (for patching)



## Choosing between Database types

* Table join and structured query at database layer or code?
* Do you need cache?
* Is it OLTP vs OLAP
* Do we need internet scale database?

## AWS RDS Lab

1. Creating an RDS Subnet Group
1. Creating a Database Using RDS
1. Setting up Security Group Rules for Connecting to the RDS Instance
1. Starting an AWS Systems Manager Session Manager Browser Shell Session
1. Connecting to RDS and Creating a Database Table
1. Deleting an RDS Database
1. Start session from Session manager
```
sudo -i -u ec2-user
sudo yum -y install mysql
mysql -h rds-endpoint-mysql-lab.cciyta5sjbyl.us-west-2.rds.amazonaws.com -u cloudacademy -p rdsappdb
mysql -h rds-mysql-lab.cciyta5sjbyl.us-west-2.rds.amazonaws.com -u cloudacademy -pmyStrongRDSpwd! rdsappdb
mysql -h database-1.cciyta5sjbyl.us-west-2.rds.amazonaws.com -u cloudacademy -admin123 rdsappdb

CREATE TABLE laboratory ( id INT, name VARCHAR(100) );
```

## AWS RDS Cost components

1. On-Demand-Instance
1. On-Demand-Instance (BYOL)
1. Reserved-Instance (BYOL)
1. Reserved-Instance (BYOL)
1. Database Storage and IO
1. Backup storage
1. Backtrack
1. Snapshot Export
1. Data Transfer



## AWS RDS Instance lab notes
1. Amazon Relational Database Service (Amazon RDS) makes it easy to set up, operate, and scale a relational database in the cloud. Before launching actual RDS instances, you need to configure a DB Subnet Group.
1. Subnets are segments of a VPC's IP address range that allow you to group your resources based on security and operational needs. A DB Subnet Group is a collection of subnets (typically private) that you create in a VPC and designate for your DB instances. Each DB subnet group should have subnets in at least two Availability Zones in a given region. Note that SQL Server Mirroring with a SQL Server DB instance requires at least 3 subnets in distinct Availability Zones.
1. When creating a DB instance in a VPC, you must select a DB subnet group. Amazon RDS uses that DB subnet group and your preferred Availability Zone to select a subnet and an IP address within that subnet to associate with your DB instance. When Amazon RDS creates a DB instance in a VPC, it assigns a network interface to your DB instance by using an IP address selected from your DB Subnet Group. If the primary DB instance of a Multi-AZ deployment fails, Amazon RDS can promote the corresponding standby and subsequently create a new standby using an IP address from an assigned subnet in one of the other Availability Zones.
1. You can create an RDS Subnet Group using the RDS launch wizard.

## [Create RDS Proxy](https://docs.aws.amazon.com/AmazonRDS/latest/AuroraUserGuide/rds-proxy-planning.html)

1. Using a proxy allows your applications to pool and share database connections to help them scale. A proxy simplifies connection management and makes applications more resilient to database failures.
2. It allows application connect usig IAM, internally proxy connects using DB credentials
2. An Amazon RDS Proxy is a service you can put in front of your MySQL and PostgreSQL Amazon RDS databases to improve their scalability and reliability.
1. RDS proxy has three main features:
   1. Pooling an application's database connections
   1. Automatically switching to a standby instance in case of a database failure
   1. Enabling the use of AWS Identity and Access Management (IAM) credentials to authenticate
2. sysbench is a general-purpose benchmarking tool that can be used to simulate and benchmark complex workloads. It has support for database testing and is also easy to modify with scripts using the Lua scripting language.
3. Your RDS Proxy has served all the connection requests made by sysbench. When more requests come simultaneously than the RDS database can handle, new connections are accepted and queued up by the RDS Proxy until database instance connections become available again.
1. Queuing increases the latency for the affected database connections. Whether this is a valid approach will depend upon your workload and specific application requirements. RDS proxies can be configured to reject connections above a certain amount.
1. A particularly good use-case for RDS proxies is for use with AWS Lambda functions. AWS Lambda functions are often used to handle unpredictable and spiky workloads. By configuring them to use an RDS proxy you can reduce or eliminate the number of database connection failures they may see. It's also easy to take advantage of RDS Proxy IAM authentication from AWS Lambda functions, simplifying credential management.
1. Something to be aware of is that an RDS Proxy can not be directly publicly accessed. It is deployed as a VPC Endpoint and assigned private IP addresses from the VPC subnets selected when creating it. RDS Proxies can be accessed across VPCs by creating additional endpoints.


## Reference

* [AWS Database Options](https://cloudacademy.com/blog/aws-database-options/)