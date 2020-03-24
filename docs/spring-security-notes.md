1. This approach uses Maven’s “bill of materials” (BOM) concept and is only available in Maven 2.0.9+. For additional details about how dependencies are resolved, see Maven’s Introduction to the Dependency Mechanism documentation.
1. Some users might be concerned that the storage format is provided for a potential hacker. This is not a concern because the storage of the password does not rely on the algorithm being a secret. Additionally, most formats are easy for an attacker to figure out without the prefix. For example, BCrypt passwords often start with $2a$.
1. XML Configuration requires the NoOpPasswordEncoder bean name to be passwordEncoder.
1. This portion of the documentation discusses the general topic of CSRF protection. Refer to the relevant sections for specific information on CSRF protection for servlet and WebFlux based applications.
1. Both protections require that Safe Methods Must be Idempotent
1. Spring Security does not directly control the creation of the session cookie, so it does not provide support for the SameSite attribute. Spring Session provides support for the SameSite attribute in servlet based applications. Spring Framework’s CookieWebSessionIdResolver provides out of the box support for the SameSite attribute in WebFlux based applications.
1. We could improve the protection and usability of SameSite protection against CSRF attacks by implementing gh-7537.
1. Before you integrate Spring Security’s CSRF protection with multipart file upload, ensure that you can upload without the CSRF protection first. More information about using multipart forms with Spring can be found within the 1.1.11. Multipart Resolver section of the Spring reference and the MultipartFilter javadoc.
1. This portion of the documentation discusses the general topic of Security HTTP Response Headers. Refer to the relevant sections for specific information on Security HTTP Response Headers servlet and WebFlux based applications.
1. Refer to the relevant sections to see how to customize the defaults for both servlet and webflux based applications.
1. Strict-Transport-Security is only added on HTTPS requests
1. Refer to the relevant sections to see how to customize the defaults for both servlet and webflux based applications.
1. Refer to the relevant sections to see how to customize the defaults for both servlet and webflux based applications.
1. There are many additional things one should do (i.e. only display the document in a distinct domain, ensure Content-Type header is set, sanitize the document, etc) when allowing content to be uploaded. However, these measures are out of the scope of what Spring Security provides. It is also important to point out when disabling content sniffing, you must specify the content type in order for things to work properly.
1. Refer to the relevant sections to see how to customize the defaults for both servlet and webflux based applications.
1. In accordance with RFC6797, the HSTS header is only injected into HTTPS responses. In order for the browser to acknowledge the header, the browser must first trust the CA that signed the SSL certificate used to make the connection (not just the SSL certificate).
1. In order to remain passive Spring Security still provides support for HPKP in servlet environments, but for the reasons listed above HPKP is no longer recommended by the security team.
1. Refer to the relevant sections to see how to customize the defaults for both servlet and webflux based applications.
1. Another modern approach to dealing with clickjacking is to use Content Security Policy (CSP).
1. Refer to the relevant sections to see how to customize the defaults for both servlet and webflux based applications.
1. Refer to the relevant sections to see how to configure both servlet and webflux based applications.
1. Content Security Policy is not intended to solve all content injection vulnerabilities. Instead, CSP can be leveraged to help reduce the harm caused by content injection attacks. As a first line of defense, web application authors should validate their input and encode their output.
1. Refer to the relevant sections to see how to configure both servlet and webflux based applications.
1. Refer to the relevant sections to see how to configure both servlet and webflux based applications.
1. Refer to the relevant sections to see how to configure both servlet and webflux based applications.
1. Refer to the relevant sections to see how to configure both servlet based applications.
1. The completed application can be found at samples/boot/helloworld For your convenience, you can download a minimal Spring Boot + Spring Security application by clicking here.
1. If the application does not throw an AccessDeniedException or an AuthenticationException, then ExceptionTranslationFilter does not do anything.
1. The default schema is also exposed as a classpath resource named org/springframework/security/core/userdetails/jdbc/users.ddl.
1. This is only used if the AuthenticationManagerBuilder has not been populated and no AuthenticationProviderBean is defined.
1. Spring Security uses ApacheDS 1.x which is no longer maintained. Unfortunately, ApacheDS 2.x has only released milestone versions with no stable release. Once a stable release of ApacheDS 2.x is available, we will consider updating.
1. If you are running your application behind a proxy, you may also be able to remove the session cookie by configuring the proxy server. For example, using Apache HTTPD’s mod_headers, the following directive would delete the JSESSIONID cookie by expiring it in the response to a logout request (assuming the application is deployed under the path /tutorial):

<LocationMatch "/tutorial/logout">
Header always set Set-Cookie "JSESSIONID=;Path=/tutorial;Expires=Thu, 01 Jan 1970 00:00:00 GMT"
</LocationMatch>
1. Previously the concurrent authentication check was made by the ProviderManager, which could be injected with a ConcurrentSessionController. The latter would check if the user was attempting to exceed the number of permitted sessions. However, this approach required that an HTTP session be created in advance, which is undesirable. In Spring Security 3, the user is first authenticated by the AuthenticationManager and once they are successfully authenticated, a session is created and the check is made whether they are allowed to have another session open.
1. Logouts can of course also be configured using the XML Namespace notation. Please see the documentation for the logout element in the Spring Security XML Namespace section for further details.
1. The annotated methods will only be secured for instances which are defined as Spring beans (in the same application context in which method-security is enabled). If you want to secure instances which are not created by Spring (using the new operator, for example) then you need to use AspectJ.
1. You can enable more than one type of annotation in the same application, but only one type should be used for any interface or class as the behaviour will not be well-defined otherwise. If two annotations are found which apply to a particular method, then only one of them will be applied.
1. OAuth 2.0 Login is implemented by using the Authorization Code Grant, as specified in the OAuth 2.0 Authorization Framework and OpenID Connect Core 1.0.
1. Google’s OAuth 2.0 implementation for authentication conforms to the OpenID Connect 1.0 specification and is OpenID Certified.
1. In order for DefaultLoginPageGeneratingFilter to show links for configured OAuth Clients, the registered ClientRegistrationRepository needs to also implement Iterable<ClientRegistration>. See InMemoryClientRegistrationRepository for reference.
1. OAuth2UserService obtains the user attributes of the end-user (the resource owner) from the UserInfo Endpoint (by using the access token granted to the client during the authorization flow) and returns an AuthenticatedPrincipal in the form of an OAuth2User.
1. For MAC based algorithms such as HS256, HS384 or HS512, the client-secret corresponding to the client-id is used as the symmetric key for signature verification.
1. Client registration information is ultimately stored and owned by the associated Authorization Server. This repository provides the ability to retrieve a sub-set of the primary client registration information, which is stored with the Authorization Server.
1. The default implementation of ClientRegistrationRepository is InMemoryClientRegistrationRepository.
1. Spring Boot 2.x auto-configuration registers an OAuth2AuthorizedClientRepository and/or OAuth2AuthorizedClientService @Bean in the ApplicationContext. However, the application may choose to override and register a custom OAuth2AuthorizedClientRepository or OAuth2AuthorizedClientService @Bean.
1. JdbcOAuth2AuthorizedClientService depends on the table definition described in OAuth 2.0 Client Schema.
1. Please refer to the OAuth 2.0 Authorization Framework for further details on the Authorization Code grant.
1. Please refer to the Authorization Request/Response protocol flow for the Authorization Code grant.
1. The AuthorizationCodeOAuth2AuthorizedClientProvider is an implementation of OAuth2AuthorizedClientProvider for the Authorization Code grant, which also initiates the Authorization Request redirect by the OAuth2AuthorizationRequestRedirectFilter.
1. {baseUrl} resolves to {baseScheme}://{baseHost}{basePort}{basePath}
1. OPTIONAL. Space delimited, case sensitive list of ASCII string values that specifies whether the Authorization Server prompts the End-User for reauthentication and consent. The defined values are: none, login, consent, select_account
1. Please refer to the Access Token Request/Response protocol flow for the Authorization Code grant.
1. Please refer to the OAuth 2.0 Authorization Framework for further details on the Refresh Token.
1. Please refer to the Access Token Request/Response protocol flow for the Refresh Token grant.
1. OAuth2AuthorizedClientProviderBuilder.builder().refreshToken() configures a RefreshTokenOAuth2AuthorizedClientProvider, which is an implementation of an OAuth2AuthorizedClientProvider for the Refresh Token grant.
1. Please refer to the OAuth 2.0 Authorization Framework for further details on the Client Credentials grant.
1. Please refer to the Access Token Request/Response protocol flow for the Client Credentials grant.
1. OAuth2AuthorizedClientProviderBuilder.builder().clientCredentials() configures a ClientCredentialsOAuth2AuthorizedClientProvider, which is an implementation of an OAuth2AuthorizedClientProvider for the Client Credentials grant.
1. HttpServletRequest and HttpServletResponse are both OPTIONAL attributes. If not provided, it will default to ServletRequestAttributes using RequestContextHolder.getRequestAttributes().
1. Please refer to the OAuth 2.0 Authorization Framework for further details on the Resource Owner Password Credentials grant.
1. Please refer to the Access Token Request/Response protocol flow for the Resource Owner Password Credentials grant.
1. OAuth2AuthorizedClientProviderBuilder.builder().password() configures a PasswordOAuth2AuthorizedClientProvider, which is an implementation of an OAuth2AuthorizedClientProvider for the Resource Owner Password Credentials grant.
1. HttpServletRequest and HttpServletResponse are both OPTIONAL attributes. If not provided, it will default to ServletRequestAttributes using RequestContextHolder.getRequestAttributes().
1. Working samples for both JWTs and Opaque Tokens are available in the Spring Security repository.
1. To use the issuer-uri property, it must also be true that one of https://idp.example.com/issuer/.well-known/openid-configuration, https://idp.example.com/.well-known/openid-configuration/issuer, or https://idp.example.com/.well-known/oauth-authorization-server/issuer is a supported endpoint for the authorization server. This endpoint is referred to as a Provider Configuration endpoint or a Authorization Server Metadata endpoint.
1. If the authorization server is down when Resource Server queries it (given appropriate timeouts), then startup will fail.
1. As the authorization server makes available new keys, Spring Security will automatically rotate the keys used to validate the JWT tokens.
1. The JWK Set uri is not standardized, but can typically be found in the authorization server’s documentation
1. This property can also be supplied directly on the DSL.
1. Calling JwtDecoders#fromIssuerLocation is what invokes the Provider Configuration or Authorization Server Metadata endpoint in order to derive the JWK Set Uri.
1. By default, Resource Server configures a clock skew of 30 seconds.
1. When using introspection, the authorization server’s word is the law. If the authorization server responses that the token is valid, then it is.
1. It would be unsafe to simply take any issuer and construct an AuthenticationManager from it. The issuer should be one that the code can verify from a trusted source like a whitelist.
1. To use this approach, make sure that the authorization server is configured to include the claim set as part of the token’s signature. Without this, you have no guarantee that the issuer hasn’t been altered by a bad actor.
1. Unlike the OAuth 2.0 Client filter function, this filter function makes no attempt to renew the token, should it be expired. To obtain this level of support, please use the OAuth 2.0 Client filter.
1. SAML 2.0 Login is implemented by using the Web Browser SSO Profile, as specified in SAML 2 Profiles. Our implementation is currently limited to a simple authentication scheme.
1. The sample explicitly sets cookieHttpOnly=false. This is necessary to allow JavaScript (i.e. AngularJS) to read it. If you do not need the ability to read the cookie with JavaScript directly, it is recommended to omit cookieHttpOnly=false to improve security.
1. The sample explicitly sets cookieHttpOnly=false. This is necessary to allow JavaScript (i.e. AngularJS) to read it. If you do not need the ability to read the cookie with JavaScript directly, it is recommended to omit cookieHttpOnly=false (by using new CookieCsrfTokenRepository() instead) to improve security.
1. More information about using multipart forms with Spring can be found within the 1.1.11. Multipart Resolver section of the Spring reference and the MultipartFilter javadoc.
1. It should be noted that it is typically bad practice to perform so much logic throughout your application. Instead, one should centralize it to reduce any coupling of Spring Security and the Servlet API’s.
1. It is not necessary to catch the ServletException if you want Spring Security to process the failed authentication attempt.
1. As of Spring Security 4.0, @EnableWebMvcSecurity is deprecated. The replacement is @EnableWebSecurity which will determine adding the Spring MVC features based upon the classpath.
1. Spring Security provides the configuration using Spring MVC’s WebMvcConfigurer. This means that if you are using more advanced options, like integrating with WebMvcConfigurationSupport directly, then you will need to manually provide the Spring Security configuration.
1. It is always recommended to provide authorization rules by matching on the HttpServletRequest and method security.

1. Providing authorization rules by matching on HttpServletRequest is good because it happens very early in the code path and helps reduce the attack surface. Method security ensures that if someone has bypassed the web authorization rules, that your application is still secured. This is what is known as Defence in Depth
1. It is important to realize that in order to remove the dependency on Spring Security, it is the consuming application that would create @CurrentUser. This step is not strictly required, but assists in isolating your dependency to Spring Security to a more central location.
1. Associating SecurityContext to Callable’s

1. Spring Security integrates with WebAsyncManager. The SecurityContext that is used to process the Callable is the SecurityContext that exists on the SecurityContextHolder at the time startCallableProcessing is invoked.
1. You can find a complete working sample of WebSocket security at https://github.com/spring-projects/spring-session/tree/master/samples/boot/websocket.
1. Spring Security provides lots of sample applications which demonstrate the use of Spring Security Java Configuration.
1. This is actually how methods like HttpSecurity.authorizeRequests() are implemented.
1. Spring Security provides a sample applications which demonstrates the use of Spring Security Kotlin Configuration.
1. You can use multiple <intercept-url> elements to define different access requirements for different sets of URLs, but they will be evaluated in the order listed and the first match will be used. So you must put the most specific matches at the top. You can also add a method attribute to limit the match to a particular HTTP method (GET, POST, PUT etc.).
1. In previous versions, the sorting took place after the filter instances had been created, during post-processing of the application context. In version 3.0+ the sorting is now done at the bean metadata level, before the classes have been instantiated. This has implications for how you add your own filters to the stack as the entire filter list must be known during the parsing of the <http> element, so the syntax has changed slightly in 3.0.
1. Spring Security hooks into Spring Test support using the WithSecurityContextTestExecutionListener which will ensure our tests are ran with the correct user. It does this by populating the SecurityContextHolder prior to running our tests. If you are using reactive method security, you will also need ReactorContextTestExecutionListener which populates ReactiveSecurityContextHolder. After the test is done, it will clear out the SecurityContextHolder. If you only need Spring Security related support, you can replace @ContextConfiguration with @SecurityTestExecutionListeners.
1. Spring Security’s testing support requires spring-test-4.1.3.RELEASE or greater.
1. The support works by associating the user to the HttpServletRequest. To associate the request to the SecurityContextHolder you need to ensure that the SecurityContextPersistenceFilter is associated with the MockMvc instance. A few ways to do this are:
    1.
    ```text
    Invoking apply(springSecurity())

    Adding Spring Security’s FilterChainProxy to MockMvc

    Manually adding SecurityContextPersistenceFilter to the MockMvc instance may make sense when using MockMvcBuilders.standaloneSetup
    1. This property is invalid for filter-security-metadata-source
    ```
1. This property is invalid for filter-security-metadata-source
1. The sample explicitly sets cookieHttpOnly=false. This is necessary to allow JavaScript (i.e. AngularJS) to read it. If you do not need the ability to read the cookie with JavaScript directly, it is recommended to omit cookieHttpOnly=false (by using new CookieServerCsrfTokenRepository() instead) to improve security.
1. More information about using multipart forms with Spring can be found within the Multipart Data section of the Spring reference.
1. OAuth 2.0 Login is implemented by using the Authorization Code Grant, as specified in the OAuth 2.0 Authorization Framework and OpenID Connect Core 1.0.
1. Google’s OAuth 2.0 implementation for authentication conforms to the OpenID Connect 1.0 specification and is OpenID Certified.
1. Spring Security will query the endpoints one at a time, stopping at the first that gives a 200 response.
1. A complete working example for JWTs is available in the Spring Security repository.
1. To use the issuer-uri property, it must also be true that one of https://idp.example.com/issuer/.well-known/openid-configuration, https://idp.example.com/.well-known/openid-configuration/issuer, or https://idp.example.com/.well-known/oauth-authorization-server/issuer is a supported endpoint for the authorization server. This endpoint is referred to as a Provider Configuration endpoint or a Authorization Server Metadata endpoint.
1. If the authorization server is down when Resource Server queries it (given appropriate timeouts), then startup will fail.
1. As the authorization server makes available new keys, Spring Security will automatically rotate the keys used to validate the JWT tokens.
1. The JWK Set uri is not standardized, but can typically be found in the authorization server’s documentation
1. This property can also be supplied directly on the DSL.
1. Calling ReactiveJwtDecoders#fromIssuerLocation is what invokes the Provider Configuration or Authorization Server Metadata endpoint in order to derive the JWK Set Uri. If the application doesn’t expose a ReactiveJwtDecoder bean, then Spring Boot will expose the above default one.
1. By default, Resource Server configures a clock skew of 30 seconds.
1. When using introspection, the authorization server’s word is the law. If the authorization server responses that the token is valid, then it is.
1. It would be unsafe to simply take any issuer and construct an ReactiveAuthenticationManager from it. The issuer should be one that the code can verify from a trusted source like a whitelist.
1. Unlike the OAuth 2.0 Client filter function, this filter function makes no attempt to renew the token, should it be expired. To obtain this level of support, please use the OAuth 2.0 Client filter.
1. A working example can be found in OAuth 2.0 WebClient WebFlux sample.
1. The following documentation is for use within Reactive environments. For Servlet environments, refer to WebClient for Servlet environments.
1. For this to work the return type of the method must be a org.reactivestreams.Publisher (i.e. Mono/Flux). This is necessary to integrate with Reactor’s Context.
1. Basic Authentication drafts evolved into Simple Authentication and is only supported for backward compatibility. See RSocketSecurity.basicAuthentication(Customizer) for setting it up.

```javascript
let notes = Array.from(document.getElementsByClassName("fa icon-note"))
notes.map(tip =>  tip.parentElement.nextSibling.nextSibling.innerText).filter(t => t.indexOf("See the")==-1).filter(t => t.indexOf("in the appendix")==-1).join("\r\n1. ")
```