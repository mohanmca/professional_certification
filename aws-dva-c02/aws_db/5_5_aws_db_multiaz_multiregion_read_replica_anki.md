## AWS RDS Mulit-AZ deployments

* Amazon RDS Multi-AZ deployments provide enhanced availability and durability for RDS database (DB) instances
* Amazon RDS automatically creates a primary DB Instance and *synchronously replicates* the data to a *standby instance* in a different Availability Zone (AZ).
* Each AZ runs on its own physically distinct, independent infrastructure, and is engineered to be highly reliable.
* In case of an infrastructure failure, Amazon RDS performs an automatic failover to the standby (or to a read replica in the case of Amazon Aurora)
* Amazon RDS read replicas can be set up with their own standby instances in a different AZ.


## [Multi-AZ deployments, multi-region deployments, and read replicas](https://aws.amazon.com/rds/features/multi-az/)

* Comparison

## Multi-AZ deployments

* Main purpose is high availability
* Non-Aurora: synchronous replication; Aurora: asynchronous replication
* Non-Aurora: only the primary instance is active; Aurora: all instances are active
* Non-Aurora: database engine version upgrades happen on primary; Aurora: all instances are updated together
* Automatic failover to standby (non-Aurora) or read replica (Aurora) when a problem is detected


## Multi-AZ deployments Failover mechanism

* Secondary instance would take over from primary instance
* It changes based on databse engine type
* For MS-SQL-server, it uses mirroring supported by MS-SQL-Server
* 60-120 seconds for DNS fail-over to secondary instance
* RDS-EVENT-0025 (Failure completion event)

## Multi-Region deployments

* Main purpose is DR and local performance
* Asynchronous replication
* All regions are accessible and can be used for reads
* Automated backups can be taken in each region
* Non-Aurora: database engine version upgrade is independent in each region; Aurora: all instances are updated together
* Aurora allows promotion of a secondary region to be the master

## Read-Replica

* Retention value of backups needs to be set a value >= 1
* It is neither primary/secondary instance, initally cloned from snapshot of one of this instance.
* It is read-only database, not for resilency
* It could be from secondary-db-instance
* They are available for MySQL, MariaDB, PostgreSQL, Oracle, and SQL Server
* There can be multiple read-replica
* Read replica can be promoted as primary if there is an event

## Read-Replica - MySQL (MariaDB)

* Should be MySQL5.6 or more
* InnoDB can be used for RR
* Nested read-replica upto 4 layers are possible
* Read ReplicaLag can be used to find the lag (ideally zero is prefered)

## Read-Replica - PostGresSQL

* Should be 9.3.5 or higher
* native PgSQL streaming is used for replication and read-replica
* write-ahead log is asynchrnously replicated between master and read-replicas
* Dedicated DB role is their to manage replication
* Multi-AZ read replica can be easily created for PgSQL