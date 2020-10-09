FROM openjdk:11.0.8-jre-buster
ARG JAR_FILE=coderadar-app/build/**/*.jar
COPY ${JAR_FILE} coderadar-app-1.0.0.local.jar
ENTRYPOINT ["java","-jar","/coderadar-app-1.0.0.local.jar"]
