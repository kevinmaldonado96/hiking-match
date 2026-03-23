FROM kmaldonado12/hiking-match-parent:1-0 AS builder
WORKDIR /app

COPY . ./match/

RUN mvn -f match/pom.xml clean install -DskipTests

FROM openjdk:17.0.1-jdk-slim

WORKDIR /app

COPY --from=builder /app/match/target/hiking-match-service-0.0.1-SNAPSHOT.jar app.jar

EXPOSE 8081

ENTRYPOINT ["java", "-jar", "app.jar"]