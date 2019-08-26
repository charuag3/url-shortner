FROM openjdk:8
ADD target/url-shortner.jar url-shortner.jar
EXPOSE 8080
ENTRYPOINT ["java","-jar","url-shortner.jar"]