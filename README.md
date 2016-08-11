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
 - Spring Mail (with thymeleaf templates, just like webpages, and attachment support).
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
- Caching with ehcache supported (via xml, as it's a lot cleaner than java code).
- Logging with log4j (log to file in production and different properties for development and production environments).
- Spring Test with Rest Assured support (http://www.jayway.com/2014/07/04/integration-testing-a-spring-boot-application/)
- Docker support with automatic image creation via POM.xml (not tested in windows) (https://spring.io/guides/gs/spring-boot-docker/) 
- Your Github markdown README.md file is automatically transformed into a webpage.
- External application.properties file to override any properties you want during runtime.
- Maven local repository example in pom.xml. (Example: https://devcenter.heroku.com/articles/local-maven-dependencies).
- Works out of the box as both JAR and WAR (see below) (http://docs.spring.io/spring-boot/docs/current/reference/htmlsingle/#build-tool-plugins-maven-packaging).
- JAR running on embedded Tomcat. Can be easily switched to Jetty (http://docs.spring.io/spring-boot/docs/current/reference/html/howto-embedded-servlet-containers.html#howto-use-jetty-instead-of-tomcat).
- More examples and explanations are in code comments.

**There is one drawback of using .JAR: since we are using classpath to serve our templates to Thymleaf, we lose the ability to change the .html files during runtime for testing purposes. We need to deploy the files again.**


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
 - Remove the compiler and minifier plugins from pom.xml (both CoffeScript and SCSS)
 - Remove this resource from pom.xml:
 ```
        <resource>
            <directory>${basedir}/src/main/webapp</directory>
            <filtering>false</filtering>
            <excludes>
              <!-- These file are excluded because they are compiled and minified by other plugins -->
              <exclude>**/*.coffee</exclude>
              <exclude>**/*.scss</exclude>
            </excludes>
        </resource>
 ```

####RUN AS WAR:
You can also run this application as a .WAR with an external Tomcat. All you have to do is:

 - 1) Follow this tutorial from spring boot: http://docs.spring.io/spring-boot/docs/current/reference/html/howto-traditional-deployment.html

- 3) Add this line:
 ```
      <exclude>webpages/</exclude>
 ```

    To this resource in pom.xml:
 ```
      <!-- WebApp Resources-->
      <resource>
        <directory>${basedir}/src/main/webapp</directory>
        <filtering>false</filtering>
        <excludes>
          <exclude>webpages/</exclude>  <!-- <--------- THIS LINE HERE -->
          <!-- These file are excluded because they are compiled and minified by other plugins -->
          <exclude>**/*.coffee</exclude>
          <exclude>**/*.scss</exclude>
        </excludes>
      </resource>
```
- 4) Remove this configuration from ThymeleafConfig.java

    ```java
      @Bean
      public ClassLoaderTemplateResolver webpagesClassLoaderResolver() {
        ClassLoaderTemplateResolver webpagesResolver = new ClassLoaderTemplateResolver();
        webpagesResolver.setTemplateMode("HTML5");
        webpagesResolver.setPrefix("webpages/");
        webpagesResolver.setSuffix(".html");

        return webpagesResolver;
      }
    ```

- 5) Add this configuration to ThymeleafConfig.java

    ```java
      @Bean
      public ServletContextTemplateResolver webpagesServletContextResolver() {
        ServletContextTemplateResolver webpagesResolver = new ServletContextTemplateResolver();
        webpagesResolver.setTemplateMode("HTML5");
        webpagesResolver.setPrefix("/webpages/");
        webpagesResolver.setSuffix(".html");

        return webpagesResolver;
      }
    ```

- 6) Add this plugin in pom.xml
 ```
      <!-- Since we copy web files into classpath to make then available for spring and thymeleaf,
      these exclusions avoid duplications of webapp content when packaging a war. -->
      <plugin>
        <groupId>org.apache.maven.plugins</groupId>
        <artifactId>maven-war-plugin</artifactId>
        <configuration>
          <packagingExcludes>mails/,resources/,templates/</packagingExcludes>
        </configuration>
      </plugin>
 ```

####WARNING:
KEYSTORE IS INVALID. PLEASE CREATE ONE TO SUBSTITUTE.
