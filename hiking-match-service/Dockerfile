FROM kmaldonado12/hiking-match-parent:1-0 AS builder
WORKDIR /app

ARG GITHUB_USERNAME
ARG GITHUB_TOKEN

RUN mkdir -p /root/.m2 && \
    printf '<settings><servers><server><id>common-security</id><username>%s</username><password>%s</password></server><server><id>common-email-sender</id><username>%s</username><password>%s</password></server></servers></settings>' \
    "$GITHUB_USERNAME" "$GITHUB_TOKEN" "$GITHUB_USERNAME" "$GITHUB_TOKEN" > /root/.m2/settings.xml

COPY . ./match/

RUN mvn -f match/pom.xml clean install -DskipTests

FROM openjdk:17.0.1-jdk-slim

WORKDIR /app

COPY --from=builder /app/match/target/hiking-match-service-0.0.1-SNAPSHOT.jar app.jar

EXPOSE 8081

ENTRYPOINT ["java", "-jar", "app.jar"]