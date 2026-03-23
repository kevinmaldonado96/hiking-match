FROM kmaldonado12/hiking-match-parent:1-0 AS builder
WORKDIR /app

COPY . ./routes/

RUN mvn -f routes/pom.xml clean install -DskipTests

FROM openjdk:17.0.1-jdk-slim

WORKDIR /app

COPY --from=builder /app/routes/target/hiking-routes-service-0.0.1-SNAPSHOT.jar app.jar

EXPOSE 8080

ENTRYPOINT ["java", "-jar", "app.jar"]