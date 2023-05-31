## [AWS Compute](https://aws.amazon.com/products/compute/)

* EC2 (compute cloud)
 * [https://aws.amazon.com/products/compute/](https://aws.amazon.com/products/compute/)
* Beanstalk
* Lambda
* Serverless application model (SAM)
* [Containers](https://aws.amazon.com/containers/)


## AWS Products
* https://aws.amazon.com/products/compute
* https://aws.amazon.com/products/storage
* https://aws.amazon.com/serverless
* https://aws.amazon.com/products/databases
* https://aws.amazon.com/products/developer-tools

## What are all the EC2 components

1. AMI
   2. AWS AMIs are free
   3. We can choose our existing running instance and burn custom AMI
   3. Marketplace also has paid AMI
   4. Community AMI
2. InstanceType
   3. ![Attributes of InstanceType](./img/compute_instance_type.png)
   4. [Family types](https://aws.amazon.com/ec2/instance-types/)
3. Instance Purchasing Option
   1. On-Demand
      2. Per second billing - costlier
   1. Reserved Instances
      2. All upfront (High discount)
      3. Partial upfront
      4. No Upfront (low discount)
   1. Scheduled Instances
      2. Charged for schedule even if not used
   1. Spot Instances
      2. Bid for unused EC2
   3. OnDemand capacity reservations
4. Tenancy
   5. Shared Tenancy - H/w shared by multiple customers
   6. Dedicated Instances - H/2 not shared by other customers
   7. Dedicated Hosts
      8. Additional visibility and control on the physical host
      9. License visibility
      10. VM flexibility
      11. Compliance could be driving reasons
5. User Data
   6. yum update -y
   7. commands that will run during the *first boot cycle*
6. Storage Options
   7. Persistent storage (EBS volumes)
      8. EBS connected via AWS network
      9. Can disconnect and maintain the data
      10. Supports encryption, take backup snapshots of all data
   8. Ephemeral Storage (local storage)
      9. Physically attached underlying host
      10. All the saved data on disk is lost when instance is terminated.
      11. If you reboot your data will remain intact
7. Security
   8. SecurityGroup (firewall rules)
      9. Security group name and rule
         10. Type (SSH/SMTP)
         11. Protocol
         12. Port Range
         13. Source (0.0.0.0/0) or CIDR or IP or SecurityGroup