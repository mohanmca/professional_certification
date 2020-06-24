# Spring boot questions

## How to create anki from this boot mock question file

```
mdanki spring-boot-mock-questions.md spring-boot-mock-questions.apkg --deck "Spring 5 Certification::MdAnki::Boot" --config spring_web_mvc.json
```

## What does @EnableAutoConfiguration do?

1. *Attempts* to create and configure Spring beans
1. It is based on **dependencies** available on the class-path.
1. Reduces boilerplate code and configuration.

## Where to use ComponentScan? What is the default package of it?

1. It should be used with @Configuration
1.  It scans from the package of the class that declares @ComponentScan annotation.

## Why spring.factories is required

1. Auto-configuration is just a regular @Configuration, but classpath scan has to find them
1. Instead of scanning entire jar to find the AutoConfiguration, they could be configured in META-INF/spring.factories

## How do you customize Spring auto-configuration?

1. spring.main.allow-bean-definition-overriding=true
   1. create a Spring bean to replace a bean created by the auto-configuration.
1. disable one or more auto-configuration classes altogether.
1. Configure @EnableAutoConfiguration annotation, 
   1. by specifying the class(es) or fully qualified class name(s), 
   1. by using the spring.autoconfigure.exclude property with one or more fully qualified class name(s).


## How are @Conditional annotations are used?

1. @Conditional annotations are applied either at class level in @Configuration classes 
1. They are also applied at method level, also in @Configuration classes, annotating @Bean methods
1. @Conditional annotations also allow for conditional creation of Spring beans if a bean of a certain type is not already present in the application context.


## How do you access an endpoint using a tag?

1. Tags can be added as query parameters to the end of an URL accessing a Spring Boot Actuator endpoint in order to further refine the request
1. http://localhost:8080/actuator/metrics/jvm.memory.used?tag=area:heap&tag=id:G1 Old Gen
1. http://localhost:8080/actuator/metrics/jvm.memory.used?tag=area:heap

## What spring-boot metrics are measured?

1. Timers
1. Gauges
1. Counters
1. Distribution Summaries
1. Long Task Timers

## Provide examples of metrics that can be found in a Spring Boot application?

1. Response time of HTTP requests.
1. Number of active connections in a database connection pool.
1. Memory usagem - This can be for instance heap memory usage. 
1. Garbage collection statistics.

## What is Actuator Health Indicator?

1. A health indicator contains the logic to perform health checks and store the result of the health check in the health indicator, from which it can be retrieved.
1. A Spring Boot Actuator health indicator provides an indication of health of a certain component or
subsystem,
   1. latency of a database, 
   1. a message broker etc. 


## What are the Health Indicator statuses that are provided out of the box?

11 Status.UP - Component or subsystem is functioning as expected.
1. Status.DOWN - Component or subsystem is malfunctioning.
1. Status.OUT_OF_SERVICE - Component or subsystem has been taken out of service and should not be used.
1. Status.UNKNOWN - Status of component or subsystem is not known.

## Give some example healthindicator that ships with spring-boot

1. CassandraHealthIndicator Verifies the availability of a Cassandra database
1. DataSourceHealthIndicator Verifies the status of a DataSource and
1. DiskSpaceHealthIndicator Verifies that available disk space is above a
1. ElasticsearchHealthIndicator Verifies the availability of an Elasticsearch
1. ElasticsearchJestHealthIndicator Verifies the availability of an Elasticsearch
1. InfluxDbHealthIndicator Verifies the availability of an Influx database
1. JmsHealthIndicator Verifies the availability of a JMS message
1. LdapHealthIndicator Verifies the availability of LDAP server(s).
1. MailHealthIndicator Verifies the availability of a mail server.
1. MongoHealthIndicator Verifies the availability of a Mongo database
1. Neo4jHealthIndicator Verifies the availability of a Neo4J database
1. RabbitHealthIndicator Verifies the availability of a RabbitMQ message
1. RedisHealthIndicator Verifies the availability of a Redis data store.
1. SolrHealthIndicator Verifies the availability of a Apache Solr server.


## How do you change the Health Indicator status severity order?

1. The severity order of the status codes can be changed and new health indicator status codes can be added using the management.health.status.order configuration property.
1. management.health.status.order=FATAL, DOWN, OUT_OF_SERVICE, UNKNOWN, UP
1. management.health.status.http-mapping.FATAL=418 (To map the status code 418)


## What is the importance of  @DataJpaTest

1. The @DataJpaTest annotation to test JPA applications. 
1. By default, it scans for @Entity classes and configures Spring Data JPA repositories. 
1. If an embedded database is available on the classpath, it configures one as well. 
1. Regular @Component beans are not loaded into the ApplicationContext.
1. Data JPA tests are transactional and roll back at the end of each test.
1. transaction management can be disabled for a test or for the whole class using @Transactional(propagation = Propagation.NOT_SUPPORTED)



## Show example usage of @DataJpaTest

```java
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.autoconfigure.orm.jpa.*;

import static org.assertj.core.api.Assertions.*;

@DataJpaTest
class ExampleRepositoryTests {

    @Autowired
    private TestEntityManager entityManager;

    @Autowired
    private UserRepository repository;

    @Test
    void testExample() throws Exception {
        this.entityManager.persist(new User("sboot", "1234"));
        User user = this.repository.findByUsername("sboot");
        assertThat(user.getUsername()).isEqualTo("sboot");
        assertThat(user.getVin()).isEqualTo("1234");
    }

}
```


## What are the differences between @org.springframework.boot.test.mock.mockito.MockBean and @org.mockito.Mock?

1. @org.springframework.boot.test.mock.mockito.MockBean and @Mock annotation can be used to create Mockito mocks
1. @org.mockito.Mock can only be applied to fields and parameters while @MockBean can only be applied to classes and fields.
1. @Mock can be used to mock any Java class or interface 
1 @Mock - As you write a test that doesn't need any dependencies from the Spring Boot container, the classic/plain Mockito is the way to follow : it is fast and favors the isolation of the tested component.
1. @org.springframework.boot.test.mock.mockito.MockBean only allows for mocking of Spring beans or creation of mock Spring beans.
1. @org.springframework.boot.test.mock.mockito.MockBean can be used to mock existing beans but also to create new beans that will belong to the Spring application context.
1. To be able to use the @org.springframework.boot.test.mock.mockito.MockBean annotation, the Spring runner (@RunWith(SpringRunner.class) ) has to be used to run the associated test.
1. @org.springframework.boot.test.mock.mockito.MockBean can be used to create custom annotations for specific, reoccurring, needs.
1. If a bean, compatible with the declared class exists in the context, @MockBean replaces it by the mock. If it is not the case, it adds the mock in the context as a bean
1. If your test needs to rely on the Spring Boot container and you want also to add or mock one of the container beans : @MockBean from Spring Boot is the way.