FROM openjdk:8-jdk-alpine
VOLUME /tmp
ARG JAR_FILE
COPY ${JAR_FILE} msvideo.jar
RUN bash -c 'touch /msvideo.jar'
EXPOSE 8763
ENTRYPOINT ["java","-Djava.security.egd=file:/dev/./urandom","-jar","/msvideo.jar"]