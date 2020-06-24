# Spring prior exam questions


## Can we replace @RestController with @Controller without any code change?

1. No, @Controller doesn't assume ResponseBody, @RestController assumes response-body
1. @Controller can return string that would be resolved into view


## Find which method is not query method
  * Supported keywords inside method names

## Can environment properties be overriden by by property-sources?	
  No, System environment properties would override everything else	
  
## Do we need @PathVariable("id") - always? Only when variable is different from request parameter


## What are all return types of @Controller method? Question has 6 and requested to choose only valid

## What are all the annotation required on return type of the @Controller	

## Do we need @ResponseBody?


## if we have two @Transaction(propagation=Reauired) on method1 and   @Transaction(propagation=Reauired_new)  method2,  and method1 invokes method2, how many transaction?	

## Would you implement HealthIndicator up() and down()?	


## Cassandar HealthIndicator?

## Can we interchange @MockBean and @Mock?	

## Is @MockBean mocks if there is an existing implementation acceissible for test?

1. Yes, it replaces existing Spring-bean loaded by application-context

## 6 JPA query methods are shown, find only they are valid

## Question about jmsTemplate

## Question about jdbcTemplate



## If there is an system environment variable daily.limit, how do you access them using SPeL	

  * @Value("${daily.limit}")	
  * @Value("#{daily.limit}")	
  * @Value("${sytemProperties['daily.limit']}")	
  * @Value("#{systemProperties.daily.lmit}) //may not work, as . means method invocation
  
  
## Would jdbcTemplate is autowired if there is Datasource()  configured?

## @After - Can you access retrun type and value of the method result


1. Whch ones are true about autowiring?	
1. The term --- usually represents 	
1. Spring MVC what can be return type of the method	
1. configuration meta-data, does proerties file inclusive?	
1. --- expresses the usage of entity-manager	
  1. @PersistenceUnit, @PeristernceContext	
1. Which of these are true about creating a deployable war?	
    1. maven pom? any class to initialize?	
    1. SpringBootServerInitializer	
1. In which isolation-level phantom read can occur	
1. List of emebedded containers support in Spring-Boot	
1. Which of these is true about DataSouce	
   1. DriverManagerDataSource	
1. Which of these programatic transaction manager	
   1. PlatformTransactionManager, TransactionTemplate	
1. Spring Security Authentication, AuthenticationManager, UserPrinciple and 	
1. @RequestMappingUsages (value or URi?)	
1. SpringSecurity @EnableGlobalMethodSecurity @PreAuthorize	
1. JDBCTemplate class that extracts Row?	
1. SpringFrameworkTest Module	
1. What is Safe Rest Operation	
1. DataAccessException Hierarchy	
1. If Singleton depends on Prototype bean? how many bean of prototype would be required	
1. How to enable Spring-Data-JPA	
   1. LocalEntityMangerFactoryBean vs LocalContainerEntityMangerFactoryBean	
1. Cross-cutting concern AOP?	
1. Where can logging configuration can be put to control logging?	
1. What is session scope?	
1. How to configure context-load listener	
1. How to enable @Security	
1. Internal_Server_error_code	
1. What is Spring MVC view resolver	
1. How to close application context	
1. What is required for component scanning	
1. How many types of advice are there	
1. Is applicationContext representation of spring container?	
1. What ties between web.xml and application context	
1. Spring security intercept-url attributes	
1. Mockito and EasyMock	
    1. Mockito.Mock(Myservice.class)  	
    1. org.mockito.Mockito Factory-method constructor org	
1. How to prevent dirty-reads in transaction isolation 	
1. SingleConnectionDataSource - What is one that doesn't close data-source	
1. What does String retrun means in Spring-MVC	
1. How to run each method of test-case in transaction @Transactional	
1. IOC vs DI (Martin fowler)	
1. @WebController, @Web or @WebComponent	
1. @RestController	
1. How to enable Transaction Management	
1. @AspectConfiguration vs @EnableAspectJAutoProxy	
1. Which Advice for normal return method	
1. Spring AOP features?	
1. Ordered or PriorityOrdered for BeanFactoryPostProcessor	
1. What does IOC solves	
1. Specialization of @Component	
1. What is the default scope of the web	
