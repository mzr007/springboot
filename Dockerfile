FROM openjdk:8-jdk-alpine
VOLUME /tmp
ARG JAR_FILE
COPY ${JAR_FILE} springboot.jar
EXPOSE 8764
ENTRYPOINT ["java","-jar","/springboot.jar"]