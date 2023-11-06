FROM openjdk:17-oracle

VOLUME /tmp

EXPOSE 9000

ARG JAR_FILE=target/dev_log_resource-0.0.1-SNAPSHOT.jar

ADD ${JAR_FILE} app.jar

ENTRYPOINT ["java", "-Djava.security.edg=file:/dev/./urandom", "-jar", "/app.jar"]