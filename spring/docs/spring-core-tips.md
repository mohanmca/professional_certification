1. Another way of accessing differently scoped target beans is an ObjectFactory/ Provider injection point. See Scoped Beans as Dependencies.

1. You may also find the ServiceLocatorFactoryBean (in the org.springframework.beans.factory.config package) to be useful.
1. The JSR-250 @PostConstruct and @PreDestroy annotations are generally considered best practice for receiving lifecycle callbacks in a modern Spring application. Using these annotations means that your beans are not coupled to Spring-specific interfaces. For details, see Using @PostConstruct and @PreDestroy.

1. If you do not want to use the JSR-250 annotations but you still want to remove coupling, consider init-method and destroy-method bean definition metadata.
1. You can assign the destroy-method attribute of a <bean> element a special (inferred) value, which instructs Spring to automatically detect a public close or shutdown method on the specific bean class. (Any class that implements java.lang.AutoCloseable or java.io.Closeable would therefore match.) You can also set this special (inferred) value on the default-destroy-method attribute of a <beans> element to apply this behavior to an entire set of beans (see Default Initialization and Destroy Methods). Note that this is the default behavior with Java configuration.
1. Note that the regular org.springframework.context.Lifecycle interface is a plain contract for explicit start and stop notifications and does not imply auto-startup at context refresh time. For fine-grained control over auto-startup of a specific bean (including startup phases), consider implementing org.springframework.context.SmartLifecycle instead.

1. Also, please note that stop notifications are not guaranteed to come before destruction. On regular shutdown, all Lifecycle beans first receive a stop notification before the general destruction callbacks are being propagated. However, on hot refresh during a context’s lifetime or on aborted refresh attempts, only destroy methods are called.
1. You can use the PropertySourcesPlaceholderConfigurer to substitute class names, which is sometimes useful when you have to pick a particular implementation class at runtime. The following example shows how to do so:
    ```xml
    <bean class="org.springframework.beans.factory.config.PropertySourcesPlaceholderConfigurer">
        <property name="locations">
            <value>classpath:com/something/strategy.properties</value>
        </property>
        <property name="properties">
            <value>custom.strategy.class=com.something.DefaultStrategy</value>
        </property>
    </bean>

    <bean id="serviceStrategy" class="${custom.strategy.class}"/>
    ```
1. If the class cannot be resolved at runtime to a valid class, resolution of the bean fails when it is about to be created, which is during the preInstantiateSingletons() phase of an ApplicationContext for a non-lazy-init bean.

1. Make sure that your target components (for example, MovieCatalog or CustomerPreferenceDao) are consistently declared by the type that you use for your @Autowired-annotated injection points. Otherwise, injection may fail due to a "no type match found" error at runtime.

1. For XML-defined beans or component classes found via classpath scanning, the container usually knows the concrete type up front. However, for @Bean factory methods, you need to make sure that the declared return type is sufficiently expressive. For components that implement several interfaces or for components potentially referred to by their implementation type, consider declaring the most specific return type on your factory method (at least as specific as required by the injection points referring to your bean).

1. Your target beans can implement the org.springframework.core.Ordered interface or use the @Order or standard @Priority annotation if you want items in the array or list to be sorted in a specific order. Otherwise, their order follows the registration order of the corresponding target bean definitions in the container.

1. You can declare the @Order annotation at the target class level and on @Bean methods, potentially for individual bean definitions (in case of multiple definitions that use the same bean class). @Order values may influence priorities at injection points, but be aware that they do not influence singleton startup order, which is an orthogonal concern determined by dependency relationships and @DependsOn declarations.

1. Note that the standard javax.annotation.Priority annotation is not available at the @Bean level, since it cannot be declared on methods. Its semantics can be modeled through @Order values in combination with @Primary on a single bean for each type.

1. Letting qualifier values select against target bean names, within the type-matching candidates, does not require a @Qualifier annotation at the injection point. If there is no other resolution indicator (such as a qualifier or a primary marker), for a non-unique dependency situation, Spring matches the injection point name (that is, the field name or par"

```javascript
let tips = Array.from(document.getElementsByClassName("fa icon-tip"))
tips.map(tip =>  tip.parentElement.nextSibling.nextSibling.innerText).filter(t => t.indexOf("See the")==-1).filter(t => t.indexOf("in the appendix")==-1).join("\r\n1. ")
```