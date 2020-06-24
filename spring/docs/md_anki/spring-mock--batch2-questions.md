# Spring core questions

## How to create anki from this spring core question file

```
mdanki spring-core-questions.md spring-core-questions.apkg  --deck "Spring 5 Certification::MdAnki::Core" --config spring_web_mvc.json
```

## If a field is private, how to inject with a dependency?

1. @Autowired
1. @Value
1. @Inject
1. https://docs.spring.io/spring-framework/docs/4.0.9.RELEASE/spring-framework-reference/html/testing.html#unit-testing-utilities


## Which class is used in Spring Transaction AOP Proxies to fullfill the declarative transaction

1. https://docs.spring.io/spring-framework/docs/4.0.9.RELEASE/spring-framework-reference/html/transaction.html#tx-decl-explained
1. TransactionInterceptor

## What is the dependency name for Spring-Test and Spring-Boot-Test

1. spring-boot-starter-test


## Spring AOP CGLIB Proxy, can it advice final method?

1. Not, it can't advise final method
1. AbstractContoller.setSynchronizeOnSession (is example for that). Closed for extension


## How to bypass spring filter security, why do we do that?

1. Set filters attribute to = none
## 
```xml
<http auto-config='true'>
    <intercept-url pattern="/css/**" filters="none"/>
    <intercept-url pattern="/login.jsp*" filters="none"/>
    <intercept-url pattern="/**" access="ROLE_USER" />
    <form-login login-page='/login.jsp'/>
  </http>
```  
1. Login page shouldn't be filtered


## When does AfterProperties invoked? can we configure method that return object?

## When does @PostConstruct method is invoked