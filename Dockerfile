# ── Etapa 1: compilar ───────────────────────────────────────────
FROM eclipse-temurin:17-jdk-alpine AS builder

WORKDIR /app

COPY gestor/pom.xml ./pom.xml
COPY gestor/.mvn/ .mvn/
COPY gestor/mvnw .
RUN ./mvnw dependency:go-offline -B

COPY gestor/src/ src/
RUN ./mvnw clean package -DskipTests -B

# ── Etapa 2: imagen de producción ───────────────────────────────
# Solo JRE — imagen más liviana sin Maven ni código fuente
FROM eclipse-temurin:17-jre-alpine

WORKDIR /app

COPY --from=builder /app/target/*.jar app.jar

EXPOSE 8080

ENTRYPOINT ["java", "-jar", "app.jar"]