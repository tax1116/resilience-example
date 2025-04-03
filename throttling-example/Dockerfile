FROM amazoncorretto:21
ARG JAR_FILE=build/libs/throttling-example.jar
ARG PROFILE
COPY ${JAR_FILE} app.jar
ENV SPRING_PROFILES_ACTIVE=$PROFILE
ENTRYPOINT ["java","-jar","/app.jar"]