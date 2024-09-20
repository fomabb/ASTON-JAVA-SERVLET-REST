FROM openjdk:23-ea-17-jdk-bullseye
COPY target/Home-work-org.iase24.nikolay.kirilyuk.servlet.jar app/course-aston.jar
ENTRYPOINT java -jar /app/course-aston.jar