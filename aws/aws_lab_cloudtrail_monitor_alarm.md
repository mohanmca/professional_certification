## Steps

* CloudTrail > CreateTrail
* Sending Events to CloudWatch Logs
* CloudTrail_CloudWatchLogs_Role use this role to deliver trail logs to cloud-watch
  ```bash
  aws iam create-role --role-name role_name --assume-role-policy-document file://<path to assume_role_policy_document>.json
  ``` 
* Choose the log group
  ```bash
    aws logs create-log-group --log-group-name CloudTrail/logs
    aws logs describe-log-groups    
  ``` 
* Update Trail
  ```
  aws cloudtrail update-trail --name trail_name --cloud-watch-logs-log-group-arn log_group_arn --cloud-watch-logs-role-arn role_arn
  ```
* Creating CloudWatch Alarms with an AWS CloudFormation Template

## CloudWatch Filters

* Always test the filter, even if filter doesn't work, syntaxes are checked
* ```
{$.sourceIPAddress=103.26.222.206}
{ ($.eventName = StopInstances) || ($.eventName = TerminateInstances) || ($.eventName = RunInstances) }
```

## Reference

* [Monitoring CloudTrail Log Files with Amazon CloudWatch Logs](https://docs.aws.amazon.com/awscloudtrail/latest/userguide/monitor-cloudtrail-log-files-with-cloudwatch-logs.html)