## AWS DynamoDB - OVerview

* Components of dynamodb
  * Tables, items, and attributes are the core components 
* Fully managed proprietary NoSQL database service that supports key-value and document data structures
* Supported local secondary index, recently global seconday index
* Automatically replicated across multiple Availability Zones within an AWS Region.
* Automatically partition data and incoming traffic across multiple partitions which are themselves stored on numerous backend servers distributed across three availability zones within a single region.


## DynamoDB Units

* **Read unit** - One read request unit represents one strongly consistent read request, or two eventually consistent read requests, for an item up to 4 KB in size.
* **write unit** - One write request unit represents one write for an item up to 1 KB in size.
* Use-cases
  * Transactional read requests require 2 read request units to perform one read for items up to 4 KB. 
  * if item size is 8 KB
    * 2 read request units to sustain one strongly consistent read
    * 1 read request unit if you choose eventually consistent reads
    * 4 read request units for a transactional read request
  * Transactional write requests require 2 write request units to perform one write for items up to 1 KB.
  * if your item size is 2 KB
    * 2 write request units to sustain one write request
    * 4 write request units for a transactional write request

## DynamoDB Keys

* Partition key – A simple primary key, composed of one attribute known as the partition key. 
* Partition key and sort key – Referred to as a composite primary key, this type of key is composed of two attributes. The first attribute is the partition key, and the second attribute is the sort key.

## Global table  - Cross region replication

* In DynamoDB, we can create tables that are automatically replicated across two or more AWS Regions, with full support for multimaster writes.
  * To build fast, massively scaled applications for a global user base without having to manage the replication process
*   


## DynamoDB supports two kinds of indexes

* Global secondary index – An index with a partition key and sort key that can be different from those on the table.
  * GSI can span all of the data in the base table, across all partitions.
  * Has own provisioned throughput settings for read and write activity that are separate from those of the table.
  * No size limit
* Local secondary index – An index that has the same partition key as the table, but a different sort key.
  * Total size of indexed items for any one partition key value can't exceed 10 GB.
* Each table in DynamoDB can have up to 20 global secondary indexes (default quota) and 5 local secondary indexes.


## Point-in-Time Recovery for DynamoDB

* Continuous backups of your DynamoDB table data.
* Optional, has to explicitly enable PITR (Point-in-time-recovery)
* PITR backup can be restored into new table

## What is the consistency model of DynamoDB?

* Read can choose the consistency model depending on demand
  * Eventually consistent reads (the default, high throughput)
  * Strongly consistent reads (read from master, low throughput)

## AWS DynamoDB DAX (Accelerator)

* in-memory cache for DynamoDB that delivers up to a 10x performance improvement
  * from milliseconds to microseconds – even at millions of requests per second.
* Dynamo DB - milliseconds, DAX - MicroSeconds
* DAX is compatible with DynamoDB (no application code change)
* DAX supporst AES encryption
* DAX doesn't handle table related operations, they are handled by DynamoDB
* All write operations ("write-through") are written to DynamoDB first and later to DAX (DAX is eventual consistency)
* DAX is not ideal
  * Applications that require strongly consistent reads.
  * Applications that do not require microsecond response times for reads.
  * Applications that are write-intensive, or that do not perform much read activity.
  * Applications that are already using a different caching solution with DynamoDB, and are using their own client-side logic for working with that caching solution.


## AWS DynamoDB DAX Cluster

* Minimum of 3 nodes, maximum of 10 nodes (1 Primary, 9 Replicas)
* DAX EC2 cluster requires additional role and would use your existing VPC
* DAX EC2 requires additional inbound rule for port 8111
* We should install "DAX Client" software on those EC2 instances
* Read Capacity Units can be reduced since DAX would take care most of the reads


## AWS DynamoDB Commands

```bash
aws dynamodb create-table --region us-west-2 --table-name cloudacademy-courses \
    --key-schema AttributeName=courseid,KeyType=HASH --attribute-definitions AttributeName=courseid,AttributeType=S \
    --billing-mode PAY_PER_REQUEST

aws dynamodb batch-write-item --request-items file://ProductCatalog.json

aws dynamodb update-continuous-backups --table-name Music --point-in-time-recovery-specification PointInTimeRecoveryEnabled=True

aws dynamodb describe-continuous-backups --table-name Music

aws dynamodb restore-table-to-point-in-time --source-table-name Music --target-table-name MusicEarliestRestorableDateTime \
--no-use-latest-restorable-time --restore-date-time 1519257118.0

```


## Reference

* [https://www.dynamodbguide.com/what-is-dynamo-db](https://www.dynamodbguide.com/what-is-dynamo-db) 
* [Amazon Dynamo Paper](https://www.allthingsdistributed.com/files/amazon-dynamo-sosp2007.pdf)
* [Global Tables](https://github.com/cloudacademy/dynamodb-globaltables)