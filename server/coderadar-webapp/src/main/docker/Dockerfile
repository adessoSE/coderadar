FROM java:8

MAINTAINER Matthias Balke <mathias.balke@googlemail.com>

VOLUME /tmp

ADD coderadar-webapp.jar app.jar

RUN bash -c 'touch /app.jar'

EXPOSE 8080

ENTRYPOINT ["java", "-Djava.security.egd=file:/dev/./urandom", "-jar", "/app.jar"]
