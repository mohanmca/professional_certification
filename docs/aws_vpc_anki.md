# Virtual Private Clouds

##  Components of VPC
1. Subnets
1. Route Tables
1. NAC - Network Access List
1. Nat instances and Nat Gateways
1. Bastion Hosts
1. VPN and Direct Connect
1. VPC Peering
1. VPC Transit Gateway
1. IGW - Internet gateways and Load Balancers


## VPC
1. Each account is allowed to create 5 VPC per region
1. VPC = Name + CIDR block (IP-Address range)
1. [VPC Quotas](https://docs.aws.amazon.com/vpc/latest/userguide/amazon-vpc-limits.html)

## NACL

* NACL works at network level (not instance level)
* NACL (pronounced like nuckle) are stateless
  *  Any response traffic generated from a request will have to be explicitly allowed and configured in either the inbound or the outbound ruleset, depending on where the response is coming from. 
  * In SecurityGroup, if we allow request traffic, response traffic is enabled (can't accept one-way)
* by default, NACL will allow all traffic, both inbound and outbound, so it's not very secure
* Inbound Rules  + Outbound Rules
  * Rule-#, Type,	Protocol,	Port range,	Source,	Allow/Deny
  * PPTRASI (Self made abbreviation - Port,Protocol+Type+Allow-Deny,Source and ID)
  * Rule-ID with "*" is considered fall back rule
* A NACL can be applied to a number of subnets (like route tables)
* A single NACL can be associated to one subnet
* NACL evaluate the rules starting with the lowest numbered rule, to determine whether traffic is allowed in or out of any subnet associated with the network ACL. 
   * The highest number that you can use for a rule is 32766. 
   * It is recommended to creating rules in increments (for example, increments of 10 or 100) so that you can insert new rules where you need to later on.
* [NACL document](https://docs.aws.amazon.com/vpc/latest/userguide/vpc-network-acls.html)

## Security Group

* All the rules are evaluated before decisions are made (unlike NACL)
* Works at instance level (not at network level)
* We can specify allow rules, but not deny rules.
* Security-Group = Type/Protocol/Port-Range/Source (MySQL|HTTP/TCP/3306/10.0.1.0`/`24)  
* By default, a security group includes an outbound rule that allows all outbound traffic. You can remove the rule and add outbound rules that allow specific outbound traffic only. If your security group has no outbound rules, no outbound traffic originating from your instance is allowed. 

### Subnets and CIDR block

* We cab create public-subnet and private subnet
* Each subnet is ip-address block using CIDR
* Every subnet has a route-table
* For VPC, CIDR can rnage from */16 to */28
   * Example: 10.0.0.0/16
   * Example: 10.0.1.0/24 - 10.0.2.0/24
* Isolated VPC can access internet by connecting with IGW
* VPN requires associated route table to access internet
* We can't attache more than one route table to a subnet
   * Same route table can be attached to multiple subnets
* Local Route - Undeletable default route helps to subnets talk ti other subnets within VPC
   * Eample Local Route - Destination: 10.0.0.16/16 -> Target: Local (Example default route)
* Route table entry in IGW 
  * 0.0.0.0/0 -> igw-212823ab5g (Any host within VPC can contact IGW)

### Subnets, Region and Resillency

* For every subnet in AZ, it is better to have replica one in another AZ
* Web/App/DB Layer - Each can be in different sub-net


## CIDR-Limitation on VPC
* First four IP address and last ip-address within subnet can't be used
* 10.0.1.0/24 - is a subnet
  * 10.0.1.0   -> Network IP address any subnet (Application can't use)
  * 10.0.1.1   -> AWS Routing reserved (Application can't use)
  * 10.0.1.2   -> AWS DNS reserved (Application can't use)
  * 10.0.1.2   -> AWS DNS reserved  (Application can't use)
  * 10.0.1.3 -to-  10.0.1.254  -> No restrictions
  * 10.0.1.255 -> Broadcast IP address on any subnet (Application can't use)
  * 251 IP addresses out of 256 is accessible for our VPC


## 0.0.0.0/0 -> Every IP that is not explicitly configured in route table.
## Largest possible VPC network
* Deploying the largest VPC possible results in over 65,000 IP addresses.
### 10.x.x.x address space
* We can use over 16,000,000 IP addresses


## VPC - Nat Gateway

* Why NAT Gateway
  * If private subnet requires OS/security update, how to download patch from internet
  * Nat gateway accepts private-subnet initiated connection
  * Nat gateway rejects internet initiated connection into private subnet
* Nat gateway should sit in public gateway (not in private)
* Private subnet should have an entry 0.0.0.0/0 ---> Nat Gateway to gain access to internet
   * 0.0.0.0/0 -> nat.0fabcdef678ghk
* Nat-gateway should be configured for resillent fallback VPC as well

### Bastion Hosts
* Why we need Bastion hosts
  * EC2 instance in private subnet might need to be accessed from internet (internet initiated)
  * Remote engineer need to work
* Bastion Host
  * It acts as Jump server
  * Should be in public subnet
  * Should be very secure/hardened/Robust
  * Hackers attack them to gain access into private subnet
* Security group configuration
  * We need two security group
  * Security group to accepts connection to bastion hosts (Example: allow SSH incoming)
  * Security group in private subnet to accepts connection from bastion hosts (Example: allow SSH incoming connection from bastion host)
  * Bastion hosts receives connection via IGW
* Checklist
  * Don't store EC2 or other instances private key in bastion hosts
  * SSH Agent forwarding should setup on trust client machine who wants to gain access  

### VPN & Direct connect to VPC

* How to access VPC private subnet from Data Center using VPN
* VPN - 192.168.0.0/16 - Address space
* VPC - 10.0.0.0/16 - Address space
* VGW - Virtual Gateway should be attached to VPC
   * VGW - should configure about CGW details such as ip-address of customer gw
   * What type of routing table to be used @ CGW dynmic/static should be configured in VGW
* CGW - Customer gateway should be attached to DataCenter (Hw/SW)
* VPN Tunnel should be created between CGW & VGW
  * CGW alone can initiated connection to VGW (not the other way)
* By defaul VPN tunnel will drop connection if it is idle for 10 seconds or more, so monitoring should be in place using customer ping
* Routing table associated with private subnet should have entry
  * 192.168.0.0/16 -> vpw-02abcndGis3ds72k2 (to route data to DataCenter from VPC)
* Route Propagation supported for customer gateway that also support BGP
  * Static route table within VPC is not required if VPN has CGW that supports BGP (border gateway protocol)
  * There could be multiple CGW with VPC
* Security group should allow only certain protocol from CGW to Private subnet

### Direct Connect

* Direct Connect is private connection and also get speeds from 1 through to 10 gigabits per second.
* AWS gas regional telecom Vendor support to add router for partner (client) and AWS router.
* All client data traffic would be routed via private connection within Vendor infrastructure
* Both AWS Public and Private VPN network is acceissble using Direct Connect
* Direct Connect =  Customer data center  + Direct Connect location ( Customer Router + AWS router) +  AWS infrastructure (specific region)


### VPC peering

* A VPC peering connection is a networking connection between two VPCs that enables you to route traffic between them privately
* Instances in any aws VPC can communicate with each other as if they are within the same network.
* The owner of the requester VPC sends a request to the owner of the accepter VPC to create the VPC peering connection. The accepter VPC can be owned by you, or another AWS account
* VPC peering doesn't work transitively (it is 1-1)
  * (If VPC-A talks to VPC-B, and VPC-B talks to VPC-C, VPC-A can't talk to VPC-C)
* If two VPC has CIDR overlap, they can't estabilsh VPC-Peering
* Routing table betwen two VPC (10.0.0.0/16 and 172.31.0.0/16)
  * Routing table at VPC1
    * 10.0.0.0/16 -> local
    * 172.31.0.0/16 -> pcx.0fabcdef678ghk
  * Routing table at VPC2
    * 172.31.0.0/16 -> local
    * 10.0.0.0/16 -> pcx.9823fig122xA83
* Security group should allow for other VPC

### AWS Transit Gateway
* A transit gateway is a network transit hub that can be used to interconnect VPC and on-premises networks.
* It is alternative to VPN Peering
* It reduces number of connection (using hub and spoke model)
* All routings are centrally managed
* Transit Gateway goes through this central hub, it allows you to centralize all your monitoring as well for your network traffic and connectivity all through the one dashboard
* Core concepts
  * Attachment  - Attach new VPC to VPN

  
