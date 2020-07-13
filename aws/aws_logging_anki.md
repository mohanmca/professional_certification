## AWS Logging

* Amazon CludWatch Logging Agent
* Amazon CloudTrail logging
* Amazon CloudFront Access logs
* Amazon VPC Flow logs
* Monitoring AWS Amazon CloudTrail with CloudWatch Logging

## Logging benefits

* Without logging finding resolution, safeguarding environment would be difficult or impossible
* Audit Control would require logging (date-time, ip-address, user-name, txn-id) for compliance certifications
* Thresholds monitoring would help to notify and corrective actions
* Performance and different service interactions can be deduced from logging

## AWS CloudWatch Logs

* Install AmazonCloudWatchAgent using AWS SSM on your instances before collections logs (if it is not AWS AMI Linux)
* Unified CloudWatch agent allows to collect logs from EC2 and on-premise servers
* EC2 instance requires two roles to upload logs to CloudWatch
  * Role to install AWS SSM
  * Role to upload log to CLoudWath

## AWS Roles

* AWS Service (identify - EC2 allows EC2 instances to call AWS services on your behalf)
* CloudWatchAgentAdminPolicy & AmazonEC2RoleforSSM

## AWS Systems Manager

* Distribute software safely
* Common configuration files for any software can be distributed using SSM manager. SSM parameter store.
* Group of resources can be operated together (onpremise and cloud)
* Update patch/install application/generate cli command
* Groups 100s of resource types into business-unit/environment/application
* What is installed on particular instance?
* Starte the systems agent for all EC2 instance


## AWS Systems Manager Agent (SSM Agent)

* AWS Systems Manager Agent (SSM Agent) is Amazon software.
* Can be installed and configured on an EC2 instance, an on-premises server, or a virtual machine (VM). 
* SSM Agent makes it possible for Systems Manager to update, manage, and configure these resources. 
* The agent processes requests from the Systems Manager service in the AWS Cloud.


# SSM Agent is preinstalled, by default, on the following Amazon Machine Images (AMIs):

* Amazon Linux
* Amazon Linux 2
* Ubuntu Server 16.04
* Ubuntu Server 18.04
* Amazon ECS-Optimized
* sudo yum install -y https://s3.amazonaws.com/ec2-downloads-windows/SSMAgent/latest/linux_amd64/amazon-ssm-agent.rpm (for other instances)


## CloudWatch configuration wizard amazon-cloudwatch-agent-config-wizard

* sudo /opt/aws/amazon-cloudwatch-agent/bin/amazon-cloudwatch-agent-config-wizard
* Are you installing the agent on an Amazon EC2 instance or an on-premises server?
* Is the server running Linux or Windows Server?
* Do you want the agent to also send log files to CloudWatch Logs? If so, do you have an existing CloudWatch Logs agent configuration file? If yes, * the CloudWatch agent can use this file to determine the logs to collect from the server.
* If you're going to collect metrics from the server, do you want to monitor one of the default sets of metrics or customize the list of metrics that you collect?
* Do you want to collect custom metrics from your applications or services, using StatsD or collectd?
* Are you migrating from an existing SSM Agent?


## Sample CloudWatch agent configuration file

```json
    {
      "agent": {
        "metrics_collection_interval": 10,
        "logfile": "/opt/aws/amazon-cloudwatch-agent/logs/amazon-cloudwatch-agent.log"
      },
      "metrics": {
        "namespace": "MyCustomNamespace",
        "metrics_collected": {
          "cpu": {
            "resources": [
              "*"
            ],
            "measurement": [
              {"name": "cpu_usage_idle", "rename": "CPU_USAGE_IDLE", "unit": "Percent"},
              {"name": "cpu_usage_nice", "unit": "Percent"},
              "cpu_usage_guest"
            ],
            "totalcpu": false,
            "metrics_collection_interval": 10,
            "append_dimensions": {
              "customized_dimension_key_1": "customized_dimension_value_1",
              "customized_dimension_key_2": "customized_dimension_value_2"
            }
          },
          "disk": {
            "resources": [
              "/",
              "/tmp"
            ],
            "measurement": [
              {"name": "free", "rename": "DISK_FREE", "unit": "Gigabytes"},
              "total",
              "used"
            ],
             "ignore_file_system_types": [
              "sysfs", "devtmpfs"
            ],
            "metrics_collection_interval": 60,
            "append_dimensions": {
              "customized_dimension_key_3": "customized_dimension_value_3",
              "customized_dimension_key_4": "customized_dimension_value_4"
            }
          },
          "diskio": {
            "resources": [
              "*"
            ],
            "measurement": [
              "reads",
              "writes",
              "read_time",
              "write_time",
              "io_time"
            ],
            "metrics_collection_interval": 60
          },
          "swap": {
            "measurement": [
              "swap_used",
              "swap_free",
              "swap_used_percent"
            ]
          },
          "mem": {
            "measurement": [
              "mem_used",
              "mem_cached",
              "mem_total"
            ],
            "metrics_collection_interval": 1
          },
          "net": {
            "resources": [
              "eth0"
            ],
            "measurement": [
              "bytes_sent",
              "bytes_recv",
              "drop_in",
              "drop_out"
            ]
          },
          "netstat": {
            "measurement": [
              "tcp_established",
              "tcp_syn_sent",
              "tcp_close"
            ],
            "metrics_collection_interval": 60
          },
          "processes": {
            "measurement": [
              "running",
              "sleeping",
              "dead"
            ]
          }
        },
        "append_dimensions": {
          "ImageId": "${aws:ImageId}",
          "InstanceId": "${aws:InstanceId}",
          "InstanceType": "${aws:InstanceType}",
          "AutoScalingGroupName": "${aws:AutoScalingGroupName}"
        },
        "aggregation_dimensions" : [["ImageId"], ["InstanceId", "InstanceType"], ["d1"],[]],
        "force_flush_interval" : 30
      },
      "logs": {
        "logs_collected": {
          "files": {
            "collect_list": [
              {
                "file_path": "/opt/aws/amazon-cloudwatch-agent/logs/amazon-cloudwatch-agent.log",
                "log_group_name": "amazon-cloudwatch-agent.log",
                "log_stream_name": "amazon-cloudwatch-agent.log",
                "timezone": "UTC"
              },
              {
                "file_path": "/opt/aws/amazon-cloudwatch-agent/logs/test.log",
                "log_group_name": "test.log",
                "log_stream_name": "test.log",
                "timezone": "Local"
              }
            ]
          }
        },
        "log_stream_name": "my_log_stream_name",
        "force_flush_interval" : 15
      }
    }
```    