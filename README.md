Spring Example 1.0
======

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
 - Spring Mail (with thymeleaf templates, just like webpages and attachment support).
 - Spring MVC Internationalization.
 - Spring Profiles development and production, with dev as default. To run your JAR in production, simply pass --env="production" to java -jar, or change the spring.profiles.active to ${env:production} in application.properties. Running in production requires root access as only root can deploy to ports 80 and 443.
 - Spring + Compile Time Weave with AspectJ for @Configurable support. (http://docs.spring.io/spring/docs/current/spring-framework-reference/html/aop.html#aop-using-aspectj).
 - Spring actuator (http://docs.spring.io/spring-boot/docs/current-SNAPSHOT/reference/htmlsingle/#production-ready).
 - Spring global rest exceptions (for user-friendly exception handling in rest requests).
- Web views using Thymeleaf templates and pure html (similar to how ruby does it and much cleaner than JSF).
- SCSS and CoffeeScript automatically supported, compiled and minified. Can be mixed with normal .css and .js files.
- WebJars support. Let Maven manage your third party JS dependencies. (http://www.webjars.org/).
- Automatic admin creation during boostrap using @PostConstruct and with data from application.properties. (You may create a BootstrapController to add more boostrap actions using @PostConstruct)
- Lombok support. And it works with AspectJ! (http://projectlombok.org/).
- Caching with ehcache supported (by xml, as it's a lot cleaner than java code).
- Logging with log4j (log to file in production and different properties for development and production environments).
- Your Github markdown README.md file is automatically transformed into a webpage.
- External application.properties file to override any properties you want during runtime.
- Maven local repository example in pom.xml. (Example: https://devcenter.heroku.com/articles/local-maven-dependencies).
- Works out of the box as both JAR and WAR.
- JAr running on embedded Tomcat. Can be easily switched to Jetty (http://docs.spring.io/spring-boot/docs/current/reference/html/howto-embedded-servlet-containers.html#howto-use-jetty-instead-of-tomcat).
- More examples and explanations are in code comments.

####WARNING:
There is one drawback of this project. Since we are using classpath to serve our templates to Thymleaf, we lose the ability to change the .html files during runtime for testing purposes. We need to deploy the files again.

####WARNING 2:
KEYSTORE IS INVALID. PLEASE CREATE ONE TO SUBSTITUTE.

