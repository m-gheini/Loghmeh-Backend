FROM maven:3.6.1-jdk-8 AS maven
COPY src /usr/src/app/src
COPY pom.xml /usr/src/app
#RUN mvn clean
#RUN mvn package
#VOLUME /app
RUN mvn -f /usr/src/app/pom.xml clean package
FROM tomcat:9.0.13-jre8

COPY --from=maven /target/IECA.war /usr/local/tomcat/webapps/IECA.war

EXPOSE 8080

CMD ["catalina.sh", "run"]


