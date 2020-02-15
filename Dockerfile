FROM maven:3.6.3-jdk-11-slim as builder
WORKDIR /opt/worldrates/
COPY pom.xml .
RUN mvn dependency:go-offline
COPY src src
RUN mvn clean install

FROM openjdk:11.0.6-jre
WORKDIR /opt/worldrates
COPY --from=builder /opt/worldrates/target/worldrates.jar bin/worldrates.jar
ENTRYPOINT ["java", "-jar", "bin/worldrates.jar"]
