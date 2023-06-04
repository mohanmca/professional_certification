## AWS Event Bridge
* Eventbridge can handle rules and targets that can react to events created by multiple AWS sources as well as SaaS providers
* EventBridge could become a new way to design solutions with event-driven patterns in mind
* Event Bridge is for SaaS providers. 
  * It provides an a Cloudwatch like event bus to do pub/sub events. 
  * The consumer of the event bus can do whatever they want by defining routing rules. 
  * So they could stick the events in SQS and drive step functions, etc.

## Event bridge use-cases
* EventBridge is more flexible than SQS in the number of event sources and targets it supports. 
  * It makes it easier to create plumbing of event sources and targets.
  * For your example, EventRules supports S3 Events as a source and Step Functions as a target. 
    * If you want the retry logic and dead letter queues of step functions, EventRules can also trigger a Step Function. 
    * Rules are also flexible: you can trivially extend the EventRule to also publish the events to CloudWatch Logs.
  * EventRules don't support s3 events directly only via cloudtrail trails which means that the s3 bucket must have object logging enabled. 
    * To build that trail you need another bucket where the logs will go in.

## Event bridge archive
1. Database -> EventBridge Archives
   2. Disaster Recovery is one more use-case
3. Replay (source and target)
   4. To many target is not available
5. Schema registry
   6. All the possible schema for event bus can be viewed
   7. for Saas Event, we can discover schema
   8. Custom Events can be defined
8. Code Bindings
   9. VS Code
   10. Extension to visual editor
   11. Allows to easily check the variable
   12. Available in Java, Python and Typescript

## AWS Step functions

1. Orchestration lambda based engine (serverless)
2. Step functions
   3. In Parallel
   3. In Sequence
   4. In Retry
   5. If Then
6. How does it work?
   7. Read json based 'AMAZON state language'
   8. ideal state -> selection state -> final state
9. States
   10. Pass state - debug state
   11. Task state
   12. Choice State
   13. Wait
   14. Success State
   15. Fail State
   16. Parallel state
   17. Map state