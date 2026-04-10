FROM kmaldonado12/hiking-match-parent:1-0 AS builder
WORKDIR /app

ARG GITHUB_USERNAME
ARG GITHUB_TOKEN

RUN mkdir -p /root/.m2 && \
    printf '<settings><servers><server><id>common-security</id><username>%s</username><password>%s</password></server></servers></settings>' \
    "$GITHUB_USERNAME" "$GITHUB_TOKEN" > /root/.m2/settings.xml

COPY . ./routes/

RUN mvn -f routes/pom.xml clean install -DskipTests

FROM openjdk:17.0.1-jdk-slim

WORKDIR /app

COPY --from=builder /app/routes/target/hiking-routes-service-0.0.1-SNAPSHOT.jar app.jar

EXPOSE 8080

ENTRYPOINT ["java", "-jar", "app.jar"]