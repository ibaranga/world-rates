FROM maven:3.6.3-jdk-11
WORKDIR /opt/worldrates
COPY target/worldrates.jar bin/worldrates.jar
ENTRYPOINT ["java", "-jar", "bin/worldrates.jar"]
