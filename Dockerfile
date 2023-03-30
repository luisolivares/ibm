FROM openjdk:17-alpine
ADD target/*.jar app-rest-ibm.jar
ENTRYPOINT ["java","-jar","app-rest-ibm.jar"]