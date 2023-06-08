## Amazon Aurora (DB cluster)

* Database engine that's compatible with MySQL and PostgreSQL.
* Aurora doesn't use local storage for the compute instances.
* It has high-performance storage subsystem.
* Involves entire clusters of database servers that are synchronized through replication, instead of individual database instances.
* Aurora cluster volume is a virtual database storage volume that spans multiple Availability Zones, with each Availability Zone having a copy of the DB cluster data.
* Not supported in the region that has lesser than 3 AZ
* Three types of DB instances
  * Primary DB Instance
  * Aurora Replica
    * Connects to the same storage volume as the primary DB instance and supports only read operations.
    * Aurora automatically fails over to an Aurora Replica in case the primary DB instance becomes unavailable.
    * Aurora multi-master clusters, all DB instances have read/write capability.

## Amazon Aurora Connection Management

* Host name and port that we specify point to an intermediate handler called an endpoint.
* Types of Aurora Endpoints
  * Cluster endpoint
    * mydbcluster.cluster-123456789012.us-east-1.rds.amazonaws.com:3306
  * Reader endpoint
    * mydbcluster.cluster-ro-123456789012.us-east-1.rds.amazonaws.com:3306
  * Custom endpoint
    * Define a custom endpoint to connect to instances that use a particular AWS instance class or a particular DB parameter group
    * Might need to direct internal users to low-capacity instances for report generation or ad hoc (one-time) querying, and direct production traffic to high-capacity instances.
    * myendpoint.cluster-custom-123456789012.us-east-1.rds.amazonaws.com:3306
  * Instance endpoint
    * mydbinstance.123456789012.us-east-1.rds.amazonaws.com:3306

## Viewing the Endpoints for an Aurora Cluster

* aws rds describe-db-clusters --query '*[].{Endpoint:Endpoint,ReaderEndpoint:ReaderEndpoint,CustomEndpoints:CustomEndpoints}'

## Amazon Aurora Storage and Reliability

* Distributed and shared storage architecture
* Cluster volume, which is a single, virtual volume that uses solid state drives (SSDs).
  * A cluster volume consists of copies of the data across multiple Availability Zones in a single AWS Region.
*  Aurora doesn't make a new copy of the table data. Instead, the DB instance connects to the shared volume that already contains all your data.


## Aurora Replicas

* Up to 15 Aurora Replicas can be distributed across the Availability Zones that a DB cluster spans within an AWS Region.
* Aurora Replicas work well for read scaling because they are fully dedicated to read operations on your cluster volume.

## Aurora Serverless

* Aurora database product without managing the resources.
* For a cluster, you can set a min and max ACU (Aurora Capacity Units) based on the load and can even go down to 0 to be paused. 
* Only billed for storage consumed when the instances are zero.
* Billing is based on resources used on a per-second basis.
* Aurora Serverless - Use Cases
  * Infrequently used applications.
  * Low volume blog site.
  * You only pay for resources as you consume them on a per second basis.

## Reference

* [Amazon Aurora](https://github.com/alozano-77/AWS-SAA-C02-Course#Amazon Aurora)