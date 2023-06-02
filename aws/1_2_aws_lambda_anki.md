## AWS Lambda

* Introduced in 2014
* Event-driven serverless compute service

## What is AWS Lambda?

* Fully-managed serverless compute
* Event-driven execution
* Sub-second metering
* Multiple Languages supported.

## What are all lambda use-cases

* Amazon S3 pushes events and invokes a Lambda function
* AWS Lambda function can records in an Amazon DynamoDB stream.
  * with DynamoDB Streams, you can trigger a Lambda function to perform additional work each time a DynamoDB table is updated.
* Can respond to HTTP request using AWS HTTP Gateway

## Restrictions for AWS Lambda

* 512MB disk space
* Memory increment in 128MB to 1536MB
* Execution maximum of 5 minutes
* Response can't exceed 6 MB
* Number of times * each 100 millisecond = Billed

## Aws Lambda Overview

* Event source triggers
* AWS Lambda function execute code upon trgger
* Lambda function interacts with service
* IAM resource policy to access the underlying service
* ```java
    interce AWSLambdaHandler {
      public void handle(event, context) {

      }
    }
  ```

## Aws application development steps

* Build and zip and upload to S3
* Create/Update role
* Create function
* Create RestAPI
* Create Resource
* Create method
* Update Integration
* Create table

## AWS SAM cli

```bash
sam init
sam package
sam deploy --guided
curl https://<restapiid>.execute-api.us-east-1.amazonaws.com/Prod/hello/
sam local start-api
sam local invoke "HelloWorldFunction" -e events/event.json
```

## AWS Lambda package

* Organize your functions into services
* Give each service one deployment template and one code-repository

## AWS Lambda unit test

* DynamoDB local and localstack (for unit test)
* Create custom Mock
* Use dedicated test environment

## AWS Lambda debug

* SAM allows to run local version of lambda function inside docker container locally.
* SAM CLI and use your IDE debugger on the docker container that it provides.


## Reference
* [Use cases](https://docs.aws.amazon.com/lambda/latest/dg/applications-usecases.html)
* [AWS SAM HelloWorld](https://docs.aws.amazon.com/serverless-application-model/latest/developerguide/serverless-getting-started-hello-world.html)
* [AWS Serverless java container](https://github.com/awslabs/aws-serverless-java-container/wiki/Quick-start---Spring-Boot2)
* [Aws serverless express](https://github.com/awslabs/aws-serverless-express)
* [AWS SAM Template](https://docs.aws.amazon.com/lambda/latest/dg/with-s3-example-use-app-spec.html)
* [AWS Lambda docker](https://github.com/awslabs/aws-lambda-container-image-converter)