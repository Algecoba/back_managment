# CLAUDE.md

# Contexto del proyecto para Claude

## ¿Qué es este sistema?
Sistema institucional de gestión de solicitudes de publicaciones científicas
para la Universidad Simón Bolívar. Maneja cuatro tipos de solicitudes:
Similitud (SIM), Traducción (TRA), APC y Reporte de Producción (REP).

## Stack
- Java 21 + Spring Boot 3.3 (monolito modular)
- Microsoft SQL Server 2022
- Spring Security + JWT
- Docker + Docker Compose
- Maven

## Convenciones obligatorias
- Nombres de clases, métodos y variables en español
- Términos técnicos de framework en inglés (Repository, Service, Controller)
- Comentarios solo donde explican lógica de negocio no obvia
- DTOs separados para entrada y salida
- Entidades JPA nunca expuestas directamente en endpoints
- Excepciones de negocio tipadas por módulo

## Módulos y sus responsabilidades
- shared: contratos y tipos compartidos (RespuestaDto, ExcepcionNegocio)
- config: configuración global de Spring (seguridad, CORS, Jackson)
- autenticacion: login Microsoft + JWT
- usuarios: CRUD usuarios + roles RBAC
- catalogos: sedes, facultades, programas (datos institucionales)
- investigadores: perfil + vinculaciones a grupos y centros
- solicitudes: ciclo de vida completo + dossier + snapshots
- flujos: motor de transiciones de estado por tipo de solicitud
- idoneidad: test de revista para detectar depredadoras
- documentos: carga, versionado y descarga de archivos
- auditoria: bitácora append-only de todos los eventos
- finanzas: centros de costo y trazabilidad financiera
- produccion: consolidación de producción académica
- reportes: dashboards y métricas por rol

## Reglas de negocio críticas
- Un dossier agrupa todas las solicitudes del mismo artículo (anti-duplicados)
- Los snapshots congelan los datos del formulario al momento del envío
- Los eventos de auditoría son inmutables: nunca se actualizan ni eliminan
- Las transiciones de estado las valida TransicionEstadoValidador, no el controlador
- El centro de costo lo asigna el sistema según facultad+tipo, nunca el investigador

## Paquete raíz
com.unisimon.gestor

## Base de datos
Las tablas ya están definidas en DBML. El esquema vive en la BD compartida
con otro sistema institucional. Usar ddl-auto=validate en producción.
