1. Note that when using a system like (Request-Header Authentication (Siteminder)), the framework performs no authentication checks at all and it is extremely important that the external system is configured properly and protects all access to the application. If an attacker is able to forge the headers in their original request without this being detected then they could potentially choose any username they wished.
1. The default redirect URI template is {baseUrl}/login/oauth2/code/{registrationId}. The registrationId is a unique identifier for the ClientRegistration.
1. A ClientRegistration can be initially configured using discovery of an OpenID Connect Provider’s Configuration endpoint or an Authorization Server’s Metadata endpoint, by specifying the spring.security.oauth2.client.provider.[providerId].issuer-uri property.
1. The auto-defaulting of client properties works seamlessly here because the registrationId (google) matches the GOOGLE enum (case-insensitive) in CommonOAuth2Provider.
1. As noted earlier, configuring oauth2Login().authorizationEndpoint().baseUri() is optional. However, if you choose to customize it, ensure the link to each OAuth Client matches the authorizationEndpoint().baseUri().
    ```text
    The following line shows an example:
    <a href="/login/oauth2/authorization/google">Google</a>
    ```
1. OAuth 2.0 Login leverages the Authorization Code Grant. Therefore, the authorization credential is the authorization code.
1. OAuth2AuthenticationToken.getAuthorities() is used for authorizing requests, such as in hasRole('USER') or hasRole('ADMIN').
1. id, name, login, and email are attributes returned in GitHub’s UserInfo Response. For detailed information returned from the UserInfo Endpoint, see the API documentation for "Get the authenticated user".
1. If more than one ClientRegistration is configured for OpenID Connect 1.0 Authentication, the JWS algorithm resolver may evaluate the provided ClientRegistration to determine which algorithm to return.
1. OAuth2AuthorizationRequest.Builder.build() constructs the OAuth2AuthorizationRequest.authorizationRequestUri, which represents the complete Authorization Request URI including all query parameters using the application/x-www-form-urlencoded format.
1. The OAuth2AuthorizationRequest is used to correlate and validate the Authorization Response.
1. Spring MVC FormHttpMessageConverter is required as it’s used when sending the OAuth 2.0 Access Token Request.
1. Spring MVC FormHttpMessageConverter is required as it’s used when sending the OAuth 2.0 Access Token Request.
1. Spring MVC FormHttpMessageConverter is required as it’s used when sending the OAuth 2.0 Access Token Request.
1. Spring MVC FormHttpMessageConverter is required as it’s used when sending the OAuth 2.0 Access Token Request.
1. If you are using new MockHttpServletRequest() it currently creates an HTTP method as an empty String "". This is an invalid HTTP method and will be rejected by Spring Security. You can resolve this by replacing it with new MockHttpServletRequest("GET", ""). See SPR_16851 for an issue requesting to improve this.
1. Avoiding filter position conflicts
    ```text
    If you are inserting a custom filter which may occupy the same position as one of the standard filters created by the namespace then it’s important that you don’t include the namespace versions by mistake. Remove any elements which create filters whose functionality you want to replace.

    Note that you can’t replace filters which are created by the use of the <http> element itself - SecurityContextPersistenceFilter, ExceptionTranslationFilter or FilterSecurityInterceptor. Some other filters are added by default, but you can disable them. An AnonymousAuthenticationFilter is added by default and unless you have session-fixation protection disabled, a SessionManagementFilter will also be added to the filter chain.
    ```
1. To use the Spring Security test support, you must include spring-security-test-5.3.0.RELEASE.jar as a dependency of your project.
1. The default redirect URI template is {baseUrl}/login/oauth2/code/{registrationId}. The registrationId is a unique identifier for the ClientRegistration. For our example, the registrationId is google."