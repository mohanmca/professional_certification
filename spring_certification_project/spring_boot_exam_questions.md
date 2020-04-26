## Spring boot exam

* @After - Can you access retrun type and value
* jdbcTemplate - can it be autowire if there is Datasource()
* Name of method for DisposableBean and Initializing Bean
* Select query method
  * 

# Assured question

* Can RestController can replace Controller without any code change?
  * No, RestController will not resolve the view
* Find limitation of each advice and when to use it
* Find which method is not query method
  * Supported keywords inside method names
* Can environment properties be overriden by by property-sources?
  No, System environment properties would override everything else
* Do we need @PathVariable("id") - always? Only when variable is different from request parameter
* What are all return types of @Controller method? They gave 6 and requested to choose few
* What are all the annotation required on return type of the @Controller
* Do we need @ResponseBody?
* if we have two @Transaction(propagation=Reauired) on method1 and   @Transaction(propagation=Reauired_new)  method2,  and method1 invokes method2, how many transaction?
* Would you implement HealthIndicator up() and down()?
* Cassandar HealthIndicator?
* Can we interchange @MockBean and @Mock?
* Is @MockBean mocks if there is an existing implementation acceissible for test?