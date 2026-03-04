# שלב 1: בניית הפרויקט (Build)
FROM maven:3.9.6-eclipse-temurin-21 AS build
WORKDIR /app
COPY pom.xml .
COPY src ./src
RUN mvn clean package -DskipTests

# שלב 2: הרצת האפליקציה (Run)
FROM eclipse-temurin:21-jre-alpine
WORKDIR /app
# מעתיק את הקובץ שנבנה בשלב הקודם
COPY --from=build /app/target/*.jar app.jar
ENTRYPOINT ["java", "-jar", "app.jar"]