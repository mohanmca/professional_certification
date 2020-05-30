## How to create anki from this boot mock question file

```
mdanki security_web_security_questions.md security_web_security_questions.apkg --deck "Spring 5 Certification::MdAnki::WebSecurity" --config spring_web_mvc.json
```


## What is securing resources? springSecurityFilterChain

* List of chained security filtered beans


## What is required to secure application

1. To write secured applications, the spring-boot-starter-security module must be added to the classpath. 
1. This module is configured with transitive dependencies needed to secure a web application.

## What is the abstract spring security filter order

* https://docs.spring.io/spring-security/site/docs/3.0.x/reference/security-filter-chain.html

## security-config.xml checklist order of configuration

* The most restrictive must be on top, otherwise a more relaxed rule will apply and some URL will be accessible to users that should not have access to them.

## Sample security-config.xml

```pre
<beans:beans xmlns:="http://www.springframework.org/schema/security"     xmlns:xsi="http://www.w3.org/2001/XMLSchema-instance"     xmlns:beans="http://www.springframework.org/schema/beans"     xsi:schemaLocation="http://www.springframework.org/schema/security     http://www.springframework.org/schema/security/spring-security.xsd     http://www.springframework.org/schema/beans     http://www.springframework.org/schema/beans/spring-beans.xsd">
<http>
    <intercept-url pattern="/persons/list" access="ROLE_USER"/>
    <intercept-url pattern="/auth*" access="IS_AUTHENTICATED_ANONYMOUSLY">
    <intercept-url pattern="/persons/**" access="IS_AUTHENTICATED_FULLY"/>
    <intercept-url pattern="/persons/**" access="IS_AUTHENTICATED_FULLY"/>
    <intercept-url pattern="/persons/newPerson" access="ROLE_ADMIN"/>
    <form-login login-page="/auth" />
    <logout logout-url="/logout" />
</http>
</beans:beans>
```


## access configuration using composite roles SpEL

<beans:beans  ...>        
<http use-expressions="true">
    <intercept-url pattern="/auth*" access="permitAll"/>  
    <intercept-url pattern="/persons/newPerson" access="hasRole('ROLE_ADMIN')"/>
    <tercept-url pattern="/persons/list"  access="hasRole('ROLE_USER')"/>              
    <intercept-url pattern="/persons/**"                   access="hasAnyRole('ROLE_USER, ROLE_ADMIN')" /> 
</http>
</beans:beans>


## Default login url and credential query parameter names

Starting with Spring 4, the default login URL value is /login and the default names for the authentication keys are username and password, thus the login form must be modified as follows.

## Default login customization

<form-login login-page="/auth"    login-processing-url="/my-login"    username-parameter="my-user"    password-parameter="my-password"    ...<!-- other attributes-->/>


## What is the advantage mvcMatchers over antMatcher

1. MvcRequestMatcher implementation was introduced which uses a bean of HandlerMappingIntrospector type to match the path and extract variables. 
1. MvcRequestMatcher bean is an MVC infrastructure bean and is created when the application boots up and is populated with information about all the handler methods declared within the application.
1. This means that Spring MVC and Spring Security must share the application context, because HandlerMappingIntrospector is created and registered by the Spring MVC configuration. 
1. antMatchers("/secured") matches only the exact /secured URL
1. mvcMatchers("/secured") matches /secured as well as /secured/, /secured.html, /secured.xyz


## How method level security works

1. Method SecurityTo apply security to lower layers of an application
1. Spring Security uses AOP. The respective bean is wrapped in a proxy that before calling the target method
1. Method invocation first checks credentials of the user and only calls the underlying method if the user is authorize to it.
1. AccessDecisionManager.isAuthorized(user) is consulted for Role based requirement

## What is required to enable @Secured annotation to secure methods

1. @EnableGlobalMethodSecurity(securedEnabled = true)
1.

```java
    @Configuration
    @EnableWebSecurity
    @EnableGlobalMethodSecurity(securedEnabled = true)
    public class SecurityConfig extends WebSecurityConfigurerAdapter {
    ...
    }
```


## What is alternative to @EnableGlobalMethodSecurity(securedEnabled = true).

```java
1. @EnableGlobalMethodSecurity(jsr250Enabled = true).
1. @RolesAllowed("ROLE_ADMIN")    public List<Detective> findAll() {...}
```



##  What is PrePostEnabled=True

1. @PreAuthorize, @PreFilter, @PostAuthorize and @PostFilter.
2. These annotation can access the method argument and apply SPeL
3. @PreAuthorize("hasPermission(#user, 'admin')")
4. @PreAuthorize("#user.username == authentication.name")

## How to control view based on role in Thymleaf

1. <span sec:authorize="isAuthenticated()">Authenticated: <em sec:authentication="name"></em></span>
1. thymeleaf-extras-springsecurity5 should be enabled, that resolves Thymeleaf security elements.

## How to inject authenticated principle into model

1.  @RequestMapping(value = "/home", method = RequestMethod.GET)    public String home(@AuthenticationPrincipal User activeUser, Model model) {
        model.addAttribute("currentUser", "Logged in: " + activeUser.getUsername() + " with roles: " + activeUser.getAuthorities().toString());
}

## What to do for logout?

1. invalidateHttpSession(true) is called to configure the invalidation of the HTTP session at logout time
