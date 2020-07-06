## Core AWS Services

* CDSN - Compute, Storage, Database and Network

## EC2
* AMI
* Instance Types
* Instance Purchasing Options (On-demand, reserved or spot)
* Tenancy
* User Data
* Storage Options
* Security


### AMI
* Preconfigured EC2 instance templates
* Image baseline with operating system and applications
* AWS AMI vs Custom AMI vs Marketplace AMI vs Community AMI


### Instance Types
* [Instance types](https://aws.amazon.com/ec2/instance-types/)
* Attributes
  * Family - Micro-instances/General-purpose/Compute/GPU/FPGA/Memory Optimized-large-scale-in-memory/Storage Optimized
  * Type - t2/t3/(micro/nano/small/medium)
  * ECU
  * vCPU
  * Physical Processor
  * Clock speed
  * Memory GiB
  * Instance Storage (EBS Only)
  * EBS Optimized availability
  * Network performance
  * IPv6 support
  * Processor Architecture
  * AES-NI
  * AVX
  * Turbo


### Instance Purchasing Options
* OnDemand
* Scheduled (lesser than on-demand but recurring charge)
* Reserved
* Spot Instances
  * Suddently interrupted
  * Bid based, can be procured cheaper
* OnDemand capacity reservations


### Tenancy
* Shared tenancy
* Dedicated tenancy
* Dedicated Hosts

### User data
* yum update -y
* Download latest os updates


### Storage options
* Persistent storage
* Ephemeral Storage
  * EC2 instance local storage
  * Like laptop hdd
  * Unable to detach instance


## Security
* During EC2 creation security group needs to be selected
* Security group decides the protocol, egress and ingress
* Create or use key-pair
* Keypairs are used to encrypt/decrypt login information
* Possible to use the same key-pair on multiple instances
* Shared responsibility model
  * Customer responsibility for os and other security patch updates


#### Create new EC2 instance
1. AWS Management console  
1. EC2 console (under compute)
1. Launch Instance
1. Select AMI (Amazon linux 2 AMI)
1. Choose instance type
1. Configure instance details
   1. Number of instance, purchasing option, network, subnet, auto-assign public IP, placement group, capcity reservation, IAM role, shutdown behaviour (stop/terminate), Enable terminate protection, monitoring, tenancy
1. Next add storage
   1. Volume-Type (root/ebs), device (/dev/xvda), Snapshot, Size, Volume-Type (SSD/HDD), IOPS, Throughput, DoT (Delete on Termination), Encrypted
1. Add Tags (50 tags maximum)
1. Configure Security Group Rules
   1. Type (SSH,HTTP,LDAP)
   1. Protocol (TCP/UDP)
   1. Port Range
   1. Source
   1. Description
1. Launch (Create keypair)


## Get the EC2 instance meta-data
```bash
curl  -w "\n" http://ec2-54-185-39-21.us-west-2.compute.amazonaws.com/meta-data
curl http://169.254.169.254/latest/meta-data/ami-id
curl http://ec2-54-185-39-21.us-west-2.compute.amazonaws.com/meta-data
```


## Key files

* PEM - Privacy Enhanced Mail (.pem)
  * Not natively supported by putty
* PEK - PuTTY Private Key (.ppk)
  * PuTTYgen - required to register this file

### Connecting to EC2 instance
```bash
chmod 400 mohan_ec2.pem
ec2-54-185-39-21.us-west-2.compute.amazonaws.com
ssh -i "/path/to/your/mohan_ec2.pem" ec2-user@ec2-54-185-39-21.us-west-2.compute.amazonaws.com
ssh -i mohan_ec2.pem ec2-user@ec2-54-185-39-21.us-west-2.compute.amazonaws.com
ssh -i mohan_ec2.pem ec2-user@54.185.39.21
```


### Convert PEM into PPK

https://aws.amazon.com/premiumsupport/knowledge-center/convert-pem-file-into-ppk/

#### Popular EC2 users
```bash
Amazon Linux AMIs typically use:ec2-user
Debian: admin
RedHat: ec2-user
Ubuntu: ubuntu
```

#### EC2 instance normalization factor
1. nano  0.25
1. micro 0.5
1. small 1
1. medium 2
1. large 4
1. xlarge 8
1. 2xlarge 16
1. 3xlarge 24
1. 4xlarge 32
1. 6xlarge 48
1. 8xlarge 64
1. 9xlarge 72
1. 10xlarge 80
1. 12xlarge 96
1. 16xlarge 128
1. 18xlarge 144
1. 24xlarge 192
1. 32xlarge 256


### Unofficial factor
* nano = 1
* micro = 2*nano
* small = 2*micro
* medium = 2*small
* large = 2*medium
* xlarge = 2*large


### Common system status check failure
* AWS owned problem due to underlying host/power/network issue/corrup file-syste
* Better to relaunch
