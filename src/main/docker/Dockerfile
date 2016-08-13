# Use clean Ubuntu
FROM ubuntu:14.04

# Install Java.
RUN \
  echo oracle-java8-installer shared/accepted-oracle-license-v1-1 select true | debconf-set-selections && \
  add-apt-repository -y ppa:webupd8team/java && \
  apt-get update && \
  apt-get install -y oracle-java8-installer && \
  rm -rf /var/lib/apt/lists/* && \
  rm -rf /var/cache/oracle-jdk8-installer

# Define commonly used JAVA_HOME variable
ENV JAVA_HOME /usr/lib/jvm/java-8-oracle

# Create the temporary folder
VOLUME /tmp

# Adding all needed files
ADD application.properties application.properties
ADD keystore.jks keystore.jks
ADD spring_example.war spring_example.war

# Add modification time for static content
RUN bash -c 'touch /spring_example.war'

ENTRYPOINT ["java","-server","-Xmx4048m","-jar", "spring_example.war", "--env='production'"]