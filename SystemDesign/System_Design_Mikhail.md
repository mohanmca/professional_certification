## System design interview channel

* [System Design](https://www.youtube.com/channel/UC9vLsnF6QPYuH51njmIooCQ)
* [Slides](https://drive.google.com/drive/folders/1lsebCpcYBuwfY4wx2vqYd0kZ4ia0eO6v)
* [Mikhail Smarshchok](https://www.linkedin.com/in/mikhail-smarshchok-507770122/)

## Steps of System Design

1. Know the bigger problem system that needs to be designed
1. Apply analyticall knowledge and break them into sub-problems
    1. What are all the qeustions needs to be asked to break into smaller systems
1. Use existing knowledge and compose and propose a solution
1. Effectively communicate thought process that went into design

## Design Distributed Message Queue

* Producer and Consumer service should communicate to each other
  * If they are synchronous, they are easier to invoke but they are coupled
  * One service would overwhelm others
* Introduce indirection using distributed Queue and decouple producer and consumer  
* It is queue (not topic - not many subscriber), only one consumer

## How to navigate ambiguous questions

1. Asking systematic question about functional and non-fuctional requirement would help to scope the problem


## How to disect bigger system problem (ambiguous question)

1. Split requirement into Functional and Non-Functional
1. What is the highlevel-api fucntional requirement
    1. What are all API 
        1. sendMessage(Message)
        1. receiveMessage()
        1. create/delete queues using api
        1. Delete message api
        1. TTL API
        1. Security API
    1. What are all behaviour of the system
        1. Handle duplicate message and/or avoid duplicate admissions
        1. ordering guarantee
1. What are all non-functional requirements
    1. Scalable (Handl load increase, large volumes of messages and queues)
    1. High Available (survives betwork and H/w Failure)
    1. High Performance (Single digit latecny)
    1. Durable (Data is not lost)
    1. SLA (Through put)
    1. Operational Cost requirement

## How to stay on the main problem

1. Don't get deviate into too much of the side-problems (such as how DB/LB works)
1. Knowing them and using them matters


## What happens in one LoadBalancer goes down?

* We can have multiple loadbalancer using DNS-Multiple-A-Records

## What are all core components of distributed Queue

1. DNS > Multiple VIP > FrontEndService
1. FrontEndService >> Metadata | BackendService (>> two split outut)
1. FrontEndService > Metadata-Service > MetaData-Database
1. FrontEndService > BackendService > Backend-Database

## Front-End service actions

1. Authentication/Authorization
1. Request Validation
1. SSL/TLS Termination
1. Server side encryption
1. Caching
1. Rate Limiting
1. Request Dispatching
1. Request DeDuplication
1. Usage data collection

## Front-End service Validations

1. Is Message parseable
1. Is sender authenticated/validated
1. Is message size and encoding valid
1. Is Queue name valid
1. Is Message-Id recently processed (is duplicated?)
    1. Cachine sometime could be used for de-duplication
1. Do we have mandatory parameter
1. Is Data falls within the range

## Front-End Throttling

1. Is number of requests lesser than expected thrshold
1. Can we throttle them
1. Throttling protects
1. Leaky Bucket Algorithm is one of the most famous

## Queue Metadata service

1. It is almost like Caching layer
1. Gives list of queues/users/applications
1. Writes are few but reads are lot
1. Strong consistency is prefered (not mandatory)

## Three ways of Scaling Metadata service

1. Replicate same meta-data, so simple metadata frontend service can connect to any metadata storage/service retrive data
1. Partition meta-data, smart metadata frontend service should connect to particular partition storage/service retrive data
    1. Should use consistent hashing ring to partition the data
1. Partition meta-data with every partition also acts as a router
    1. Simple metadata frontend service can connect to any partition storage/service retrive data
    1. Metadata storage/service can route and retrive required partition data
    1. Should use consistent hashing ring to partition the data


## BackEnd Service Questions

1. Ask more questions (if you are stuck)
1. Can we store in database
1. how to build distributed database?
1. Where else we can store data?
    1. Memory
    1. File-System
    1. Replicated memory
1. How do we replicate the data?
1. How does FrontEnd know where to retrieve data from?

## BackEnd Service Design

1. BackEnd instance could be many
1. BackEnd instance could follow leader/follower pattern
    1. Backend leader is responsible for replicate data
    1. Backend leader is also responsible to clean the data once it is properly delivered
1. ZooKeeper Cluster Manager can be used for Cluster-Management
    1. Every queue/partition can have different leader
    1. In-cluster Manger can manage above leader election for each partition/queue
1. FrontEnd would always talk to Leader
1. FrontEnd can find the leader from MetaData service


## BackEnd Service Design (without leader election - Out-cluster-manager)

1. Every data-node is peer (no-leader)
1. Every node stores messages (of Queue) similar across multiple machine
1. Consumer randomly consumes message from any machine and marks it as completed
1. Every node that marks a queue-message as consumed is responsible to replicate the state and cleanup


## What is the difference between InClusterManager vs OutClusterManager

** TODO

## Queue Creation and Deletion

* API would be helpful (parameters can be configured)
* Queue can be autocrated and/or deleted
* Is DeleteQueue required?
    * Who can do that?
    * Could be command line utility only ADMIN can do something

## Can we delete message immediately?

1. How it works when there are duplicate message?
1. Can we retain for many days?
1. We have to manage order?
1. Messages can be marked as invisible
1. Can consumer mark it deleted till they confirm
1. Message should be replicated for durability
    1. Should we replicate synchronus or asynchronous
1. Synchronous Replication - high durability - less availability  - higher latency
1. ASynchronous Replication - less durability - lower latency
1. Message delivery semantics, why we need 3?
    1. At-Most once (May be message lost)
    1. Exactly once (very hard to acheive in distributed systems)
    1. At-least once  (Message never lost, but could be delviered more than once)
1. Why so many message delivery semantics
    1. Producer can fail, might deliver twice
    1. Consumer can fail
    1. Data replication can fail
1. Push-vs-Pull
    1. Push - Consumer has less work, no-need to continuously poll but get notified
    1. Pull is easier to implement than push (for distibuted queue)
        1. Pull has more work for consumer
1. FIFO (is for queue)
    1. Distributed system FIFO queue
    1. Delivery strict order is not guaranteed (as FIFO) in distributed system
1. Security    
    1. For transport use SSL/HTTPS for secure transfer
    1. Store the message after encrypted
1. Monitoring
    1. Has to monitor every subcomponent of entire distributed system
    1. Has to monitor visibility of customer experience
    1. Customer of queue system should have ability to trace the state of their queues
    1. Each service has to emit metrics and logs data
    1. 