## Reliability - Ability of the system to recover from Infrastructure or Service disruptions.
  * Reover from transient issues
  * Network issues, DB Issues
  * System faults and errors

## Well architected is basic requirement for reliability

### How to increase reliabilit
* Test recover procedures (Every failure can be used to test recovery procedurs)
  * Simulate system failure (Chaos monkey)
* Automatic reocvery  
  * Plan for automatic recovery
  * By monitoring a system for key performance indicators (KPIs), you can trigger automation when a threshold is breached.
  * KPIs should be a measure of business value, not of the technical aspects of the operation of the service.  
* Scale horizontally
* Stop guessing capacity
* Manage change in automation


### Reliablity measurment using availability
* Service availability over period of time
* 99% - 3 days 15 hours
* 99.9% - 8 hours 45 minutes
* 99.95% - 
* 99.999% - 5 minutes
* When the 9's are increasing, every kind of errors/failures should be handled by application (and architecture) itself


### Calculating availability with hard dependencies = 99.99% * 99.99% * 99.99% = 99.97%
### Calculating availability with redundant components = maximum availability - ((downtime of dependent 1) * (downtime of dependent2)) = 100% - (0.1% * 0.1%) = 99.9999%
### Availability Estimate = MTBF / (MTBF + MTTR)
  * Mean Time Between Failure 
  * Mean Time to Recover (MTTR)
## What does cost for availabilit?
  * Design, Redundancy
  * Cost of dependencies
## Limit management
  * Network IO
  * EBS Volume allocations
  * Number of instances in Autoscaling Group
  * Provisioned IOPS
  * RDS Storage allocation
  * Available addresses in a subnet
  * VPC

## VPC

* Classless Inter-Domain Routing (CIDR) blocks should be allocated for each VPC. Allow IP address space for more than one VPC per Region.
* Consider cross-account connections. For example, each line of business might have a unique account and VPCs. These accounts should be able to connect back to shared services.
* Within a VPC, allow space for multiple subnets that span multiple Availability Zones.
* Always leave unused CIDR block space within a VPC

## Planning network topology to ensure the resiliency of connectivity

* How are you going to be resilient to failures in your topology?
* What happens if you misconfigure something and remove connectivity?
* Will you be able to handle an unexpected increase in traffic/use of your services?
* Will you be able to absorb an attempted Denial of Service (DoS) attack?



## High availablity example - ATM network
* Devices themselves are not available all of the time, nor is the network connectivity of any single device
* Deploy a large number of them to enable a customer to easily use a different device if one is down or lacks connectivity.
* In “availability-speak”, they are redundant and fail independently.


## Conlcusion

* High availability is costly
* High availability requires redundancy
* High avialability requires
  * Automated canary deployment
  * Automated recovery testing
  * High availablity for every dependency

### Business value related questions

  * What are the most valuable transactions to your customers and to your business?
  * Can you predict when certain geographies have the greatest demand?
  * What times of day, week, month, quarter, or year are your peak times?


### Understanding Availability Needs


  * Which part of the system is critical
  * When it is critical
  * Control plane vs Data plane - which is critical


### Application Design for Availability  

* Fault Isolation Zones
  * Avoiding single points of failure,
  * Shuffle sharding - As one example, Amazon Route53 uses the concept of shuffle sharding to isolate customer requests into cells.
* Redundant components
* Micro-service architecture
  * [Microservice-trade-offs to consider when deploying a micro-service architecture.](https://martinfowler.com/articles/microservice-trade-offs.html])
* Recovery Oriented Computing
  * If we don’t frequently test failover, we might find that assumptions about the capabilities of the secondary are incorrect.
* Distributed systems best practices
  * Throttling
  * Retry with exponential fallback
  * Use of idempotency tokens
    * It is more resilient to build systems with idempotency than to build systems that assume an action must occur exactly once.
  * Constant work
  * Circuit breaker
  * Bi-modal behavior and static stability
    * Maintain enough internal state to continue operating as they were before the failure without adding additional load to the system
  * 

## Operational Considerations for Availability
## Automate Deployments to Eliminate Impact
  * Although conventional wisdom suggests that you keep humans in the loop for the most difficult operational procedures, we suggest that you automate the most difficult procedures for that very reason.
### These are deployment patterns that minimize risk
  * Canary deployment
  *  Blue-Green deployment
  * Feature toggles
  *  Failure isolation zone deployments


### Monitoring and Alarming

  * Worst failures are the ones that fails silently - Your customer knows before you do.
  * Monitoring steps
    * Generating
    * Aggregation
    * Real-time processing and alarming
    * Storage
    * Analytics

## Operational Readiness Reviews

* Conduct an Operational Readiness Review (ORR) for applications prior to initial production use, and periodically thereafter.


##[Aws reliability pillar](https://d1.awsstatic.com/whitepapers/architecture/AWS-Reliability-Pillar.pdf)
## [Calculating Total System Availability](https://www.delaat.net/rp/2013-2014/p17/report.pdf)