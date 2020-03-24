All of Spring Boot is open source, including the documentation. If you find problems with the docs or if you want to improve them, please get involved.
1. If you do not see the formula, your installation of brew might be out-of-date. In that case, run brew update and try again.
1. If you install the Spring Boot CLI by using Homebrew or MacPorts, the command-line completion scripts are automatically registered with your shell.
1. If you do not see the app manifest, your installation of scoop might be out-of-date. In that case, run scoop update and try again.
1. The first run of your application is slow, as dependencies are downloaded. Subsequent runs are much quicker.
1. Once you’re done with the migration, please make sure to remove this module from your project’s dependencies.
1. This sample needs to be created in its own folder. Subsequent instructions assume that you have created a suitable folder and that it is your current directory.
1. At this point, you could import the project into an IDE (most modern Java IDEs include built-in support for Maven). For simplicity, we continue to use a plain text editor for this example.
1. You can still specify a version and override Spring Boot’s recommendations if you need to do so.
1. You should need to specify only the Spring Boot version number on this dependency. If you import additional starters, you can safely omit the version number.
1. In the preceding example, we specify a BOM, but any dependency type can be overridden in the same way.
1. If you use the Spring Boot starter parent pom, you need to add only the plugin. There is no need to configure it unless you want to change the settings defined in the parent.
1. Even though auto-configuration classes are public, the only aspect of the class that is considered public API is the name of the class which can be used for disabling the auto-configuration. The actual contents of those classes, such as nested configuration classes or bean methods are for internal use only and we do not recommend using those directly.
1. @SpringBootApplication also provides aliases to customize the attributes of @EnableAutoConfiguration and @ComponentScan.
    1. None of these features are mandatory and you may choose to replace this single annotation by any of the features that it enables. For instance, you may not want to use component scan or configuration properties scan in your application:
    ```java
    package com.example.myapplication;

    import org.springframework.boot.SpringApplication;
    import org.springframework.context.annotation.ComponentScan
    import org.springframework.context.annotation.Configuration;
    import org.springframework.context.annotation.Import;

    @Configuration(proxyBeanMethods = false)
    @EnableAutoConfiguration
    @Import({ MyConfig.class, MyAnotherConfig.class })
    public class Application {

        public static void main(String[] args) {
                SpringApplication.run(Application.class, args);
        }

    }
    ```
    In this example, Application is just like any other Spring Boot application except that @Component-annotated classes and @ConfigurationProperties-annotated classes are not detected automatically and the user-defined beans are imported explicitly (see @Import).
1. This section only covers jar based packaging. If you choose to package your application as a war file, you should refer to your server and IDE documentation.
1. Developer tools are automatically disabled when running a fully packaged application. If your application is launched from java -jar or if it is started from a special classloader, then it is considered a “production application”. If that does not apply to you (i.e. if you run your application from a container), consider excluding devtools or set the -Dspring.devtools.restart.enabled=false system property.
1. If you don’t want property defaults to be applied you can set spring.devtools.add-properties to false in your application.properties.
1. As long as forking is enabled, you can also start your application by using the supported build plugins (Maven and Gradle), since DevTools needs an isolated application classloader to operate properly. By default, the Gradle and Maven plugins fork the application process.
1. DevTools relies on the application context’s shutdown hook to close it during a restart. It does not work correctly if you have disabled the shutdown hook (SpringApplication.setRegisterShutdownHook(false)).
1. When deciding if an entry on the classpath should trigger a restart when it changes, DevTools automatically ignores projects named spring-boot, spring-boot-devtools, spring-boot-autoconfigure, spring-boot-actuator, and spring-boot-starter.
1. DevTools needs to customize the ResourceLoader used by the ApplicationContext. If your application provides one already, it is going to be wrapped. Direct override of the getResource method on the ApplicationContext is not supported.
1. Any update to the file will trigger a check, but restart only actually occurs if Devtools has detected it has something to do.
1. All property keys must be unique. As long as a property starts with restart.include. or restart.exclude. it is considered.
1. You can only run one LiveReload server at a time. Before starting your application, ensure that no other LiveReload servers are running. If you start multiple applications from your IDE, only the first has LiveReload support.
1. If devtools configuration files are not found in $HOME/.config/spring-boot, the root of the $HOME folder is searched for the presence of a .spring-boot-devtools.properties file. This allows you to share the devtools global configuration with applications that are on an older version of Spring Boot that does not support the $HOME/.config/spring-boot location.
1. Profiles activated in the above files will not affect the loading of profile-specific configuration files.
1. Because the remote client is using the same classpath as the real application it can directly read application properties. This is how the spring.devtools.remote.secret property is read and passed to the server for authentication.
1. Files are only monitored when the remote client is running. If you change a file before starting the remote client, it is not pushed to the remote server.
1. Spring Boot provides numerous FailureAnalyzer implementations, and you can add your own.
1. The constructor arguments passed to SpringApplication are configuration sources for Spring beans. In most cases, these are references to @Configuration classes, but they could also be references to XML configuration or to packages that should be scanned.
1. Some events are actually triggered before the ApplicationContext is created, so you cannot register a listener on those as a @Bean. You can register them with the SpringApplication.addListeners(…​) method or the SpringApplicationBuilder.listeners(…​) method.

    If you want those listeners to be registered automatically, regardless of the way the application is created, you can add a META-INF/spring.factories file to your project and reference your listener(s) by using the org.springframework.context.ApplicationListener key, as shown in the following example:

    org.springframework.context.ApplicationListener=com.example.project.MyListener
1. You can also use YAML ('.yml') files as an alternative to '.properties'.
1. If you use environment variables rather than system properties, most operating systems disallow period-separated key names, but you can use underscores instead (for example, SPRING_CONFIG_NAME instead of spring.config.name).
1. If your application runs in a container, then JNDI properties (in java:comp/env) or servlet context initialization parameters can be used instead of, or as well as, environment variables or system properties.
1. If you have specified any files in spring.config.location, profile-specific variants of those files are not considered. Use directories in spring.config.location if you want to also use profile-specific properties.
1. If you use “Starters”, SnakeYAML is automatically provided by spring-boot-starter.
1. spring.profiles can therefore contain a simple profile name (for example production) or a profile expression. A profile expression allows for more complicated profile logic to be expressed, for example production & (eu-central | eu-west). Check the reference guide for more details.
1. The properties that map to @ConfigurationProperties classes available in Spring Boot, which are configured via properties files, YAML files, environment variables etc., are public API but the accessors (getters/setters) of the class itself are not meant to be used directly.
1. Such arrangement relies on a default empty constructor and getters and setters are usually mandatory, since binding is through standard Java Beans property descriptors, just like in Spring MVC. A setter may be omitted in the following cases:

    Maps, as long as they are initialized, need a getter but not necessarily a setter, since they can be mutated by the binder.

    Collections and arrays can be accessed either through an index (typically with YAML) or by using a single comma-separated value (properties). In the latter case, a setter is mandatory. We recommend to always add a setter for such types. If you initialize a collection, make sure it is not immutable (as in the preceding example).

    If nested POJO properties are initialized (like the Security field in the preceding example), a setter is not required. If you want the binder to create the instance on the fly by using its default constructor, you need a setter.

    Some people use Project Lombok to add getters and setters automatically. Make sure that Lombok does not generate any particular constructor for such a type, as it is used automatically by the container to instantiate the object.

    Finally, only standard Java Bean properties are considered and binding on static properties is not supported.
1. To use constructor binding the class must be enabled using @EnableConfigurationProperties or configuration property scanning. You cannot use constructor binding with beans that are created by the regular Spring mechanisms (e.g. @Component beans, beans created via @Bean methods or beans loaded using @Import)
1. When the @ConfigurationProperties bean is registered using configuration property scanning or via @EnableConfigurationProperties, the bean has a conventional name: <prefix>-<fqn>, where <prefix> is the environment key prefix specified in the @ConfigurationProperties annotation and <fqn> is the fully qualified name of the bean. If the annotation does not provide any prefix, only the fully qualified name of the bean is used.

    The bean name in the example above is acme-com.example.AcmeProperties.
1. The prefix value for the annotation must be in kebab case (lowercase and separated by -, such as acme.my-project.person).
1. For YAML files, the brackets need to be surrounded by quotes for the keys to be parsed properly.
1. The preceding merging rules apply to properties from all property sources and not just YAML files.
1. As this bean is requested very early during the application lifecycle, make sure to limit the dependencies that your ConversionService is using. Typically, any dependency that you require may not be fully initialized at creation time. You may want to rename your custom ConversionService if it is not required for configuration keys coercion and only rely on custom converters qualified with @ConfigurationPropertiesBinding.
1. If @ConfigurationProperties beans are registered via @EnableConfigurationProperties instead of automatic scanning, the @Profile annotation needs to be specified on the @Configuration class that has the @EnableConfigurationProperties annotation. In the case where @ConfigurationProperties are scanned, @Profile can be specified on the @ConfigurationProperties class itself.
1. Remember that the spring.profiles property can be defined in a YAML document to determine when this particular document is included in the configuration. See Change Configuration Depending on the Environment for more details.
1. Logback does not have a FATAL level. It is mapped to ERROR.
1. You can also specify debug=true in your application.properties.
1. The above approach will only work for package level logging. Since relaxed binding always converts environment variables to lowercase, it’s not possible to configure logging for an individual class in this way. If you need to configure logging for a class, you can use the SPRING_APPLICATION_JSON variable.
1. Since logging is initialized before the ApplicationContext is created, it is not possible to control logging from @PropertySources in Spring @Configuration files. The only way to change the logging system or disable it entirely is via System properties.
1. When possible, we recommend that you use the -spring variants for your logging configuration (for example, logback-spring.xml rather than logback.xml). If you use standard configuration locations, Spring cannot completely control log initialization.
1. Because the standard logback.xml configuration file is loaded too early, you cannot use extensions in it. You need to either use logback-spring.xml or define a logging.config property.
1. The source must be specified in kebab case (such as my.property-name). However, properties can be added to the Environment by using the relaxed rules.
1. The auto-configuration applies when the default properties file for the configured resource bundle is available (i.e. messages.properties by default). If your resource bundle contains only language-specific properties files, you are required to add the default. If no properties file is found that matches any of the configured base names, there will be no auto-configured MessageSource.
1. If you use JBoss, you need to declare the webjars-locator-jboss-vfs dependency instead of the webjars-locator-core. Otherwise, all Webjars resolve as a 404.
1. Links to resources are rewritten in templates at runtime, thanks to a ResourceUrlEncodingFilter that is auto-configured for Thymeleaf and FreeMarker. You should manually declare this filter when using JSPs. Other template engines are currently not automatically supported but can be with custom template macros/helpers and the use of the ResourceUrlProvider.
1. If you register an ErrorPage with a path that ends up being handled by a Filter (as is common with some non-Spring web frameworks, like Jersey and Wicket), then the Filter has to be explicitly registered as an ERROR dispatcher, as shown in the following example:
1. Adding both spring-boot-starter-web and spring-boot-starter-webflux modules in your application results in Spring Boot auto-configuring Spring MVC, not WebFlux. This behavior has been chosen because many Spring developers add spring-boot-starter-webflux to their Spring MVC application to use the reactive WebClient. You can still enforce your choice by setting the chosen application type to SpringApplication.setWebApplicationType(WebApplicationType.REACTIVE).
1. You usually do not need to be aware of these implementation classes. Most applications are auto-configured, and the appropriate ApplicationContext and ServletWebServerFactory are created on your behalf.
1. TomcatServletWebServerFactory, JettyServletWebServerFactory and UndertowServletWebServerFactory are dedicated variants of ConfigurableServletWebServerFactory that have additional customization setter methods for Tomcat, Jetty and Undertow respectively.
1. If you fine-tune your logging configuration, ensure that the org.springframework.boot.autoconfigure.security category is set to log INFO-level messages. Otherwise, the default password is not printed.
1. If the authorization server does not support a JWK Set URI, you can configure the resource server with the Public Key used for verifying the signature of the JWT. This can be done using the spring.security.oauth2.resourceserver.jwt.public-key-location property, where the value needs to point to a file containing the public key in the PEM-encoded x509 format.
1. Before setting the management.endpoints.web.exposure.include, ensure that the exposed actuators do not contain sensitive information and/or are secured by placing them behind a firewall or by something like Spring Security.
1. We recommend disabling CSRF protection completely only if you are creating a service that is used by non-browser clients.
1. If you are using this feature in your tests, you may notice that the same database is reused by your whole test suite regardless of the number of application contexts that you use. If you want to make sure that each context has a separate embedded database, you should set spring.datasource.generate-unique-name to true.
1. You need a dependency on spring-jdbc for an embedded database to be auto-configured. In this example, it is pulled in transitively through spring-boot-starter-data-jpa.
1. You can bypass that algorithm completely and specify the connection pool to use by setting the spring.datasource.type property. This is especially important if you run your application in a Tomcat container, as tomcat-jdbc is provided by default.
1. You should at least specify the URL by setting the spring.datasource.url property. Otherwise, Spring Boot tries to auto-configure an embedded database.
1. For a pooling DataSource to be created, we need to be able to verify that a valid Driver class is available, so we check for that before doing anything. In other words, if you set spring.datasource.driver-class-name=com.mysql.jdbc.Driver, then that class has to be loadable.
1. The NamedParameterJdbcTemplate reuses the same JdbcTemplate instance behind the scenes. If more than one JdbcTemplate is defined and no primary candidate exists, the NamedParameterJdbcTemplate is not auto-configured.
1. Hibernate’s own internal property name for this (if you happen to remember it better) is hibernate.hbm2ddl.auto. You can set it, along with other Hibernate native properties, by using spring.jpa.properties.* (the prefix is stripped before adding them to the entity manager). The following line shows an example of setting JPA properties for Hibernate:
1. The H2 console is only intended for use during development, so you should take care to ensure that spring.h2.console.enabled is not set to true in production.
1. Spring Boot can only auto-configure dialects supported by the open source version of jOOQ.
1. If you use the Mongo 3.0 Java driver, spring.data.mongodb.host and spring.data.mongodb.port are not supported. In such cases, spring.data.mongodb.uri should be used to provide all of the configuration.
1. If you are using the reactive driver, Netty is required for SSL. The auto-configuration configures this factory automatically if Netty is available and the factory to use hasn’t been customized already.
1. If you do not configure a custom port, the embedded support uses a random port (rather than 27017) by default.
1. As the embedded Neo4j OGM driver does not provide the Neo4j kernel itself, you have to declare org.neo4j:neo4j as dependency yourself. Refer to the Neo4j OGM documentation for a list of compatible versions.
1. You can enable persistence for the embedded mode by providing a path to a database file in your configuration, e.g. spring.data.neo4j.uri=file://var/tmp/graph.db.
1. It is possible to define multiple base-dn values, however, since distinguished names usually contain commas, they must be defined using the correct notation.

    In yaml files, you can use the yaml list notation:
    ```yaml
    spring.ldap.embedded.base-dn:
      - dc=spring,dc=io
      - dc=pivotal,dc=io
    ```
    In properties files, you must include the index as part of the property name:

    spring.ldap.embedded.base-dn[0]=dc=spring,dc=io
    spring.ldap.embedded.base-dn[1]=dc=pivotal,dc=io
    1. Check the relevant section of the Spring Framework reference for more details.
1. In the preceding example, an auto-configured ConcurrentMapCacheManager is expected. If that is not the case (either you provided your own config or a different cache provider was auto-configured), the customizer is not invoked at all. You can have as many customizers as you want, and you can also order them by using @Order or Ordered.
1. When a cache library offers both a native implementation and JSR-107 support, Spring Boot prefers the JSR-107 support, so that the same features are available if you switch to a different JSR-107 implementation.
1. The support of Infinispan in Spring Boot is restricted to the embedded mode and is quite basic. If you want more options, you should use the official Infinispan Spring Boot starter instead. See Infinispan’s documentation for more details.
1. By default, a key prefix is added so that, if two separate caches use the same key, Redis does not have overlapping keys and cannot return invalid values. We strongly recommend keeping this setting enabled if you create your own RedisCacheManager.
1. If you use spring-boot-starter-activemq, the necessary dependencies to connect or embed an ActiveMQ instance are provided, as is the Spring infrastructure to integrate with JMS.
1. If you use spring-boot-starter-artemis, the necessary dependencies to connect to an existing Artemis instance are provided, as well as the Spring infrastructure to integrate with JMS. Adding org.apache.activemq:artemis-jms-server to your application lets you use embedded mode.
1. JmsMessagingTemplate can be injected in a similar manner. If a DestinationResolver or a MessageConverter bean is defined, it is associated automatically to the auto-configured JmsTemplate.
1. RabbitMessagingTemplate can be injected in a similar manner. If a MessageConverter bean is defined, it is associated automatically to the auto-configured AmqpTemplate.
1. If the property spring.kafka.producer.transaction-id-prefix is defined, a KafkaTransactionManager is automatically configured. Also, if a RecordMessageConverter bean is defined, it is automatically associated to the auto-configured KafkaTemplate.
1. To ensure that multiple transaction managers can safely coordinate the same resource managers, each Atomikos instance must be configured with a unique ID. By default, this ID is the IP address of the machine on which Atomikos is running. To ensure uniqueness in production, you should configure the spring.jta.transaction-manager-id property with a different value for each instance of your application.
1. To ensure that multiple transaction managers can safely coordinate the same resource managers, each Bitronix instance must be configured with a unique ID. By default, this ID is the IP address of the machine on which Bitronix is running. To ensure uniqueness in production, you should configure the spring.jta.transaction-manager-id property with a different value for each instance of your application.
1. Spring Boot also has explicit caching support for Hazelcast. If caching is enabled, the HazelcastInstance is automatically wrapped in a CacheManager implementation.
1. In particular, an Executor bean is not associated with the scheduler as Quartz offers a way to configure the scheduler via spring.quartz.properties. If you need to customize the task executor, consider implementing SchedulerFactoryBeanCustomizer.
1. External properties, logging, and other features of Spring Boot are installed in the context by default only if you use SpringApplication to create it.
1. If your test is @Transactional, it rolls back the transaction at the end of each test method by default. However, as using this arrangement with either RANDOM_PORT or DEFINED_PORT implicitly provides a real servlet environment, the HTTP client and server run in separate threads and, thus, in separate transactions. Any transaction initiated on the server does not roll back in this case.
1. @SpringBootTest with webEnvironment = WebEnvironment.RANDOM_PORT will also start the management server on a separate random port if your application uses a different port for the management server.
1. If you use a test annotation to test a more specific slice of your application, you should avoid adding configuration settings that are specific to a particular area on the main method’s application class.

    The underlying component scan configuration of @SpringBootApplication defines exclude filters that are used to make sure slicing works as expected. If you are using an explicit @ComponentScan directive on your @SpringBootApplication-annotated class, be aware that those filters will be disabled. If you are using slicing, you should define them again.
1. Spring’s test framework caches application contexts between tests. Therefore, as long as your tests share the same configuration (no matter how it is discovered), the potentially time-consuming process of loading the context happens only once.
1. If your test uses one of Spring Boot’s test annotations (such as @SpringBootTest), this feature is automatically enabled. To use this feature with a different arrangement, a listener must be explicitly added, as shown in the following example:

    @TestExecutionListeners(MockitoTestExecutionListener.class)
1. @MockBean cannot be used to mock the behavior of a bean that’s exercised during application context refresh. By the time the test is executed, the application context refresh has completed and it is too late to configure the mocked behavior. We recommend using a @Bean method to create and configure the mock in this situation.
1. CGLib proxies, such as those created for scoped beans, declare the proxied methods as final. This stops Mockito from functioning correctly as it cannot mock or spy on final methods in its default configuration. If you want to mock or spy on such a bean, configure Mockito to use its inline mock maker by adding org.mockito:mockito-inline to your application’s test dependencies. This allows Mockito to mock and spy on final methods.
1. While Spring’s test framework caches application contexts between tests and reuses a context for tests sharing the same configuration, the use of @MockBean or @SpyBean influences the cache key, which will most likely increase the number of contexts.
1. Each slice restricts component scan to appropriate components and loads a very restricted set of auto-configuration classes. If you need to exclude one of them, most @…​Test annotations provide an excludeAutoConfiguration attribute. Alternatively, you can use @ImportAutoConfiguration#exclude.
1. Including multiple “slices” by using several @…​Test annotations in one test is not supported. If you need multiple “slices”, pick one of the @…​Test annotations and include the @AutoConfigure…​ annotations of the other “slices” by hand.
1. JSON helper classes can also be used directly in standard unit tests. To do so, call the initFields method of the helper in your @Before method if you do not use @JsonTest.
1. By default, Spring Boot puts WebDriver beans in a special “scope” to ensure that the driver exits after each test and that a new instance is injected. If you do not want this behavior, you can add @Scope("singleton") to your WebDriver @Bean definition.
1. @WebFluxTest cannot detect routes registered via the functional web framework. For testing RouterFunction beans in the context, consider importing your RouterFunction yourself via @Import or using @SpringBootTest.
1. @WebFluxTest cannot detect custom security configuration registered via a @Bean of type SecurityWebFilterChain. To include that in your test, you will need to import the configuration that registers the bean via @Import or use @SpringBootTest.
1. Make sure to not use the regular @Import annotation to import auto-configurations as they are handled in a specific way by Spring Boot.
1. Depending on the complexity of your application, you may either have a single @Configuration class for your customizations or one class per domain area. The latter approach lets you enable it in one of your tests, if necessary, with the @Import annotation.
1. Using ConfigFileApplicationContextInitializer alone does not provide support for @Value("${…​}") injection. Its only job is to ensure that application.properties files are loaded into Spring’s Environment. For @Value support, you need to either additionally configure a PropertySourcesPlaceholderConfigurer or use @SpringBootTest, which auto-configures one for you.
1. Auto-configurations must be loaded that way only. Make sure that they are defined in a specific package space and that they are never the target of component scanning. Furthermore, auto-configuration classes should not enable component scanning to find additional components. Specific @Imports should be used instead.
1. @ConditionalOnBean and @ConditionalOnMissingBean do not prevent @Configuration classes from being created. The only difference between using these conditions at the class level and marking each contained @Bean method with the annotation is that the former prevents registration of the @Configuration class as a bean if the condition does not match.
1. You should only use simple text with @ConfigurationProperties field Javadoc, since they are not processed before being added to the JSON.
1. Either way, your starter must reference the core Spring Boot starter (spring-boot-starter) directly or indirectly (i.e. no need to add it if your starter relies on another starter). If a project is created with only your custom starter, Spring Boot’s core features will be honoured by the presence of the core starter.
1. Disabled endpoints are removed entirely from the application context. If you want to change only the technologies over which an endpoint is exposed, use the include and exclude properties instead.
1. * has a special meaning in YAML, so be sure to add quotes if you want to include (or exclude) all endpoints, as shown in the following example:
    ```yaml
    management:
      endpoints:
        web:
          exposure:
            include: "*"
    ```        
1. If your application is exposed publicly, we strongly recommend that you also secure your endpoints.
1. The prefix management.endpoint.<name> is used to uniquely identify the endpoint that is being configured.
1. When making an authenticated HTTP request, the Principal is considered as input to the endpoint and, therefore, the response will not be cached.
1. To allow the input to be mapped to the operation method’s parameters, Java code implementing an endpoint should be compiled with -parameters, and Kotlin code implementing an endpoint should be compiled with -java-parameters. This will happen automatically if you are using Spring Boot’s Gradle plugin or if you are using Maven and spring-boot-starter-parent.
1. Range requests are not supported when using Jersey.
1. If you have secured your application and wish to use always, your security configuration must permit access to the health endpoint for both authenticated and unauthenticated users.
1. The identifier for a given HealthIndicator is the name of the bean without the HealthIndicator suffix, if it exists. In the preceding example, the health information is available in an entry named my.
1. Unless the management port has been configured to expose endpoints by using a different HTTP port, management.endpoints.web.base-path is relative to server.servlet.context-path. If management.server.port is configured, management.endpoints.web.base-path is relative to management.server.servlet.context-path.
1. On Cloud Foundry, applications only receive requests on port 8080 for both HTTP and TCP routing, by default. If you want to use a custom management port on Cloud Foundry, you will need to explicitly set up the application’s routes to forward traffic to the custom port.
1. You can listen on a different address only when the port differs from the main server port.
1. Only caches that are configured on startup are bound to the registry. For caches not defined in the cache’s configuration, e.g. caches created on-the-fly or programmatically after the startup phase, an explicit registration is required. A CacheMetricsRegistrar bean is made available to make that process easier.
1. The order of common tags is important if you are using Graphite. As the order of common tags cannot be guaranteed using this approach, Graphite users are advised to define a custom MeterFilter instead.
1. The /cloudfoundryapplication path is not directly accessible to regular users. In order to use the endpoint, a valid UAA token must be passed with the request.
1. In the preceding example, we substitute acloudyspringtime for whatever value you give cf as the name of your application.
1. The following is a set of guidelines on how to secure a Spring Boot application that runs as an init.d service. It is not intended to be an exhaustive list of everything that should be done to harden an application and the environment in which it runs.
1. The ExecStart field does not declare the script action command, which means that the run command is used by default.
1. When setting JAVA_OPTS on Microsoft Windows, make sure to quote the entire instruction, such as set "JAVA_OPTS=-Xms256m -Xmx2048m". Doing so ensures the values are properly passed to the process.
1. If you need access to the BeanFactory or the Environment, your FailureAnalyzer can simply implement BeanFactoryAware or EnvironmentAware respectively.
1. Only production configuration is filtered that way (in other words, no filtering is applied on src/test/resources).
1. The useDefaultDelimiters property is important if you use standard Spring placeholders (such as ${placeholder}) in your configuration. If that property is not set to false, these may be expanded by the build.
1. Gradle’s expand method uses Groovy’s SimpleTemplateEngine, which transforms ${..} tokens. The ${..} style conflicts with Spring’s own property placeholder mechanism. To use Spring property placeholders together with automatic expansion, escape the Spring property placeholders as follows: \${..}.
1. In this specific case, the port binding works in a PaaS environment such as Heroku or Cloud Foundry. In those two platforms, the PORT environment variable is set automatically and Spring can bind to capitalized synonyms for Environment properties.
1. The YAML documents are merged in the order in which they are encountered. Later values override earlier values.
1. The version of the Servlet API has been overridden as, unlike Tomcat 9 and Undertow 2.0, Jetty 9.4 does not support Servlet 4.0.
1. spring-boot-starter-reactor-netty is required to use the WebClient class, so you may need to keep a dependency on Netty even when you need to include a different HTTP server.
1. @LocalServerPort is a meta-annotation for @Value("${local.server.port}"). Do not try to inject the port in a regular application. As we just saw, the value is set only after the container has been initialized. Contrary to a test, application code callbacks are processed early (before the value is actually available).
1. Spring Boot does not support h2c, the cleartext version of the HTTP/2 protocol. So you must configure SSL first.
1. If no dispatcherType is specified on a filter registration, REQUEST is used. This aligns with the Servlet Specification’s default dispatcher type.
1. The default location for logs is a logs directory relative to the Tomcat base directory. By default, the logs directory is a temporary directory, so you may want to fix Tomcat’s base directory or use an absolute path for the logs. In the preceding example, the logs are available in my-tomcat/logs relative to the working directory of the application.
1. If your application runs in Cloud Foundry or Heroku, the server.forward-headers-strategy property defaults to NATIVE. In all other instances, it defaults to NONE.
1. The double backslashes are required only when you use a properties file for configuration. If you use YAML, single backslashes are sufficient, and a value equivalent to that shown in the preceding example would be 192\.168\.\d{1,3}\.\d{1,3}.
1. You can trust all proxies by setting the internal-proxies to empty (but do not do so in production).
1. To get the server to render XML instead of JSON, you might have to send an Accept: text/xml header (or use a browser).
1. It is recommended to use the container’s built-in support for multipart uploads rather than introducing an additional dependency such as Apache Commons File Upload.
1. Spring extensions are not supported with Groovy configuration. Any logback-spring.groovy files will not be detected.
1. The Log4j starters gather together the dependencies for common logging requirements (such as having Tomcat use java.util.logging but configuring the output using Log4j 2).
1. To ensure that debug logging performed using java.util.logging is routed into Log4j 2, configure its JDK logging adapter by setting the java.util.logging.manager system property to org.apache.logging.log4j.jul.LogManager.
1. Because your custom configuration chooses to go with Hikari, app.datasource.type has no effect. In practice, the builder is initialized with whatever value you might set there and then overridden by the call to .type().
1. When you create a bean for LocalContainerEntityManagerFactoryBean yourself, any customization that was applied during the creation of the auto-configured LocalContainerEntityManagerFactoryBean is lost. For example, in case of Hibernate, any properties under the spring.jpa.hibernate prefix will not be automatically applied to your LocalContainerEntityManagerFactoryBean. If you were relying on these properties for configuring things like the naming strategy or the DDL mode, you will need to explicitly configure that when creating the LocalContainerEntityManagerFactoryBean bean. On the other hand, properties that get applied to the auto-configured EntityManagerFactoryBuilder, which are specified via spring.jpa.properties, will automatically be applied, provided you use the auto-configured EntityManagerFactoryBuilder to build the LocalContainerEntityManagerFactoryBean bean.
1. If you do not specify any order on your custom RepositoryRestConfigurer, it runs after the one Spring Boot uses internally. If you need to specify an order, make sure it is higher than 0.
1. You can output the schema creation by enabling the org.hibernate.SQL logger. This is done for you automatically if you enable the debug mode.
1. Spring Boot automatically creates the schema of an embedded DataSource. This behavior can be customized by using the spring.datasource.initialization-mode property. For instance, if you want to always initialize the DataSource regardless of its type:

    spring.datasource.initialization-mode=always
1. In a JPA-based app, you can choose to let Hibernate create the schema or use schema.sql, but you cannot do both. Make sure to disable spring.jpa.hibernate.ddl-auto if you use schema.sql.
1. Set server.error.whitelabel.enabled=false to switch the default error page off. Doing so restores the default of the servlet container that you are using. Note that Spring Boot still tries to resolve the error view, so you should probably add your own error page rather than disabling it completely.
1. Doing so only works if your Maven project inherits (directly or indirectly) from spring-boot-dependencies. If you have added spring-boot-dependencies in your own dependencyManagement section with <scope>import</scope>, you have to redefine the artifact yourself instead of overriding the property.
1. If you intend to start your application as a war or as an executable application, you need to share the customizations of the builder in a method that is both available to the SpringBootServletInitializer callback and in the main method in a class similar to the following:
    ```java
    @SpringBootApplication
    public class Application extends SpringBootServletInitializer {

        @Override
        protected SpringApplicationBuilder configure(SpringApplicationBuilder builder) {
            return configureApplication(builder);
        }

        public static void main(String[] args) {
            configureApplication(new SpringApplicationBuilder()).run(args);
        }

        private static SpringApplicationBuilder configureApplication(SpringApplicationBuilder builder) {
            return builder.sources(Application.class).bannerMode(Banner.Mode.OFF);
        }

    }
    ```
1. Property contributions can come from additional jar files on your classpath, so you should not consider this an exhaustive list. Also, you can define your own properties.
1. It is not required that every “property” has a “group”. Some properties might exist in their own right.
1. Prior to Spring Boot 1.3, a single deprecated boolean attribute can be used instead of the deprecation element. This is still supported in a deprecated fashion and should no longer be used. If no reason and replacement are available, an empty deprecation object should be set.
1. There is no way to set a level. warning is always assumed, since code is still handling the property.
1. As this is a new feature, IDE vendors must catch up with how it works. Adoption times naturally vary.
1. The binder is not aware of the metadata. If you provide that hint, you still need to transform the bean name into an actual Bean reference using by the ApplicationContext.
1. You should only use simple text with @ConfigurationProperties field Javadoc, since they are not processed before being added to the JSON.
1. If you are using AspectJ in your project, you need to make sure that the annotation processor runs only once. There are several ways to do this. With Maven, you can configure the maven-apt-plugin explicitly and add the dependency to the annotation processor only there. You could also let the AspectJ plugin run all the processing and disable annotation processing in the maven-compiler-plugin configuration, as follows:
    ```xml
    <plugin>
        <groupId>org.apache.maven.plugins</groupId>
        <artifactId>maven-compiler-plugin</artifactId>
        <configuration>
            <proc>none</proc>
        </configuration>
    </plugin>
    ```
1. You need not specify Class-Path entries in your manifest file. The classpath is deduced from the nested jars.