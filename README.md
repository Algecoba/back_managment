# Sistema Gestor de Solicitudes de Publicaciones Científicas
**Universidad Simón Bolívar — Departamento de Publicaciones**

Sistema institucional para gestionar solicitudes de servicios editoriales:
Similitud, Traducción, APC y Reporte de Producción Científica.

---

## Stack tecnológico

| Capa | Tecnología |
|------|------------|
| Backend | Java 21 · Spring Boot 3.3 · Spring Security · JWT |
| Base de datos | Microsoft SQL Server 2022 |
| Contenedores | Docker · Docker Compose |
| Control de versiones | Git · GitHub |

---

## Requisitos previos

- Java 21
- Maven 3.9+
- Docker Desktop
- DBeaver (opcional, para inspeccionar la BD)
- Postman (opcional, para probar endpoints)

---

## Cómo levantar el entorno de desarrollo

### 1. Clonar el repositorio
```bash
git clone https://github.com/Algecoba/back_managment.git
cd back_managment/gestor
```

### 2. Crear el archivo de configuración local

Copia el archivo de ejemplo y completa tus valores:
```bash
cp src/main/resources/application-dev.properties.example \
   src/main/resources/application-dev.properties
```

Edita `application-dev.properties` con tus credenciales locales.

### 3. Levantar SQL Server en Docker
```bash
docker-compose up sqlserver -d
```

Espera 30 segundos a que SQL Server termine de iniciar, luego verifica:
```bash
docker-compose ps
```

### 4. Ejecutar la aplicación
```bash
./mvnw spring-boot:run
```

La aplicación queda disponible en `http://localhost:8080`.

---

## Estructura del proyecto
```
gestor/
├── src/main/java/com/unisimon/gestor/
│   ├── shared/          # Contratos y tipos compartidos entre módulos
│   ├── config/          # Configuración global (seguridad, CORS, Jackson)
│   ├── autenticacion/   # Login con Microsoft + generación de JWT
│   ├── usuarios/        # Gestión de usuarios y roles (RBAC)
│   ├── catalogos/       # Datos institucionales (sede, facultad, programa)
│   ├── investigadores/  # Perfil y vinculaciones del investigador
│   ├── solicitudes/     # Núcleo: ciclo de vida de solicitudes
│   ├── flujos/          # Motor de transiciones de estado
│   ├── idoneidad/       # Test de idoneidad editorial
│   ├── documentos/      # Carga y gestión de archivos
│   ├── auditoria/       # Bitácora inmutable de eventos
│   ├── finanzas/        # Trazabilidad financiera y centros de costo
│   ├── produccion/      # Producción académica consolidada
│   └── reportes/        # Dashboards y métricas por rol
└── src/main/resources/
    ├── application.properties           # Configuración base
    ├── application-dev.properties       # Local (no está en Git)
    └── application-prod.properties      # Producción (usa variables de entorno)
```

---

## Convenciones del equipo

### Commits
Usamos [Conventional Commits](https://www.conventionalcommits.org/):
```
feat(modulo): descripción corta en español
fix(modulo): descripción del bug corregido
refactor(modulo): descripción del cambio
docs(modulo): descripción de la documentación
```

### Ramas
```
main          → código estable, solo merge con PR revisado
dev           → integración continua del equipo
feat/nombre   → funcionalidades nuevas
fix/nombre    → correcciones puntuales
```

---

## Equipo

| Rol | Persona |
|-----|---------|
| Backend | Alejandro Corrales |
| Frontend | Sharon Benítez |