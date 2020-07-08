## AWS WAF - Web Application Firewall

* The AWS Web Application Firewall is a service to prevent web sites and web applications from being maliciously attacked by common web attack patterns such as SQL injection and cross-site scripting.
* Used to identify how Amazon CloudFront distributions and application load balancers respond to web requests based upon specific conditions.
* The pricing is based on how many rules you deploy and how many web requests your application receives.
* All CloudFront traffic would be routed to WAF, if all the rules are matched, it would trigger (block/allow)


## WAF - Features

* Filter web traffic based on conditions that include IP addresses, HTTP headers and body, or custom URIs.
* Create a centralized set of rules that you can deploy across multiple websites.
* Allow all requests except the ones that you specify
* Block all requests except the ones that you specify
* Count the requests that match the properties that you specify (for analysis)

## WAF - Components

* Web ACLs
* Rules
  * Rule statements
    * Geographic match/IP set match/Regex pattern/Size constraint/SQLi attack/String match/XSS scripting
  * Rule-name
  * Rule-Action (ALlow/Block/Count)
* Rule Groups


## WAF - How to

* AWS MC > Security, Identity & Compliance > WAF & Shield > AWS WAF
* Configure Web ACL > Conditions & Rules contains Conditions & Web ACL Contains Rules
* Inputs
  * Web ACL name, Cloud Watch Name & Region
  * Create condition (geo-match condition/IP match condition)


## Monitoring WAF

* Monitor Web requests and web ACLs and rules using Amazon CloudWatch
* Raw data from AWS WAF and AWS Shield Advanced into readable, near real-time metrics.
* Statistics are available only for two weeks, they are for every-minute.
* Amazon CloudWatch alarm can send an Amazon SNS message when the alarm changes state.
  * Action could be a notification sent to an Amazon SNS topic or Auto Scaling policy.
  * WAF Metrics on CloudWatch
  * AllowedRequests, BlockedRequests, CountedRequests and PassedRequests

## General prinple of monitoring or Monitoring checklist

* What is the purpose of monitoring?
* How often do you intend to monitor?
* What elements do you want to monitor?
* Are you looking to maintain reliablity and operational performance?
* Are you looking to understand trends to implement additional controls to your infrastructure?


## AWS WAF Limitation and Quotas

* AWS WAF is subject to the quotas
* The maximum requests per second (RPS) allowed for AWS WAF on CloudFront is set by CloudFront and described in the CloudFront Developer Guide.
* AWS WAF can't work without CloudFront, whereas CloudFront can work without WAF
* WAF pricing (as of July-2020)
  * Web ACL - $5.00 per month (prorated hourly)
  * Rule - $1.00 per month (prorated hourly)
  * Request -	$0.60 per 1 million requests
* It would take 15 minutes to probagate all the WEB-ACL  to all the regions

## AWS Firewall manager

* AWS Firewall Manager is a security management service which allows you to centrally configure and manage firewall rules across your accounts and applications in AWS Organization.
* AWS FM = AWS WAF on entire AWS Organization
* Prerequisite = AWS Config enabled + AWS Account to act as Firewall Manager Admin + All accounts should be part of AWS Organization (not just consolidated billing)

## AWS WAF Rule evaluation order

* When creating a Web ACL for AWS Web Application Firewall (WAF), it is critical to remember that WAF rules are evaluated in a specific order. What general order of WAF rules within a Web ACL is recommended?
* Whitelist rules first, then Blacklist, then Bad Signature

## AWS Firewall manager  - Components

* WAF Rules
* Rule Groups
  * One or more WAF rules that have the same action applied
  * We can purchase pre-existing rule groups via the AWS marketplace
  * OWASP preconfigured rules are available in Marketplace
  * Maximum of 10 rules are applicable
* Firewall manager

## AWS Firewall manager policy

* Policy is rule group tha AWS Orgnization wants to applies to AWS Resources
* Policy can have 2 rule-groups per policy (1 from customer) + 1 from market-place (this limit can't be increased)
* Policy costs 100 per-region/per-month

## AWS Firewal manager policy - how-to

* AWS Management console > "Waf & Shield service" > AWS FMS > "Security Policies"
* Create policy > Create Rule Group (Similar to WAF)
* Choose between "Create AWS FM policy and add new rule group or Create AWS FM policy and add existing rule group"
* Policy name & Region > Select rule group (action set by rule-group)


## AWS Shield

* Protects infrastructure from DDoS
* DDoS prevents legimate requests getting through
  * SYN Flood
  * DNS Flood
  * HTTP Flood/Cache-busting
* AWS Shield standard vs AWS Shield advanced
* AWS Shield standard
  * Free for everyone
  * DDoS layer-3 (Network) and layer-4 (Transport)  production (APSTNDP)
* AWS Shield Advanced protection
 * Protection against EC2, CloudFront, ELB and Route53
 * 24x7 team for DDoS attack
 * Layer-7 protection
 * Real time metrics for AWS DDoS attacks


## To add protection for an AWS resource using AWS Shield

* Gototo AWS Shield > Protected resources.
* Choose Add protected resources. (using ARN)
  * Choose or enter the resource types and resources to protect. For Classic Load Balancer and Application Load Balancer resources, you also must choose a Region.
  * You can choose from the provided list or enter the Amazon Resource Name (ARN) of specific resources to protect. You can choose or enter any combination of resource types and resources.
* Shield Advanced lists a maximum of 100 resources at one time. If you have more than 100 resources, choose Next to see the next set.
* If you want to protect an Amazon EC2 instance, you must first associate an Elastic IP address with the instance, and then choose the Elastic IP address as the resource to protect.


## Reference

* [WAF](https://aws.amazon.com/waf/)
* [WAF Quotas](https://docs.aws.amazon.com/waf/latest/developerguide/limits.html)