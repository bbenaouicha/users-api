FROM maven:3.9.6-amazoncorretto-17-al2023 as builder

COPY src /usr/src/app/src
COPY pom.xml /usr/src/app

RUN mvn -f /usr/src/app/pom.xml clean package

FROM amazoncorretto:17

COPY --from=builder /usr/src/app/target/users-api.jar /usr/app/users-api.jar

ENTRYPOINT ["java", "-jar", "/usr/app/users-api.jar", "--spring.profiles.active=dev"]
