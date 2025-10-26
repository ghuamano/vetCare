# 🐾 VetCare - Sistema de Gestión Veterinaria

## 📋 Descripción
Sistema completo de gestión para clínicas veterinarias desarrollado con Spring Boot 3.2, Java 21 y MySQL.

## 🛠️ Tecnologías Utilizadas
- **Java 21**
- **Spring Boot 3.2.0**
- **Spring Data JPA**
- **MySQL 8.0+**
- **Lombok**
- **Maven**

## 📁 Estructura del Proyecto
```
vetcare/
├── src/main/java/com/vetcare/
│   ├── VetcareApplication.java
│   ├── config/
│   │   ├── CorsConfig.java
│   │   └── DataInitializer.java
│   ├── controllers/
│   │   ├── OwnerController.java
│   │   ├── PetController.java
│   │   ├── PetTypeController.java
│   │   ├── ClinicController.java
│   │   ├── SpecialtyController.java
│   │   ├── VeterinarianController.java
│   │   └── VisitController.java
│   ├── models/
│   │   ├── Owner.java
│   │   ├── Pet.java
│   │   ├── PetType.java
│   │   ├── Clinic.java
│   │   ├── Specialty.java
│   │   ├── Veterinarian.java
│   │   └── Visit.java
│   ├── repositories/
│   │   ├── OwnerRepository.java
│   │   ├── PetRepository.java
│   │   ├── PetTypeRepository.java
│   │   ├── ClinicRepository.java
│   │   ├── SpecialtyRepository.java
│   │   ├── VeterinarianRepository.java
│   │   └── VisitRepository.java
│   ├── services/
│   │   ├── OwnerService.java
│   │   ├── PetService.java
│   │   ├── PetTypeService.java
│   │   ├── ClinicService.java
│   │   ├── SpecialtyService.java
│   │   ├── VeterinarianService.java
│   │   └── VisitService.java
│   └── exceptions/
│       ├── ResourceNotFoundException.java
│       ├── DuplicateResourceException.java
│       ├── GlobalExceptionHandler.java
│       └── ErrorResponse.java
└── src/main/resources/
    └── application.properties
```

## 🚀 Instalación y Configuración

### 1. Prerrequisitos
- Java 21 instalado
- Maven 3.8+
- MySQL 8.0+ instalado y ejecutándose
- IDE (IntelliJ IDEA, Eclipse, VS Code con extensiones Java)

### 2. Configurar Base de Datos MySQL

Crear la base de datos (o dejar que Spring la cree automáticamente):

```sql
CREATE DATABASE vetcare_db;
CREATE USER 'vetcare_user'@'localhost' IDENTIFIED BY 'vetcare_password';
GRANT ALL PRIVILEGES ON vetcare_db.* TO 'vetcare_user'@'localhost';
FLUSH PRIVILEGES;
```

### 3. Configurar `application.properties`

Edita `src/main/resources/application.properties`:

```properties
spring.datasource.url=jdbc:mysql://localhost:3306/vetcare_db?createDatabaseIfNotExist=true
spring.datasource.username=root
spring.datasource.password=TU_PASSWORD_AQUI
```

### 4. Ejecutar el Proyecto

```bash
# Clonar o descargar el proyecto
cd vetcare

# Compilar
mvn clean install

# Ejecutar
mvn spring-boot:run
```

O desde tu IDE: **Run VetcareApplication.java**

### 5. Verificar
La aplicación estará disponible en: `http://localhost:8080`

## 📌 Endpoints API

### 🏥 Clinics (Clínicas)

| Método | Endpoint | Descripción |
|--------|----------|-------------|
| GET | `/api/clinics` | Listar todas las clínicas activas |
| GET | `/api/clinics/{id}` | Obtener clínica por ID |
| GET | `/api/clinics/city/{city}` | Buscar clínicas por ciudad |
| POST | `/api/clinics` | Crear nueva clínica |
| PUT | `/api/clinics/{id}` | Actualizar clínica |
| DELETE | `/api/clinics/{id}` | Eliminar clínica |
| PATCH | `/api/clinics/{id}/deactivate` | Desactivar clínica |

### 👥 Owners (Dueños)

| Método | Endpoint | Descripción |
|--------|----------|-------------|
| GET | `/api/owners` | Listar todos los dueños |
| GET | `/api/owners/{id}` | Obtener dueño por ID |
| GET | `/api/owners/{id}/pets` | Obtener dueño con sus mascotas |
| GET | `/api/owners/search?name={name}` | Buscar dueños por nombre |
| POST | `/api/owners` | Crear nuevo dueño |
| PUT | `/api/owners/{id}` | Actualizar dueño |
| DELETE | `/api/owners/{id}` | Eliminar dueño |
| PATCH | `/api/owners/{id}/deactivate` | Desactivar dueño |

### 🐕 Pets (Mascotas)

| Método | Endpoint | Descripción |
|--------|----------|-------------|
| GET | `/api/pets` | Listar todas las mascotas |
| GET | `/api/pets/{id}` | Obtener mascota por ID |
| GET | `/api/pets/owner/{ownerId}` | Mascotas de un dueño |
| GET | `/api/pets/search?name={name}` | Buscar mascotas por nombre |
| POST | `/api/pets?ownerId={id}&petTypeId={id}` | Crear nueva mascota |
| PUT | `/api/pets/{id}` | Actualizar mascota |
| DELETE | `/api/pets/{id}` | Eliminar mascota |
| PATCH | `/api/pets/{id}/deactivate` | Desactivar mascota |

### 🏷️ Pet Types (Tipos de Mascota)

| Método | Endpoint | Descripción |
|--------|----------|-------------|
| GET | `/api/pet-types` | Listar todos los tipos |
| GET | `/api/pet-types/{id}` | Obtener tipo por ID |
| POST | `/api/pet-types` | Crear nuevo tipo |
| PUT | `/api/pet-types/{id}` | Actualizar tipo |
| DELETE | `/api/pet-types/{id}` | Eliminar tipo |

### 👨‍⚕️ Veterinarians (Veterinarios)

| Método | Endpoint | Descripción |
|--------|----------|-------------|
| GET | `/api/veterinarians` | Listar veterinarios |
| GET | `/api/veterinarians/{id}` | Obtener veterinario por ID |
| GET | `/api/veterinarians/specialty/{id}` | Veterinarios por especialidad |
| POST | `/api/veterinarians` | Crear veterinario |
| POST | `/api/veterinarians/{vetId}/specialties/{specId}` | Agregar especialidad |
| DELETE | `/api/veterinarians/{vetId}/specialties/{specId}` | Quitar especialidad |
| PUT | `/api/veterinarians/{id}` | Actualizar veterinario |
| DELETE | `/api/veterinarians/{id}` | Eliminar veterinario |

### 🎯 Specialties (Especialidades)

| Método | Endpoint | Descripción |
|--------|----------|-------------|
| GET | `/api/specialties` | Listar especialidades |
| GET | `/api/specialties/{id}` | Obtener especialidad por ID |
| POST | `/api/specialties` | Crear especialidad |
| PUT | `/api/specialties/{id}` | Actualizar especialidad |
| DELETE | `/api/specialties/{id}` | Eliminar especialidad |

### 📅 Visits (Visitas)

| Método | Endpoint | Descripción |
|--------|----------|-------------|
| GET | `/api/visits` | Listar todas las visitas |
| GET | `/api/visits/{id}` | Obtener visita por ID |
| GET | `/api/visits/pet/{petId}` | Visitas de una mascota |
| GET | `/api/visits/veterinarian/{vetId}` | Visitas de un veterinario |
| GET | `/api/visits/date-range?startDate=...&endDate=...` | Visitas por rango de fechas |
| POST | `/api/visits?petId={id}&veterinarianId={id}&clinicId={id}` | Crear visita |
| PUT | `/api/visits/{id}` | Actualizar visita |
| PATCH | `/api/visits/{id}/status?status=COMPLETED` | Actualizar estado |
| DELETE | `/api/visits/{id}` | Eliminar visita |

## 🧪 Ejemplos de Uso con cURL

### Crear un dueño
```bash
curl -X POST http://localhost:8080/api/owners \
-H "Content-Type: application/json" \
-d '{
  "firstName": "Pedro",
  "lastName": "González",
  "documentNumber": "5566778899",
  "email": "pedro.gonzalez@email.com",
  "phone": "+57 300-9998877",
  "address": "Calle 80 #30-40",
  "city": "Bogotá"
}'
```

### Crear una mascota
```bash
curl -X POST "http://localhost:8080/api/pets?ownerId=1&petTypeId=1" \
-H "Content-Type: application/json" \
-d '{
  "name": "Toby",
  "birthDate": "2021-06-15",
  "breed": "Labrador",
  "gender": "MALE",
  "color": "Negro",
  "weight": 28.5
}'
```

### Listar todas las mascotas
```bash
curl http://localhost:8080/api/owners
```

### Crear una visita
```bash
curl -X POST "http://localhost:8080/api/visits?petId=1&veterinarianId=1&clinicId=1" \
-H "Content-Type: application/json" \
-d '{
  "visitDate": "2025-10-30T10:00:00",
  "reason": "Consulta de rutina",
  "status": "SCHEDULED"
}'
```

## 📊 Datos Iniciales

El sistema incluye `DataInitializer` que carga automáticamente:
- 5 tipos de mascotas (Perro, Gato, Ave, Conejo, Hámster)
- 6 especialidades veterinarias
- 3 clínicas en diferentes ciudades
- 3 dueños de ejemplo
- 3 veterinarios con especialidades
- 4 mascotas
- 4 visitas (pasadas y futuras)

## 🔧 Próximos Pasos

### Etapa 2: Swagger y DTOs
- Agregar Swagger UI para documentación interactiva
- Implementar DTOs con MapStruct
- Agregar validaciones avanzadas

### Etapa 3: Seguridad
- Implementar Spring Security
- Autenticación JWT
- Roles y permisos (ADMIN, VET, OWNER)

### Etapa 4: Funcionalidades Avanzadas
- Generación de PDFs con historial médico
- Integración con Supabase para imágenes
- Sistema de notificaciones

### Etapa 5: Frontend
- Desarrollar frontend con Angular + Tailwind
- Aplicación móvil con Flutter

## 📝 Notas Importantes

- El sistema usa `@JsonIgnoreProperties` para evitar recursión infinita en relaciones
- Todas las entidades tienen el campo `active` para soft delete
- Las fechas se manejan con `LocalDateTime` y `LocalDate`
- Los logs están configurados para mostrar SQL en consola

## 🐛 Solución de Problemas

### Error de conexión a MySQL
- Verifica que MySQL esté ejecutándose
- Confirma usuario y contraseña en `application.properties`
- Asegúrate que el puerto 3306 esté disponible

### Error de Lombok
- Habilita el procesamiento de anotaciones en tu IDE
- IntelliJ: Settings → Build → Compiler → Annotation Processors → Enable

### Puerto 8080 ocupado
- Cambiar puerto en `application.properties`: `server.port=8081`

## 📞 Soporte

Para dudas o problemas, revisa los logs en consola que muestran información detallada de las operaciones.

---

**¡Listo para comenzar! 🚀**