## AWS CloudWatch

* Used for
  * Tracking metrics
  * Trending
  * Setting Alarms
  * Consolidated Resource Logging
* Comprehensive monitoring tool, could react to events
* Create Trend reports
* Example: Monitor EC2, and setup alarm, Concern about recurrence Billing

## AWS CloudWatch - Overview

* Geneos + ELK
* Region specific billing
* Free 5 minutes refreshed monitoring with 3 dashboard is free
* Enhanced monitoring refresh rate is 1 minutes
* AWS CloudWatch - Logging
  * Similar to ELK, but not applicable for complex applications
  * There is no logstash equivalent in CloudWatch, you need lambda function
  * Search features are not as efficient/feature-rich like kibana
  * Kibana dashboard has more options and well suited for logs from multiple-sources
  * We can export logs from CloudWatch Log and analyze using Splunk/ELK/3rd party

## AWS CloudWatch - monitoring theory

* Why bother monitoring?
* Customer experience: Are my customers getting good experience?
* Performance and costs: How are my charges impacting overall performance
* Trends: Do I need to scale my environment?
* Troubleshooting and radiation: Where did the problem occur?
* Learning and improvement: Can we detect and prevent this problem in the future?

## AWS CloudWatch - How to?

* Launch EC2
* Select EC2 > Monitoring


## AWS CloudWatch - What could be monitored?

* CPU (Utilization/Credit-Usage/Credit-Balance)
* Network (Byte-In/Byte-Out/Packets-In/Packets-Out/Status-Check)
* Disk (Read/Write/inBytes/Count of operations)
* What is missing in basic?
  * How much memory is left?
  * Disk space level monitoring?

## AWS CloudWatch - Build Dashboard

* AWS CloudWatch > Dashboard > CreateDashboard > "DashboardName"
* Add Configuration (Line/Stacked-Area/Number/Text) to Dashboard
* Choose EBS or EC2 Metrics > Per-Instance Metrics > "Cpu Utilization" > Create Widget > Save Dashboard


## AWS CloudWatch - Monitor EC2?

* EC2 requires additional role with CloudWatchFullAccess (policy) for EC2, so that it could be monitored by CloudWatch with more the details.
  * We can create upfront - EC2WithCloudWatchFullAccess (role)
* Configure monitoring script available in AWS documentation using 'crontab -e'
* AWS CloudWatch > Metrics > Select Metrics > "ADD to Dashboard" > "Select Dashboard that you already created"
* Chaging GrphOptions (from text to trend-line)
  * Select Widget (any widget) > Graph Options > "Line (from Number or vice Versa)" > Update Widget
* Save Dashboard (often)

## AWS CloudWatch - Sending files to CloudWatch

* CloudWatch acts as simple repository for logs
* Its analytics is not as sophisticated like elk/splunk
* [What Is Amazon CloudWatch Logs?](https://docs.aws.amazon.com/AmazonCloudWatch/latest/logs/WhatIsCloudWatchLogs.html)
* CloudWatchLogFull access policy can be added to roles that requires logging
* EC2 instances can have user-id assigned by storing keys (but less convenies compared to assigning roles), Roles can be assigned dynamically to running instance
* View CloudWatchLog
  * AWS CloudWatch > Logs > /var/log/messages > LogStreams (select one) > view log-messages
* Rotate logs
  * Select log-groups > logs > Select "Expire Events After" > "2 weeks" (instead of never) 
  * They are stored in S3 by default, without changing log will be kept forever

## Monitor Apache ec2 logs

```bash
# Ensure EC2 has role that has policy CloudWatchFullAccess CloudWatchLogFullAccess
sudo yum update -y
sudo yum install -y awslogs
sudo chkconfig awlogs on
cd /etc/awslogs/
vi /etc/awslogs/awscli.conf ## Edit the region=us-west1 to us-west1
sudo service awslogs start
```

## Monitor Apache httpd access logs

```bash
# Ensure EC2 has role that has policy CloudWatchFullAccess CloudWatchLogFullAccess
sudo yum install httpd
sudo service httpd start
sudo su
cd /var/log/httpd ## access_log error_log are listed
vi /etc/awslogs/awslogs.conf
#copy whatever available for /var/log/messages to /var/log/httpd/access_log (clone/paste)
service awslogs restart
```

## Sample awslogs.conf

```conf
[general]
state_file = /var/awslogs/state/agent-state

[syslog]
datetime_format = %Y-%m-%d %H:%M:%S
file = /var/log/syslog
buffer_duration = 5000
log_stream_name = {instance_id}-{hostname}-{ip_address}-syslog
initial_position = start_of_file
log_group_name = ecs
```


## AWS CloudWatch Alarms

* Thresholds are upper/lower tollerances.
* Simple healthcheck setup (via) Route53
  * EC2> Select Security Group > Open inbound port 80
  * cd /var/www/html/
  * echo 'success' > healthcheck.html 
  * curl http://aws-ec2-dns/healthcheck.html
  * Goto Route53 > HealthCheck > Create Health Check > "Configure above url"
  * Goto Route53 > HealthCheck > Select Health Check > Selct Alarms Tab
  * Create Alarm > Name: "Server Down" > "Send Notification": Yes > "Topic Name"; Server Down > "Receipient Email Address": "mm@mm.com"
  * In Sufficient Data : "Wait for 1 minute" (just created, wait for enough data)

## AWS CloudWatch Alarms

* CloudWatch > Alarms > CreateAlarm > Linux System Metrics > FileSystemInstanceIdMountPath
* DiskspaceUsed > Select "Root Volume" > Next > Name: "name" && >= "80" (Period minutes/1 hr/6hr/days/) > "Average" (Treat missing data as bad or good using dropdown)
* Send notification to : "Email" > Create Alarm
* Email should be confirmed within 72 hours




## Reference

* [Monitoring memory and disk metrics for Amazon EC2 Linux instances](https://docs.aws.amazon.com/AWSEC2/latest/UserGuide/mon-scripts.html)
* [What Is Amazon CloudWatch Logs?](https://docs.aws.amazon.com/AmazonCloudWatch/latest/logs/WhatIsCloudWatchLogs.html)
* [Quick Start: Install and Configure the CloudWatch Logs Agent on a Running EC2 Linux Instance](https://docs.aws.amazon.com/AmazonCloudWatch/latest/logs/QuickStartEC2Instance.html)
* [Sample loggroups awslogs.conf](https://github.com/amazon-archives/ecs-cloudwatch-logs/blob/master/awslogs.conf)