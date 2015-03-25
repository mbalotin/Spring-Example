package com.configurations;

import com.annotations.Production;
import org.apache.catalina.Context;
import org.apache.catalina.connector.Connector;
import org.apache.tomcat.util.descriptor.web.SecurityCollection;
import org.apache.tomcat.util.descriptor.web.SecurityConstraint;
import org.springframework.boot.context.embedded.EmbeddedServletContainerFactory;
import org.springframework.boot.context.embedded.tomcat.TomcatEmbeddedServletContainerFactory;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

//Forward Http to Https
//Other SecurityConstraints can be added to fine grain the http forward.
@Configuration
@Production
public class TomcatServletConfig {

  @Bean
  public EmbeddedServletContainerFactory tomcatEmbeddedServletContainerFactory() {
    TomcatEmbeddedServletContainerFactory factory = new TomcatEmbeddedServletContainerFactory() {
      @Override
      protected void postProcessContext(Context context) {
        SecurityConstraint securityConstraint = new SecurityConstraint();
        securityConstraint.setUserConstraint("CONFIDENTIAL");
        SecurityCollection collection = new SecurityCollection();
        collection.addPattern("/*");
        securityConstraint.addCollection(collection);
        context.addConstraint(securityConstraint);
      }
    };
    factory.addAdditionalTomcatConnectors(this.createConnection());
    return factory;
  }

  private Connector createConnection() {
    Connector connector = new Connector("org.apache.coyote.http11.Http11NioProtocol");
    connector.setScheme("http");
    connector.setPort(80);
    connector.setRedirectPort(443);
    return connector;
  }

}
