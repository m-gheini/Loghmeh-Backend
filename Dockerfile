FROM maven:3.6.1-jdk-8 AS maven

COPY src /app/src/

COPY pom.xml /app/pom.xml

WORKDIR /app/

RUN mvn -f pom.xml clean package

FROM tomcat:9.0.13-jre8

COPY --from=maven /app/target/IECA.war $CATALINA_HOME/webapps/IECA.war

EXPOSE 8080 8080

CMD ["catalina.sh", "run"]


