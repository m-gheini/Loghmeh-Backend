FROM maven:3.6.1-jdk-8 AS maven-app
WORKDIR /app
COPY src/ /app/src/
COPY pom.xml /app/pom.xml
RUN mvn clean
RUN mvn package
VOLUME /app

# Tomcat config
FROM tomcat:9.0.13-jre8 As tomcat-app
COPY --from=maven-app /app/target/IECA.war /usr/local/tomcat/webapps/IECA.war

CMD ["catalina.sh", "run"]


