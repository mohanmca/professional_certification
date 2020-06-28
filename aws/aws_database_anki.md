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
      * PostgreSQL
      * MySQL for RDS  
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
  * Need to run on multi-core and multi-threads
  * Scale-in/scale-out


## AWS Neptune

* Graph Database based on blazegraph
* Knowledge graphs
  * W3C RDF
  * Property Graph
  * Sparql, Gremlin


## AWS RDS (instead of own EC2)

* scale compute metrics in or out independently
  * processor size, the amount of storage, or the IOPS speed, independently of each other.
* The automatic backups and patching.
* RDS run a synchronous, or asynchronous version of your database, in a different availability zone. (multi AZ support for all RDS)
* Automatic failure detection and recovery

## Amazon RDS for MySQL

* Supports 5.5, 5.6, 5.7 and 8.0
* Four instance types - micro-instances, general purpose, memory optimized, and the burstable
* Can create read replicas of database and deploy those across more than one availability zone, using the multi-AZ feature of MySQL and RDS.
* Increase the size of your database storage on the fly, with zero down time.
* Point-in-time restore and snapshot restore features for InnoDB storage engine.
* MariaDB also supported

## Amazon RDS for Microsoft SQL Server

* Default database size is four terabytes
* Create a database up to 16 terabytes, and as a managed service you can select a multi-AZ deployment
* provision from 1000 IOPS to 32,000 IOPS for new SQL Server DB
* The transaction log is backed up at five-minute intervals enabling point-in-time recovery to any one given second. 
* Automatic backups can be stored for up to 35 days.


## Amazon RDS for Oracle

* BYOL
* Supports multiple edition - SE, SE1, SE2 and EE
* Amazon Redshift is a supportive data-source for Oracle Business Intelligence versions 12.2.1.0 and 12.2.1.1


## Amazon RDS for Postgres 

* Supports from 9.3 to 12.3 on Amazon RDS


## Amazon Aurora, the cloud native database from Amazon

* Amazon's own fork of MySQL and PostgreSQL
* Designed and built from the ground up to be cloud native
* Amazon Aurora replicates data across three availability zones by default
* Aurora service also deploys a cloud native database cluster and this database cluster as the underlying data store
* Each cluster has one primary instance which performs all of the data modifications to the cluster volume and supports read and write operations.
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


## Reference

* [AWS Database Options](https://cloudacademy.com/blog/aws-database-options/)