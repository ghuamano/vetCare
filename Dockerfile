# ===============================
#   BUILD STAGE
# ===============================
FROM maven:3.9.8-eclipse-temurin-21 AS build

# Crea directorio de trabajo
WORKDIR /app

# Copia el archivo pom.xml y descarga dependencias
COPY pom.xml .
RUN mvn dependency:go-offline -B

# Copia el código fuente
COPY src ./src

# Compila el proyecto (genera el .jar)
RUN mvn clean package -DskipTests

# ===============================
#   RUNTIME STAGE
# ===============================
FROM eclipse-temurin:21-jdk-alpine

WORKDIR /app

# Copia el .jar generado desde el build stage
COPY --from=build /app/target/*.jar app.jar

# Expone el puerto de la app
EXPOSE 8080

# Comando para ejecutar la app
ENTRYPOINT ["java", "-jar", "app.jar"]