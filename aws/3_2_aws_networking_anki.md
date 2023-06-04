# Managing network infrastructure and optimizing networking connectivity within an AWS environment

## Networking components

* Elastic IP Addresses (EIPs)
* Elastic Network Interfaces (ENIs)
* EC2 Enhanced Networking with the Elastic Network Adapter (ENA)
* VPC Endpoints
* AWS Global Accelerator

### Elastic IP addresses

* Private IP addresses
* Public IP addresses (EIP)
  * Pooled public IP address
  * IP address will remain with instance until it is stopped or terminated
  * can't convert an existing pooled public IP address to an EIP
* Elastic IP Addresses
  * Persistent IPv4 address that attached to aws account
  * Can attach an EIP address to an instance or an Elastic Network Interface
  * Detached EIP will still incur cost (unless returned to AWS)

### Elastic Network Interface (ENI/Eth0)

* Why we need?
  * We might need secondary interface for management network
  * We might need more than one interface for single instance
* Should be attached to a subnet  
* Logical virtual network interface card inside cloud
   * Create, configure, and attach to your EC2 instances
   * We can attach private IP address or an elastic IP address or it's MAC address.
* AWS VPC Flowlogs can be used to capture the trafic of ENI/PNI (logs)
* PNI - Primary network interface (default one attached to EC2)


## How to create enhanced networking features to reach speeds of up to 100 Gbps?

* Enhanced Elastic Network Adapter (ENA)
  * No additional cost involved
* Enhanced networking offers higher bandwidth with increased packet per second (PPS) performance
* Prerequisite
  * Not all aws instance supports ENA
  * Kernel version >= 2.6.2 or >= 3.2
* Instances with latest version of the Amazon Linux AMI will have enhanced networking enabled by default
* How to check if ENA is supported
  * ```bash
          aws ec2 describe-instances --instance-ids instance_id --query "Reservations[].Instances[].EnaSupport"
    ```

## VPC Endpoints

* Allow to privately access AWS services using the AWS internal network (without public DNS network)
* Doesn't require following gateway/instances
  * Internet Gateway, NAT Gateway, a Virtual Private Network or a Direct Connect connection.
* Interface Endpoints vs Gateway Endpoints
* Interface Endpoints
  * Interface Endpoints are essentially ENIs
  * PrivateLink
    * PrivateLink target would be Interface enpoints (that could in turn allow other AWS services accessible)
    * Allows a private and secure connection between VPCs, AWS services, and on-premises applications, via the AWS internal network.
    * Private DNS name that resolved to the private IP address of the interface endpoint and will route through the internal AWS network instead of the internet
* Gateway Endpoint
  * RouteTable would be target enpoint for Gateway
  * Amazon S3 and DynamoDB - are supported
  * Route that was selected will have automatic entry for Gateway endpoint
  * Entry will have prefix related to the vpc-endpoint-id

## Aws Global Accelarator

* Enable client TCP/UDP connection quickly communicate with AWS Global infrastructure
* Intelligently routes customer traffic (uses Edge locations)
* Configure global Accelerator
  * Requires 2 ip-address (that sits behind load-balancers)
  * Configure listeners for TCP/UDP - port + protocol-TCP/UDP
    * Can select client affinity
  * Configure end-point groups
    * Region + Traffic-Dial
    * Configure health check
  * Add endpoints (with weight info)
    * EC2 instance
    * ELB
    * EIP
  * Global Accelerator - will have output DNS names to be used globally

  
