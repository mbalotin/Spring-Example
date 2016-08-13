Spring Example 1.0
======

####TODO:

- 1) Improve GlobalControllerExceptionHandler with better and more examples
- 2) Http to Https forward in Embedded Jetty



This is a skeleton project using spring.

The main idea behind this is to have a working java spring
web and/or rest application up and running as quick as possible,
seeing as creating a new java project is usually a very time consuming process.

Everything should be completely automatic with the exception of the mysql database creation.
Default database name is "example". It can be change in application.properties.

```
spring.datasource.url = jdbc:mysql://localhost:3306/example
spring.datasource.username = root
spring.datasource.password =
```

So far, the project includes:
- Java 1.8.
- Lots and lots of Spring:
 - Spring Boot (doing most of the heavy work behind curtains).
 - Spring MCV.
 - Spring REST.
 - Spring Data JPA with mysql (can be easily changed to any other, including mongoDB).
 - Spring Security (with basic auth OR login/logout pages).
 - Spring SSL support, with Maven copying your keystore.jks automatically.
 - Spring Mail (with thymeleaf templates, just like webpages, and attachment support).
 - Spring MVC Internationalization.
 - Spring Profiles development and production, with dev as default. To run your WAR in production, simply pass --env="production" to java -jar, or change the spring.profiles.active to ${env:production} in application.properties. Running in production requires root access as only root can deploy to ports 80 and 443.
 - Spring + Compile Time Weave with AspectJ for @Configurable support. (http://docs.spring.io/spring/docs/current/spring-framework-reference/html/aop.html#aop-using-aspectj).
 - Spring actuator (http://docs.spring.io/spring-boot/docs/current-SNAPSHOT/reference/htmlsingle/#production-ready).
 - Spring global rest exceptions (for user-friendly exception handling in rest requests).
- Web views using Thymeleaf templates and pure html (similar to how ruby does it and much cleaner than JSF).
- WebJars support. Let Maven manage your third party JS dependencies. (http://www.webjars.org/).
- Automatic admin creation during boostrap using @PostConstruct and with data from application.properties. (You may create a BootstrapController to add more boostrap actions using @PostConstruct)
- Lombok support. And it works with AspectJ! (http://projectlombok.org/).
- Caching with ehcache supported (via xml, as it's a lot cleaner than java code).
- Logging with log4j (log to file in production and different properties for development and production environments).
- Spring Test with Rest Assured support (http://www.jayway.com/2014/07/04/integration-testing-a-spring-boot-application/)
- Docker support with automatic image creation via POM.xml (not tested in windows) (https://spring.io/guides/gs/spring-boot-docker/) 
- External application.properties file to override any properties you want during runtime.
- Maven local repository example in pom.xml. (Example: https://devcenter.heroku.com/articles/local-maven-dependencies).
- Works out of the box both as a JAR as well as a deployable or executable WAR.
- Running on embedded Tomcat. Can be easily switched to Jetty (http://docs.spring.io/spring-boot/docs/current/reference/html/howto-embedded-servlet-containers.html#howto-use-jetty-instead-of-tomcat).
- More examples and explanations are in code comments.

####RUN AS WAR:
- 1) Change package to war in pom.xml: <packaging>war</packaging>

- 2) Uncomment this line in pom.xml:

 ```
    <dependency>
      <groupId>org.springframework.boot</groupId>
      <artifactId>spring-boot-starter-tomcat</artifactId>
      <scope>provided</scope>
    </dependency>
 ```
	
- 3) Delete this line in pom.xml:

 ```
      <!-- Webapps Resources-->
      <resource>
        <directory>${basedir}/src/main/webapp</directory>
        <filtering>false</filtering>
        <excludes>
          <exclude>resources/**</exclude>
        </excludes>
      </resource>
 ```



####REMOVE WEB CONTENT:
If you don't need a WEB Application:

- 1) Delete the folder webapp/webpages AND webapp/templates. You will then use thymeleaf only for email templates.

- 2) If you want absolutely NOTHING to do with HTML and WEB pages:
 - Delete the webapp folder entirely
 - Remove ThymeleafConfig.java
 - Remove "Security configuration for web content" in SecurityConfig.java and change the security configurations to your liking
 - Delete Controllers' WEB folder
 - Remove LocalizationConfig.java and the resources/i18n folder
 - Remove MailerService.java
 - Remove thymeleaf configurations from application.properties
 - Remove all Thymeleaf AND WebJar dependencies from pom.xml
 - Remove this resource from pom.xml:
 ```
        <resource>
            <directory>${basedir}/src/main/webapp</directory>
            <filtering>false</filtering>
        </resource>
 ```

####WARNING:
KEYSTORE IS INVALID. PLEASE CREATE ONE TO SUBSTITUTE.
