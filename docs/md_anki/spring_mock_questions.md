# Spring mock questions

## How to create anki from this mock question file

```
mdanki spring-mock-questions.md spring-mock-questions.apkg --deck "Spring 5 Certification::MdAnki::Mock" --config spring_web_mvc.json
```

## Its advantage include faster development, lesser repetitive SQL code

1. ORM
1. Little more initial learning curve
1. can be little slower than SQL code
1. Less dependency with vendor
```

## State four return type in Spring MVC

1. Model
1. ModelAndView
1. void
1. String


## On which of these isolation level, phantom read occurs

1. ReadUnCommitted
1. ReadCommitted
1. RepeatableReads

## Show some less frequent RequestMapping

1. @RequestMapping(method=RequestMethod.PATCH)
1. @RequestMapping(valu="/{systemId}", method=RequestMethod.HEAD)

## Restrictions on Prototype Scopes

1. Spring does not manage the complete lifecycle of a prototype bean
1. Container instantiates, configures, and otherwise assembles a prototype object, and hands it to the client, with no further record of that prototype instance. 
1. Thus, although initialization lifecycle callback methods are called on all objects regardless of scope, in the case of prototypes, configured destruction lifecycle callbacks are not called.
1. configured destruction lifecycle callbacks are not called.


## How to clearn costliest resources from prototype-beans

The client code must clean up prototype-scoped objects and release expensive resources that the prototype bean(s) are holding. To get the Spring container to release resources held by prototype-scoped beans, try using a custom bean post-processor, which holds a reference to beans that need to be cleaned up.


## Can spring-aop proxy that doesn't implement interface

1. Yes, It could
1. CGLIB is used by default if a business object does not implement an interface.


## Who can alter internal bean factory after the context's standard initialization

1. BeanFactoryPostProcessor

## How to create anki from this file

```
mdanki spring_mock_questions.md spring_mock_question.apkg --config spring_web_mvc.json
```