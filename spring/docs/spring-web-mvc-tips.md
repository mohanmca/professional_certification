1. If an application context hierarchy is not required, applications can return all configuration through getRootConfigClasses() and null from getServletConfigClasses().
1. If an application context hierarchy is not required, applications may configure a “root” context only and leave the contextConfigLocation Servlet parameter empty.
1. The Servlet API does not provide a way to create error page mappings in Java. You can, however, use both a WebApplicationInitializer and a minimal web.xml.
1. Guides and tutorials on spring.io use the annotation-based programming model described in this section.
1. MediaType provides constants for commonly used media types, such as APPLICATION_JSON_VALUE and APPLICATION_XML_VALUE.
1. MediaType provides constants for commonly used media types, such as APPLICATION_JSON_VALUE and APPLICATION_XML_VALUE.
1. You can match Content-Type and Accept with the headers condition, but it is better to use consumes and produces instead.
1. Built-in support is available for converting a comma-separated string into an array or collection of strings or other types known to the type conversion system. For example, a method parameter annotated with @RequestHeader("Accept") can be of type String but also String[] or List<String>.
1. Consider ";", which is legal in a path but has reserved meaning. The first option replaces ";" with "%3B" in URI variables but not in the URI template. By contrast, the second option never replaces ";", since it is a legal character in a path.
1. Spring MVC supports Reactor and RxJava through the ReactiveAdapterRegistry from spring-core, which lets it adapt from multiple reactive libraries.
1. To learn more from the source or make advanced customizations, check the code behind:
  CorsConfiguration, CorsProcessor, DefaultCorsProcessor, AbstractHandlerMapping
1. The basic rule for integrating any other script engine is that it must implement the ScriptEngine and Invocable interfaces.
1. If you need to have a LocalValidatorFactoryBean injected somewhere, create a bean and mark it with @Primary in order to avoid conflict with the one declared in the MVC configuration.
1. Spring provides a WebSocketHandlerDecorator base class that you can use to decorate a WebSocketHandler with additional behavior. Logging and exception handling implementations are provided and added by default when using the WebSocket Java configuration or XML namespace. The ExceptionWebSocketHandlerDecorator catches all uncaught exceptions that arise from any WebSocketHandler method and closes the WebSocket session with status 1011, which indicates a server error.