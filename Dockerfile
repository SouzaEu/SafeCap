# Etapa 1: Build do JAR com Maven
FROM eclipse-temurin:25-jdk AS build
WORKDIR /app
COPY . .
RUN ./mvnw clean package -DskipTests

# Etapa 2: Imagem final com o JAR gerado
FROM eclipse-temurin:25-jdk
WORKDIR /app
COPY --from=build /app/target/safecap-1.0.0.jar app.jar
EXPOSE 8080
ENTRYPOINT ["java", "-jar", "app.jar"]
