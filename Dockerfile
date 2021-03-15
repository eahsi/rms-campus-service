FROM openjdk:8-jdk-alpine
VOLUME /tmp
ARG JAR_FILE
ARG SPRING_ENV
ENV ACTIVE_SPRING_PROFILES=$SPRING_ENV
EXPOSE 10001
COPY target/${JAR_FILE} app.jar
ENTRYPOINT [ "sh", "-c", "java $JAVA_OPTS -Djava.security.egd=file:/dev/urandom -Dspring.profiles.active=$SPRING_ENV -jar /app.jar" ]