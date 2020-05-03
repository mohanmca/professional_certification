## How to create anki from this file

```
mdanki spring_web_mvc.md spring_web_mvc.apkg --config spring_web_mvc.json
```

## Spring Web MVC consists of 

1. The Spring Web model-view-controller (MVC) framework is designed around a DispatcherServlet
1. Dispatches requests to handlers, 
  1. with configurable handler mappings
  1. view resolution, 
  1. locale
  1. Timezone
  1. Theme resolution 
  1. Support for uploading files.

## Spring 3.0, Restful support 

  @Controller mechanism also allows you to create RESTful
Web sites and applications, through the @PathVariable annotation and other features.

## Example of Open for extension, but closed for modification in Spring Web MVC

You cannot add advice to final methods when you use Spring MVC. For example, you cannot
add advice to the AbstractController.setSynchronizeOnSession() method.

## How to Register DispaterServlet in Spring Web MVC

```java
public class MyWebApplicationInitializer implements WebApplicationInitializer {

    @Override
    public void onStartup(ServletContext container) {
        ServletRegistration.Dynamic registration = container.addServlet("example", new DispatcherServlet());
        registration.setLoadOnStartup(1);
        registration.addMapping("/example/*");
    }
}
```

<!-- web.xml-->
<web-app>
    <servlet>
        <servlet-name>example</servlet-name>
        <servlet-class>org.springframework.web.servlet.DispatcherServlet</servlet-class>
        <load-on-startup>1</load-on-startup>
    </servlet>

    <servlet-mapping>
        <servlet-name>example</servlet-name>
        <url-pattern>/example/*</url-pattern>
    </servlet-mapping>

</web-app>
```

## How to configure multiple web-application context (hierarchical)

ApplicationContext instances in Spring can be scoped. In the Web MVC framework, each DispatcherServlet has its own WebApplicationContext, which inherits all the beans already defined in the root WebApplicationContext. The root WebApplicationContext should contain all the infrastructure beans that should be shared between your other contexts and Servlet instances. These inherited beans can be overridden in the servlet-specific scope, and you can define new scope-specific beans local to a given Servlet instance.


## How to configure Servlet specific web-application.xml

<web-app>
    <servlet>
        <servlet-name>golfing</servlet-name>
        <servlet-class>org.springframework.web.servlet.DispatcherServlet</servlet-class>
        <load-on-startup>1</load-on-startup>
        <init-param>
            <param-name>contextConfigLocation</param-name>
            <param-value>/WEB-INF/golfing-servlet.xml</param-value>
        </init-param>        
    </servlet>
    <servlet-mapping>
        <servlet-name>golfing</servlet-name>
        <url-pattern>/golfing/*</url-pattern>
    </servlet-mapping>
</web-app>

## What is the defualt-configuration xml searched by Spring

* /WEB-INF/{$servlet-name}-servlet.xml
* /WEB-INF/golfing-servlet.xml

It is also possible to have just one root context for single DispatcherServlet scenarios.

## Single root context in Spring Web MVC

Single root context can be configured by setting an empty contextConfigLocation servlet init parameter for all servlet except root

```xml
<web-app>
    <context-param>
        <param-name>contextConfigLocation</param-name>
        <param-value>/WEB-INF/root-context.xml</param-value>
    </context-param>
    <servlet>
        <servlet-name>dispatcher</servlet-name>
        <servlet-class>org.springframework.web.servlet.DispatcherServlet</servlet-class>
        <init-param>
            <param-name>contextConfigLocation</param-name>
            <param-value></param-value>
        </init-param>
        <load-on-startup>1</load-on-startup>
    </servlet>
    <servlet-mapping>
        <servlet-name>dispatcher</servlet-name>
        <url-pattern>/*</url-pattern>
    </servlet-mapping>
    <listener>
        <listener-class>org.springframework.web.context.ContextLoaderListener</listener-class>
    </listener>
</web-app>
```

## How to get webapplicationContext object reference

static org.springframework.web.servlet.support.RequestContextUtils.findWebApplicationContext(HttpServletRequest request) ==> WebApplicationContext

## When use AbstractAnnotationConfigDispatcherServletInitializer and WebApplicationInitializer?

With the release of the Servlet 3.0 spec it became possible to configure your Servlet Container with (almost) no xml. For this there is the ServletContainerInitializer in the Servlet specification. In this class you can register filters, listeners, servlets etc. as you would traditionally do in a web.xml.

Spring provides a an implementation the SpringServletContainerInitializer which knows how to handle WebApplicationInitializer classes. Spring also provides a couple of base classes to extend to make your life easier the AbstractAnnotationConfigDispatcherServletInitializer is one of those. It registers a ContextLoaderlistener (optionally) and a DispatcherServlet and allows you to easily add configuration classes to load for both classes and to apply filters to the DispatcherServlet and to provide the servlet mapping.

The WebMvcConfigurerAdapter is for configuring Spring MVC, the replacement of the xml file loaded by the DispatcherServlet for configuring Spring MVC. The WebMvcConfigurerAdapter should be used for a @Configuration class.

```java
@Configuration
@EnableWebMvc
public class WebConfiguration 
    extends WebMvcConfigurerAdapter implements WebApplicationInitializer
{ ... }
I wouldn't recommend mixing those as they are basically 2 different concerns. The first is for configuring the servlet container, the latter for configuring Spring MVC.

You would want to split those into 2 classes.

For the configuration.

@Configuration
@EnableWebMvc
public class WebConfiguration extends WebMvcConfigurerAdapter { ... }
For bootstrapping the application.

public class MyWebApplicationInitializer
    extends AbstractAnnotationConfigDispatcherServletInitializer
{

    protected Class<?>[] getRootConfigClasses() {
        return new Class[] {RootConfig.class};
    }

    protected Class<?>[] getServletConfigClasses()  {
        return new Class[] {WebConfiguration .class};
    }

    protected String[] getServletMappings() {
        return new String[] {"/"};
    }

}
```

An added advantage is that you now can use the convenience classes provided by Spring instead of manually configuring the DispatcherServlet and/or ContextLoaderListener.

## How to configure WebApplicationInitializer xml vs Java  (Configure servlet container)

```xml
 <servlet>
   <servlet-name>dispatcher</servlet-name>
   <servlet-class>
     org.springframework.web.servlet.DispatcherServlet
   </servlet-class>
   <init-param>
     <param-name>contextConfigLocation</param-name>
     <param-value>/WEB-INF/spring/dispatcher-config.xml</param-value>
   </init-param>
   <load-on-startup>1</load-on-startup>
 </servlet>

 <servlet-mapping>
   <servlet-name>dispatcher</servlet-name>
   <url-pattern>/</url-pattern>
 </servlet-mapping>
```

```java
 public class MyWebAppInitializer implements WebApplicationInitializer {

    @Override
    public void onStartup(ServletContext container) {
      XmlWebApplicationContext appContext = new XmlWebApplicationContext();
      appContext.setConfigLocation("/WEB-INF/spring/dispatcher-config.xml");

      ServletRegistration.Dynamic dispatcher =
        container.addServlet("dispatcher", new DispatcherServlet(appContext));
      dispatcher.setLoadOnStartup(1);
      dispatcher.addMapping("/");
    }

 }
 ```

##A 100% code-based approach to configuration for WebApplicationInitializer (Configure servlet container)

```java
  public class MyWebAppInitializer implements WebApplicationInitializer {

    @Override
    public void onStartup(ServletContext container) {
      // Create the 'root' Spring application context
      AnnotationConfigWebApplicationContext rootContext =
        new AnnotationConfigWebApplicationContext();
      rootContext.register(AppConfig.class);

      // Manage the lifecycle of the root application context
      container.addListener(new ContextLoaderListener(rootContext));

      // Create the dispatcher servlet's Spring application context
      AnnotationConfigWebApplicationContext dispatcherContext =
        new AnnotationConfigWebApplicationContext();
      dispatcherContext.register(DispatcherConfig.class);

      // Register and map the dispatcher servlet
      ServletRegistration.Dynamic dispatcher =
        container.addServlet("dispatcher", new DispatcherServlet(dispatcherContext));
      dispatcher.setLoadOnStartup(1);
      dispatcher.addMapping("/");
    }

 }
 ```


## How to configure Spring-web-mvc


For the configuration.

```java
@Configuration
@EnableWebMvc
public class WebConfiguration extends WebMvcConfigurerAdapter { ... }
```

## What is the relationship between SpringWebMVC_Configuration class and ServletInitializer

* SpringWebMVCConfiguration is refered inside ServletInitializer
* It is refered as RootConfigClasses or getServletConfigClasses

```java
public class MyWebApplicationInitializer
    extends AbstractAnnotationConfigDispatcherServletInitializer
{

    protected Class<?>[] getRootConfigClasses() {
        return new Class[] {RootConfig.class};
    }

    protected Class<?>[] getServletConfigClasses()  {
        return new Class[] {WebConfiguration .class};
    }

    protected String[] getServletMappings() {
        return new String[] {"/"};
    }

}
```


## What are all Special Bean Types In the WebApplicationContext?

HandlerMapping	Maps incoming requests to handlers and a list of pre- and post-processors (handler interceptors) based on some criteria the details of which vary by HandlerMapping implementation. The most popular implementation supports annotated controllers but other implementations exists as well.

HandlerAdapter	Helps the DispatcherServlet to invoke a handler mapped to a request regardless of the handler is actually invoked. For example, invoking an annotated controller requires resolving various annotations. Thus the main purpose of a HandlerAdapter is to shield the DispatcherServlet from such details.

HandlerExceptionResolver	Maps exceptions to views also allowing for more complex exception handling code.

ViewResolver	Resolves logical String-based view names to actual View types.

LocaleResolver & LocaleContextResolver	Resolves the locale a client is using and possibly their time zone, in order to be able to offer internationalized views

ThemeResolver	Resolves themes your web application can use, for example, to offer personalized layouts

MultipartResolver	Parses multi-part requests for example to support processing file uploads from HTML forms.

FlashMapManager	Stores and retrieves the "input" and the "output" FlashMap that can be used to pass attributes from one request to another, usually across a redirect.


## What is Handler and preprocessor does in spring-webmvc

After request is decided either as request or MultipartHttpServletRequest, An appropriate handler is searched for. If a handler is found, the execution chain associated with the handler (preprocessors, postprocessors, and controllers) is executed in order to prepare a model or rendering.

If a model is returned, the view is rendered. If no model is returned, (may be due to a preprocessor or postprocessor intercepting the request, perhaps for security reasons), no view is rendered, because the request could already have been fulfilled.


## @Controller and AOP Proxying (Class based proxying)

In some cases a controller may need to be decorated with an AOP proxy at runtime. One example is if you choose to have @Transactional annotations directly on the controller. When this is the case, for controllers specifically, we recommend using class-based proxying. This is typically the default choice with controllers. However if a controller must implement an interface that is not a Spring Context callback (e.g. InitializingBean, *Aware, etc), you may need to explicitly configure class-based proxying. For example with <tx:annotation-driven/>, change to <tx:annotation-driven proxy-target-class="true"/>.


## When Spring MVC needs to find the matching URI template variable by name. 

* To process the @PathVariable annotation, Spring MVC needs to find the matching URI template variable by name. You can specify it in the annotation:
* Or if the URI template variable name matches the method argument name you can omit that detail. As long as your code is compiled with debugging information or the -parameters compiler flag on Java 8, Spring MVC will match the method argument name to the URI template variable name:

```java
@GetMapping("/owners/{ownerId}")
public String findOwner(@PathVariable("ownerId") String theOwner, Model model) {
    // implementation omitted
}

@GetMapping("/owners/{ownerId}")
public String findOwner(@PathVariable String ownerId, Model model) {
    // implementation omitted
}
```

## Produces and consumes

1. @GetMapping(path = "/pets/{petId}", produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
1. @PostMapping(path = "/pets", consumes = "application/json")
1. Although you can match to Content-Type and Accept header values using media type wild cards (for example "content-type=text/*" will match to "text/plain" and "text/html"), it is recommended to use the consumes and produces conditions respectively instead. They are intended specifically for that purpose.


## Example controller with usage of Model and returning view name

```java
@Controller
public class HelloWorldController {

    @RequestMapping("/helloWorld")
    public String helloWorld(Model model) {
        model.addAttribute("message", "Hello World!");
        return "helloWorld";
    }
}
```


## How to URI Template Patterns with Regular Expressions

```java
@RequestMapping("/spring-web/{symbolicName:[a-z-]+}-{version:\\d\\.\\d\\.\\d}{extension:\\.[a-z]+}")
public void handle(@PathVariable String version, @PathVariable String extension) {
    // ...
}
```

## Invalid ordering of BindingResult and @ModelAttribute. 

@PostMapping
public String processSubmit(@ModelAttribute("pet") Pet pet, Model model, BindingResult result) { ... }
Note, that there is a Model parameter in between Pet and BindingResult. To get this working you have to reorder the parameters as follows:

@PostMapping
public String processSubmit(@ModelAttribute("pet") Pet pet, BindingResult result, Model model) { ... }



## HTTP HEAD and HTTP OPTIONS

@RequestMapping methods mapped to "GET" are also implicitly mapped to "HEAD"

## Supported method argument types
org.springframework.validation.Errors / org.springframework.validation.BindingResult validation results for a preceding command or form object (the immediately preceding method argument).

## HttpEntity vs @RequestBody and @ResponseBody. 

The HttpEntity is similar to @RequestBody and @ResponseBody. Besides getting access to the request and response body, HttpEntity (and the response-specific subclass ResponseEntity) also allows access to the request and response headers, like so:

```java
@RequestMapping("/something")
public ResponseEntity<String> handle(HttpEntity<byte[]> requestEntity) throws UnsupportedEncodingException {
    String requestHeader = requestEntity.getHeaders().getFirst("MyRequestHeader"));
    byte[] requestBody = requestEntity.getBody();

    // do something with request header and body

    HttpHeaders responseHeaders = new HttpHeaders();
    responseHeaders.set("MyResponseHeader", "MyValue");
    return new ResponseEntity<String>("Hello World", responseHeaders, HttpStatus.CREATED);
}
```

----

## Model attribute on method

```java
// Add one attribute
// The return value of the method is added to the model under the name "account"
// You can customize the name via @ModelAttribute("myAccount")

@ModelAttribute
public Account addAccount(@RequestParam String number) {
    return accountManager.findAccount(number);
}

// Add multiple attributes

@ModelAttribute
public void populateModel(@RequestParam String number, Model model) {
    model.addAttribute(accountManager.findAccount(number));
    // add more ...
}
```

## What is the role of Controller

Controller is a class that is responsible for 
1. preparing a model Map with data to be displayed by the view 
1. Choosing the right view itself
1. @Controller is a specialization of @Component
It can also directly write into the response stream by using @ResponseBody annotation and complete the request.

## What is the role of RestController

1. You don't need view, just data in json/xml
1. @RestController is a specialization of @Controller annotation.
1. you don't need to use @ResponseBody on every handler method once you annotate the class with @RestController as shown below:


## What is matrix variable

```java
// GET /pets/42;q=11;r=22

@GetMapping("/pets/{petId}")
public void findPet(@PathVariable String petId, @MatrixVariable int q) {

    // petId == 42
    // q == 11

}

// GET /owners/42;q=11/pets/21;q=22

@GetMapping("/owners/{ownerId}/pets/{petId}")
public void findPet(
        @MatrixVariable(name="q", pathVar="ownerId") int q1,
        @MatrixVariable(name="q", pathVar="petId") int q2) {

    // q1 == 11
    // q2 == 22

}


//A matrix variable may be defined as optional and a default value specified:

// GET /pets/42

@GetMapping("/pets/{petId}")
public void findPet(@MatrixVariable(required=false, defaultValue="1") int q) {

    // q == 1

}


//All matrix variables may be obtained in a Map:

// GET /owners/42;q=11;r=12/pets/21;q=22;s=23

@GetMapping("/owners/{ownerId}/pets/{petId}")
public void findPet(
        @MatrixVariable MultiValueMap<String, String> matrixVars,
        @MatrixVariable(pathVar="petId"") MultiValueMap<String, String> petMatrixVars) {

    // matrixVars: ["q" : [11,22], "r" : 12, "s" : 23]
    // petMatrixVars: ["q" : 11, "s" : 23]

}
```

## How to modelAndView

```java

@Controller
public class ContentController {

    private List<SampleContent> contentList = new ArrayList<SampleContent>();

    @GetMapping("/content")
    public ModelAndView getContent() {
        ModelAndView mav = new ModelAndView();
        mav.setViewName("content");
        mav.addObject("sampleContentList", contentList);
        return mav;
    }

}

public class DisplayShoppingCartController implements Controller {

    public ModelAndView handleRequest(HttpServletRequest request, HttpServletResponse response) {

        List cartItems = // get a List of CartItem objects
        User user = // get the User doing the shopping

        ModelAndView mav = new ModelAndView("displayShoppingCart"); <-- the logical view name

        mav.addObject(cartItems); <-- look ma, no name, just the object
        mav.addObject(user); <-- and again ma!

        return mav;
    }
}
```



## How to control Cache-Control HTTP header

```java

@Configuration
@EnableWebMvc
public class WebConfig extends WebMvcConfigurerAdapter {

    @Override
    public void addResourceHandlers(ResourceHandlerRegistry registry) {
        registry.addResourceHandler("/resources/**")
                .addResourceLocations("/public-resources/")
                .setCacheControl(CacheControl.maxAge(1, TimeUnit.HOURS).cachePublic());
    }

}

// Cache for an hour - "Cache-Control: max-age=3600"
CacheControl ccCacheOneHour = CacheControl.maxAge(1, TimeUnit.HOURS);

// Prevent caching - "Cache-Control: no-store"
CacheControl ccNoStore = CacheControl.noStore();

// Cache for ten days in public and private caches,
// public caches should not transform the response
// "Cache-Control: max-age=864000, public, no-transform"
CacheControl ccCustom = CacheControl.maxAge(10, TimeUnit.DAYS)
                                    .noTransform().cachePublic();
```


### How to configure Interceptors

```java
@Configuration
@EnableWebMvc
public class WebConfig extends WebMvcConfigurerAdapter {

    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        registry.addInterceptor(new LocaleInterceptor());
        registry.addInterceptor(new ThemeInterceptor()).addPathPatterns("/**").excludePathPatterns("/admin/**");
        registry.addInterceptor(new SecurityInterceptor()).addPathPatterns("/secure/*");
    }

}
```

## How to use view controller

```java
//An example of forwarding a request for "/" to a view called "home" in Java:

@Configuration
@EnableWebMvc
public class WebConfig extends WebMvcConfigurerAdapter {

    @Override
    public void addViewControllers(ViewControllerRegistry registry) {
        registry.addViewController("/").setViewName("home");
    }
}
```