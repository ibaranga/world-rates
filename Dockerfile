FROM openjdk:11.0.6-jre
WORKDIR /opt/worldrates
COPY target/worldrates.jar bin/worldrates.jar
ENTRYPOINT ["java", "-jar", "bin/worldrates.jar"]
