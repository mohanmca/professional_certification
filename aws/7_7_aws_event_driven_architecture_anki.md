## Components of decoupled architecture in AWS ( utilizing aws services for decoupled and/or event-driven environment.)

* Amazon Simple Queue Service
* Amazon Simple Notification Service
* Amazon Kinesis
* AWS Lambda


## Decoupled Architecture

* Each component in a decoupled solution is effectively unaware of changes to other components due to the segregation of boundaries applied.
* Each service within a decoupled environment communicates with others using specific interfaces which remain constant

## Event-Driven Architectures.

* Event-driven architectures closely relate and interact with decoupled architectures, however, services in an event-driven architecture are triggered by *events* that occur within the infrastructure.
* What is an event?
  * EC2 instance changing from ‘running’ to ‘stopped’ (Change of state)
  * An order has been placed on a website
  * An item has been moved from for sale to sold
  * A message that conveys something tangible
* Three components of event-driven architecture
  * CFS (Consumer, Function, Supplier) - Spring Integration terms
  * PRC (Producer, router and consumer)
    * A producer is the element within the infrastructure that will push an event to the event router.
    * The event router then processes the event and takes the necessary action in pushing the outcome to the consumers.


## AWS SQS

* Sending, storing and receiving messages at scale
* Serverless system, microservices and distributed architecture
* SQS types
  * Standard Queue
    * Unlimited throughput
      * Unlimited TPS
    * At-least once delivery
    * Best-effor ordering
  * FIFO Queue
    * High throughput
      * Default 300 TPS
      * Batching could work with 10 messages per operation (3000TPS with batching)
    * FIFO-deliver delivery
    * Exactly-once processing
  * Dead letter queue
    * Depends on above two types
    * If consumer couldn't process the message after maxiumum number of tries specified, queue will send the message to DLQ
* SQS Components
  * PQC - Producer, Queue and Consumer
  * Visibility timeout
  * Queue attributes
    * Message retention period
    * Delay deliverry
    * Maximum Receives (1-1000)
    * Maximum message size
* Visibility timeout
  * Visibility-timeout starts when a message is retrieved by a consumer
  * Consumers can process the message before visibility timeout expires
  * 30-seconds to 12-hours
  * If visibility timeout expires, the message will become available for other consumer to process the message

## AWS SNS

* Publisher, Topic and Subscriber
* Policy of publisher and sender
  * Allow specific/anyone to subscribe/publish
  * 
* AWS Deliver protocols
  * HTTP/HTTP(S)
  * Email
  * Email-JSON
  * SQS
  * Application
  * Lambda
  * SMS
  
## AWS SNS and AWS SQS integration

* AWS SNS could be producer for SQS
* SQS Queue actions > Subscribe SNS Topic
  * Topic region and Topic ARN (ER Ireland/arn:aws:sns:eu-west-1:737739171055:EmailQueue)
* SQS Queue actions > Configure trigger for Lambda function
* SQS Topic actions > Subscribe to topic
  * Protocol > AWS Lambda > arn:aws:lambda:eu-west-1:737739171055:function:OthersTest
  * Version (or alias)
  

### AWS SNS Policy

* The following example of a full policy grants the AWS account ID 1111-2222-3333 the ability to subscribe to notifications from a topic.

```json
{
  "Statement": [{
    "Sid": "Statement1",
    "Effect": "Allow",
    "Principal": {
      "AWS": "111122223333"
    },
    "Action": ["sns:Subscribe"],
    "Resource": "arn:aws:sns:us-east-2:444455556666:MyTopic",
    "Condition": {
      "StringEquals": {
        "sns:Protocol": "https"
      }
    }
  }]
}
```

* AWS account 1111-2222-3333 can publish messages to the topic.

```json
{
  "Statement": [{
    "Sid": "grant-1234-publish",
    "Effect": "Allow",
    "Principal": {
      "AWS": "111122223333"
    },
    "Action": ["sns:Publish"],
    "Resource": "arn:aws:sns:us-east-2:444455556666:MyTopic"
  }]
}
```


### Amazon Kinesis (event-driven)

* Collect, Process and analyze realtime-streaming data
* Collect streams of data from sources and pipe to sink
  * Application-logs/IOT data/Imagery data/Website clickstream
  * Database/Data lakes/Data warehouses
* Kinesis doesn't store data itself
* Versions of Kinesis
  * Amazon Kinesis Streams
    * Data streams (Amazon Kinesis Streams)
    * Video streams (Amazon Kinesis Streams)
  * Date Firehose
    * Doesn't require consumer application
    * We can configure transformer to transform data
    * Transform the data using ready-made blueprint lambda function vs custom lambda function
    * Target delivery stream should be configured
      * Streaming data into S3/Redshift/Elastic Search/Splunk
      * Spluk/DataDog/GenericHTTPEndpoint/Splunk/NewRelic
  * Kinesis analytics
    * Standard SQL queries on streaming data

#### Amazon Kinesis Streams

* Amazon Kinesis Streams is ordered sequence of data records
* Data Record = Partition Key + Sequence Number + Data Blob
  * A record (Sequence number + Partition Key + Data Blob)
  * A record is the unit of data (in Kinesis stream)
  * 1000 Record per second or 1MB per second (Per shard)
* Web service sending data can be considered as producers
* Consumer can be RedShift/S3
* Consumer application should use kinesis client library

#### Amazon Kinesis Data Analytics

* 3 steps requires 
  * Create input stream
  * Create SQL processing logic
  * Create output stream
* Data processing real-time
* Output of stream of one-query can feed to second in-application stream
* Apache flink java/scala is available for Kinesis Data Streams

## AWS Kinesis limits and price
1. 0.014$ per (1.14 cents) per 1million unit of put payload
2. 0.015$ (per shard/per hour) 1.15 cent per shards per hour
3. aws kinesis create-stream --stream-name mystream --shard-count 3 --output json
4. aws kinesis describe-stream --stream-name mystream --output json
5. Partition Key
   5. Every shard has a unique range of partition key (non overlapping with other shard)
   6. MD5 hash value of the Partition kye and based on this value decides to which shard the record will be written
   7. Size of the partition key is not included in the data-record payload limit of 1MB Single record Data Record limit
      8. But included for throughput calculation
9. Retention Period
   10. 24 hours default
   11. 8760 hours (365 days) maximum
   12. increaseDetentionPeriod() / decreaseDetentionPeriod()
13. Producer
    14. 1000 messages per second / 1MB / default retention period 24 hours (per shard) ProvisionExceededThroughputException
15. Consumer
    16. Shared Throughput consumer / Standard Consumer / Minimal cost
        17. Max 10000 records
        18. 2 MB/sec  read throughput
        18. 5 API calls per second across all consumer per shard (crossing more than 5 consumer might be diminishing return per shard)
        19. That is 10MB/sec per shard read throughput
        20. If 1 request returns 10 MB of record, it is 5 seconds worth of records
        21. Usage : 5 or less applications simultaneously consuming the same stream and latency of around 200 milliseconds, to reduce cost
    17. Enhanced Fan-out consumer
        18. Multiple consumer for same shard (each for difference application)
        19. Push based
            20. SubscribeToShard() for every 5 minutes (client has to call)
            21. Data pushed for 5 minutes for that client
            22. 2 MB per consumer per shard/5API
        23. Usage : 5 or 10 applications simultaneously consuming the same stream and latency of around 70 milliseconds
        24. Upto 20 consumer and each getting 2 MB per second
    19. HTTP/2 Data retrieval API
        20. No limit of 5 API
        21. 200-1000 millisecond vs 70 milliseconds (standard vs enhanced fan-out)

## AWS Stream processing
1. Some data looses value over time
   2. click-steam
   3. temperature
   4. tick data
   5. Anomaly detection
   6. Location data
4. Exact moment we have to take decision
5. Use case for streaming
   6. Clickstream - add and offer based on clicks
   7. Preventive Maintenance
   8. Fraud Detection
   9. Emotion Analytics
      10. Unhappy users
   10. Dynamic Pricing Engine
11. Challenges of stream processing
    12. High touch system
    13. difficult to setup due to moving parts
    14. Expensive
    15. Scaling operation
        16. Seasonal stream


#### AWS Lambda

* It is like legacy CGI
* Serverless compute service
* Allow to run application code without having to manage EC2 instances
* Pay only for compute power
  * Subsecond metering
* Lambda functions should be configured to be triggered by events
  * Events from S3 buckets
* Event sources can be triggered to lambda functions
* Trigger - operation from an even source that causes the function to invoke
* Log streams - help to identify issues and troubleshoot issues with your Lambda functions
* Blueprint template provided by AWS Lambda functions


## Reference

* [SNS Access policy](https://github.com/awsdocs/amazon-sns-developer-guide/blob/master/doc_source/sns-access-policy-use-cases.md)