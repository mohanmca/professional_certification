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
   1. AWS AMIs are free
   1. We can choose our existing running instance and burn custom AMI
   1. Marketplace also has paid AMI
   1. Community AMI
2. InstanceType
   1. ![Attributes of InstanceType](img/compute/compute_instance_type.png)
   1. [Family types](https://aws.amazon.com/ec2/instance-types/)
3. Instance Purchasing Option
   1. On-Demand
      1. Per second billing - costlier
   1. Reserved Instances
      1. All upfront (High discount)
      1. Partial upfront
      1. No Upfront (low discount)
   1. Scheduled Instances
      1. Charged for schedule even if not used
   1. Spot Instances
      1. Bid for unused EC2
   3. OnDemand capacity reservations
4. Tenancy
   1. Shared Tenancy - H/w shared by multiple customers
   1. Dedicated Instances - H/2 not shared by other customers
   1. Dedicated Hosts
      1. Additional visibility and control on the physical host
      1. License visibility
      1. VM flexibility
      1. Compliance could be driving reasons
5. User Data
   1. yum update -y
   1. commands that will run during the *first boot cycle*
6. Storage Options
   7. Persistent storage (EBS volumes)
      1. EBS connected via AWS network
      1. Can disconnect and maintain the data
      1. Supports encryption, take backup snapshots of all data
   8. Ephemeral Storage (local storage)
      1. Physically attached underlying host
      1. All the saved data on disk is lost when instance is terminated.
      1. If you reboot your data will remain intact
7. Security
   1. SecurityGroup (firewall rules)
      1. Security group name and rule
         1. Type (SSH/SMTP)
         1. Protocol
         1. Port Range
         1. Source (0.0.0.0/0) or CIDR or IP or SecurityGroup
   2. KeyPair
      1. Public Key
         1. Kept by AWS
      1. Private Key
         1. Responsibility of the user
      1. Same keypair can be used for multiple instances
      1We can't create after instance is created (hence we have to download while generating as part of last step)


## Launching Instance

1. Select AMI
2. InstanceType
3. SecurityGroup
   1. We can select SecurityGroup for a given template
4. VPC (Select)
5. Subnet (Select)
   1. We can select multiple subject for a given template
6. Shutdown Behavior
   1. Stop
   1. Terminate
9. Configure for optional CloudWatch

## AWS EC2 Autoscalling
1. scale new instances based on
   1. Response time
   1. CPU > 70%
   1. Memory usage
1. We can also scale-back/scale up
   1. scale-in (downsize)
1. Advantages
   1. Automation
   1. Customer satisfaction
   1. Cost reduction
1. [**Launch configuration vs Launch Template**](https://docs.aws.amazon.com/autoscaling/ec2/userguide/create-launch-template.html)
   1. Iam Profile vs IAM Profile Instance
   
1. ![Launch-Template vs Launch-Configuration](img/compute/Auto-Scaling-Launch-Template-vs-Launch-Configuration.jpg)
   1. ![Launch-Template Instance](img/compute/create_launch_template_1.png)
   1. ![Launch-Template Storage](img/compute/create_launch_template_2.png)
   1. ![Launch-Template Advanced](img/compute/create_launch_template_3.png)

## AWS Launch Configuration
1. ![Launch-Configuration](img/compute/Launch-Configuration.png)


## Autoscaling Readings
1. [Using Elastic Load Balancers and EC2 Auto Scaling to Support AWS Workloads](https://cloudacademy.com/blog/elastic-load-balancers-ec2-auto-scaling-to-support-aws-workloads/)
1. [Three Ways to Cut Your EC2 Costs with Hands-on Labs](https://cloudacademy.com/blog/three-ways-to-cut-your-ec2-costs/)
1. [Application Load Balancer vs. Classic Load Balancer](https://cloudacademy.com/blog/application-load-balancer-vs-classic-load-balancer/)
1. [Your First Day on Amazon Web Services: 10 AWS Pitfalls and How to Avoid Them](https://cloudacademy.com/blog/your-first-day-on-aws-10-pitfalls-and-how-to-avoid-them/)
1. [Elastic File System: What You Need to Know](https://cloudacademy.com/blog/elastic-file-system-what-you-need-to-know-about-amazons-new-service/)


## Autoscaling steps

1. From Launch Configuration or Launch Template
2. Can override puchase-option from launch template
3. ![create_auto_scaling_group](img/compute/create_auto_scaling_group.png)
4. ![Add Alarm](img/compute/alarm_to_add_node.png)
5. ![alarm_to_remove_node](img/compute/alarm_to_remove_node.png)
6. ![create_auto_scaling_group](img/compute/create_auto_scaling_increase_decrease.png)
7. ![auto_scaling_summary](img/compute/auto_scaling_summary.png)

## Autoscaling policy types

* Manual Scaling
  * Creating scale ahead of any marketing campaign
  * Ideal for planned events
* Dynamic Scaling
  * Step scaling
    * Could be based on overall CPU usage
    * Cooldown policy - adding new node would take few minutes... policy should consider that
    * Trigger points using CloudWatch Alarm
      * There could be multiple levels of alarm (60% 75% and 95% alarms)
      * ![step_scaling_multiple_level_alarms](img/compute/step_scaling_multiple_level_alarms.png)
    * Scale up quickly (it takes time to spin-up machines)
    * Scale down slowly (it is nearly instantaneous to tear down)
  * Target Tracking
    * We don't need to setup alarms (it is built-in, automatically created, don't delete it)
    * Auto-alarms are removed when target scaling policy is created
* Predictive Scaling
  * CloudWatch metrics (from archived data) used for ML predictive scaling
  * Can run only forecast mode (without scaling)
  * For start of every hour
* Scheduled Scaling
  * Batch process (when spot instances are lower)
  * Time based trigger (preferably during less workload times)
* Yaml/json
  * stored with .config extension
  * .ebextensions folder of the source code
* ![elastic_bean_stalk](img/compute/elastic_bean_stalk.png)


## AWS Beanstalk

1. [Amazon EC2 Container Service and Elastic Beanstalk: Docker on AWS](https://cloudacademy.com/blog/amazon-ec2-container-service-docker-aws/)
2. [Deployment Orchestration with AWS Elastic Beanstalk](https://cloudacademy.com/blog/deployment-orchestration-with-aws-elastic-beanstalk/)
3. [How to Deploy Docker Containers on AWS Elastic Beanstalk Applications](https://cloudacademy.com/blog/how-to-deploy-docker-containers-on-aws-elastic-beanstalk/)

## AWS Beanstalk notes

* [Step by step notes](https://docs.aws.amazon.com/elasticbeanstalk/latest/dg/GettingStarted.html)
* Cloud web application without explicitly using cloud API
  * Whether you’ve written your app in Python, Ruby, Java, Node.js, Go, or PHP
  * Elastic Beanstalk only requires you to set the appropriate platform (including an operating system and platform version) from a pull-down menu
  * Upload your application, and run it. 
  * AWS will take care of all the details 
    * Compute, networking, autoscaling, load balancing, and monitoring – invisibly.
* Bealstalk core components
  * Applications
  * Application Version
  * Configuration Template
  * Environment Configuration
  * Environment Template
  * Environment
  * Platform


## ElasticBeanStalk Environment Tiers (Two tiers)

* HTTP Requests - WebServer Environment
  * Webserver Tier
    * Route53
      * Uses CName record
      * Used by ELB
    * ELB
    * AutoScaling
    * EC2 instance
    * Security Groups
    * Host Manager
* SQS Queue - Worker Environment
  * Auto Scaling
  * IAM Service Role
  * EC2 instances
  * Daemon (similar to Host manager)

## AWS Beanstalk - Deployment Options

* All at once
  * Default choice
* Rolling
  * Minimize the amount of disruption that is caused
  * Two different version at the same time
  * Gradually rolled
* Rolling with additional batch
  * Similar to rolling
  * Additional new batch of instances are started
  * Application availability is maintained
* Immutable
  * Create entirely new instanced
  * Double in size (in-between)

## AWS Beanstalk - Steps
* ![elastic_bean_stalk](img/compute/beanstalk/elastic_beanstalk_step1.png)
* ![elastic_bean_stalk](img/compute/beanstalk/elastic_beanstalk_step2.png)
* ![elastic_bean_stalk](img/compute/beanstalk/elastic_beanstalk_step3.png)
* ![elastic_bean_stalk](img/compute/beanstalk/elastic_beanstalk_step4.png)
* ![elastic_bean_stalk](img/compute/beanstalk/elastic_beanstalk_step5.png)
* ![elastic_bean_stalk](img/compute/beanstalk/elastic_beanstalk_step6.png)
* ![elastic_bean_stalk](img/compute/beanstalk/beanstalk_webtier_summary.png)
* ![elastic_bean_stalk](img/compute/beanstalk/beanstalk_sqs_summary.png)
  
## AWS Beanstalk - Monitoring

* Two different level
  * Basic Health Reporting
    * 5 Minute interval
    * 4 color update
    * 10 sec autoscaling for ELB interval
  * Two status check
    * System status check (h/w check)
    * Instance status check (health monitoring urls)
      * Failure reasons
        * Incorrect network configurations
        * Corrupt file systems
        * Exhausted memory
        * Incompatible kernel
  * Advanced Health Reporting
    * 7 color update
      * degraded/pending/severe/suspended/warning/ok
    * Health agent is installed and monitors
      * CloudWatch collect collects the data
    * CloudWatch is integrated with additional cost
* ![elastic_bean_stalk](img/compute/beanstalk/basic_health_monitoring_1.png)
* ![elastic_bean_stalk](img/compute/beanstalk/basic_health_monitoring_2.png)
* ![elastic_bean_stalk](img/compute/beanstalk/basic_health_monitoring_3.png)
* ![elastic_bean_stalk](img/compute/beanstalk/basic_health_monitoring_4.png)

## AWS Lambda
* What to focus with cloud (servers)
  * Installation
  * Patching
  * Scaling
  * Storage
  * Coding
  * Deployment
* Lambda
  * Code (functions)
  * Permissions
  * Env Variables
  * CPU/Memory
* Code
  * Zip upload to S3
  * Runtimes
    * java/javascript/go/node.js/python/ruby/custom-runtime-api
* Invocation Input
  * via console
  * sdk
  * toolkit
  * cli
  * function URL
  * triggers
    * S3/SQS/Time
* Events
  * S3 Event
    * putObject
  * SQS Event
* Output
  * S3
  * SQS
  * Dynamodb
  * SnS/SQS
* Monitoring
  * CloudWatch
  * log streams
* Cost
  * Amount of request that send to your function
  * duration (1 ms rounded)
  * Amount of compute power provisioned

## Steps required for AWS lambda function
* ![aws_lambda_function](img/compute/lambda/aws_lambda_function.png)
```python
import json
import urllib.parse
import boto3
print('Loading function')

s3 = boto3.client('s3')

def lambda_handler(event, context):
    #print("Received event: " + json.dumps(event, indent=2))

    # Get the object from the event and show its content type
    bucket = event['Records'][0]['s3']['bucket']['name']
    key = urllib.parse.unquote_plus(event['Records'][0]['s3']['object']['key'], encoding='utf-8')
    try:
        response = s3.get_object(Bucket=bucket, Key=key)
        print("CONTENT TYPE: " + response['ContentType'])
        return response['ContentType']
    except Exception as e:
        print(e)
        print('Error getting object {} from bucket {}. Make sure they exist and your bucket is in the same region as this function.'.format(key, bucket))
        raise e              
```
* Boto3 - python AWS-SDK
1. Create function
   1. Author from scratch
   1. Use a blueprint
   1. Container image
   1. Browse serverless app respository
1. AWS Lambda deployment
  1. Container-images
  1. zip-file archive
1. Create function wizard has 5 components
1. ![aws_lambda_create_function](img/compute/lambda/aws_lambda_create_function.png)
1. Add a trigger
1. ![aws_lambda_add_trigger](img/compute/lambda/aws_lambda_add_trigger.png)
2. S3-Warning and guidelines
   1. S3 bucket should be added for LambdaRole
   1. Don't use same bucket as input and output, causes recursive invocations
   1. Without role enabled in bucket, lambda will not be created
2. Select lambda > Monitor (for metrics and invocation counts) > CloudWatch for logs

## Lamdba configurations

* Runtime settings
  * Runtime
    * Python
  * Architecture
    * x86
    * ARM
  * Handler info
    * file_name + function_name
    * lamdba_function.lambda_handler (default)
* Basic Settings
  * 128MB (10240MB is maximum)
  * Ephemeral storage - 512
  * 0min-3sec
  * Execution role
* Permissions
  * By-Action
  * Resource-based policy
    * Add permissions
* Environment variables
  * Key-value pairs
* Tags
* VPC
* Monitoring and operation tools
* Concurrency
  * Unreserved account concurrency - 50
  * Provisions concurrency
    * Cold start (pre initialize)

## Invocation of Lambda Function

* Synchronous (Push-based) Invocation
  * if fails, no-retry
* ```aws lambda invoke --function-name lambda.function --cli-binary-format raw-in-base-out --payload '{"key"" "value"}'``` response.json
* Asynchronous (Event-based) Invocation (uses built-in queue)
  * S3
  * SQS
  * Automatic retries
    * Failed event sends to dead-letter queue
    * Or Lambda destination (has request and response context)
* Stream (Poll-based) invocation
  * Lambda service runs poller behalf of function
  * Requires Event source mapping
  * Famous
    * Dynamodb streams
    * Kinesis Streams
    * SQS
  * ```
    aws lambda create-event-source-mapping \
    --function-name my-function \
    --maximum-batching-window-in-seconds 5 \
    --batch-size 5 \
    --event-source-arn arn:aws:sqs:us-west-2:123456789012:mySQSqueue
    --event-source-arn arn:aws:dynamodb:us-west-2:45456789012:table/my-table/stream/2021-0610T19:26:16:525
    ```

## AWS ECS and Docker
* [The Hitchhiker's Guide to AWS ECS and Docker](https://start.jcolemorrison.com/the-hitchhikers-guide-to-aws-ecs-and-docker/)


## Lambda monitoring
1. ![aws_lambda_monitoring_metrics](img/compute/lambda/aws_lambda_monitoring_metrics.png)
2. CloudWatch logstream

## AWS SAM model
* Open source framework
* Any language that supports lambda
* YAML template
* During deployment sam will transform these template into CloudFormation
* SAM CLI provides environment
  * sam build
    * yaml to cloudformation template
  * sam package
    * upload to S3
  * sam deploy

* [AWS Lambda: an Introduction and Practical Walkthrough](https://cloudacademy.com/blog/aws-lambda-introduction/)
* [AWS Lambda Adds Amazon Simple Queue Service to Supported Event Sources](https://aws.amazon.com/blogs/aws/aws-lambda-adds-amazon-simple-queue-service-to-supported-event-sources/)

