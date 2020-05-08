# Spring core questions

## How to create anki from this spring core question file

```
mdanki spring-core-questions.md spring-core-questions.apkg  --deck "Spring 5 Certification::MdAnki::Core" --config spring_web_mvc.json
```

## ApplicationContext implementations __________ create and configure all singleton beans as part of the initialization process

1. Singletons are eagerly created.
1. Singletons eager initialization can be prevented by marking the bean definition as lazy-initialized.
1. When a lazy-initialized bean is a dependency of a singleton bean that is not lazy-initialized, the ApplicationContext creates the lazy-initialized bean at startup, because it must satisfy the singleton’s dependencies.

## Who should manage the lifecycle of prototype beans


1. Spring does not manage the complete lifecycle of a prototype bean: the container instantiates, configures, and otherwise assembles a prototype object, and hands it to the client, with no further record of that prototype instance. 
1. Although initialization lifecycle callback methods are called on all objects regardless of scope, in the case of prototypes, configured destruction lifecycle callbacks are not called. 
1. The client code must clean up prototype-scoped objects and release expensive resources that the prototype bean(s) are holding.


## @ContextConfiguration without explicit attributes are allowed

package com.example;

@RunWith(SpringJUnit4ClassRunner.class)
// ApplicationContext will be loaded from
// "classpath:/com/example/MyTest-context.xml"
@ContextConfiguration
public class MyTest {
    // class body...
}


## AbstractJUnit4SpringContextTests and AbstractTestNGSpringContextTests implement ApplicationContextAware 

1. Therefore provide access to the ApplicationContext automatically.


## PersistenceUnit  vs PersistenceContext 

1. javax.Persistence.PersistenceUnit injects an EntityManagerFactory
1. javax.persistence.PersistenceContext injects an EntityManager.
1. It's generally better to use PersistenceContext unless you really need to manage the EntityManager lifecycle manually.

## Opt-in Autoconfiguration


1. You need to opt-in to auto-configuration by adding the @EnableAutoConfiguration or @SpringBootApplication annotations to one of your @Configuration classes.
1. You should only ever add one @SpringBootApplication or @EnableAutoConfiguration annotation. We generally recommend that you add one or the other to your primary @Configuration class only.

## Default Transaction Propagation type

1. Propagation Required

## Spring AOP uses AspectJ style annotation

1. Spring borrwed AspectJ Annotation, and favours them instead of xml

## Transcation rollback attributes

1. rollbackFor
1. rollbackForClassName
1. noRollbackFor (throwables)
1. NoRollbackForClassname

