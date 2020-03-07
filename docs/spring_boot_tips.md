## Spring boot tips

### List of tips in spring boot documentaiton
```javascript
#https://docs.spring.io/spring-boot/docs/current/reference/htmlsingle/
let tips = Array.from(document.getElementsByClassName("fa icon-tip"))
tips.map(tip =>  tip.parentElement.nextSibling.nextSibling.innerText).filter(t => t.indexOf("See the")==-1).filter(t => t.indexOf("in the appendix")==-1).join("\r\n1. ")
```

1. The spring-boot-starter-parent is a great way to use Spring Boot, but it might not be suitable all of the time. Sometimes you may need to inherit from a different parent POM, or you might not like our default settings. In those cases, see Using Spring Boot without the Parent POM for an alternative solution that uses an import scope.

1. The spring.io web site contains many “Getting Started” guides that use Spring Boot. If you need to solve a specific problem, check there first.

You can shortcut the steps below by going to start.spring.io and choosing the \"Web\" starter from the dependencies searcher. Doing so generates a new project structure so that you can start coding right away. Check the Spring Initializr documentation for more details.

1. Check the spring-boot-dependencies pom for a list of supported properties.

1. If you do not want to use the spring-boot-antlib module, see the Build an Executable Archive from Ant without Using spring-boot-antlib “How-to” .

1. For a list of additional community contributed starters, see the README file in the spring-boot-starters module on GitHub.

1. We recommend that you follow Java’s recommended package naming conventions and use a reversed domain name (for example, com.example.project).

1. If you don’t want to use @SpringBootApplication, the @EnableAutoConfiguration and @ComponentScan annotations that it imports defines that behaviour so you can also use those instead.

1. Many Spring configuration examples have been published on the Internet that use XML configuration. If possible, always try to use the equivalent Java-based configuration. Searching for Enable* annotations can be a good starting point.

1. You should only ever add one @SpringBootApplication or @EnableAutoConfiguration annotation. We generally recommend that you add one or the other to your primary @Configuration class only.

1. You can define exclusions both at the annotation level and by using the property.

1. Notice how using constructor injection lets the riskAssessor field be marked as final, indicating that it cannot be subsequently changed.

1. If you accidentally run a web application twice, you see a “Port already in use” error. STS users can use the Relaunch button rather than the Run button to ensure that any existing instance is closed.

1. Flagging the dependency as optional in Maven or using a custom developmentOnly configuration in Gradle (as shown above) is a best practice that prevents devtools from being transitively applied to other modules that use your project.

1. Repackaged archives do not contain devtools by default. If you want to use a certain remote devtools feature, you need to disable the excludeDevtools build property to include it. The property is supported with both the Maven and Gradle plugins.

1. For a complete list of the properties that are applied by the devtools, see DevToolsPropertyDefaultsPostProcessor.

1. If you want to keep those defaults and add additional exclusions, use the spring.devtools.restart.additional-exclude property instead.

1. You might want to set spring.devtools.restart.trigger-file as a global setting, so that all your projects behave in the same way.

1. All META-INF/spring-devtools.properties from the classpath are loaded. You can package files inside your project, or in the libraries that the project consumes.

1. It is always advisable to use https:// as the connection protocol, so that traffic is encrypted and passwords cannot be intercepted.

1. If you need to use a proxy to access the remote application, configure the spring.devtools.remote.proxy.host and spring.devtools.remote.proxy.port properties.

1. To add additional logging during startup, you can override logStartupInfo(boolean) in a subclass of SpringApplication.

1. If you want to disable lazy initialization for certain beans while using lazy initialization for the rest of the application, you can explicitly set their lazy attribute to false using the @Lazy(false) annotation.

1. The SpringApplication.setBanner(…​) method can be used if you want to generate a banner programmatically. Use the org.springframework.boot.Banner interface and implement your own printBanner() method.

1. You often need not use application events, but it can be handy to know that they exist. Internally, Spring Boot uses events to handle a variety of tasks.

1. It is often desirable to call setWebApplicationType(WebApplicationType.NONE) when using SpringApplication within a JUnit test.

1. Spring Boot also registers a CommandLinePropertySource with the Spring Environment. This lets you also inject single application arguments by using the @Value annotation.

1. If you want to know on which HTTP port the application is running, get the property with a key of local.server.port.

1. The SPRING_APPLICATION_JSON properties can be supplied on the command line with an environment variable. For example, you could use the following line in a UN*X shell:

$ SPRING_APPLICATION_JSON='{\"acme\":{\"name\":\"test\"}}' java -jar myapp.jar

In the preceding example, you end up with acme.name=test in the Spring Environment. You can also supply the JSON as spring.application.json in a System property, as shown in the following example:

$ java -Dspring.application.json='{\"name\":\"test\"}' -jar myapp.jar

You can also supply the JSON by using a command line argument, as shown in the following example:

$ java -jar myapp.jar --spring.application.json='{\"name\":\"test\"}'

You can also supply the JSON as a JNDI variable, as follows: java:comp/env/spring.application.json.

1. We recommend that you don’t mix profile-specific YAML files and multiple YAML documents. Stick to using only one of them.

1. See also the differences between @Value and type-safe configuration properties.

1. If you have more than one constructor for your class you can also use @ConstructorBinding directly on the constructor that should be bound.

1. We recommend that, when possible, properties are stored in lower-case kebab format, such as my.property-name=acme.

1. If you are upgrading from a previous version that is simply using Long to express the duration, make sure to define the unit (using @DurationUnit) if it isn’t milliseconds alongside the switch to Duration. Doing so gives a transparent upgrade path while supporting a much richer format.

1. If you are upgrading from a previous version that is simply using Long to express the size, make sure to define the unit (using @DataSizeUnit) if it isn’t bytes alongsidethe switch to DataSize. Doing so gives a transparent upgrade path while supporting a much richer format.

1. You can also trigger validation by annotating the @Bean method that creates the configuration properties with @Validated.

1. There are a lot of logging frameworks available for Java. Do not worry if the above list seems confusing. Generally, you do not need to change your logging dependencies and the Spring Boot defaults work just fine.

1. When you deploy your application to a servlet container or application server, logging performed via the Java Util Logging API is not routed into your application’s logs. This prevents logging performed by the container or other applications that have been deployed to it from appearing in your application’s logs.

1. Logging properties are independent of the actual logging infrastructure. As a result, specific configuration keys (such as logback.configurationFile for Logback) are not managed by spring Boot.

1. If you want to use a placeholder in a logging property, you should use Spring Boot’s syntax and not the syntax of the underlying framework. Notably, if you use Logback, you should use : as the delimiter between a property name and its default value and not use :-.

1. You can add MDC and other ad-hoc content to log lines by overriding only the LOG_LEVEL_PATTERN (or logging.pattern.level with Logback). For example, if you use logging.pattern.level=user:%X{user} %5p, then the default log format contains an MDC entry for \"user\", if it exists, as shown in the following example.

2019-08-30 12:30:04.031 user:someone INFO 22174 --- [  nio-8080-exec-0] demo.Controller
Handling authenticated request

1. spring.messages.basename supports comma-separated list of locations, either a package qualifier or a resource resolved from the classpath root.

1. Do not use the src/main/webapp directory if your application is packaged as a jar. Although this directory is a common standard, it works only with war packaging, and it is silently ignored by most build tools if you generate a jar.

1. This feature has been thoroughly described in a dedicated blog post and in Spring Framework’s reference documentation.

1. If possible, JSPs should be avoided. There are several known limitations when using them with embedded servlet containers.

1. Depending on how you run your application, IntelliJ IDEA orders the classpath differently. Running your application in the IDE from its main method results in a different ordering than when you run your application by using Maven or Gradle or from its packaged jar. This can cause Spring Boot to fail to find the templates on the classpath. If you have this problem, you can reorder the classpath in the IDE to place the module’s classes and resources first. Alternatively, you can configure the template prefix to search every templates directory on the classpath, as follows: classpath*:/templates/.

1. The BasicErrorController can be used as a base class for a custom ErrorController. This is particularly useful if you want to add a handler for a new content type (the default is to handle text/html specifically and provide a fallback for everything else). To do so, extend BasicErrorController, add a public method with a @RequestMapping that has a produces attribute, and create a bean of your new type.

1. You can define as many RouterFunction beans as you like to modularize the definition of the router. Beans can be ordered if you need to apply a precedence.

1. Spring WebFlux applications do not strictly depend on the Servlet API, so they cannot be deployed as war files and do not use the src/main/webapp directory.

1. To see the order of every Filter in your application, enable debug level logging for the web logging group (logging.level.web=debug). Details of the registered filters, including their order and URL patterns, will then be logged at startup.

1. @ServletComponentScan has no effect in a standalone container, where the container’s built-in discovery mechanisms are used instead.

1. The “How-to” section includes a section on how to initialize a database.

1. If, for whatever reason, you do configure the connection URL for an embedded database, take care to ensure that the database’s automatic shutdown is disabled. If you use H2, you should use DB_CLOSE_ON_EXIT=FALSE to do so. If you use HSQLDB, you should ensure that shutdown=true is not used. Disabling the database’s automatic shutdown lets Spring Boot control when the database is closed, thereby ensuring that it happens once access to the database is no longer needed.

1. Additional connection pools can always be configured manually. If you define your own DataSource bean, auto-configuration does not occur.

1. You often do not need to specify the driver-class-name, since Spring Boot can deduce it for most databases from the url.

1. We do not go into too many details of JPA or Spring Data here. You can follow the “Accessing Data with JPA” guide from spring.io and read the Spring Data JPA and Hibernate reference documentation.

1. We have barely scratched the surface of Spring Data JPA. For complete details, see the Spring Data JPA reference documentation.

1. For complete details of Spring Data JDBC, please refer to the reference documentation.

1. If you are not using Spring Boot’s developer tools but would still like to make use of H2’s console, you can configure the spring.h2.console.enabled property with a value of true.

1. The jOOQ manual tends to use a variable named create to hold the DSLContext.

1. we also provide a spring-boot-starter-data-redis-reactive “Starter” for consistency with the other stores with reactive support.

1. You can also register an arbitrary number of beans that implement LettuceClientConfigurationBuilderCustomizer for more advanced customizations. If you use Jedis, JedisClientConfigurationBuilderCustomizer is also available.

1. If spring.data.mongodb.port is not specified, the default of 27017 is used. You could delete this line from the example shown earlier.

1. If you do not use Spring Data Mongo, you can inject com.mongodb.MongoClient beans instead of using MongoDbFactory. If you want to take complete control of establishing the MongoDB connection, you can also declare your own MongoDbFactory or MongoClient bean.

1. You can customize document scanning locations by using the @EntityScan annotation.

1. For complete details of Spring Data MongoDB, including its rich object mapping technologies, refer to its reference documentation.

1. For complete details of Spring Data Neo4j, including its object mapping technologies, refer to the reference documentation.

1. For complete details of Spring Data Elasticsearch, refer to the reference documentation.

1. For complete details of Spring Data Cassandra, refer to the reference documentation.

1. You need to provide at least the bootstrap host(s), in which case the bucket name is default and the password is an empty String. Alternatively, you can define your own org.springframework.data.couchbase.config.CouchbaseConfigurer @Bean to take control over the whole configuration.

1. If you want to fully bypass the auto-configuration for Spring Data Couchbase, provide your own implementation of org.springframework.data.couchbase.config.AbstractCouchbaseDataConfiguration.

1. It is also possible to transparently update or evict data from the cache.

1. It is also possible to force a particular cache provider by setting the spring.cache.type property. Use this property if you need to disable caching altogether in certain environment (such as tests).

1. Use the spring-boot-starter-cache “Starter” to quickly add basic caching dependencies. The starter brings in spring-context-support. If you add dependencies manually, you must include spring-context-support in order to use the JCache, EhCache 2.x, or Caffeine support.

1. Spring Boot has general support for Hazelcast. If a single HazelcastInstance is available, it is automatically reused for the CacheManager as well, unless the spring.cache.jcache.config property is specified.

1. If a standard javax.cache.CacheManager bean is defined, it is wrapped automatically in an org.springframework.cache.CacheManager implementation that the abstraction expects. No further customization is applied to it.

1. You can take full control of the default configuration by adding a RedisCacheConfiguration @Bean of your own. This can be useful if you’re looking for customizing the default serialization strategy.

1. See ActiveMQProperties for more of the supported options. You can also register an arbitrary number of beans that implement ActiveMQConnectionFactoryCustomizer for more advanced customizations.

1. See Understanding AMQP, the protocol used by RabbitMQ for more details.

1. It does not matter which container type you chose. Those two beans are exposed by the auto-configuration.

1. To create a topic on startup, add a bean of type NewTopic. If the topic already exists, the bean is ignored.

1. A custom ChainedKafkaTransactionManager must be marked @Primary as it usually references the auto-configured KafkaTransactionManager bean.

1. RestTemplateBuilder includes a number of useful methods that can be used to quickly configure a RestTemplate. For example, to add BASIC auth support, you can use builder.basicAuthentication(\"user\", \"password\").build().

1. If you have defined a custom Executor in the context, regular task execution (i.e. @EnableAsync) will use it transparently but the Spring MVC support will not be configured as it requires an AsyncTaskExecutor implementation (named applicationTaskExecutor). Depending on your target arrangement, you could change your Executor into a ThreadPoolTaskExecutor or define both a ThreadPoolTaskExecutor and an AsyncConfigurer wrapping your custom Executor.

The auto-configured TaskExecutorBuilder allows you to easily create instances that reproduce what the auto-configuration does by default.

1. You can disable Spring Session by setting the store-type to none.

1. The starter also brings the vintage engine so that you can run both JUnit 4 and JUnit 5 tests. If you have migrated your tests to JUnit 5, you should exclude JUnit 4 support, as shown in the following example:

<dependency>
    <groupId>org.springframework.boot</groupId>
    <artifactId>spring-boot-starter-test</artifactId>
    <scope>test</scope>
    <exclusions>
        <exclusion>
            <groupId>org.junit.vintage</groupId>
            <artifactId>junit-vintage-engine</artifactId>
        </exclusion>
    </exclusions>
</dependency>

1. If you are using JUnit 4, don’t forget to also add @RunWith(SpringRunner.class) to your test, otherwise the annotations will be ignored. If you are using JUnit 5, there’s no need to add the equivalent @ExtendWith(SpringExtension.class) as @SpringBootTest and the other @…Test annotations are already annotated with it.

1. If you want to focus only on the web layer and not start a complete ApplicationContext, consider using @WebMvcTest instead.

1. Testing within a mocked environment is usually faster than running with a full Servlet container. However, since mocking occurs at the Spring MVC layer, code that relies on lower-level Servlet container behavior cannot be directly tested with MockMvc.

For example, Spring Boot’s error handling is based on the “error page” support provided by the Servlet container. This means that, whilst you can test your MVC layer throws and handles exceptions as expected, you cannot directly test that a specific custom error page is rendered. If you need to test these lower-level concerns, you can start a fully running server as described in the next section.

1. If you are using @SpyBean to spy on a bean with @Cacheable methods that refer to parameters by name, your application must be compiled with -parameters. This ensures that the parameter names are available to the caching infrastructure once the bean has been spied upon.

1. It is also possible to use the @AutoConfigure…​ annotations with the standard @SpringBootTest annotation. You can use this combination if you are not interested in “slicing” your application but you want some of the auto-configured test beans.

1. If you need to register extra components, such as the Jackson Module, you can import additional configuration classes by using @Import on your test.

1. You can also auto-configure MockMvc in a non-@WebMvcTest (such as @SpringBootTest) by annotating it with @AutoConfigureMockMvc. The following example uses MockMvc:

1. If you need to configure elements of the auto-configuration (for example, when servlet filters should be applied) you can use attributes in the @AutoConfigureMockMvc annotation.

1. Sometimes writing Spring MVC tests is not enough; Spring Boot can help you run full end-to-end tests with an actual server.

1. If you need to register extra components, such as Jackson Module, you can import additional configuration classes using @Import on your test.

1. You can also auto-configure WebTestClient in a non-@WebFluxTest (such as @SpringBootTest) by annotating it with @AutoConfigureWebTestClient. The following example shows a class that uses both @WebFluxTest and a WebTestClient:

1. This setup is only supported by WebFlux applications as using WebTestClient in a mocked web application only works with WebFlux at the moment.

1. Sometimes writing Spring WebFlux tests is not enough; Spring Boot can help you run full end-to-end tests with an actual server.

1. If this is not an option for you, you can create a @SpringBootConfiguration somewhere in the hierarchy of your test so that it is used instead. Alternatively, you can specify a source for your test, which disables the behavior of finding a default one.

1. Spring Framework 5.0 provides a new WebTestClient that works for WebFlux integration tests and both WebFlux and MVC end-to-end testing. It provides a fluent API for assertions, unlike TestRestTemplate.

1. A demo project is available to showcase how you can create a starter step-by-step.

1. If you use @ConditionalOnClass or @ConditionalOnMissingClass as a part of a meta-annotation to compose your own composed annotations, you must use name as referring to the class in such a case is not handled.

1. You need to be very careful about the order in which bean definitions are added, as these conditions are evaluated based on what has been processed so far. For this reason, we recommend using only @ConditionalOnBean and @ConditionalOnMissingBean annotations on auto-configuration classes (since these are guaranteed to load after any user-defined bean definitions have been added).

1. If multiple auto-configurations have to be defined, there is no need to order their declarations as they are invoked in the exact same order as when running the application.

1. You may combine the auto-configuration code and the dependency management in a single module if you do not need to separate those two concerns.

1. You should mark the dependencies to the library as optional so that you can include the autoconfigure module in your projects more easily. If you do it that way, the library is not provided and, by default, Spring Boot backs off.

1. These dependencies and plugins are provided by default if one bootstraps a Kotlin project on start.spring.io.

1. org.jetbrains.kotlinx:kotlinx-coroutines-reactor dependency is provided by default if one bootstraps a Kotlin project with at least one reactive dependency on start.spring.io.

1. To generate your own metadata using the annotation processor, kapt should be configured with the spring-boot-configuration-processor dependency. Note that some features (such as detecting the default value or deprecated items) are not working due to limitations in the model kapt provides.

1. If you want to implement your own strategy for when endpoints are exposed, you can register an EndpointFilter bean.

1. See CorsEndpointProperties for a complete list of options.

1. Because endpoints are technology agnostic, only simple types can be specified in the method signature. In particular declaring a single parameter with a custom type defining a name and counter properties is not supported.

1. The HealthContributorRegistry can be used to register and unregister health indicators at runtime.

1. You can disable them all by setting the management.health.defaults.enabled property.

1. If you need more control, you can define your own HttpCodeStatusMapper bean.

1. In a reactive application, The ReactiveHealthContributorRegistry should be used to register and unregister health indicators at runtime. If you need to register a regular HealthContributor, you should wrap it using ReactiveHealthContributor#adapt.

1. To handle the error automatically, consider extending from AbstractReactiveHealthIndicator.

1. If necessary, reactive indicators replace the regular ones. Also, any HealthIndicator that is not handled explicitly is wrapped automatically.

1. You can use @Qualifier(\"groupname\") if you need to register custom StatusAggregator or HttpCodeStatusMapper beans for use with the group.

1. It is possible to disable them all by setting the management.info.defaults.enabled property.

1. Rather than hardcoding those values, you could also expand info properties at build time.

Assuming you use Maven, you could rewrite the preceding example as follows:

info.app.encoding=@project.build.sourceEncoding@
info.app.java.source=@java.version@
info.app.java.target=@java.version@

1. A GitProperties bean is auto-configured if a git.properties file is available at the root of the classpath. See \"Generate git information\" for more details.

1. The Maven and Gradle plugins can both generate that file. See \"Generate build information\" for more details.

1. Actuator is supported natively with Spring MVC, Spring WebFlux, and Jersey. If both Jersey and Spring MVC are available, Spring MVC will be used.

1. To “reset” the specific level of the logger (and use the default configuration instead), you can pass a value of null as the configuredLevel.

1. To learn more about Micrometer’s capabilities, please refer to its reference documentation, in particular the concepts section.

1. To take control over this behaviour, define your GraphiteMeterRegistry and supply your own HierarchicalNameMapper. An auto-configured GraphiteConfig and Clock beans are provided unless you define your own:

1. To take control over this behaviour, define your JmxMeterRegistry and supply your own HierarchicalNameMapper. An auto-configured JmxConfig and Clock beans are provided unless you define your own:

1. The endpoint is not available by default and must be exposed, see exposing endpoints for more details.

1. If publishing metrics to a Wavefront proxy (as described in the documentation), the host must be in the proxy://HOST:PORT format.

1. By default, Spring Boot provides metadata for all supported data sources; you can add additional DataSourcePoolMetadataProvider beans if your favorite data source isn’t supported out of the box. See DataSourcePoolMetadataProvidersConfiguration for examples.

1. The name you use here should match the name used in the code, not the name after it has been naming-convention normalized for a monitoring system it is shipped to. In other words, if jvm.memory.max appears as jvm_memory_max in Prometheus because of its snake case naming convention, you should still use jvm.memory.max as the selector when inspecting the meter in the metrics endpoint.

1. The reported measurements are the sum of the statistics of all meters matching the meter name and any tags that have been applied. So in the example above, the returned \"Value\" statistic is the sum of the maximum memory footprints of \"Code Cache\", \"Compressed Class Space\", and \"Metaspace\" areas of the heap. If you just wanted to see the maximum size for the \"Metaspace\", you could add an additional tag=id:Metaspace, i.e. /actuator/metrics/jvm.memory.max?tag=area:nonheap&tag=id:Metaspace.

1. The Java CFEnv project is a better fit for tasks such as configuring a DataSource.

1. Upload binaries instead of sources

By default, Elastic Beanstalk uploads sources and compiles them in AWS. However, it is best to upload the binaries instead. To do so, add lines similar to the following to your .elasticbeanstalk/config.yml file:

deploy:
    artifact: target/demo-0.0.1-SNAPSHOT.jar

1. Reduce costs by setting the environment type

By default an Elastic Beanstalk environment is load balanced. The load balancer has a significant cost. To avoid that cost, set the environment type to “Single instance”, as described in the Amazon documentation. You can also create single instance environments by using the CLI and the following command:

eb create -s

1. By default, Boxfuse activates a Spring profile named boxfuse on startup. If your executable jar or war contains an application-boxfuse.properties file, Boxfuse bases its configuration on the properties it contains.

1. If your application fails to start, check the log file written to /var/log/<appname>.log for errors.

1. To reduce the chances of the application’s user account being compromised, you should consider preventing it from using a login shell. For example, you can set the account’s shell to /usr/sbin/nologin.

1. If you do not like having the config file next to the jar file, you can set a CONF_FOLDER environment variable to customize the location of the config file.

1. See subclasses of CompilerAutoConfiguration in the Spring Boot CLI source code to understand exactly how customizations are applied.

1. Many Spring annotations work without using import statements. Try running your application to see what fails before adding imports.

1. The “Using Spring Boot” section includes a more complete example of using Apache Ant with spring-boot-antlib.

1. The Environment has already been prepared with all the usual property sources that Spring Boot loads by default. It is therefore possible to get the location of the file from the environment. The preceding example adds the custom-resource property source at the end of the list so that a key defined in any of the usual other locations takes precedence. A custom implementation may define another order.

1. If you inherit from the spring-boot-starter-parent POM, the default filter token of the maven-resources-plugins has been changed from ${*} to @ (that is, @maven.token@ instead of ${maven.token}) to prevent conflicts with Spring-style placeholders. If you have enabled Maven filtering for the application.properties directly, you may want to also change the default filter token to use other delimiters.

1. Note the use of ReactorResourceFactory for the connection provider and event loop resources. This ensures efficient sharing of resources for the server receiving requests and the client making requests.

1. The Logback documentation has a dedicated section that covers configuration in some detail.

1. Spring Boot will expose Hikari-specific settings to spring.datasource.hikari. This example uses a more generic configuration sub namespace as the example does not support multiple datasource implementations.

1. firstDataSourceProperties has to be flagged as @Primary so that the database initializer feature uses your copy (if you use the initializer).

1. If you need to apply advanced customization to Hibernate properties, consider registering a HibernatePropertiesCustomizer bean that will be invoked prior to creating the EntityManagerFactory. This takes precedence to anything that is applied by the auto-configuration.

1. In particular, JooqExceptionTranslator and SpringTransactionProvider can be reused to provide similar features to what the auto-configuration does with a single DataSource.

1. The commit time in git.properties is expected to match the following format: yyyy-MM-dd’T’HH:mm:ssZ. This is the default format for both plugins listed above. Using this format lets the time be parsed into a Date and its format, when serialized to JSON, to be controlled by Jackson’s date serialization configuration settings.

1. providedRuntime is preferred to Gradle’s compileOnly configuration. Among other limitations, compileOnly dependencies are not on the test classpath, so any web-based integration tests fail.

1. Spring Boot provides various conversion mechanism with advanced value formatting, make sure to review the properties conversion section.

1. We recommend that you use an Enum for those two values instead. If your IDE supports it, this is by far the most effective approach to auto-completion.

1. Only one provider can be active for a given property, but you can specify several providers if they can all manage the property in some way. Make sure to place the most powerful provider first, as the IDE must use the first one in the JSON section that it can handle. If no provider for a given property is supported, no special content assistance is provided, either.

1. If multiple values can be provided, use a Collection or Array type to teach the IDE about it.

1. This has no effect on collections and maps, as those types are automatically identified, and a single metadata property is generated for each of them.

1. Build plugins automatically move the Main-Class attribute to Start-Class when the fat jar is built. If you use that, specify the name of the class to launch by using the Main-Class attribute and leaving out Start-Class."