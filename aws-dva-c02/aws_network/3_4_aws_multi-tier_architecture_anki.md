## Multi-tier (tier = layer)

* Presentation + Logic  + Data -> 3 tier architecture
* Every tier can be highly available in multiple AZ
* Each tier should be in different subnet to make it secure and scalable
* In AWS, we can increase the resources in anyone tier without scaling other tiers

## Private Subnet vs Public Subnet

* A subnet is public if it has an internet gateway, and a route to that internet gateway
* A subnet is private if it doesn't have route to internet gateway
* Public subnet has a default route table enable us to allow inbound and outbound traffic
* Private subnet need a route table to direct traffic flow within VPC
* Always make sure your subnet has spare, so it could be scaled for future use
* Always makes sure we leave spare capacity for additional subnets.


## How to scale multi-tier

* Use network load balancer to front the presentation tier
* Use application load balancer between web-tier and application-tier

## Scaling and securing web tier

* AWS WAF - Aws web application firewall solution can be integrated with CloudFront
* AWS Shield - AWS Shield could be used to protect from DDoS attacks
* AWS VPC Flow logs - can be used to audit/monitor VPC traffic

### Autoscaling default termination policy

* Autoscaling selects the availability zone with two instances, and terminates the instance launched from the oldest launch configuration. 
* If the instances were launched from the same launch configuration, then autoscaling selects the instance that is closest to the next billing hour and terminates that. 
* This helps you maximize the use of your EC2 instances, while minimizing the number of hours you are billed for Amazon EC2 usage.


### Serverless architecture patterns

* Aws Lambda + AWS API Gateway
* What is avoided?
  * No need to provision EC2 instances
  * No need for autoscaling groups, no autoscaling rules
  * No need to install code interpreters
  * No patching OS'es
  * No need to define AZ
* What may be required?
  * Some definition of VPC might require
  * Some subnet configuration may be required (based on design)  
* We can configure ENI for database kinds of resources
  * So they are accessible only lambda functions (not internet accessible)
  * Lambda function can access them only via private subnet or private ENI
* [AWS Serverless multi-tier architecture](https://d0.awsstatic.com/whitepapers/AWS_Serverless_Multi-Tier_Architectures.pdf)  
* Logic layer can use AWS API Gateway
* We can improve API performance via caching and content delivery which immediately means that we don't need to create, manage and pay for Elastic load balancers between our tiers.
* One lambda for one api (/tickets, /weddings, /info)
* Each lambda could have IAM role
* Amazon ElasticCache can be configured with dynamo-db table, if cache miss, it could retrieve from DynamoDb table


### MicroService architecture patterns

* MicroService can realize significant benefits when used with serverless resources.
* Each of the application components are completely decoupled and independently deployed and operated.
* API Gateway manages their API, and the functions subsequently are executed by AWS Lambda (those two are enough to build microservice architecture)


### MicroService architecture patterns benefits

* The serverless microservice pattern lowers that bar to creation of each subsequent microservice.
* Optimizing server utilization is not so much of an issue with this pattern.
* An API gateway provides programmatic-generated client SDKs, which can make it even easier to integrate with other services and other runtimes.


### Reference

* [AWS Serverless multi-tier architecture](https://d0.awsstatic.com/whitepapers/AWS_Serverless_Multi-Tier_Architectures.pdf)
